package service;

import bean.Login;

public interface LoginService {
    /**
     * Retrieves the username associated with the given ID and password.
     *
     * @param ID       User ID
     * @param password User password
     * @return Username associated with the given ID and password
     */
    public String getUser(int ID, int password);
}