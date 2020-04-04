package fudan.se.lab5;

import fudan.se.lab5.constant.DiscountConstant;
import fudan.se.lab5.constant.SystemInfoConstant;
import fudan.se.lab5.constant.UserInfoConstantImpl.UserInfoConstantEnglish;
import fudan.se.lab5.discount.DiscountComponent;
import fudan.se.lab5.discount.DiscountInfo;
import fudan.se.lab5.discount.components.ComboDiscountComponent;
import fudan.se.lab5.dto.Order;
import fudan.se.lab5.entity.AccountAssistant;
import fudan.se.lab5.entity.Drink;
import fudan.se.lab5.entity.User;
import fudan.se.lab5.repository.DrinkRepository;
import fudan.se.lab5.repository.impl.DrinkRepositoryImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static fudan.se.lab5.util.LogUtil.sysLogger;
import static org.junit.Assert.*;

public class TestComboDiscountComponent {
    private DrinkRepository drinkRepository;
    private List<Drink> orderItemList;
    private UserInfoConstantEnglish userInfoConstantEnglish = new UserInfoConstantEnglish();
    private NodeList nodeList;

    @Before
    public void setUp() {
        drinkRepository = new DrinkRepositoryImpl();
        orderItemList = new ArrayList<>();

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
        nodeList = doc.getElementsByTagName("ComboDiscountComponent").item(0).getChildNodes();

        User user = new User();
        user.setName("starbb_test");
        user.setPassword("abc_1234");
        AccountAssistant.setUserOnline(user);
    }

    @After
    public void tearDown() {
        drinkRepository = null;
        orderItemList = null;
        nodeList = null;
        AccountAssistant.setUserOnline(null);
    }

    @Test
    public void testDiscount1() {
        Order order = new Order("English", "USDollar", null);
        // 中杯Espresso 2杯
        Drink espresso = drinkRepository.getDrink("espresso", "USDollar", "English", order.getId());
        espresso.setSize(2);
        orderItemList.add(espresso);
        orderItemList.add(espresso);

        // 中杯Cappuccino 1杯
        Drink cappuccino = drinkRepository.getDrink("cappuccino", "USDollar", "English", order.getId());
        cappuccino.setSize(2);
        orderItemList.add(cappuccino);
        double priceBeforeDiscount = 74;
        DiscountComponent discountComponent = new ComboDiscountComponent(nodeList);
        DiscountInfo result = discountComponent.getDiscountObj(orderItemList, priceBeforeDiscount, "USDollar", new UserInfoConstantEnglish());
        assertEquals(11.1, result.getDiscount(), 0.1);
    }

    // no discountUnit
    @Test
    public void testDiscount2() {
        Order order = new Order("English", "USDollar", null);
        Drink greenTea = drinkRepository.getDrink("greentea", "USDollar", "English", order.getId());
        greenTea.setSize(2);
        orderItemList.add(greenTea);
        orderItemList.add(greenTea);
        Drink redTea = drinkRepository.getDrink("redtea", "USDollar", "English", order.getId());
        redTea.setSize(3);
        orderItemList.add(redTea);
        double priceBeforeDiscount = 63;
        DiscountComponent discountComponent = new ComboDiscountComponent(nodeList);
        DiscountInfo result = discountComponent.getDiscountObj(orderItemList, priceBeforeDiscount, "USDollar", new UserInfoConstantEnglish());
        assertEquals(9.45, result.getDiscount(), 0.1);
    }

    // 大杯Espresso, 2杯8折
    @Test
    public void testTwoLargeEspresso1() {
        Order order = new Order("English", "USDollar", null);
        Drink espresso = drinkRepository.getDrink("espresso", "USDollar", "English", order.getId());
        espresso.setSize(3);
        orderItemList.add(espresso);
        orderItemList.add(espresso);
        double priceBeforeDiscount = 52;
        DiscountComponent discountComponent = new ComboDiscountComponent(nodeList);
        DiscountInfo result = discountComponent.getDiscountObj(orderItemList, priceBeforeDiscount, "USDollar", new UserInfoConstantEnglish());
        assertEquals(15.8, result.getDiscount(), 0.1);
        assertEquals(DiscountConstant.COMBO_NAME, result.getDiscountType());
        String expectedMsg = MessageFormat.format(userInfoConstantEnglish.getComboDiscountPerSet(), "default_espresso_large_second_20%_off", "large", 20, 2, "$", 8);
        assertTrue(result.getMsgs().contains(expectedMsg));
    }

    // 大杯Espresso, 2杯8折
    @Test
    public void testTwoLargeEspresso2() {
        Order order = new Order("English", "USDollar", null);
        Drink espresso = drinkRepository.getDrink("espresso", "USDollar", "English", order.getId());
        espresso.setSize(3);
        //7杯
        for (int i = 0; i < 7; i++) {
            orderItemList.add(espresso);
        }
        double priceBeforeDiscount = 182;
        DiscountComponent discountComponent = new ComboDiscountComponent(nodeList);
        DiscountInfo result = discountComponent.getDiscountObj(orderItemList, priceBeforeDiscount, "USDollar", new UserInfoConstantEnglish());
        assertEquals(51.3, result.getDiscount(), 0.1);
        String expectedMsg = MessageFormat.format(userInfoConstantEnglish.getComboDiscountPerSet(), "default_espresso_large_second_20%_off", "large", 20, 2, "$", 24);
        assertTrue(result.getMsgs().contains(expectedMsg));
    }

