package fudan.se.lab5;

import fudan.se.lab5.constant.SystemInfoConstant;
import fudan.se.lab5.constant.UserInfoConstantImpl.UserInfoConstantEnglish;
import fudan.se.lab5.entity.User;
import fudan.se.lab5.service.AccountService;
import fudan.se.lab5.service.impl.AccountServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.MessageFormat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestAccountService {
    private AccountService accountService;
    private UserInfoConstantEnglish userInfoConstantEnglish = new UserInfoConstantEnglish();

    @Before
    public void setUp() {
        accountService = new AccountServiceImpl();
    }

    @After
    public void tearDown() {
        accountService = null;
    }

    // Test signup()
    @Test
    public void testSignUpNullUser() {
        try {
            accountService.signup(null);
        } catch (RuntimeException e) {
            String expect = MessageFormat.format(userInfoConstantEnglish.getFailedToSignupReason(), userInfoConstantEnglish.getNullUser());
            Assert.assertEquals(e.getMessage(), expect);
        }
    }

    @Test
    public void testSignUpUserExists() {
        try {
            User user = new User();
            user.setName("starbb_test");
            user.setPassword("abc_1234");
            accountService.signup(user);
        } catch (RuntimeException e) {
            String expect = MessageFormat.format(userInfoConstantEnglish.getFailedToSignupReason(), MessageFormat.format(SystemInfoConstant.Entity_EXIST, "User", "starbb_test"));
            Assert.assertEquals(e.getMessage(), expect);
        }
    }

    @Test
    public void testSignUpSuccess() {
        User user = new User();
        user.setName("starbb_new");
        user.setPassword("abc_1234");
        Assert.assertTrue("signup error: failed to sign up for a valid user", accountService.signup(user));
    }

    // Test login()
    @Test
    public void testLoginNullUser() {
        try {
            accountService.login(null);
        } catch (RuntimeException e) {
            String expect = MessageFormat.format(userInfoConstantEnglish.getFailedToLoginReason(), userInfoConstantEnglish.getNullUser());
            Assert.assertEquals(e.getMessage(), expect);
        }
    }

    @Test
    public void testLoginUserNotExists() {
        try {
            User user = new User();
            user.setName("starbb_another_new");
            user.setPassword("abc_1234");
            accountService.login(user);
        } catch (RuntimeException e) {
            String expect = MessageFormat.format(userInfoConstantEnglish.getFailedToLoginReason(), MessageFormat.format(SystemInfoConstant.Entity_NOT_FOUND, "starbb_another_new"));
            Assert.assertEquals(e.getMessage(), expect);
        }
    }

    @Test
    public void testLoginUserPasswordWrong() {
        try {
            User user = new User();
            user.setName("starbb_test");
            user.setPassword("1234_abc");
            accountService.login(user);
        } catch (RuntimeException e) {
            String expect = MessageFormat.format(userInfoConstantEnglish.getFailedToLoginReason(), userInfoConstantEnglish.getUsernamePassMismatch());
            Assert.assertEquals(e.getMessage(), expect);
        }
    }

    @Test
    public void testLoginSuccess() {
        User user = new User();
        user.setName("starbb_test");
        user.setPassword("abc_1234");
        Assert.assertTrue("login error: failed to log in for a registered user", accountService.login(user));
    }

    // Test checkStatus()
    @Test
    public void testCheckStatusFalse() {
        Assert.assertFalse("check status error: return true under no user has logged in", accountService.checkStatus());
    }

    @Test
    public void testCheckStatusTrue() {
        User user = new User();
        user.setName("starbb_test");
        user.setPassword("abc_1234");
        accountService.login(user);
        Assert.assertTrue("check status error: return false under user has logged in", accountService.checkStatus());
    }

    // Test checkName()
    //账户名输入为空
    @Test
    public void testCheckNameNull() {
        assertFalse("checkName error: user name should not be null", accountService.checkName(null));
    }

    //账户名不是以starbb_开始
    @Test
    public void testCheckNameIsNotBeginWithstarbb_() {
        assertFalse("checkName error: user name does not begin with \"starbb_\"",
                accountService.checkName("starbucks"));
    }

    //账户名中是否有其他不合法字符
    @Test
    public void testCheckNameHasOtherWords() {
        assertFalse("checkName error: user name can only consist of letters, numbers and underline \"_\"", accountService.checkName("starbb_1?"));
        assertFalse("checkName error: user name can only consist of letters, numbers and underline \"_\"", accountService.checkName("starbb_ 0"));
    }

    //账户名长度不小于50
    @Test
    public void testCheckNameLongThan49() {
        String name = "starbb_0123456789012345678901234567890123456789012";
        assertFalse("checkName error: user name should be shorter than 50 characters", accountService.checkName(name));
    }

    //账户名长度不大于等于8
    @Test
    public void testCheckNameShorterThan8() {
        assertFalse("checkName error: user name should be at least 8 characters", accountService.checkName("starbb_"));
    }

    //正确账户名格式
    @Test
    public void testCheckNameTruePattern() {
        assertTrue("checkName error: valid name can not pass check", accountService.checkName("starbb_123"));
    }

    // Test checkPassword()
    //密码输入为空
    @Test
    public void testCheckPassWordNull() {
        assertFalse("checkPass error: password should not be null", accountService.checkPassword(null));
    }

    //密码中不含数字
    @Test
    public void testCheckPassWordMissingNumber() {
        assertFalse("checkPass error:" + "abcdefghij_" + ", password should consist of letters, numbers and underline \"_\"", accountService.checkPassword("abcdefghij_"));
    }

    //密码中不含字母
    @Test
    public void testCheckPassWordMissingLetter() {
        assertFalse("checkPass error:" + "1234567890_" + ", password should consist of letters, numbers and underline \"_\"", accountService.checkPassword("1234567890_"));
    }

    //密码中不含下划线
    @Test
    public void testCheckPassWordMissing_() {
        assertFalse("checkPass error:" + "1234567890abc" + ", password should consist of letters, numbers and underline \"_\"", accountService.checkPassword("1234567890abc"));
    }

    //密码中含有其他非法字符
    @Test
    public void testCheckPassWordHasOtherWords() {
        assertFalse("checkPass error: password should only consist of letters, numbers and underline \"_\"", accountService.checkPassword("abcd1234_?"));
        assertFalse("checkPass error: password should only consist of letters, numbers and underline \"_\"", accountService.checkPassword("abcd-1234_"));
    }

    //密码长度不小于100
    @Test
    public void testCheckPassWordLongerThan99() {
        String pass = "abcdefghi1234567890_abcdefghi1234567890_abcdefghi1234567890_abcdefghi1234567890_abcdefghi1234567890_";
        assertFalse("checkPass error: password should be shorter than 100 characters", accountService.checkPassword(pass));
    }

    //密码长度不大于等于8
    @Test
    public void testCheckPassWordShorterThan8() {
        assertFalse("checkPass error: password should be at least 8 characters", accountService.checkPassword("abc123_"));
    }

    //正确密码格式
    @Test
    public void testCheckPassWordTruePattern() {
        assertTrue("checkPass error: valid password can not pass check", accountService.checkPassword("abc123_a"));
    }
}