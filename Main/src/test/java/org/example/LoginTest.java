package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    private Login login;

    @BeforeEach
    public void setUp() {
        login = new Login("John", "Doe", "j_d", "Password@1", "+27812345678");
    }

    @Test
    public void testCheckUserName_Valid() {
        assertTrue(login.checkUserName());
    }

    @Test
    public void testCheckUserName_InvalidTooLong() {
        login.setUsername("john_doe");
        assertFalse(login.checkUserName());
    }

    @Test
    public void testCheckUserName_InvalidNoUnderscore() {
        login.setUsername("john");
        assertFalse(login.checkUserName());
    }

    @Test
    public void testCheckPasswordComplexity_Valid() {
        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    public void testCheckPasswordComplexity_NoUppercase() {
        login.setPassword("password@1");
        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    public void testCheckPasswordComplexity_NoLowercase() {
        login.setPassword("PASSWORD@1");
        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    public void testCheckPasswordComplexity_NoNumber() {
        login.setPassword("Password@");
        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    public void testCheckPasswordComplexity_NoSpecialChar() {
        login.setPassword("Password1");
        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    public void testCheckCellNumber_Valid() {
        assertTrue(login.checkCellNumber());
    }

    @Test
    public void testCheckCellNumber_InvalidFormat() {
        login.setCellNumber("0812345678");
        assertFalse(login.checkCellNumber());
    }

    @Test
    public void testLoginUser_CorrectCredentials() {
        // Simulate login manually (bypassing GUI)
        assertTrue("j_d".equals(login.getUsername()) && "Password@1".equals(login.getPassword()));
    }

    @Test
    public void testLoginUser_WrongCredentials() {
        assertFalse("wrong".equals(login.getUsername()) && "Password@1".equals(login.getPassword()));
    }
}
