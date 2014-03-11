package com.tailoredshapes.inventoryserver.urlbuilders;

import com.google.inject.Inject;
import com.tailoredshapes.inventoryserver.model.User;

import java.net.MalformedURLException;
import java.net.URL;

public class UserUrlBuilder implements UrlBuilder<User> {


    private final String protocol;
    private final String host;
    private final int port;

    @Inject
    public UserUrlBuilder(String protocol, String host, int port) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
    }

    @Override
    public String build(User user) {
        try {
            return new URL(protocol, host, port, String.format("/users/" + user.getId())).toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
