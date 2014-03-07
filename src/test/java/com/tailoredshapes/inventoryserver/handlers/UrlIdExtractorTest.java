package com.tailoredshapes.inventoryserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.tailoredshapes.inventoryserver.extractors.UrlIdExtractor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UrlIdExtractorTest {

    @Mock
    HttpExchange exchange;


    @Test
    public void testExtract() throws Exception {
        String path = "http://localhost:80/users/555/inventories/666";

        URI uri = new URI(path);
        when(exchange.getRequestURI()).thenReturn(uri);

        UrlIdExtractor urlIdExtractor = new UrlIdExtractor();
        Long extract = urlIdExtractor.extract(exchange);
        assertEquals(new Long(555l), extract);
    }

    @Test
    public void testExtractNegativeId() throws Exception {
        String path = "http://localhost:80/users/-555/inventories/666";

        URI uri = new URI(path);
        when(exchange.getRequestURI()).thenReturn(uri);

        UrlIdExtractor urlIdExtractor = new UrlIdExtractor();
        Long extract = urlIdExtractor.extract(exchange);
        assertEquals(new Long(-555l), extract);
    }
}
