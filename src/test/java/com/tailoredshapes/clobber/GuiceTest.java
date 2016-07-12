package com.tailoredshapes.clobber;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.google.inject.servlet.RequestScoped;
import com.tailoredshapes.clobber.model.Inventory;
import com.tailoredshapes.clobber.model.User;
import com.tailoredshapes.clobber.modules.*;
import com.tailoredshapes.clobber.modules.memory.InMemoryDBModule;
import com.tailoredshapes.clobber.modules.memory.InMemoryModule;
import com.tailoredshapes.clobber.modules.memory.UserRootInMemoryRepositoryModule;
import com.tailoredshapes.clobber.scopes.SimpleScope;

public class GuiceTest {

    public static Injector injector = Guice.createInjector(
            new InventoryServerModule("localhost", 5555),
            new InMemoryModule(),
            new InMemoryDBModule(),
            new UserRootInMemoryRepositoryModule(),
            new UserRootUrlBuilders(),
            new JSONModule(),
            new EncoderModule(),
            new RandomShitModule(),
            new Module() {

                @Override
                public void configure(Binder binder) {
                    SimpleScope requestScope = new SimpleScope();
                    binder.bindScope(RequestScoped.class, requestScope);
                    binder.bind(SimpleScope.class).toInstance(requestScope);
                    binder.bind(User.class).annotatedWith(Names.named("current_user"))
                            .toProvider(SimpleScope.<User>seededKeyProvider())
                            .in(RequestScoped.class);
                    binder.bind(Inventory.class)
                            .annotatedWith(Names.named("current_inventory"))
                            .toProvider(SimpleScope.<Inventory>seededKeyProvider())
                            .in(RequestScoped.class);
                }
            });
}
