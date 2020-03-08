/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-04T21:29:01.399+08:00
 */

package jken.support.json.pathfilter;

import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * This is just a help for a start.
 */
public class Jackson2Helper {
    /**
     * Build the FilterProvider for the given filters
     *
     * @param filters The filters to be user
     * @return The configured FilterProvider
     */
    public static SimpleFilterProvider buildFilterProvider(final String... filters) {
        return new SimpleFilterProvider().addFilter("antPathFilter", new AntPathPropertyFilter(filters));
    }
}