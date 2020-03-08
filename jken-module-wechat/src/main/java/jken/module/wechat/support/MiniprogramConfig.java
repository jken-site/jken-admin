package jken.module.wechat.support;

import jken.module.wechat.repo.MiniprogramRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MiniprogramConfig {

    @Bean
    public WxMaServiceFactory wxMaServiceFactory(MiniprogramRepository miniprogramRepository) {
        return new WxMaServiceFactory(miniprogramRepository);
    }

}
