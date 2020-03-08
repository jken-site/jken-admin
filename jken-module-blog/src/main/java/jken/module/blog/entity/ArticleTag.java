/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-10T20:41:59.705+08:00
 */

package jken.module.blog.entity;

import jken.module.core.entity.User;
import jken.support.data.Corpable;
import jken.support.data.jpa.SortEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_blog_article_tag")
public class ArticleTag extends SortEntity<User, Long> implements Corpable {

    /**
     * 名称
     */
    @NotNull
    @Size(max = 20)
    @Column(length = 63)
    private String name;

    /**
     * 图标
     */
    @Size(max = 200)
    @Column(length = 255)
    private String icon;

    /**
     * 备注
     */
    @Size(max = 200)
    @Column(length = 255)
    private String memo;

    @Column(length = 100)
    private String corpCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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
