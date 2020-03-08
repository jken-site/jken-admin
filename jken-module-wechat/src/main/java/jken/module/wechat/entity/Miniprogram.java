package jken.module.wechat.entity;

import jken.module.core.entity.User;
import jken.support.data.jpa.CorpableEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_wechat_miniprogram")
public class Miniprogram extends CorpableEntity<User, Long> {

    private String name;
    private String logo;
    private String description;

    private String appid;
    private String secret;
    private String token;
    private String aesKey;
    private String msgDataFormat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getMsgDataFormat() {
        return msgDataFormat;
    }

    public void setMsgDataFormat(String msgDataFormat) {
        this.msgDataFormat = msgDataFormat;
    }
}
