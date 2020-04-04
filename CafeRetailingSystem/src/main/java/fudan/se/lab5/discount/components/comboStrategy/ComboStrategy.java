package fudan.se.lab5.discount.components.comboStrategy;

import fudan.se.lab5.constant.UserInfoConstant;
import fudan.se.lab5.discount.DiscountPair;
import fudan.se.lab5.entity.Drink;

import java.util.List;

public interface ComboStrategy {
    String getStrategyName();
    DiscountPair getDiscount(List<Drink> orderItems, double priceBeforeDiscount, UserInfoConstant infoConstant, List<String> msgs, String currencyName);
}
