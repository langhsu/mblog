package com.mtons.mblog.config;

import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.base.utils.Printer;
import com.mtons.mblog.modules.entity.Options;
import com.mtons.mblog.modules.service.ChannelService;
import com.mtons.mblog.modules.service.OptionsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;

/**
 * 加载配置信息到系统
 * @since 3.0
 */
@Order(2)
@Component
public class ContextStartup implements ApplicationRunner, ServletContextAware {
    @Autowired
    private OptionsService optionsService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private SiteOptions siteOptions;

    private ServletContext servletContext;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        Printer.info("initialization ...");
        reloadOptions(true);
        resetSiteConfig();
        resetChannels();
        Printer.info("OK, completed");
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void reloadOptions(boolean startup) {
        List<Options> options = optionsService.findAll();

        if (startup && CollectionUtils.isEmpty(options)) {
            try {
                Printer.info("init options...");
                Resource resource = new ClassPathResource("/config/db/db_mblog.sql");
                optionsService.initSettings(resource);
                options = optionsService.findAll();
            } catch (Exception e) {
                Printer.error("------------------------------------------------------------");
                Printer.error("-          ERROR: The database is not initialized          -");
                Printer.error("------------------------------------------------------------");
                Printer.error(e.getMessage(), e);
                System.exit(1);
            }
        }

        Map<String, String> map = siteOptions.getOptions();
        options.forEach(opt -> {
            if (StringUtils.isNoneBlank(opt.getKey(), opt.getValue())) {
                map.put(opt.getKey(), opt.getValue());
            }
        });
        servletContext.setAttribute("options", map);
    }

    public void resetChannels() {
        servletContext.setAttribute("channels", channelService.findAll(Consts.STATUS_NORMAL));
    }

    public void resetSiteConfig() {
        servletContext.setAttribute("site", siteOptions);
    }
}
