package jken.module.wechat.support;

import jken.module.wechat.handler.*;
import jken.module.wechat.repo.OfficialAccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OfficialAccountConfig {

    @Bean
    public WxMpServiceFactory wxMpServiceFactory(OfficialAccountRepository officialAccountRepository, LogHandler logHandler, NullHandler nullHandler, KfSessionHandler kfSessionHandler,
                                                 StoreCheckNotifyHandler storeCheckNotifyHandler, LocationHandler locationHandler, MenuHandler menuHandler, MsgHandler msgHandler,
                                                 UnsubscribeHandler unsubscribeHandler, SubscribeHandler subscribeHandler, ScanHandler scanHandler) {
        return new WxMpServiceFactory(officialAccountRepository, logHandler, nullHandler, kfSessionHandler, storeCheckNotifyHandler, locationHandler, menuHandler, msgHandler, unsubscribeHandler, subscribeHandler, scanHandler);
    }

}
