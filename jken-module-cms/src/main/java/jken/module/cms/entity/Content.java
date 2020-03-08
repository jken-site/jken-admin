package jken.module.cms.entity;

import jken.module.core.entity.User;
import jken.support.data.jpa.CorpableEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_cms_content")
public class Content extends CorpableEntity<User, Long> {

    //浏览设置（1-允许游客访问 2-登录后访问）
    public enum ViewControl {
        AllowVisitor,
        AfterLogon
    }

    //评论设置(1允许游客评论 2登录后评论 3不允许评论)
    public enum CommentControl {
        AllowVisitor,
        AfterLogon,
        None
    }

    //内容状态(1:草稿; 2-初稿 3:流转中; 4:已审核; 5:已发布; 6:退回; 7:下线; 8:归档; 9:暂存;10:驳回 )
    public enum Status {
        Draft, Turning, Audited, Released, Returned, Offline, Documented, Temporary, Reject
    }

    //创建方式（1:直接创建 2:投稿 3:站群推送 4:站群采集 5:复制 6:链接型引用 7:镜像型引用）8 外部采集
    public enum CreateType {
        Directly, Contribute
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    private Status status = Status.Draft;
    private CreateType createType = CreateType.Directly;
    private CommentControl commentControl = CommentControl.AllowVisitor;
    private ViewControl viewControl = ViewControl.AllowVisitor;

    /**
     * 内容标题
     */
    private String title;
    /**
     * 内容标题是否加粗
     */
    private boolean titleIsBold;
    /**
     * 内容标题的颜色
     */
    private String titleColor;
    /**
     * 简短标题
     */
    private String shortTitle;
    /**
     * 发布时间
     */
    private Date releaseTime;
    /**
     * 下线时间
     **/
    private Date offlineTime;
    /**
     * 是否编辑（0-否 1-是）
     **/
    private boolean edit;
    /**
     * 浏览量
     */
    private int views;
    /**
     * 评论量
     */
    private int comments;
    /**
     * 点赞数
     **/
    private int ups;
    /**
     * 点踩数
     **/
    private int downs;
    /**
     * 下载量
     */
    private int downloads;
    /**
     * 是否置顶
     **/
    private boolean top;
    /**
     * 置顶开始时间
     **/
    private Date topStartTime;
    /**
     * 置顶结束时间
     **/
    private Date topEndTime;
    /**
     * 是否发布至pc（0-否 1-是）
     **/
    private boolean releasePc;
    /**
     * 是否发布至wap（0-否 1-是）
     **/
    private boolean releaseWap;
    /**
     * 是否发布至app（0-否 1-是）
     **/
    private boolean releaseApp;
    /**
     * 是否发布至小程序（0-否 1-是）
     **/
    private boolean releaseMiniprogram;
    /**
     * 是否加入回收站（0-否 1-是）
     **/
    private boolean recycle;
    /**
     * 是否已生成静态化页面
     */
    private boolean hasStatic;

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

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public CreateType getCreateType() {
        return createType;
    }

    public void setCreateType(CreateType createType) {
        this.createType = createType;
    }

    public CommentControl getCommentControl() {
        return commentControl;
    }

    public void setCommentControl(CommentControl commentControl) {
        this.commentControl = commentControl;
    }

    public ViewControl getViewControl() {
        return viewControl;
    }

    public void setViewControl(ViewControl viewControl) {
        this.viewControl = viewControl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isTitleIsBold() {
        return titleIsBold;
    }

    public void setTitleIsBold(boolean titleIsBold) {
        this.titleIsBold = titleIsBold;
    }

    public String getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Date getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(Date offlineTime) {
        this.offlineTime = offlineTime;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getUps() {
        return ups;
    }

    public void setUps(int ups) {
        this.ups = ups;
    }

    public int getDowns() {
        return downs;
    }

    public void setDowns(int downs) {
        this.downs = downs;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public Date getTopStartTime() {
        return topStartTime;
    }

    public void setTopStartTime(Date topStartTime) {
        this.topStartTime = topStartTime;
    }

    public Date getTopEndTime() {
        return topEndTime;
    }

    public void setTopEndTime(Date topEndTime) {
        this.topEndTime = topEndTime;
    }

    public boolean isReleasePc() {
        return releasePc;
    }

    public void setReleasePc(boolean releasePc) {
        this.releasePc = releasePc;
    }

    public boolean isReleaseWap() {
        return releaseWap;
    }

    public void setReleaseWap(boolean releaseWap) {
        this.releaseWap = releaseWap;
    }

    public boolean isReleaseApp() {
        return releaseApp;
    }

    public void setReleaseApp(boolean releaseApp) {
        this.releaseApp = releaseApp;
    }

    public boolean isReleaseMiniprogram() {
        return releaseMiniprogram;
    }

    public void setReleaseMiniprogram(boolean releaseMiniprogram) {
        this.releaseMiniprogram = releaseMiniprogram;
    }

    public boolean isRecycle() {
        return recycle;
    }

    public void setRecycle(boolean recycle) {
        this.recycle = recycle;
    }

    public boolean isHasStatic() {
        return hasStatic;
    }

    public void setHasStatic(boolean hasStatic) {
        this.hasStatic = hasStatic;
    }

}
