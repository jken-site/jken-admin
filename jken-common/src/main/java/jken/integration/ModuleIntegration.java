/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-03T20:13:33.752+08:00
 */

package jken.integration;

public interface ModuleIntegration {

    String getName();

    void integrate(JkenModule module);

}
