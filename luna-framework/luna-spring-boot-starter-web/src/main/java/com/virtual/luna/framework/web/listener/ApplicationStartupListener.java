package com.virtual.luna.framework.web.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(ApplicationStartupListener.class);
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("\n----------------------------------------------------------\n" +
                        "    Application '{}' is running! \n" +
                        "----------------------------------------------------------",
                event.getSpringApplication().getMainApplicationClass().getSimpleName());
    }
}

