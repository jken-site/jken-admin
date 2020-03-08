/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-10T20:37:05.182+08:00
 */

package jken.module.blog.entity;

import jken.module.core.entity.User;
import jken.support.data.Corpable;
import jken.support.data.jpa.TreeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_blog_article_category")
public class ArticleCategory extends TreeEntity<ArticleCategory, User, Long> implements Corpable {

    /**
     * 名称
     */
    @NotNull
    @Size(max = 20)
    @Column(length = 63)
    private String name;

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

    @Column(length = 100)
    private String corpCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String getCorpCode() {
        return corpCode;
    }

    @Override
    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }
}
