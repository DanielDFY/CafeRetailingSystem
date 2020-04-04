package fudan.se.lab5.service.impl;

import fudan.se.lab5.constant.UserInfoConstant;
import fudan.se.lab5.discount.DiscountInfo;
import fudan.se.lab5.dto.Ingredient;
import fudan.se.lab5.entity.Currency;
import fudan.se.lab5.entity.Drink;
import fudan.se.lab5.entity.IngredientEntity;
import fudan.se.lab5.repository.impl.DiscountRepositoryImpl;
import fudan.se.lab5.repository.impl.IngredientRepositoryImpl;
import fudan.se.lab5.service.PriceService;

import java.text.MessageFormat;
import java.util.List;

import static fudan.se.lab5.util.LogUtil.orderLogger;
import static fudan.se.lab5.util.LogUtil.sysLogger;

public class PriceServiceImpl implements PriceService {
    private String currencyName;
    private UserInfoConstant infoConstant;
    private final DiscountRepositoryImpl discountRepository;

    public PriceServiceImpl(String currencyName, UserInfoConstant infoConstant) {
        this.currencyName = currencyName;
        this.infoConstant = infoConstant;
        this.discountRepository = new DiscountRepositoryImpl();
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setInfoConstant(UserInfoConstant infoConstant) {
        this.infoConstant = infoConstant;
    }

    public UserInfoConstant getInfoConstant() {
        return infoConstant;
    }

    @Override
    public DiscountInfo getPriceInfo(List<Drink> drinks, String orderId) {
        double totalPrice = 0;
        double basicPrice;
        IngredientRepositoryImpl ingredientRepository = new IngredientRepositoryImpl();
        try {
            if (null == drinks) {
                throw new RuntimeException(infoConstant.getOrderStyleMismatch());
            }
            for (Drink drink : drinks) {
                basicPrice = drink.cost();
                if (null != drink.getIngredients()) {
                    for (Ingredient ingredient : drink.getIngredients()) {
                        if (ingredient == null) {
                            throw new RuntimeException(infoConstant.getNullIngredientExist());
                        }
                        try {
                            IngredientEntity entity = ingredientRepository.getIngredient(ingredient.getName(), ingredient.getNumber());
                            basicPrice += entity.cost();
                        } catch (RuntimeException e) {
                            throw new RuntimeException(infoConstant.getUnknownIngredientName());
                        }
                    }
                }
                orderLogger.info(orderId + " " + MessageFormat.format(infoConstant.getSuccessToCreateOrder(), drink.getName(), drink.getSize(), 1, Currency.getSymbol(currencyName), basicPrice));
                totalPrice += basicPrice;
            }
        } catch (RuntimeException e) {
            sysLogger.error(orderId + " " + e.getMessage());
            throw new RuntimeException(MessageFormat.format(infoConstant.getFailToCreateOrder(), e.getMessage()));
        }

        return discountRepository.getLowestDiscountInfo(drinks, totalPrice, currencyName, infoConstant);
    }
}
