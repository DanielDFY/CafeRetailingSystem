package fudan.se.lab5;

import fudan.se.lab5.constant.SystemInfoConstant;
import fudan.se.lab5.constant.UserInfoConstantImpl.UserInfoConstantEnglish;
import fudan.se.lab5.dto.Order;
import fudan.se.lab5.entity.AccountAssistant;
import fudan.se.lab5.entity.Drink;
import fudan.se.lab5.entity.User;
import fudan.se.lab5.repository.DrinkRepository;
import fudan.se.lab5.repository.impl.DrinkRepositoryImpl;
import fudan.se.lab5.service.PriceService;
import fudan.se.lab5.service.impl.PriceServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestPriceService {
    private DrinkRepository drinkRepository;
    private List<Drink> drinks;
    private UserInfoConstantEnglish userInfoConstantEnglish = new UserInfoConstantEnglish();

    @Before
    public void setUp() {
        drinkRepository = new DrinkRepositoryImpl();
        drinks = new ArrayList<>();

        User user = new User();
        user.setName("starbb_test");
        user.setPassword("abc_1234");
        AccountAssistant.setUserOnline(user);
    }

    @After
    public void tearDown() {
        drinkRepository = null;
        AccountAssistant.setUserOnline(null);
    }

    //正确的情况
    @Test
    public void testPrice() {
        Order order = new Order();
        PriceService priceService = new PriceServiceImpl("USDollar", new UserInfoConstantEnglish());
        Drink espresso = drinkRepository.getDrink("espresso", "USDollar", "English", order.getId());
        espresso.setSize(1);
        drinks.add(espresso);
        Drink cappuccino = drinkRepository.getDrink("cappuccino", "USDollar", "English", order.getId());
        cappuccino.setSize(1);
        for (int i = 0; i < 3; i++) {
            drinks.add(cappuccino);
        }
        assertEquals(94, priceService.getPriceInfo(drinks, order.getId()).getPriceBeforeDiscount(), 0.1);
    }


    //杯数小于等于0的情况
    @Test
    public void testPriceNumberInvalid() {
        Order order = new Order();
        PriceService priceService = new PriceServiceImpl("USDollar", new UserInfoConstantEnglish());
        try {
            priceService.getPriceInfo(drinks, order.getId());
        } catch (RuntimeException e) {
            String expect = MessageFormat.format(userInfoConstantEnglish.getFailToCreateOrder(), userInfoConstantEnglish.getNullOrderValueExist());
            assertEquals(expect, e.getMessage());
        }
    }

    //表为空
    @Test
    public void testPriceTableNull() {
        Order order = new Order();
        PriceService priceService = new PriceServiceImpl("USDollar", new UserInfoConstantEnglish());
        try {
            priceService.getPriceInfo(null, order.getId());
        } catch (RuntimeException e) {
            String expect = MessageFormat.format(userInfoConstantEnglish.getFailToCreateOrder(), userInfoConstantEnglish.getOrderStyleMismatch());
            Assert.assertEquals(expect, e.getMessage());
        }
    }

    //咖啡名字key空
    @Test
    public void testPriceNullCoffee() {
        Order order = new Order();
        PriceService priceService = new PriceServiceImpl("USDollar", new UserInfoConstantEnglish());
        try {
            drinks.add(drinkRepository.getDrink("", "USDollar", "English", order.getId()));
            for (int i = 0; i < 2; i++) {
                drinks.add(drinkRepository.getDrink("espresso", "USDollar", "English", order.getId()));
            }
            priceService.getPriceInfo(drinks, order.getId());
        } catch (RuntimeException e) {
            String expect = SystemInfoConstant.UNKNOWN_DRINK_NAME;
            assertEquals(expect, e.getMessage());
        }
    }

    @Test
    public void testGetCurrency() {
        Order order = new Order();
        PriceServiceImpl priceService = new PriceServiceImpl("RMB", new UserInfoConstantEnglish());
        Drink espresso = drinkRepository.getDrink("espresso", "USDollar", "English", order.getId());
        espresso.setSize(1);
        drinks.add(espresso);
        assertEquals("RMB", priceService.getCurrencyName());
    }

    @Test
    public void testSetCurrency() {
        Order order = new Order();
        PriceServiceImpl priceService = new PriceServiceImpl("RMB", new UserInfoConstantEnglish());
        Drink espresso = drinkRepository.getDrink("espresso", "USDollar", "English", order.getId());
        espresso.setSize(1);
        drinks.add(espresso);
        priceService.setCurrencyName("HKDollar");
        assertEquals("HKDollar", priceService.getCurrencyName());
    }

    @Test
    public void testCurrency1() {
        Order order = new Order();
        PriceServiceImpl priceService = new PriceServiceImpl("RMB", new UserInfoConstantEnglish());
        Drink espresso = drinkRepository.getDrink("espresso", "RMB", "English", order.getId());
        espresso.setSize(1);
        drinks.add(espresso);
        assertEquals(151.58, priceService.getPriceInfo(drinks, order.getId()).getPriceBeforeDiscount(), 0.1);
    }

    @Test
    public void testCurrency2() {
        Order order = new Order();
        PriceServiceImpl priceService = new PriceServiceImpl("HKDollar", new UserInfoConstantEnglish());
        Drink espresso = drinkRepository.getDrink("cappuccino", "HKDollar", "English", order.getId());
        espresso.setSize(1);
        drinks.add(espresso);
        assertEquals(188.4, priceService.getPriceInfo(drinks, order.getId()).getPriceBeforeDiscount(), 0.01);
    }

    @Test
    public void testUnknownCurrencyName() {
        Order order = new Order();
        try {
            PriceServiceImpl priceService = new PriceServiceImpl("USD", new UserInfoConstantEnglish());
        } catch (RuntimeException e) {
            String expect = SystemInfoConstant.UNKNOWN_CURRENCY_NAME;
            assertEquals(expect, e.getMessage());
        }
    }
}