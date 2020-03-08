/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.474+08:00
 */

package jken.module.core.entity;

import jken.support.data.Disabledable;
import jken.support.data.jpa.DataEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_core_corp")
public class Corp extends DataEntity<User, Long> implements Disabledable {

    @NotNull
    @Column(length = 100, nullable = false)
    private String name;

    @NotNull
    @Column(unique = true, length = 100, nullable = false)
    private String code;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(length = 200)
    private String logo;

    @Column(length = 200)
    private String website;

    @Column(length = 1000)
    private String introduction;

    private boolean disabled = false;

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public boolean isDisabled() {
        return disabled;
    }

    @Override
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public enum Status {
        NORMAL,
        ARREARAGE
    }
}
