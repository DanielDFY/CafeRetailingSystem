package fudan.se.lab5.repository.impl;

import fudan.se.lab5.constant.FileConstant;
import fudan.se.lab5.constant.SystemInfoConstant;
import fudan.se.lab5.entity.User;
import fudan.se.lab5.repository.UserRepository;
import fudan.se.lab5.util.FileUtil;

import java.text.MessageFormat;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public void createUser(User user) { FileUtil.write(objectToStringArray(user), FileConstant.USER_CSV); }

    @Override
    public User getUser(String name) {
        return stringArrayToObject(FileUtil.readByName(name, FileConstant.USER_CSV));
    }

    private String[] objectToStringArray(User user) {
        // if user already exists, throw exception
        if (FileUtil.exist(user.getName(), FileConstant.USER_CSV)) {
            throw new RuntimeException(MessageFormat.format(SystemInfoConstant.Entity_EXIST, "User",
                    user.getName()));
        }
        String[] array = new String[3];
        array[0] = user.getName();
        array[1] = user.getPassword();
        array[2] = String.valueOf(User.count++);
        return array;
    }

    private User stringArrayToObject(String[] array) {
        User user = new User();
        user.setName(array[0]);
        user.setPassword(array[1]);
        user.setId(Integer.parseInt(array[2]));
        return user;
    }
}
