/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-07T18:03:18.918+08:00
 */

package jken.module.core.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jken.support.data.jpa.TreeEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "tbl_core_dict_item")
public class DictItem extends TreeEntity<DictItem, User, Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dict_id")
    @JsonBackReference
    private Dict dict;

    @NotNull
    @Size(max = 60)
    @Column(length = 64)
    private String name;

    @NotNull
    @Size(max = 60)
    @Column(length = 64)
    private String value;

    public Dict.ItemType getType() {
        Dict.ItemType itemType = getDict().getType();
        if (Objects.equals(itemType, Dict.ItemType.LIST_GROUP) && getParent() != null) {
            itemType = Dict.ItemType.LIST_OPTION;
        }
        return itemType;
    }

    public Dict getDict() {
        return dict;
    }

    public void setDict(Dict dict) {
        this.dict = dict;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
