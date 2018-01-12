package mblog.core.hook.event;

import mblog.core.event.NotifyEvent;
import org.springframework.stereotype.Component;


/**
 * 处理LogEvent钩子
 *
 * @author Beldon 2015/10/29
 */
@Component
public class NotifyEventHook extends EventHookSupport<NotifyEvent> {
    @Override
    public void init() {
        this.plugins = getPlugins(NotifyEventListener.class);
    }

    @Override
    public void onApplicationEvent(NotifyEvent event) {
    }

    public interface NotifyEventListener extends EventListener<NotifyEvent> {

    }

}
