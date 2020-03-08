package jken.module.scheduler.support;

import com.google.common.base.MoreObjects;

import java.util.HashMap;

public class BooleanMap extends HashMap<String, Boolean> {

    @Override
    public Boolean get(Object key) {
        return MoreObjects.firstNonNull(super.get(key), false);
    }
}
