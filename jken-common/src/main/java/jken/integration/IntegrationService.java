/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-03T20:13:33.750+08:00
 */

package jken.integration;

import com.google.common.collect.Lists;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.*;

public class IntegrationService {

    private static final List<JkenModule> modules = new ArrayList<>();
    private final static Set<Authority> authorities = new HashSet<>();
    private final static Set<String> ignorePatterns = new HashSet<>();

    static {

        XStream xStream = new XStream(new StaxDriver());
        xStream.alias("module", JkenModule.class);
        xStream.useAttributeFor(JkenModule.class, "name");
        xStream.useAttributeFor(JkenModule.class, "ignorePatterns");
        xStream.registerLocalConverter(JkenModule.class, "ignorePatterns", new StringToArrayConverter<>(String.class, i -> i));
        xStream.useAttributeFor(JkenModule.class, "sortNo");

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
        xStream.registerLocalConverter(Authority.class, "patterns", new StringToArrayConverter<>(String.class, i -> i.trim()));
        xStream.registerLocalConverter(Authority.class, "patternType", new StringToArrayConverter<>(Authority.PatternType.class, i -> Authority.PatternType.valueOf(i.trim())));
        xStream.registerLocalConverter(Authority.class, "httpMethods", new StringToArrayConverter<>(HttpMethod.class, i -> HttpMethod.valueOf(i.trim())));

        xStream.alias("mi", JkenModule.Mi.class);
        xStream.useAttributeFor(JkenModule.Mi.class, "name");
        xStream.useAttributeFor(JkenModule.Mi.class, "code");
        xStream.useAttributeFor(JkenModule.Mi.class, "href");
        xStream.useAttributeFor(JkenModule.Mi.class, "iconCls");
        xStream.useAttributeFor(JkenModule.Mi.class, "authorities");
        xStream.addImplicitCollection(JkenModule.Mi.class, "children", JkenModule.Mi.class);

        xStream.alias("dict", JkenModule.Dict.class);
        xStream.useAttributeFor(JkenModule.Dict.class, "name");
        xStream.useAttributeFor(JkenModule.Dict.class, "code");
        xStream.addImplicitCollection(JkenModule.Dict.class, "items", JkenModule.Dict.Item.class);

        xStream.alias("item", JkenModule.Dict.Item.class);
        xStream.useAttributeFor(JkenModule.Dict.Item.class, "name");
        xStream.useAttributeFor(JkenModule.Dict.Item.class, "value");

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] moduleResources = resourcePatternResolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "jk-module-*.xml");
            Set<String> sets = new HashSet<>();
            for (Resource resource : moduleResources) {
                JkenModule module = (JkenModule) xStream.fromXML(resource.getInputStream());
                if (sets.contains(module.getName())) {
                    throw new RuntimeException("exist module code");
                }
                sets.add(module.getName());
                modules.add(module);
            }
            sets.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ServiceLoader<ModuleIntegration> serviceLoader = ServiceLoader.load(ModuleIntegration.class);
        List<ModuleIntegration> integrations = Lists.newArrayList(serviceLoader);
        AnnotationAwareOrderComparator.sort(integrations);

        for (ModuleIntegration integration : integrations) {
            String name = integration.getName();
            JkenModule module = findModule(modules, name);
            if (module == null) {
                module = new JkenModule();
                module.setName(name);
                modules.add(module);
            }
            integration.integrate(module);
        }

        AnnotationAwareOrderComparator.sort(modules);

        for (JkenModule module : modules) {
            if (module.getIgnorePatterns() != null && module.getIgnorePatterns().length > 0) {
                Collections.addAll(ignorePatterns, module.getIgnorePatterns());
            }

            if (module.getDomains() != null) {
                for (JkenModule.Domain domain : module.getDomains()) {
                    if (domain.isCrud()) {
                        String namePrefix = domain.getName();
                        String codePrefix = domain.getCode();
                        String patternPrefix = "/" + domain.getCode();
                        authorities.add(authority(namePrefix + "列表", codePrefix + "-list", new String[]{patternPrefix + ""}, Authority.PatternType.ANT, HttpMethod.GET));
                        authorities.add(authority(namePrefix + "查看", codePrefix + "-view", new String[]{patternPrefix + "/*"}, Authority.PatternType.ANT, HttpMethod.GET));
                        authorities.add(authority(namePrefix + "新增", codePrefix + "-add", new String[]{patternPrefix + "/*"}, Authority.PatternType.ANT, HttpMethod.POST));
                        authorities.add(authority(namePrefix + "修改", codePrefix + "-edit", new String[]{patternPrefix + "/*"}, Authority.PatternType.ANT, HttpMethod.PUT));
                        authorities.add(authority(namePrefix + "删除", codePrefix + "-delete", new String[]{patternPrefix + ""}, Authority.PatternType.ANT, HttpMethod.DELETE));
                    }
                    if (domain.getAuthorities() != null) {
                        for (Authority authority : domain.getAuthorities()) {
                            authorities.add(authority);
                        }
                    }
                }
            }
        }
    }

    public static List<JkenModule> getModules() {
        return modules;
    }

    public static Set<Authority> getAuthorities() {
        return authorities;
    }

    public static Set<String> getIgnorePatterns() {
        return ignorePatterns;
    }

    private static JkenModule findModule(List<JkenModule> list, String name) {
        for (JkenModule module : list) {
            if (Objects.equals(module.getName(), name)) {
                return module;
            }
        }
        return null;
    }

    private static Authority authority(String name, String code, String[] patterns, Authority.PatternType patternType, HttpMethod... httpMethods) {
        Authority authority = new Authority();
        authority.setName(name);
        authority.setCode(code);
        authority.setPatterns(patterns);
        authority.setPatternType(patternType);
        authority.setHttpMethods(httpMethods);
        return authority;
    }
}
