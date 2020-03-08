/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-07T17:53:39.986+08:00
 */

package jken.module.core.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jken.support.data.Lockedable;
import jken.support.data.jpa.CorpableEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "tbl_core_dict")
public class Dict extends CorpableEntity<User, Long> implements Lockedable {

    @NotNull
    @Size(max = 20)
    @Column(length = 31)
    private String name;

    @NotNull
    @Size(max = 20)
    @Column(length = 31)
    private String code;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 31)
    private ItemType type = ItemType.LIST_OPTION;

    @Size(max = 1000)
    @Column(length = 1023)
    private String remark;

    @OneToMany(mappedBy = "dict", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortNo asc")
    @JsonManagedReference
    private List<DictItem> items;

    private boolean locked = false;

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

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public List<DictItem> getItems() {
        return items;
    }

    public void setItems(List<DictItem> items) {
        this.items = items;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public enum ItemType {
        LIST_OPTION,
        LIST_GROUP,
        TREE
    }
}
