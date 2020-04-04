package fudan.se.lab5.entity;

import fudan.se.lab5.constant.UserInfoConstant;
import fudan.se.lab5.entity.User;

public class AccountAssistant {
    private static User userOnline = null;
    private static UserInfoConstant infoConstant;
    private AccountAssistant(){ }

    public static int getUserOnlineID(){
        if(userOnline == null){
            throw new RuntimeException("Please login first");
        }
        return userOnline.getId();
    }

    public static void setUserOnline(User user){
        userOnline = user;
    }
}
