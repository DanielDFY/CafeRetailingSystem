package fudan.se.lab5.discount.components.timeTriggerStrategy;

import fudan.se.lab5.constant.UserInfoConstant;
import fudan.se.lab5.discount.DiscountPair;

import java.util.List;

public interface TimeTriggerStrategy {
    String getStrategyName();
    DiscountPair getDiscount(UserInfoConstant infoConstant, List<String> msgs, String currencyName);
}
