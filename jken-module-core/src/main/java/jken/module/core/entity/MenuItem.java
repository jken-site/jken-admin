/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-03T20:13:33.759+08:00
 */

package jken.module.core.entity;

import jken.support.data.Corpable;
import jken.support.data.Lockedable;
import jken.support.data.jpa.TreeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_core_menu_item")
public class MenuItem extends TreeEntity<MenuItem, User, Long> implements Corpable, Lockedable {

    @NotNull
    @Size(max = 20)
    @Column(length = 63)
    private String name;

    @NotNull
    @Size(max = 20)
    @Column(length = 31)
    private String code;

    @Size(max = 200)
    @Column(length = 255)
    private String href;

    @Size(max = 60)
    @Column(length = 64)
    private String iconCls;

    @Size(max = 500)
    @Column(length = 512)
    private String authorities;

    private boolean locked = false;

    @Column(length = 100)
    private String corpCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
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
