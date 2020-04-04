package fudan.se.lab5.repository;

import fudan.se.lab5.constant.UserInfoConstant;
import fudan.se.lab5.discount.DiscountInfo;
import fudan.se.lab5.entity.Drink;

import java.util.List;

public interface DiscountRepository {
    /**
     * Get discountUnit information with the lowest price
     *
     * @return discountUnit info
     */
    DiscountInfo getLowestDiscountInfo(List<Drink> orderItems, double priceBeforeDiscount, String currencyName, UserInfoConstant infoConstant);
}
