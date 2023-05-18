package service.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceImplTest {

    @Test
    void getUser() {
        LoginServiceImpl loginService=new LoginServiceImpl();
        String line=loginService.getUser(2020213301,12345);

        assertEquals("2020213301",line);
    }
}