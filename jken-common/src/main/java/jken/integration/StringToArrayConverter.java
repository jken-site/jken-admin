/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-10T18:24:29.648+08:00
 */

package jken.integration;

import com.google.common.collect.Iterables;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringToArrayConverter<T> extends AbstractSingleValueConverter {

    private Function<String, T> mapper;
    private Class<T> itemType;

    public StringToArrayConverter(Class<T> itemType, Function<String, T> mapper) {
        this.itemType = itemType;
        this.mapper = mapper;
    }


    @Override
    public boolean canConvert(Class type) {
        return type.isArray();
    }

    @Override
    public Object fromString(String str) {
        String[] values = str.split(",");
        return Iterables.toArray(Stream.of(values).map(mapper).collect(Collectors.toSet()), itemType);
    }
}
