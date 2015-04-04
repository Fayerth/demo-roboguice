package com.seemingamusing.demo.roboguice.module;

import com.google.inject.AbstractModule;
import com.seemingamusing.demo.roboguice.provider.DataService;
import com.seemingamusing.demo.roboguice.provider.JsonAssetService;

/**
 * @author Eric Lee
 */
public class DataServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DataService.class).to(JsonAssetService.class);
    }
}