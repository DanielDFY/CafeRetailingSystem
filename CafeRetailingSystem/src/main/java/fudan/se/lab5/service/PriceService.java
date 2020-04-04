package fudan.se.lab5.service;

import fudan.se.lab5.discount.DiscountInfo;
import fudan.se.lab5.entity.Drink;

import java.util.List;

public interface PriceService {
    /**
     * @parm drinks Coffee and its number.
     *              eg. if Espresso is 4$, 2 cups of Espresso is 8$.
     * @return discount info
     */
    DiscountInfo getPriceInfo(List<Drink> drinks, String id);
}
