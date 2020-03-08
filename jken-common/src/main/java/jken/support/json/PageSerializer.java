/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.448+08:00
 */

package jken.support.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Persistable;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class PageSerializer<P extends Page<T>, T extends Persistable<I>, I extends Serializable> extends JsonSerializer<P> {

    public static final String DEFAULT_TOTAL_FIELD = "total";
    public static final String DEFAULT_CURRENT_FIELD = "current";
    public static final String DEFAULT_ROWCOUNT_FIELD = "rowCount";
    public static final String DEFAULT_ROWS_FIELD = "rows";

    @Override
    public void serialize(P ts, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField(DEFAULT_CURRENT_FIELD, ts.getNumber() + 1);
        jsonGenerator.writeNumberField(DEFAULT_ROWCOUNT_FIELD, ts.getSize());
        jsonGenerator.writeFieldName(DEFAULT_ROWS_FIELD);
        serializerProvider.findValueSerializer(List.class, null).serialize(ts.getContent(), jsonGenerator, serializerProvider);
        jsonGenerator.writeNumberField(DEFAULT_TOTAL_FIELD, ts.getTotalElements());
        jsonGenerator.writeEndObject();
    }
}
