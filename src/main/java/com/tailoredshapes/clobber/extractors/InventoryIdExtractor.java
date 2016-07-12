package com.tailoredshapes.clobber.extractors;

import com.sun.net.httpserver.HttpExchange;
import com.tailoredshapes.clobber.model.Inventory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InventoryIdExtractor implements IdExtractor<Long, Inventory> {

    private final Pattern userIdPattern = Pattern.compile("^.*/inventories/?(-?\\d+)");


    @Override
    public Long extract(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        return extract(path);
    }

    @Override
    public Long extract(String path) {
        Matcher matcher = userIdPattern.matcher(path);
        if (matcher.matches()) {
            return Long.parseLong(matcher.group(1));
        } else {
            return null;
        }
    }
}

