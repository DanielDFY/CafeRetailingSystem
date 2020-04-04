package fudan.se.lab5.service;

import fudan.se.lab5.entity.User;

public interface AccountService {

    boolean login(User user);

    boolean signup(User user);

    /**
     * Check the login status, you can maintain this status in environment variable.
     *
     * @return if user has already login, return true, else return false.
     */
    boolean checkStatus();

    /**
     * Check whether the given name is valid
     * @para name the given name to check
     * @return whether the name is valid
     */
    boolean checkName(String name);

    /**
     * Check whether the given password is valid
     * @para password the given password to check
     * @return whether the password is valid
     */
    boolean checkPassword(String password);
}
