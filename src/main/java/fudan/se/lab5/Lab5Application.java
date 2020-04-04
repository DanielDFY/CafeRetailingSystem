package fudan.se.lab5;

import fudan.se.lab5.constant.UserInfoConstantImpl.UserInfoConstantEnglish;
import fudan.se.lab5.dto.Ingredient;
import fudan.se.lab5.entity.Drink;
import fudan.se.lab5.entity.User;
import fudan.se.lab5.repository.DrinkRepository;
import fudan.se.lab5.repository.impl.DrinkRepositoryImpl;
import fudan.se.lab5.service.impl.AccountServiceImpl;
import fudan.se.lab5.service.impl.PriceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@SpringBootApplication
public class Lab5Application {
    private static Logger logger = LoggerFactory.getLogger(Lab5Application.class);

    private static User createUserObj(AccountServiceImpl accountService) {
        Scanner scanner = new Scanner(System.in);
        logger.info("Please input username: ");
        String name = scanner.nextLine();
        // Check styles of the name and password for signup
        while (!accountService.checkName(name)) {
            logger.info("Please input username: ");
            name = scanner.nextLine();
        }

        logger.info("Please input password: ");
        String password = scanner.nextLine();
        while (!accountService.checkPassword(password)) {
            logger.info("Please input password: ");
            password = scanner.nextLine();
        }

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        return user;
    }

    public static void main(String[] args) {
        SpringApplication.run(Lab5Application.class, args);

        // todo: here to write your main business logic.
        AccountServiceImpl accountService = new AccountServiceImpl();
        PriceServiceImpl priceService = new PriceServiceImpl("RMB",new UserInfoConstantEnglish());

        // Temporary account and order
        ArrayList<Ingredient> teaIngredients = new ArrayList<>();
        teaIngredients.add(new Ingredient("milk", 1));

        DrinkRepository drinkRepository = new DrinkRepositoryImpl();
        List<Drink> orderItems  = new ArrayList<>();
        Drink cappuccino = drinkRepository.getDrink("cappuccino", "USDollar", "English", "0000000000000000000000");
        cappuccino.setSize(2);
        Drink redTea = drinkRepository.getDrink("redtea", "USDollar", "English", "0000000000000000000000");
        redTea.setSize(2);
        redTea.setIngredients(teaIngredients);
        orderItems.add(cappuccino);
        orderItems.add(redTea);

        // step1: signup
        User user = createUserObj(accountService);
        accountService.signup(user);

        // step2: login
        accountService.login(user);

        // step3: check status
        accountService.checkStatus();

        // step4: price service
        //logger.info(MessageFormat.format(UserInfoConstantEnglish.ORDER_TOTAL_PRICE, priceService.cost(orderItems, "")));
    }
}