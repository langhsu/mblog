package mblog.core.hook.event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Event钩子管理器
 *
 * @author Beldon 2015/10/29
 */
@Component("eventHookManager")
public class EventHookManager implements ApplicationListener<ApplicationEvent> {
	@Autowired
	private ApplicationContext applicationContext;
	
	private Map<Class, Set<EventHook>> eventHookMap = new HashMap<>();

    /**
     * 初始化，获取所有继承EventHook接口的类，并添加到eventHookMap中管理
     */
    @PostConstruct
    public void init() {
        Map<String, EventHook> map = applicationContext.getBeansOfType(EventHook.class);
        Iterator<Map.Entry<String, EventHook>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, EventHook> entry = it.next();
            EventHook eventHook = entry.getValue();
            addEventHook(eventHook.getEventClass(), eventHook);
        }
    }

    /**
     * Event触发，该方法会优先EventHandler，
     * <p>
     * 如FeedsEvent,若有管理FeedsEvent的钩子存在，则先执行钩子程序，再执行如FeedsEventHandler
     * 在子钩子中，可以优先管理FeedsEvent。
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        Class eventKey = event.getClass();
        if (eventHookMap.containsKey(eventKey)) {
            Set<EventHook> hooks = eventHookMap.get(eventKey);
            for (EventHook hook : hooks) {
                hook.onApplicationEvent(event);
            }
        }
    }


    /**
     * 新增一个Event钩子
     *
     * @param clazz Event class用来做可一类钩子的key，如FeedsEvent
     * @param hook  钩子
     */
    public void addEventHook(Class clazz, EventHook hook) {
        if (!this.eventHookMap.containsKey(clazz)) {
            Set<EventHook> hooks = new HashSet<>();
            hooks.add(hook);
            this.eventHookMap.put(clazz, hooks);
        } else {
            this.eventHookMap.get(clazz).add(hook);
        }

    }

    /**
     * 删除一个Event钩子
     *
     * @param hook 钩子
     */
    public void removeHook(EventHook hook) {
        Iterator<Map.Entry<Class, Set<EventHook>>> it = eventHookMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Class, Set<EventHook>> entry = it.next();
            entry.getValue().remove(hook);
        }
    }
}
