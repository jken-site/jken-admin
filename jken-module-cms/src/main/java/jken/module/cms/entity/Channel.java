package jken.module.cms.entity;

import jken.module.core.entity.User;
import jken.support.data.Corpable;
import jken.support.data.jpa.TreeEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_cms_channel")
public class Channel extends TreeEntity<Channel, User, Long> implements Corpable {

    /**
     * 访问路径
     */
    private String path;
    /**
     * 栏目描述
     */
    private String description;
    /**
     * 是否显示
     */
    private boolean display;
    /**
     * 是否静态化（默认为0）
     */
    private boolean staticChannel;
    /**
     * 栏目名称
     */
    private String name;
    /**
     * 外链
     */
    private String link;
    /**
     * 栏目页模板
     */
    private String tplPc;
    /**
     * 手机栏目页模板
     */
    private String tplMobile;
    /**
     * 外链是否新窗口打开 (0-否 1-是)
     */
    private boolean linkTarget;
    /**
     * 是否允许投稿（0-不允许 1-允许）
     */
    private boolean contribute;
    /**
     * 回收站标识
     */
    private boolean recycle;
    /**
     * 加入回收站时间
     */
    private Date recycleTime;
    /**
     * 是否已生成栏目静态化页面
     */
    private boolean hasStaticChannel;
    /**
     * 是否内容已全部静态化
     */
    private boolean hasStaticContent;

    private int viewSum;

    private Boolean operatingContribute = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public boolean isStaticChannel() {
        return staticChannel;
    }

    public void setStaticChannel(boolean staticChannel) {
        this.staticChannel = staticChannel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTplPc() {
        return tplPc;
    }

    public void setTplPc(String tplPc) {
        this.tplPc = tplPc;
    }

    public String getTplMobile() {
        return tplMobile;
    }

    public void setTplMobile(String tplMobile) {
        this.tplMobile = tplMobile;
    }

    public boolean isLinkTarget() {
        return linkTarget;
    }

    public void setLinkTarget(boolean linkTarget) {
        this.linkTarget = linkTarget;
    }

    public boolean isContribute() {
        return contribute;
    }

    public void setContribute(boolean contribute) {
        this.contribute = contribute;
    }

    public boolean isRecycle() {
        return recycle;
    }

    public void setRecycle(boolean recycle) {
        this.recycle = recycle;
    }

    public Date getRecycleTime() {
        return recycleTime;
    }

    public void setRecycleTime(Date recycleTime) {
        this.recycleTime = recycleTime;
    }

    public boolean isHasStaticChannel() {
        return hasStaticChannel;
    }

    public void setHasStaticChannel(boolean hasStaticChannel) {
        this.hasStaticChannel = hasStaticChannel;
    }

    public boolean isHasStaticContent() {
        return hasStaticContent;
    }

    public void setHasStaticContent(boolean hasStaticContent) {
        this.hasStaticContent = hasStaticContent;
    }

    public int getViewSum() {
        return viewSum;
    }

    public void setViewSum(int viewSum) {
        this.viewSum = viewSum;
    }

    public Boolean getOperatingContribute() {
        return operatingContribute;
    }

    public void setOperatingContribute(Boolean operatingContribute) {
        this.operatingContribute = operatingContribute;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
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

    @Column(length = 100)
    private String corpCode;

    @Override
    public String getCorpCode() {
        return corpCode;
    }

    @Override
    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }
}
