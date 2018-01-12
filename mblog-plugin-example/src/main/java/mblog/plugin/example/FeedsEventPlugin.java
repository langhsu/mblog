package mblog.plugin.example;

import mblog.core.event.FeedsEvent;
import mblog.core.hook.event.FeedsEventHook;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @author Beldon 2015/10/29
 */
@Component
public class FeedsEventPlugin implements FeedsEventHook.FeedsEventListener {
    private Logger log = Logger.getLogger(getClass());
    @Override
    public void onEvent(FeedsEvent event) {
        log.debug("插件发出来的消息:您又派发动态了");
    }
}
