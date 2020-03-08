/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.441+08:00
 */

package jken.support.data;

public interface Lockedable {
    boolean isLocked();

    void setLocked(boolean locked);
}
