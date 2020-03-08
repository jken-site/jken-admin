package jken.module.cms.entity;

import jken.module.core.entity.User;
import jken.support.data.LogicDeleteable;
import jken.support.data.Sortable;
import jken.support.data.jpa.CorpableEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "tbl_cms_station")
public class Station extends CorpableEntity<User, Long> implements LogicDeleteable, Sortable {

    /**
     * 站点描述
     **/
    private String description;
    /**
     * 站点favicon
     **/
    private String favicon;
    /**
     * seo描述
     **/
    private String seoDescription;
    /**
     * seo关键字
     **/
    private String seoKeywords;
    /**
     * seo标题
     **/
    private String seoTitle;
    /**
     * 域名
     **/
    private String domain;
    /**
     * 域名别名
     **/
    private String domainAlias;
    /**
     * 语言
     **/
    private String siteLanguage;
    /**
     * 站点名称
     **/
    private String name;
    /**
     * 资源目录
     **/
    private String path;
    /**
     * 访问协议'
     **/
    private String protocol;
    /**
     * 站点状态（0未发布1已发布）
     **/
    private boolean isOpen;
    /**
     * pc首页模板
     **/
    private String pcIndexTpl;
    /**
     * 手机首页模板
     **/
    private String mobileIndexTpl;

    @OneToMany(mappedBy = "station")
    private List<Channel> channels;

    private boolean deleted;
    private Integer sortNo;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
    }

    public String getSeoKeywords() {
        return seoKeywords;
    }

    public void setSeoKeywords(String seoKeywords) {
        this.seoKeywords = seoKeywords;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDomainAlias() {
        return domainAlias;
    }

    public void setDomainAlias(String domainAlias) {
        this.domainAlias = domainAlias;
    }

    public String getSiteLanguage() {
        return siteLanguage;
    }

    public void setSiteLanguage(String siteLanguage) {
        this.siteLanguage = siteLanguage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getPcIndexTpl() {
        return pcIndexTpl;
    }

    public void setPcIndexTpl(String pcIndexTpl) {
        this.pcIndexTpl = pcIndexTpl;
    }

    public String getMobileIndexTpl() {
        return mobileIndexTpl;
    }

    public void setMobileIndexTpl(String mobileIndexTpl) {
        this.mobileIndexTpl = mobileIndexTpl;
    }

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public Integer getSortNo() {
        return sortNo;
    }

    @Override
    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }
}
