/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-06T17:22:17.044+08:00
 */

package jken.module.core.entity;

import jken.support.data.jpa.DataEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_core_message")
public class Message extends DataEntity<User, Long> {

    @NotNull
    @Size(max = 100)
    @Column(length = 255)
    private String title;

    @Size(max = 1000)
    @Column(length = 2047)
    private String content;

    @ManyToOne
    private User from;

    @ManyToOne
    private User to;

    @Enumerated(EnumType.STRING)
    private Type type;

    private boolean readed = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isReaded() {
        return readed;
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
    }

    public enum Type {
        NOTIFICATION,
        LETTER
    }
}
