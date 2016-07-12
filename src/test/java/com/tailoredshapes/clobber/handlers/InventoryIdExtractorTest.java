package com.tailoredshapes.clobber.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.tailoredshapes.clobber.extractors.InventoryIdExtractor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InventoryIdExtractorTest {

    @Mock
    private HttpExchange exchange;

    @Test
    public void testExtract() throws Exception {
        URI uri = new URI("http://testdomain.com:80/users/Punka/-333/inventories/666");
        when(exchange.getRequestURI()).thenReturn(uri);
        InventoryIdExtractor inventoryIdExtractor = new InventoryIdExtractor();
        Long extract = inventoryIdExtractor.extract(exchange);
        assertEquals(666l, extract.longValue());
    }

    @Test
    public void testExtractWithNegativeId() throws Exception {
        URI uri = new URI("http://testdomain.com:80/users/Punka/333/inventories/-666");
        when(exchange.getRequestURI()).thenReturn(uri);
        InventoryIdExtractor inventoryIdExtractor = new InventoryIdExtractor();
        Long extract = inventoryIdExtractor.extract(exchange);
        assertEquals(-666l, extract.longValue());
    }
}
