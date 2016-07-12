package com.tailoredshapes.clobber.extractors;

import com.google.inject.servlet.RequestScoped;
import com.sun.net.httpserver.HttpExchange;
import com.tailoredshapes.clobber.model.User;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequestScoped
public class UserNameExtractor implements IdExtractor<String, User> {

    private final Pattern userIdPattern = Pattern.compile("/users/(\\w+)/?.*$");

    @Override
    public String extract(HttpExchange exchange) {
        URI requestURI = exchange.getRequestURI();
        String path = requestURI.getPath();
        return extract(path);
    }

    @Override
    public String extract(String path) {
        Matcher matcher = userIdPattern.matcher(path);
        String id = null;
        if (matcher.matches()) {
            id = matcher.group(1);
        }

        return id;
    }
}
