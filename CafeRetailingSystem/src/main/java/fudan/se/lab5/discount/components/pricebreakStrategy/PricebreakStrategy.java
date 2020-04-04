package fudan.se.lab5.discount.components.pricebreakStrategy;

import fudan.se.lab5.constant.UserInfoConstant;
import fudan.se.lab5.discount.DiscountPair;

import java.util.List;

public interface PricebreakStrategy {
    String getStrategyName();
    DiscountPair getDiscount(double priceBeforeDiscount, UserInfoConstant infoConstant, List<String> msgs, String currencyName);
}
