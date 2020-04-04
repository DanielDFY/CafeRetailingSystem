package fudan.se.lab5.discount.components.comboStrategy.impl;

import fudan.se.lab5.constant.DiscountConstant;
import fudan.se.lab5.constant.SystemInfoConstant;
import fudan.se.lab5.constant.UserInfoConstant;
import fudan.se.lab5.discount.DiscountPair;
import fudan.se.lab5.discount.components.comboStrategy.ComboStrategy;
import fudan.se.lab5.entity.Currency;
import fudan.se.lab5.entity.Drink;
import org.w3c.dom.Node;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class SecondHalfPriceStrategy implements ComboStrategy {
    private final String strategyName;
    private final String targetName;
    private final int targetSize;

    public SecondHalfPriceStrategy(ArrayList<Node> argNodes) {
        String strategyName = argNodes.get(1).getTextContent();
        String targetName = argNodes.get(2).getTextContent();
        String targetSizeString = argNodes.get(3).getTextContent();

        int targetSize = Integer.parseInt(targetSizeString);
        if (targetSize < 0 || targetSize > 4) {
            throw new RuntimeException(SystemInfoConstant.COMBO_INVALID_ARGUMENT);
        }

        this.strategyName = strategyName;
        this.targetName = targetName;
        this.targetSize = targetSize;
    }

    @Override
    public String getStrategyName() {
        return strategyName;
    }

    @Override
    public DiscountPair getDiscount(List<Drink> orderItems, double priceBeforeDiscount, UserInfoConstant infoConstant, List<String> msgs, String currencyName) {
        int count = 0;
        double basePrice = 0;
        double discount;

        for (Drink orderItem : orderItems) {
            if (targetName.equals(orderItem.getName()) && (targetSize == DiscountConstant.ALL || targetSize == orderItem.getSize())) {
                basePrice = orderItem.getPrice();
                ++count;
            }
        }

        if (count % 2 == 1) {
            count -= 1;
        }

        discount = count * basePrice / 4;

        msgs.add(MessageFormat.format(infoConstant.getComboSecondHalfPrice(), strategyName, Drink.getSizeName(targetSize), Currency.getSymbol(currencyName), discount));

        return new DiscountPair(discount, 0);
    }
}
