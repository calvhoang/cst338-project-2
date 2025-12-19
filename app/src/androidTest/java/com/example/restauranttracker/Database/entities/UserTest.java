package com.example.restauranttracker.Database.entities;

import junit.framework.TestCase;

import org.junit.Test;

public class UserTest extends TestCase {

    User user;

    private int id;
    private String username = "testName";
    private String password = "testPassword";
    private boolean isAdmin = true;

    @Override
    public void setUp() {
        user = new User(username,password);
    }

    public void testGetId() {
        assertEquals(user.getId(), id);
    }

    public void testSetId() {
        user.setId(4);
        assertEquals(4, user.getId());
    }

    public void testGetUsername() {
        assertEquals(user.getUsername(),username);
    }

    public void testSetUsername() {
        user.setUsername("test");
        assertEquals("test", user.getUsername());
    }

    public void testGetPassword() {
        assertEquals(user.getPassword(), password);
    }

    public void testSetPassword() {
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    public void testIsAdmin() {
        user.setAdmin(true);
        assertTrue(user.isAdmin());
    }

    public void testSetAdmin() {
        user.setAdmin(false);
        assertFalse(user.isAdmin());
    }

    public void testTestEquals() {
        User user1 = new User("exampleName", "examplePassword");
        User user2 = new User("exampleName", "examplePassword");
        assertEquals(user1, user2);
    }

    public void testTestHashCode() {
        User user1 = new User("exampleName", "examplePassword");
        User user2 = new User("exampleName", "examplePassword");
        assertEquals(user1.hashCode(), user2.hashCode());
    }
}