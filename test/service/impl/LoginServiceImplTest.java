package service.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceImplTest {

    @Test
    void getUser() {
        // Creating an instance of LoginServiceImpl
        LoginServiceImpl loginService = new LoginServiceImpl();

        // Calling the getUser method with the specified ID and password
        String line = loginService.getUser(2020213301, 12345);

        // Asserting that the returned username is as expected
        assertEquals("2020213301", line);
    }
}