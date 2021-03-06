package com.tailoredshapes.clobber.handlers;

import com.tailoredshapes.clobber.model.User;
import com.tailoredshapes.clobber.model.builders.UserBuilder;
import com.tailoredshapes.clobber.urlbuilders.UserUrlBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserUrlBuilderTest {
    @Test
    public void testBuild() throws Exception {
        UserUrlBuilder builder = new UserUrlBuilder("http", "tailoredshapes.com", 80);
        User user = new UserBuilder().name("Cassie").id(39291l).build();
        assertEquals("http://tailoredshapes.com:80/users/Cassie/39291", builder.build(user));
    }

    @Test
    public void shouldReturnNullIfIdIsNull() throws Exception {
        UserUrlBuilder builder = new UserUrlBuilder("http", "tailoredshapes.com", 80);
        User user = new UserBuilder().id(null).build();
        assertNull(builder.build(user));
    }
}
