package service;

import bean.PersonalInformation;


/**
 * The PersonalInformationService interface provides operations to manage personal information.
 */
public interface PersonalInformationService {
    /**
     * Get the personal information for the provided ID.
     *
     * @param ID the ID of the user
     * @return the PersonalInformation object associated with the ID, or null if not found
     */
    PersonalInformation getInformation(String ID);

    /**
     * Updates the personal information for the provided ID with the given phone number and email.
     *
     * @param ID          the ID of the user
     * @param PhoneNumber the new phone number to update
     * @param Email       the new email to update
     */
    void executeUpdate(String ID, String PhoneNumber, String Email);
}

