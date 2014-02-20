package com.tailoredshapes.inventoryserver.repositories;

import com.tailoredshapes.inventoryserver.dao.Encoder;
import com.tailoredshapes.inventoryserver.dao.InMemoryUserDAO;
import com.tailoredshapes.inventoryserver.dao.Serialiser;
import com.tailoredshapes.inventoryserver.dao.UserDAO;
import com.tailoredshapes.inventoryserver.model.User;
import org.junit.Before;
import org.junit.Test;

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

    private Encoder encoder = new Encoder() {
        @Override
        public Long encode(User user, byte[] bits) {
            return testId;
        }
    };

    private UserDAO dao;
    private User storedUser;

    @Before
    public void setUp() throws Exception {
        dao = new InMemoryUserDAO(serialiser, encoder);
        storedUser = dao.create(new User());

    }

    @Test
    public void shouldFindUserById() throws Exception {
        InMemoryUserRepository inMemoryUserDAO = new InMemoryUserRepository(dao);

        User byId = inMemoryUserDAO.findById(1l);
        assertEquals(storedUser, byId);
    }
}