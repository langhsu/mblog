package mblog.boot;

import mblog.base.context.AppContext;
import mblog.base.lang.Consts;
import mblog.base.print.Printer;
import mblog.core.data.Config;
import mblog.core.persist.service.ChannelService;
import mblog.core.persist.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.*;

/**
 * 加载配置信息到系统
 *
 */
@Component
public class ContextInitRunner implements ApplicationRunner, Ordered, ServletContextAware {
    @Autowired
    private ConfigService configService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private AppContext appContext;

    private ServletContext servletContext;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        Timer timer = new Timer("init config");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Printer.info("站点信息初始化...");

                List<Config> configs = configService.findAll();
                Map<String, String> configMap = new HashMap<>();

                if (configs.isEmpty()) {
                    Printer.error("--------------------------------------------------");
                    Printer.error("- 系统启动失败,原因: 没有导入初始化数据(db_mblog.sql) -");
                    Printer.error("-             导入初始数据库再来启动吧               -");
                    Printer.error("--------------------------------------------------");
                    System.exit(1);
                } else {

                    if (configs.size() < 13) {
                        Printer.error("--------------------------------------------------");
                        Printer.error("-  系统异常,原因: 初始数据不全, 请重导(db_mblog.sql)  -");
                        Printer.error("--------------------------------------------------");
                    }
                    configs.forEach(conf -> {
//						servletContext.setAttribute(conf.getKey(), conf.getValue());
                        configMap.put(conf.getKey(), conf.getValue());
                    });
                }

                appContext.setServletContext(servletContext);
                appContext.setConfig(configMap);

                servletContext.setAttribute("channels", channelService.findAll(Consts.STATUS_NORMAL));

                Printer.info("OK, mblog 加载完了");
            }
        }, 1 * Consts.TIME_MIN);
    }

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
