package com.tailoredshapes.clobber.modules;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.tailoredshapes.clobber.dao.*;
import com.tailoredshapes.clobber.extractors.IdExtractor;
import com.tailoredshapes.clobber.extractors.InventoryIdExtractor;
import com.tailoredshapes.clobber.extractors.UserIdExtractor;
import com.tailoredshapes.clobber.extractors.UserNameExtractor;
import com.tailoredshapes.clobber.model.Inventory;
import com.tailoredshapes.clobber.model.Metric;
import com.tailoredshapes.clobber.model.MetricType;
import com.tailoredshapes.clobber.model.User;
import com.tailoredshapes.clobber.parsers.Parser;
import com.tailoredshapes.clobber.parsers.UserParser;
import com.tailoredshapes.clobber.repositories.InventoryCategoryPredicate;
import com.tailoredshapes.clobber.security.RSA;
import com.tailoredshapes.clobber.validators.InventoryValidator;
import com.tailoredshapes.clobber.validators.UserValidator;
import com.tailoredshapes.clobber.validators.Validator;

import java.util.function.Predicate;

public class RandomShitModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(new TypeLiteral<Parser<User>>() {})
                .to(UserParser.class);

        binder.bind(new TypeLiteral<IdExtractor<Long, User>>() {})
                .to(UserIdExtractor.class);

        binder.bind(new TypeLiteral<IdExtractor<String, User>>() {})
                .to(UserNameExtractor.class);

        binder.bind(new TypeLiteral<IdExtractor<Long, Inventory>>() {})
                .to(InventoryIdExtractor.class);

        binder.bind(new TypeLiteral<Saver<User>>() {})
                .to(new TypeLiteral<UserSaver<RSA>>() {});

        binder.bind(new TypeLiteral<Saver<Inventory>>() {})
                .to(InventorySaver.class);

        binder.bind(new TypeLiteral<Saver<Metric>>() {})
                .to(MetricSaver.class);

        binder.bind(new TypeLiteral<Saver<MetricType>>() {})
                .to(new TypeLiteral<ChildFreeSaver<MetricType>>() {});

        binder.bind(new TypeLiteral<Predicate<Inventory>>() {})
                .to(InventoryCategoryPredicate.class);

        binder.bind(new TypeLiteral<Validator<User>>() {})
                .to(UserValidator.class);

        binder.bind(new TypeLiteral<Validator<Inventory>>() {})
                .to(InventoryValidator.class);
    }
}
