/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-05T19:35:47.652+08:00
 */

package jken.module.core.entity;

import jken.support.data.Lockedable;
import jken.support.data.jpa.CorpableEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tbl_core_role")
public class Role extends CorpableEntity<User, Long> implements Lockedable {

    @NotNull
    @Size(max = 20)
    @Column(length = 63)
    private String name;

    @NotNull
    @Size(max = 20)
    @Column(length = 31)
    private String code;

    @Size(max = 1000)
    @Column(length = 1023)
    private String description;

    private boolean locked = false;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tbl_core_role_menu",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id"))
    private List<MenuItem> menuItems;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "tbl_core_role_authority")
    private List<String> authorities;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
