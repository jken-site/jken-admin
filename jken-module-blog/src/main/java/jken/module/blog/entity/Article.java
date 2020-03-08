/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-10T20:40:49.509+08:00
 */

package jken.module.blog.entity;

import jken.module.core.entity.User;
import jken.support.data.jpa.CorpableEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_blog_article")
public class Article extends CorpableEntity<User, Long> {

    /**
     * 标题
     */
    @NotNull
    @Size(max = 100)
    @Column(length = 127)
    private String title;

    /**
     * 作者
     */
    @Size(max = 100)
    @Column(length = 127)
    private String author;

    /**
     * 内容
     */
    @Lob
    private String content;

    /**
     * 页面标题
     */
    @Size(max = 200)
    @Column(length = 255)
    private String seoTitle;

    /**
     * 页面关键词
     */
    @Size(max = 200)
    @Column(length = 255)
    private String seoKeywords;

    /**
     * 页面描述
     */
    @Size(max = 200)
    @Column(length = 255)
    private String seoDescription;

    /**
     * 是否发布
     */
    private boolean published;

    /**
     * 是否置顶
     */
    private boolean top;

    /**
     * 点击数
     */
    private long hits;

    /**
     * 文章分类
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ArticleCategory category;

    /**
     * 标签
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tbl_blog_article_to_tag",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    @OrderBy("order asc")
    private Set<ArticleTag> articleTags = new HashSet<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getSeoKeywords() {
        return seoKeywords;
    }

    public void setSeoKeywords(String seoKeywords) {
        this.seoKeywords = seoKeywords;
    }

    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public long getHits() {
        return hits;
    }

    public void setHits(long hits) {
        this.hits = hits;
    }

    public ArticleCategory getCategory() {
        return category;
    }

    public void setCategory(ArticleCategory category) {
        this.category = category;
    }

    public Set<ArticleTag> getArticleTags() {
        return articleTags;
    }

    public void setArticleTags(Set<ArticleTag> articleTags) {
        this.articleTags = articleTags;
    }
}
