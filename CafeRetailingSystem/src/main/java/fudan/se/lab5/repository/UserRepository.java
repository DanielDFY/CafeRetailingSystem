package fudan.se.lab5.repository;

import fudan.se.lab5.entity.User;

public interface UserRepository {
    /**
     * persist user in data/user.csv
     *
     * @param user
     */
    void createUser(User user);

    /**
     * get User by name in data/user.csv
     *
     * @param name
     * @return user
     */
    User getUser(String name);
}
