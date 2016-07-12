package com.tailoredshapes.clobber.modules;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.tailoredshapes.clobber.model.Inventory;
import com.tailoredshapes.clobber.model.User;
import com.tailoredshapes.clobber.urlbuilders.UrlBuilder;
import com.tailoredshapes.clobber.urlbuilders.UserRootInventoryUrlBuilder;
import com.tailoredshapes.clobber.urlbuilders.UserUrlBuilder;

public class UserRootUrlBuilders implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(new TypeLiteral<UrlBuilder<User>>() {})
                .to(UserUrlBuilder.class);

        binder.bind(new TypeLiteral<UrlBuilder<Inventory>>() {})
                .to(UserRootInventoryUrlBuilder.class);
    }
}

