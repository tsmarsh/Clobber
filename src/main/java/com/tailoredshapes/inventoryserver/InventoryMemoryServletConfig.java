package com.tailoredshapes.inventoryserver;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tailoredshapes.inventoryserver.modules.memory.InMemoryDBModule;

public class InventoryMemoryServletConfig {

    protected static final Injector injector = Guice.createInjector(
            new InMemoryDBModule()
    );
}

