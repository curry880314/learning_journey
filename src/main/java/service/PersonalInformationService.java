package service;

import bean.PersonalInformation;

public interface PersonalInformationService {

    public PersonalInformation getInformation(String ID);
    public void executeUpdate(String ID,String PhoneNumber,String Email);

}
