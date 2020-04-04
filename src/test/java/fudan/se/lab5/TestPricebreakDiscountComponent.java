package fudan.se.lab5;

import fudan.se.lab5.constant.DiscountConstant;
import fudan.se.lab5.constant.SystemInfoConstant;
import fudan.se.lab5.constant.UserInfoConstantImpl.UserInfoConstantEnglish;
import fudan.se.lab5.discount.DiscountComponent;
import fudan.se.lab5.discount.DiscountInfo;
import fudan.se.lab5.discount.components.PricebreakDiscountComponent;
import fudan.se.lab5.entity.AccountAssistant;
import fudan.se.lab5.entity.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.text.MessageFormat;

import static fudan.se.lab5.util.LogUtil.sysLogger;
import static org.junit.Assert.*;

public class TestPricebreakDiscountComponent {
    private UserInfoConstantEnglish userInfoConstantEnglish = new UserInfoConstantEnglish();
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
        nodeList = doc.getElementsByTagName("PricebreakDiscountComponent").item(0).getChildNodes();

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
    public void testNegativePriceBeforeDiscount() {
        try {
            DiscountComponent discountComponent = new PricebreakDiscountComponent(nodeList);
            discountComponent.getDiscountObj(null, -10, "RMB", new UserInfoConstantEnglish());
        } catch (RuntimeException e) {
            String expect = MessageFormat.format(SystemInfoConstant.PRICEBREAK_FAILINFO, SystemInfoConstant.NEGATIVE_PRICE_INPUT);
            Assert.assertEquals(expect, e.getMessage());
        }
    }

    @Test
    public void testDiscountInfo1() {
        DiscountComponent discountComponent = new PricebreakDiscountComponent(nodeList);
        DiscountInfo result = discountComponent.getDiscountObj(null, 100, "RMB", new UserInfoConstantEnglish());
        assertEquals(30, result.getDiscount(), 0);
        assertEquals(DiscountConstant.PRICEBREAK_NAME, result.getDiscountType());
        String expectedMsg = MessageFormat.format(userInfoConstantEnglish.getPricebreakMsg(), "default_pricebreak_strategy", "￥", 30, 100, 30);
        assertTrue(result.getMsgs().contains(expectedMsg));
    }

    @Test
    public void testDiscountInfo2() {
        DiscountComponent discountComponent = new PricebreakDiscountComponent(nodeList);
        DiscountInfo result = discountComponent.getDiscountObj(null, 280, "USDollar", new UserInfoConstantEnglish());
        assertEquals(60, result.getDiscount(), 0);
        assertEquals(DiscountConstant.PRICEBREAK_NAME, result.getDiscountType());
        String expectedMsg = MessageFormat.format(userInfoConstantEnglish.getPricebreakMsg(), "default_pricebreak_strategy", "$", 30, 100, 60);
        assertTrue(result.getMsgs().contains(expectedMsg));
    }

    @Test
    public void testNoDiscount() {
        DiscountComponent discountComponent = new PricebreakDiscountComponent(nodeList);
        DiscountInfo result = discountComponent.getDiscountObj(null, 99, "HKDollar", new UserInfoConstantEnglish());
        assertEquals(0, result.getDiscount(), 0);
    }

    @Test
    public void testXML() {
        DiscountComponent discountComponent = new PricebreakDiscountComponent(nodeList);
        DiscountInfo result = discountComponent.getDiscountObj(null, 100, "RMB", userInfoConstantEnglish);
        assertEquals(30, result.getDiscount(), 0);
        assertEquals(DiscountConstant.PRICEBREAK_NAME, result.getDiscountType());
        String expectedMsg = MessageFormat.format(userInfoConstantEnglish.getPricebreakMsg(), "default_price_break", "￥", 30, 100, 30);
        System.out.print(result.getMsgs());
    }
}
