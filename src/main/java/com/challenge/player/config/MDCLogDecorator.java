package com.challenge.player.config;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

public class MDCLogDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(final Runnable runnable) {
        final Map<String, String> mdcMap = MDC.getCopyOfContextMap();
        return () -> {
            try {
                MDC.setContextMap(mdcMap);
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}
