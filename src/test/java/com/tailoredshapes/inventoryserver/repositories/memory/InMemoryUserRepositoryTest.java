package com.tailoredshapes.inventoryserver.repositories.memory;

import com.tailoredshapes.inventoryserver.dao.Encoder;
import com.tailoredshapes.inventoryserver.dao.InMemoryUserDAO;
import com.tailoredshapes.inventoryserver.dao.Serialiser;
import com.tailoredshapes.inventoryserver.dao.UserDAO;
import com.tailoredshapes.inventoryserver.model.User;
import com.tailoredshapes.inventoryserver.utils.KeyProvider;
import com.tailoredshapes.inventoryserver.utils.TestAlgorithm;
import org.junit.Before;
import org.junit.Test;

import java.security.KeyPair;

import static org.junit.Assert.assertEquals;

public class InMemoryUserRepositoryTest {

    Long testId = 1l;
    private byte[] testSerializedUser = new byte[0];

    private Serialiser<User> serialiser = new Serialiser<User>() {
        @Override
        public byte[] serialise(User object) {
            return testSerializedUser;
        }
    };

    private Encoder<TestAlgorithm> encoder = new Encoder() {
        @Override
        public Long encode(User user, byte[] bits) {
            return testId;
        }
    };

    private KeyProvider<TestAlgorithm> keyprovider = new KeyProvider<TestAlgorithm>() {
        @Override
        public KeyPair generate() {
            return new KeyPair(null, null);
        }
    };

    private UserDAO dao;
    private User storedUser;

    @Before
    public void setUp() throws Exception {
        dao = new InMemoryUserDAO(serialiser, encoder, keyprovider);
        storedUser = dao.create(new User());

    }

    @Test
    public void shouldFindUserById() throws Exception {
        InMemoryUserRepository inMemoryUserDAO = new InMemoryUserRepository(dao);

        User byId = inMemoryUserDAO.findById(1l);
        assertEquals(storedUser, byId);
    }
}
