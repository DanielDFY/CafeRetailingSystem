package fudan.se.lab5.service.impl;

import fudan.se.lab5.constant.UserInfoConstant;
import fudan.se.lab5.constant.UserInfoConstantImpl.UserInfoConstantChinese;
import fudan.se.lab5.constant.UserInfoConstantImpl.UserInfoConstantEnglish;
import fudan.se.lab5.entity.AccountAssistant;
import fudan.se.lab5.entity.User;
import fudan.se.lab5.repository.UserRepository;
import fudan.se.lab5.repository.impl.UserRepositoryImpl;
import fudan.se.lab5.service.AccountService;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static fudan.se.lab5.util.LogUtil.orderLogger;
import static fudan.se.lab5.util.LogUtil.sysLogger;


public class AccountServiceImpl implements AccountService {
    private boolean status;
    private UserInfoConstant infoConstant;

    public AccountServiceImpl() {
        this.status = false;
        infoConstant = new UserInfoConstantEnglish();
    }

    public void setLanguage(String language) {
        switch (language) {
            case "English" :
                this.infoConstant = new UserInfoConstantEnglish();
                break;
            case "Chinese" :
                this.infoConstant = new UserInfoConstantChinese();
                break;
                default:
                    throw new RuntimeException(infoConstant.getUnknownLanguage());
        }
    }

    @Override
    public boolean signup(User user) {
        try {
            if (null == user)
                throw new RuntimeException(infoConstant.getNullUser());
            UserRepository userRepository = new UserRepositoryImpl();
            userRepository.createUser(user);
            orderLogger.info(MessageFormat.format(infoConstant.getSuccessToSignup(), user.getName()));
            return true;
        } catch (RuntimeException e) {
            sysLogger.error(e.getMessage());
            throw new RuntimeException(MessageFormat.format(infoConstant.getFailedToSignupReason(), e.getMessage()));
        }
    }

    @Override
    public boolean login(User user) {
        UserRepository userRepository = new UserRepositoryImpl();
        User targetUser;
        try {
            if (null == user)
                throw new RuntimeException(infoConstant.getNullUser());
            targetUser = userRepository.getUser(user.getName());
            if (user.getPassword().equals(targetUser.getPassword())) {
                status = true;
                AccountAssistant.setUserOnline(user);
                orderLogger.info(MessageFormat.format(infoConstant.getLoginSuccess(), user.getName()));
                return true;
            } else {
                throw new RuntimeException(infoConstant.getUsernamePassMismatch());
            }
        } catch (RuntimeException e) {
            sysLogger.error(e.getMessage());
            throw new RuntimeException(MessageFormat.format(infoConstant.getFailedToLoginReason(), e.getMessage()));
        }
    }

    @Override
    public boolean checkStatus() {
        if (status) {
            orderLogger.info(infoConstant.getLoginSuccess());
        } else {
            sysLogger.error(infoConstant.getLoginFail());
        }
        return status;
    }

    public boolean checkName(String name) {
        Pattern p;
        Matcher m;
        if (null == name) {
            sysLogger.error(infoConstant.getNullName());
            return false;
        } else {
            p = Pattern.compile("[a-zA-Z0-9_]{8,49}");
            m = p.matcher(name);
            if (m.matches() && name.substring(0, 7).equals("starbb_")) {
                orderLogger.info(infoConstant.getValidName());
                return true;
            } else if (m.matches() && !name.substring(0, 7).equals("starbb_")) {
                sysLogger.error(infoConstant.getInvalidNameWrongBegin());
                return false;
            } else if (name.length() < 8 || name.length() > 49) {
                sysLogger.error(infoConstant.getInvalidNameLength());
                return false;
            } else{
                sysLogger.error(infoConstant.getInvalidNameHasOtherWords());
                return false;
            }
        }
    }
    public boolean checkPassword(String password){
        Pattern p,p1,p2,p3;
        Matcher m,m1,m2,m3;
        if (null == password) {
            sysLogger.error(infoConstant.getNullPass());
            return false;
        } else {
            p = Pattern.compile("^(?![0-9_]+$)(?![a-zA-Z0-9]+$)(?![a-zA-Z_]+$)[0-9A-Za-z_]{8,99}$");
            p1 = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,99}$");
            p2 = Pattern.compile("^(?![0-9]+$)(?![_]+$)[0-9_]{8,99}$");
            p3 = Pattern.compile("^(?![_]+$)(?![a-zA-Z]+$)[A-Za-z_]{8,99}$");
            m = p.matcher(password);
            m1 = p1.matcher(password);
            m2 = p2.matcher(password);
            m3 = p3.matcher(password);
            if ( m.matches()){
                orderLogger.info(infoConstant.getValidPass());
                return true;
            } else if(password.length() < 8 || password.length() > 99) {
                sysLogger.error(infoConstant.getInvalidPassLength());
                return false;
            } else if(m1.matches()) {
                sysLogger.error(infoConstant.getInvalidPassMissing());
                return false;
            } else if(m2.matches()) {
                sysLogger.error(infoConstant.getInvalidPassMissingLetter());
                return false;
            } else if(m3.matches()) {
                sysLogger.error(infoConstant.getInvalidPassMissingNumber());
                return false;
            } else {
                sysLogger.error(infoConstant.getInvalidPassIllegalCharacter());
                return false;
            }
        }
    }
}