/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.518+08:00
 */

package jken;

import jken.module.core.service.CorpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private CorpService corpService;

    private volatile boolean nonExecuted = true;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (nonExecuted) {
            synchronized (this) {
                if (nonExecuted) {
                    nonExecuted = false;

                    doInitialize();
                }
            }
        }
    }

    private void doInitialize() {
        if (corpService.count() == 0) {
            corpService.createNewCorp("广州当凌信息科技有限公司", "wl", "admin", "qwe123");
            corpService.createNewCorp("广州微禹信息科技有限公司", "wy", "admin", "qwe123");
            corpService.createNewCorp("广州XXXX有限公司", "demo", "admin", "qwe123");
        }
    }
}
