package fudan.se.lab5;

import fudan.se.lab5.constant.DiscountConstant;
import fudan.se.lab5.constant.SystemInfoConstant;
import fudan.se.lab5.constant.UserInfoConstantImpl.UserInfoConstantChinese;
import fudan.se.lab5.constant.UserInfoConstantImpl.UserInfoConstantEnglish;
import fudan.se.lab5.discount.DiscountInfo;
import fudan.se.lab5.discount.components.TimeTriggerDiscountComponent;
import fudan.se.lab5.dto.Order;
import fudan.se.lab5.entity.AccountAssistant;
import fudan.se.lab5.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

import static fudan.se.lab5.util.LogUtil.sysLogger;
import static org.junit.Assert.*;

public class TestTimeTriggerDiscountComponent {
    private UserInfoConstantEnglish userInfoConstantEnglish = new UserInfoConstantEnglish();
    private UserInfoConstantChinese userInfoConstantChinese = new UserInfoConstantChinese();

    private NodeList nodeList;

    @Before
    public void setUp() {
        String filePath = "config/discountConfig.xml";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(new File(filePath));
        } catch (Exception e) {
            sysLogger.error(e.getMessage());
            throw new RuntimeException(SystemInfoConstant.DISCOUNTCONFIG_NOTFOUND);
        }

        assertNotNull(doc);
        nodeList = doc.getElementsByTagName("TimeTriggerDiscountComponent").item(0).getChildNodes();

        User user = new User();
        user.setName("starbb_test");
        user.setPassword("abc_1234");
        AccountAssistant.setUserOnline(user);
    }

    @After
    public void tearDown() {
        nodeList = null;
        AccountAssistant.setUserOnline(null);
    }

    @Test
    public void testNoPromotion() {
        TimeTriggerDiscountComponent timeTriggerDiscountComponent = new TimeTriggerDiscountComponent(nodeList);
        DiscountInfo result = timeTriggerDiscountComponent.getDiscountObj(null, 400, "USDollar", new UserInfoConstantEnglish());
        assertEquals(DiscountConstant.TIMETRIGGER_NAME, result.getDiscountType());
        assertEquals(0, result.getDiscount(), 0.1);
    }
}
