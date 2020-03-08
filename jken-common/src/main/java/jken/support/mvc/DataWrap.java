/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-03T20:13:33.753+08:00
 */

package jken.support.mvc;

public class DataWrap {

    private Object data;

    public DataWrap() {
    }

    public DataWrap(Object data) {
        this.data = data;
    }

    public static DataWrap of(Object data) {
        return new DataWrap(data);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