    // RedTea/GreenTea（总数）买三送一
    @Test
    public void testThreeTeaOneFree1() {
        Order order = new Order("English", "USDollar", null);
        Drink greenTea1 = drinkRepository.getDrink("greentea", "USDollar", "English", order.getId());
        greenTea1.setSize(1);
        Drink greenTea2 = drinkRepository.getDrink("greentea", "USDollar", "English", order.getId());
        greenTea2.setSize(2);
        orderItemList.add(greenTea1);
        orderItemList.add(greenTea1);
        orderItemList.add(greenTea2);
        Drink redTea = drinkRepository.getDrink("redtea", "USDollar", "English", order.getId());
        redTea.setSize(3);
        orderItemList.add(redTea);
        double priceBeforeDiscount = 79;
        DiscountComponent discountComponent = new ComboDiscountComponent(nodeList);
        DiscountInfo result = discountComponent.getDiscountObj(orderItemList, priceBeforeDiscount, "USDollar", new UserInfoConstantEnglish());
        assertEquals(29.85, result.getDiscount(), 0.1);
        assertEquals(DiscountConstant.COMBO_NAME, result.getDiscountType());
        String expectedMsg = MessageFormat.format(userInfoConstantEnglish.getComboOneFreePerSet(), "default_redTea_and_greenTea_1_free_per_3", "all", 3, "$", 18);
        assertTrue(result.getMsgs().contains(expectedMsg));
    }

    // RedTea/GreenTea（总数）买三送一
    @Test
    public void testThreeTeaOneFree2() {
        Order order = new Order("English", "USDollar", null);
        Drink greenTea1 = drinkRepository.getDrink("greentea", "USDollar", "English", order.getId());
        greenTea1.setSize(1);
        Drink greenTea2 = drinkRepository.getDrink("greentea", "USDollar", "English", order.getId());
        greenTea2.setSize(2);
        Drink greenTea3 = drinkRepository.getDrink("greentea", "USDollar", "English", order.getId());
        greenTea3.setSize(3);
        orderItemList.add(greenTea1);
        orderItemList.add(greenTea1);
        orderItemList.add(greenTea2);
        orderItemList.add(greenTea3);
        double priceBeforeDiscount = 77;
        DiscountComponent discountComponent = new ComboDiscountComponent(nodeList);
        DiscountInfo result = discountComponent.getDiscountObj(orderItemList, priceBeforeDiscount, "USDollar", new UserInfoConstantEnglish());
        assertEquals(27.55, result.getDiscount(), 0.1);
        assertEquals(DiscountConstant.COMBO_NAME, result.getDiscountType());
        String expectedMsg = MessageFormat.format(userInfoConstantEnglish.getComboOneFreePerSet(), "default_redTea_and_greenTea_1_free_per_3", "all", 3, "$", 16);
        assertTrue(result.getMsgs().contains(expectedMsg));
    }

    // Cappucino第2杯半价
    @Test
    public void testSecondCappucinoHalfPrice1() {
        Order order = new Order("English", "USDollar", null);
        Drink cappuccino1 = drinkRepository.getDrink("cappuccino", "USDollar", "English", order.getId());
        cappuccino1.setSize(1);
        Drink cappuccino2 = drinkRepository.getDrink("cappuccino", "USDollar", "English", order.getId());
        cappuccino2.setSize(2);
        orderItemList.add(cappuccino1);
        orderItemList.add(cappuccino2);
        double priceBeforeDiscount = 50;
        DiscountComponent discountComponent = new ComboDiscountComponent(nodeList);
        DiscountInfo result = discountComponent.getDiscountObj(orderItemList, priceBeforeDiscount, "USDollar", new UserInfoConstantEnglish());
        assertEquals(18.5, result.getDiscount(), 0.1);
        assertEquals(DiscountConstant.COMBO_NAME, result.getDiscountType());
        String expectedMsg = MessageFormat.format(userInfoConstantEnglish.getComboSecondHalfPrice(), "default_cappuccino_second_half", "all", "$", 11);
        assertTrue(result.getMsgs().contains(expectedMsg));
    }

    // Cappucino第2杯半价
    @Test
    public void testSecondCappucinoHalfPrice2() {
        Order order = new Order("English", "USDollar", null);
        Drink cappuccino = drinkRepository.getDrink("cappuccino", "USDollar", "English", order.getId());
        cappuccino.setSize(2);
        //7杯
        for (int i = 0; i < 7; i++) {
            orderItemList.add(cappuccino);
        }
        double priceBeforeDiscount = 84;
        DiscountComponent discountComponent = new ComboDiscountComponent(nodeList);
        DiscountInfo result = discountComponent.getDiscountObj(orderItemList, priceBeforeDiscount, "USDollar", new UserInfoConstantEnglish());
        assertEquals(45.6, result.getDiscount(), 0.1);
        assertEquals(DiscountConstant.COMBO_NAME, result.getDiscountType());
        String expectedMsg = MessageFormat.format(userInfoConstantEnglish.getComboSecondHalfPrice(), "default_cappuccino_second_half", "all", "$", 33);
        assertTrue(result.getMsgs().contains(expectedMsg));
    }
}
