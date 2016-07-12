package com.tailoredshapes.clobber.model;

import com.tailoredshapes.clobber.model.builders.UserBuilder;
import org.junit.Test;

import java.security.interfaces.RSAPublicKey;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void shouldOverrideEqualsForHibernate() throws Exception {
        User bobby = new UserBuilder().name("Bobby").build();
        User alice = new UserBuilder().name("Alice").build();
        User bobby2 = bobby.shallowCopy().setId(bobby.getId());

        assertTrue(bobby.equals(bobby2));
        assertFalse(bobby.equals(alice));
    }

    @Test
    public void shouldGenerateAUsefulString() throws Exception {
        User bobby = new UserBuilder().name("Bobby").build();

        assertEquals(
                "User{id=555, name='Bobby', inventories=[]}", bobby.toString());
    }
}