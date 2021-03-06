package com.tailoredshapes.clobber.modules;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.tailoredshapes.clobber.model.*;
import com.tailoredshapes.clobber.responders.JSONListResponder;
import com.tailoredshapes.clobber.responders.JSONResponder;
import com.tailoredshapes.clobber.responders.Responder;
import com.tailoredshapes.clobber.serialisers.*;

import java.util.Collection;

public class JSONModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(new TypeLiteral<Serialiser<Inventory, byte[]>>() {})
                .to(InventorySerialiser.class);

        binder.bind(new TypeLiteral<Serialiser<Inventory, String>>() {})
                .to(InventoryStringSerialiser.class);

        binder.bind(new TypeLiteral<Serialiser<Category, byte[]>>() {})
                .to(new TypeLiteral<CategorySerialiser>() {});

        binder.bind(new TypeLiteral<Serialiser<Category, String>>() {})
                .to(new TypeLiteral<CategoryStringSerialiser>() {});

        binder.bind(new TypeLiteral<Serialiser<User, byte[]>>() {})
                .to(UserSerialiser.class);

        binder.bind(new TypeLiteral<Serialiser<User, String>>() {})
                .to(UserStringSerialiser.class);

        binder.bind(new TypeLiteral<Serialiser<Metric, byte[]>>() {})
                .to(MetricSerialiser.class);

        binder.bind(new TypeLiteral<Serialiser<Metric, String>>() {})
                .to(MetricStringSerialiser.class);

        binder.bind(new TypeLiteral<Serialiser<MetricType, byte[]>>() {})
                .to(new TypeLiteral<MetricTypeSerialiser>() {});

        binder.bind(new TypeLiteral<Serialiser<MetricType, String>>() {})
                .to(new TypeLiteral<MetricTypeStringSerialiser>() {});

        binder.bind(new TypeLiteral<Responder<Inventory>>() {})
                .to(new TypeLiteral<JSONResponder<Inventory>>() {});

        binder.bind(new TypeLiteral<Responder<User>>() {})
                .to(new TypeLiteral<JSONResponder<User>>() {});
    }

    @Provides
    Responder<Collection<User>> userCollectionResponderProvider(Serialiser<User, String> serialiser) {
        return new JSONListResponder<>(serialiser, "users");
    }

    @Provides
    Responder<Collection<Inventory>> inventoryCollectionResponderProvider(Serialiser<Inventory, String> serialiser) {
        return new JSONListResponder<>(serialiser, "inventories");
    }
}
