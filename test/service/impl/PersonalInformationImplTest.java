package service.impl;

import bean.PersonalInformation;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonalInformationImplTest {

    @Test
    public void getInformation() {
        String id = "2020213301";
        PersonalInformation personalInformation = new PersonalInformationImpl().getInformation(id);
        assertEquals("San Zhang",personalInformation.name);
    }

    @Test
    public void executeUpdate() {
        new PersonalInformationImpl().executeUpdate("2020213301","18659854563","1467859256@qq.com");
        PersonalInformation personalInformation = new PersonalInformationImpl().getInformation("2020213301");
        assertEquals("18659854563",personalInformation.phoneNumber);
        assertEquals("1467859256@qq.com",personalInformation.email);
    }
}