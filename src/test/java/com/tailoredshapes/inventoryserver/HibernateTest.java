package com.tailoredshapes.inventoryserver;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.RequestScoped;
import com.tailoredshapes.inventoryserver.model.User;
import com.tailoredshapes.inventoryserver.scopes.SimpleScope;

public class HibernateTest {
    public static Injector hibernateInjector;

    static {
        hibernateInjector = Guice.createInjector(
                new JpaPersistModule("inventory_server"),
                new InventoryServerModule("localhost", 5555),
                new HibernateModule(),
                new JSONModule(),
                new RSAModule(),
                new Module() {

                    @Override
                    public void configure(Binder binder) {
                        SimpleScope requestScope = new SimpleScope();
                        binder.bindScope(RequestScoped.class, requestScope);
                        binder.bind(SimpleScope.class).toInstance(requestScope);
                        binder.bind(User.class)
                                .toProvider(SimpleScope.<User>seededKeyProvider())
                                .in(RequestScoped.class);
                    }
                });

        PersistService instance = hibernateInjector.getInstance(PersistService.class);
        instance.start();

    }
}
