package com.tailoredshapes.clobber.urlbuilders;

import com.google.inject.name.Named;
import com.tailoredshapes.clobber.model.User;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.net.URL;

public class UserUrlBuilder implements UrlBuilder<User> {


    private final String protocol;
    private final String host;
    private final int port;

    @Inject
    public UserUrlBuilder(@Named("protocol") String protocol,
                          @Named("host") String host,
                          @Named("port") int port) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
    }

    @Override
    public String build(User user) {
        try {

            return user.getId() != null ?
                    new URL(protocol, host, port, String.format("/users/%s/%d", user.getName(), user.getId())).toString()
                    : null;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
