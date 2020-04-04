package fudan.se.lab5;

import fudan.se.lab5.constant.SystemInfoConstant;
import fudan.se.lab5.constant.UserInfoConstantImpl.UserInfoConstantEnglish;
import fudan.se.lab5.dto.*;
import fudan.se.lab5.entity.AccountAssistant;
import fudan.se.lab5.entity.User;
import fudan.se.lab5.service.OrderService;
import fudan.se.lab5.service.impl.OrderServiceImpl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestOrderService {
    private OrderService orderService;
    private List<OrderItem> orderItemList;
    private UserInfoConstantEnglish userInfoConstantEnglish = new UserInfoConstantEnglish();

    @Before
    public void setUp() {
        orderService = new OrderServiceImpl();
        orderItemList = new ArrayList<>();

        User user = new User();
        user.setName("starbb_test");
        user.setPassword("abc_1234");
        AccountAssistant.setUserOnline(user);
    }

    @After
    public void tearDown() {
        orderService = null;
        orderItemList = null;

        AccountAssistant.setUserOnline(null);
    }

    //Test pay()
    //order为null
    @Test
    public void testNullOrder() {
        try {
            orderService.pay(null);
        } catch (Exception e) {
            assertEquals(MessageFormat.format(SystemInfoConstant.FAILED_TO_PAY, SystemInfoConstant.NULL_ORDER), e.getMessage());
        }
    }

    //no discountUnit
    @Test
    public void testPayWithDiscount() {
        List<Ingredient> ingredientList = new ArrayList<>();
        //小杯cappuccino加1份奶
        ingredientList.add(new Ingredient("milk", 1));
        orderItemList.add(new OrderItem("cappuccino", ingredientList, 1));
        Order order = new Order("English", "USDollar", orderItemList);
        PaymentInfo result = orderService.pay(order);
        assertEquals(25.2, result.getPrice(), 0.1);
        assertEquals(3.78, result.getDiscount(), 0.1);
        assertEquals(21.42, result.getDiscountPrice(), 0.1);
    }

    @Test
    public void testPayWithPriceBreakDiscount() {
        List<Ingredient> ingredientList = new ArrayList<>();
        //中杯espresso三份奶油 三份糖 4杯
        ingredientList.add(new Ingredient("cream", 3));
        ingredientList.add(new Ingredient("sugar", 3));
        for (int i = 0; i < 4; i++) {
            orderItemList.add(new OrderItem("espresso", ingredientList, 2));
        }
        Order order = new Order("English", "USDollar", orderItemList);
        PaymentInfo result = orderService.pay(order);
        assertEquals(120, result.getPrice(), 0.1);
        assertEquals(30, result.getDiscount(), 0.1);
        assertEquals(90, result.getDiscountPrice(), 0.1);
        String expectedMsg = MessageFormat.format(userInfoConstantEnglish.getPricebreakMsg(), "default_pricebreak_strategy", "$", 30, 100, 30);
        assertTrue(result.getMsgs().contains(expectedMsg));
    }

    @Test
    public void testPayWithComboDiscount() {
        //两杯大杯espresso
        for (int i = 0; i < 2; i++) {
            orderItemList.add(new OrderItem("espresso", null, 3));
        }
        //两杯小杯cappuccino
        for (int i = 0; i < 2; i++) {
            orderItemList.add(new OrderItem("cappuccino", null, 1));
        }
        //三杯小杯greentea
        for (int i = 0; i < 3; i++) {
            orderItemList.add(new OrderItem("greentea", null, 1));
        }
        //一杯小杯redtea
        orderItemList.add(new OrderItem("redtea", null, 1));
        Order order = new Order("English", "USDollar", orderItemList);
        PaymentInfo result = orderService.pay(order);
        assertEquals(174, result.getPrice(), 0.1);
        assertEquals(63.1, result.getDiscount(), 0.1);
        assertEquals(110.9, result.getDiscountPrice(), 0.1);
        String expectedMsg1 = MessageFormat.format(userInfoConstantEnglish.getComboDiscountPerSet(),
                "default_espresso_large_second_20%_off", "large", 20, 2, "$", 8);
        String expectedMsg2 = MessageFormat.format(userInfoConstantEnglish.getComboOneFreePerSet(),
                "default_redTea_and_greenTea_1_free_per_3", "all", 3, "$", 18);
        String expectedMsg3 = MessageFormat.format(userInfoConstantEnglish.getComboSecondHalfPrice(),
                "default_cappuccino_second_half", "all", "$", 11);
        assertTrue(result.getMsgs().contains(expectedMsg1));
        assertTrue(result.getMsgs().contains(expectedMsg2));
        assertTrue(result.getMsgs().contains(expectedMsg3));
    }

    // test order number
    @Test
    public void testOrderNum1() {
        // login
        User user = new User();
        user.setName("starbb_test");
        user.setPassword("abc_1234");
        AccountAssistant.setUserOnline(user);

        Order order = new Order();
        String orderNum = order.getId();
        assertEquals(orderNum.substring(orderNum.length() - 1), "0");

        SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmss");
        String expected = sfDate.format(new Date());
        assertEquals(orderNum.substring(0, 14), expected);
        AccountAssistant.setUserOnline(null);
    }

    @Test
    public void testOrderNum2() {
        // login
        User user = new User();
        user.setName("starbb_test");
        user.setPassword("abc_1234");
        AccountAssistant.setUserOnline(user);

        Order order = new Order("English", "USDollar", null);
        String orderNum = order.getId();
        assertEquals(orderNum.substring(orderNum.length() - 1), "0");

        SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmss");
        String expected = sfDate.format(new Date());
        assertEquals(orderNum.substring(0, 14), expected);
        AccountAssistant.setUserOnline(null);
    }
}
