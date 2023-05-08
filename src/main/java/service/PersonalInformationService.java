package service;

import bean.PersonalInformation;

/**
 * 个人信息的服务层
 */
public interface PersonalInformationService {

    public PersonalInformation getInformation(String ID);
    public void executeUpdate(String ID,String PhoneNumber,String Email);

}
