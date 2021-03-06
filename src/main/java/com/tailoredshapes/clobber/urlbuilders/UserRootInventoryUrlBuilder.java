package com.tailoredshapes.clobber.urlbuilders;

import com.google.inject.servlet.RequestScoped;
import com.tailoredshapes.clobber.model.Inventory;
import com.tailoredshapes.clobber.model.User;

import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
public class UserRootInventoryUrlBuilder implements UrlBuilder<Inventory> {

    private final User currentUser;
    private final String protocol;
    private final String host;
    private final Integer port;

    @Inject
    public UserRootInventoryUrlBuilder(@Named("current_user") User currentUser,
                                       @Named("protocol") String protocol,
                                       @Named("host") String host,
                                       @Named("port") Integer port) {
        this.currentUser = currentUser;

        this.protocol = protocol;
        this.host = host;
        this.port = port;
    }

    @Override
    public String build(Inventory inventory) {
        return inventory.getId() != null ?
                String.format("%s://%s:%s/users/%s/%s/inventories/%s", protocol, host, port, currentUser.getName(), currentUser.getId(), inventory.getId())
                : null;

    }
}

