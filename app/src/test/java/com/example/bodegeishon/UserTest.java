package com.example.bodegeishon;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testUserCreationAndGetters() {
        User user = new User(1, "admin", "admin123", "Administrador", true, "Ángel Administrador");

        assertEquals(1, user.getId());
        assertEquals("admin", user.getUsername());
        assertEquals("admin123", user.getPassword());
        assertEquals("Administrador", user.getRole());
        assertTrue(user.isActivo());
        assertEquals("Ángel Administrador", user.getNombre());
    }

    @Test
    public void testInactiveUserStatus() {
        User user = new User(2, "inactivo", "user123", "Operador", false, "Pedro Inactivo");

        assertFalse(user.isActivo());
    }
}
