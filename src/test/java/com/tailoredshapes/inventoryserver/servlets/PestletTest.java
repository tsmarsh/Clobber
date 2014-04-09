package com.tailoredshapes.inventoryserver.servlets;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PestletTest {

    @Test
    public void testCanHandleInventoryRequestsInHibernate() throws Exception {
        int port = 7777;

        final Server server = new Server(port);
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/");

        webAppContext.setWar(this.getClass().getResource("/").getPath());
        server.setHandler(webAppContext);
        server.start();

        try {
            testCanCreateAnInventory(port);
        } finally {
            server.stop();
        }
    }

    public void testCanCreateAnInventory(Integer port) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //CREATE USER

        JSONObject userJsonObject = new JSONObject();
        userJsonObject.put("name", "Archer");

        HttpPost userPost = new HttpPost(new URI(String.format("http://localhost:%d/users", port)));
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("user", userJsonObject.toString()));
        userPost.setEntity(new UrlEncodedFormEntity(parameters));
        HttpResponse userReponse = httpClient.execute(userPost);
        Header location = userReponse.getFirstHeader("Location");
        String userUrl = location.getValue();

        //READ USER
        HttpGet userGet = new HttpGet(userUrl);
        HttpResponse response = httpClient.execute(userGet);
        String userResponseString = EntityUtils.toString(response.getEntity());
        userGet.releaseConnection();

        JSONObject readUser = new JSONObject(userResponseString);
        assertEquals("Archer", readUser.getString("name"));
        assertNotNull(readUser.getString("publicKey"));

        //CREATE

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("category", "com.tailoredshapes.test");
        jsonObject.put("user", userUrl);

        HttpPost httpPost = new HttpPost(new URI(String.format("%s/inventories", userUrl)));
        parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("inventory", jsonObject.toString()));
        httpPost.setEntity(new UrlEncodedFormEntity(parameters));


        HttpResponse createInventoryResponse = httpClient.execute(httpPost);
        assertEquals(302, createInventoryResponse.getStatusLine().getStatusCode());
        String inventoryLocation = createInventoryResponse.getFirstHeader("Location").getValue();

        httpPost.releaseConnection();

        //READ

        URI uri = new URI(inventoryLocation);
        assertTrue(uri.getPath().matches("/users/?-?\\d+/inventories(/-?\\d+)?"));
        HttpGet httpGet = new HttpGet(uri);

        HttpResponse readResponse = httpClient.execute(httpGet);
        assertEquals(200, readResponse.getStatusLine().getStatusCode());

        String inventoryJsonString = EntityUtils.toString(readResponse.getEntity());

        httpGet.releaseConnection();

        JSONObject getResponseObject = new JSONObject(inventoryJsonString);
        assertEquals(inventoryLocation, getResponseObject.getString("id"));
        assertEquals("com.tailoredshapes.test", getResponseObject.getString("category"));
        assertEquals(0, getResponseObject.getJSONArray("metrics").length());


        //Update
        parameters = new ArrayList<>();
        HttpPost updatePost = new HttpPost(uri);

        JSONObject updatedObject = new JSONObject(getResponseObject.toString());
        JSONArray metrics = updatedObject.getJSONArray("metrics");

        JSONObject metricJson = new JSONObject();
        metricJson.put("value", "Cassie");
        metricJson.put("type", "string");

        metrics.put(metricJson);

        parameters.add(new BasicNameValuePair("inventory", updatedObject.toString()));

        updatePost.setEntity(new UrlEncodedFormEntity(parameters));

        HttpResponse updateResponse = httpClient.execute(updatePost);
        assertEquals(302, updateResponse.getStatusLine().getStatusCode());
        assertTrue(updateResponse.containsHeader("Location"));
        String updateLocation = updateResponse.getFirstHeader("Location").getValue();
        assertNotSame(inventoryLocation, updateLocation);

        updatePost.releaseConnection();

        //READ

        HttpGet updateGet = new HttpGet(new URI(updateLocation));
        HttpResponse updatedResponse = httpClient.execute(updateGet);

        assertEquals(200, updatedResponse.getStatusLine().getStatusCode());

        String updatedInventoryString = EntityUtils.toString(updatedResponse.getEntity());

        updateGet.releaseConnection();

        JSONObject updatedResponseObject = new JSONObject(updatedInventoryString);
        assertEquals(updateLocation, updatedResponseObject.getString("id"));
        assertEquals("com.tailoredshapes.test", updatedResponseObject.getString("category"));
        assertEquals(1, updatedResponseObject.getJSONArray("metrics").length());
    }
}

