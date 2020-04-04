package fudan.se.lab5.discount;

import fudan.se.lab5.constant.UserInfoConstant;
import fudan.se.lab5.entity.Drink;

import java.util.List;

public interface DiscountComponent {
    String getComponentName();
    DiscountInfo getDiscountObj(List<Drink> orderItems, double priceBeforeDiscount, String currency, UserInfoConstant infoConstant);
}
