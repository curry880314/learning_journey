package service.impl;

import bean.PersonalInformation;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Junit tests for the {@link PersonalInformationImpl} class.
 */
public class PersonalInformationImplTest {

    /**
     * Test case for the {@link PersonalInformationImpl#getInformation(String)} method.
     */
    @Test
    public void getInformation() {
        String id = "2020213301";
        PersonalInformation personalInformation = new PersonalInformationImpl().getInformation(id);
        assertEquals("San Zhang", personalInformation.name);
    }

    /**
     * Test case for the {@link PersonalInformationImpl#executeUpdate(String, String, String)} method.
     */
    @Test
    public void executeUpdate() {
        new PersonalInformationImpl().executeUpdate("2020213301", "18659854563", "1467859256@qq.com");
        PersonalInformation personalInformation = new PersonalInformationImpl().getInformation("2020213301");
        assertEquals("18659854563", personalInformation.phoneNumber);
        assertEquals("1467859256@qq.com", personalInformation.email);
    }
}
