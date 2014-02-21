package com.tailoredshapes.inventoryserver.dao;

import com.tailoredshapes.inventoryserver.model.User;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class RSAEncoderTest {

    User user;
    byte[] bits;

    @Before
    public void setUp() throws Exception{
        KeyPairGenerator rsa1024 = KeyPairGenerator.getInstance("RSA");
        rsa1024.initialize(1024);
        KeyPair keyPair = rsa1024.generateKeyPair();
        user = new User().setId(1412l).setName("Archer").setPrivateKey(keyPair.getPrivate()).setPublicKey(keyPair.getPublic());
        bits = "Archer".getBytes();
    }

    @Test
    public void testShouldEncodeBitsConsistently(){
        RSAEncoder rsaEncoder = new RSAEncoder();

        Long encode = rsaEncoder.encode(user, bits);
        Long encode2 = rsaEncoder.encode(user, bits);

        assertEquals(encode, encode2);
    }

    @Test
    public void testShouldProvideDifferentValuesForDifferentValues() throws Exception {
        RSAEncoder rsaEncoder = new RSAEncoder();

        Long encode = rsaEncoder.encode(user, bits);
        Long encode2 = rsaEncoder.encode(user, "Cassie".getBytes());

        assertFalse(encode.equals(encode2));
    }
}
