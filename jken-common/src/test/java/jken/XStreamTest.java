/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-09T20:58:49.611+08:00
 */

package jken;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import jken.integration.Authority;
import jken.integration.JkenModule;
import jken.integration.StringToArrayConverter;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;

import java.io.IOException;

public class XStreamTest {

    @Test
    public void testUnmarshalModule() {
        XStream xStream = new XStream(new StaxDriver());
        xStream.alias("module", JkenModule.class);

        xStream.alias("domain", JkenModule.Domain.class);
        xStream.useAttributeFor(JkenModule.Domain.class, "name");
        xStream.useAttributeFor(JkenModule.Domain.class, "code");
        xStream.useAttributeFor(JkenModule.Domain.class, "crud");
        xStream.addImplicitCollection(JkenModule.Domain.class, "authorities", Authority.class);

        xStream.alias("authority", Authority.class);
        xStream.useAttributeFor(Authority.class, "name");
        xStream.useAttributeFor(Authority.class, "code");
        xStream.useAttributeFor(Authority.class, "patterns");
        xStream.useAttributeFor(Authority.class, "patternType");
        xStream.useAttributeFor(Authority.class, "httpMethods");
        xStream.registerLocalConverter(Authority.class, "patterns", new StringToArrayConverter<>(String.class, i -> i));
        xStream.registerLocalConverter(Authority.class, "patternType", new StringToArrayConverter<>(Authority.PatternType.class, i -> Authority.PatternType.valueOf(i)));
        xStream.registerLocalConverter(Authority.class, "httpMethods", new StringToArrayConverter<>(HttpMethod.class, i -> HttpMethod.valueOf(i)));

        xStream.alias("mi", JkenModule.Mi.class);
        xStream.useAttributeFor(JkenModule.Mi.class, "name");
        xStream.useAttributeFor(JkenModule.Mi.class, "code");
        xStream.useAttributeFor(JkenModule.Mi.class, "href");
        xStream.useAttributeFor(JkenModule.Mi.class, "iconCls");
        xStream.useAttributeFor(JkenModule.Mi.class, "authorities");
        xStream.addImplicitCollection(JkenModule.Mi.class, "children", JkenModule.Mi.class);

        try {
            JkenModule module = (JkenModule) xStream.fromXML(new ClassPathResource("test-jk-module.xml").getInputStream());
            System.out.println(module.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
