package fudan.se.lab5.service.impl;

import fudan.se.lab5.constant.SystemInfoConstant;
import fudan.se.lab5.constant.UserInfoConstant;
import fudan.se.lab5.constant.UserInfoConstantImpl.UserInfoConstantChinese;
import fudan.se.lab5.constant.UserInfoConstantImpl.UserInfoConstantEnglish;
import fudan.se.lab5.discount.DiscountInfo;
import fudan.se.lab5.dto.Order;
import fudan.se.lab5.dto.OrderItem;
import fudan.se.lab5.dto.PaymentInfo;
import fudan.se.lab5.entity.Drink;
import fudan.se.lab5.repository.impl.DrinkRepositoryImpl;
import fudan.se.lab5.service.OrderService;
import fudan.se.lab5.service.PriceService;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static fudan.se.lab5.util.LogUtil.orderLogger;
import static fudan.se.lab5.util.LogUtil.sysLogger;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public PaymentInfo pay(Order order) {
        DiscountInfo discountInfo;
        double price;
        double discount;
        double discountPrice;
        List<String> msgs;
        try {
            if (null == order) {
                throw new RuntimeException(SystemInfoConstant.NULL_ORDER);
            }
        } catch (RuntimeException e) {
            sysLogger.error(e.getMessage());
            throw new RuntimeException(MessageFormat.format(SystemInfoConstant.FAILED_TO_PAY, e.getMessage()));
        }

        try {
            DrinkRepositoryImpl drinkRepository = new DrinkRepositoryImpl();

            List<OrderItem> orderItems = order.getOrderItems();
            List<Drink> drinks = new ArrayList<>();
            for (OrderItem orderItem : orderItems) {
                Drink drink = drinkRepository.getDrink(orderItem.getName(), order.getCurrency(), order.getLanguage(), order.getId());
                drink.setSize(orderItem.getSize());
                drink.setIngredients(orderItem.getIngredients());
                drinks.add(drink);
            }

            UserInfoConstant userInfoConstant;
            switch (order.getLanguage()) {
                case "Chinese":
                    userInfoConstant = new UserInfoConstantChinese();
                    break;
                case "English":
                    userInfoConstant = new UserInfoConstantEnglish();
                    break;
                default:
                    throw new RuntimeException(SystemInfoConstant.UNKNOWN_LANGUAGE);
            }

            PriceService priceService = new PriceServiceImpl(order.getCurrency(), userInfoConstant);
            discountInfo = priceService.getPriceInfo(drinks, order.getId());

            price = discountInfo.getPriceBeforeDiscount();
            discount = discountInfo.getDiscount();
            discountPrice = price - discount;
            msgs = discountInfo.getMsgs();

            orderLogger.info(MessageFormat.format(SystemInfoConstant.SUCCESS_TO_CREATE_ORDER, order.getId(), price, discount, discountPrice));
            for (String msg : msgs) {
                orderLogger.info(order.getId() + " " + msg);
            }
            return new PaymentInfo(order.getId(), price, discount, discountPrice, msgs);
        } catch (Exception e) {
            sysLogger.error(order.getId() + e.getMessage());
            throw new RuntimeException(MessageFormat.format(SystemInfoConstant.FAILED_TO_PAY, e.getMessage()));
        }
    }
}
