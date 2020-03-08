package jken.module.wechat.support;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import jken.module.wechat.entity.Miniprogram;
import jken.module.wechat.repo.MiniprogramRepository;
import org.apache.commons.codec.binary.Base64;

import java.util.HashMap;
import java.util.Map;

public class WxMaServiceFactory {

    private final Map<String, WxMaService> SERVICES = new HashMap<>();

    private final MiniprogramRepository miniprogramRepository;

    public WxMaServiceFactory(MiniprogramRepository miniprogramRepository) {
        this.miniprogramRepository = miniprogramRepository;
    }

    public WxMaService get(String appid) {
        WxMaService service = SERVICES.get(appid);
        if (service == null) {
            synchronized (this) {
                if (service == null) {
                    service = buildService(appid);
                    if (service != null) {
                        SERVICES.put(appid, service);
                    }
                }
            }
        }
        return service;
    }

    protected WxMaService buildService(String appid) {
        Miniprogram setting = miniprogramRepository.findByAppid(appid);

        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(setting.getAppid());
        config.setSecret(setting.getSecret());
        config.setToken(setting.getToken());
        config.setAesKey(Base64.encodeBase64String(setting.getAesKey().getBytes()));
        config.setMsgDataFormat(setting.getMsgDataFormat());

        WxMaServiceImpl service = new WxMaServiceImpl();
        service.setWxMaConfig(config);
        return service;
    }
}
