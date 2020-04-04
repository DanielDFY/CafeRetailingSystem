package fudan.se.lab5.discount.components.pricebreakStrategy.impl;

import fudan.se.lab5.constant.SystemInfoConstant;
import fudan.se.lab5.constant.UserInfoConstant;
import fudan.se.lab5.discount.DiscountPair;
import fudan.se.lab5.discount.components.pricebreakStrategy.PricebreakStrategy;
import fudan.se.lab5.entity.Currency;
import org.w3c.dom.Node;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class DefaultPricebreakStrategy implements PricebreakStrategy {
    private final String strategyName;
    private final double threshold;
    private final double unit;

    public DefaultPricebreakStrategy(ArrayList<Node> argNodes) {
        String strategyName = argNodes.get(1).getTextContent();
        String thresholdString = argNodes.get(2).getTextContent();
        String unitString = argNodes.get(3).getTextContent();

        double threshold = Double.parseDouble(thresholdString);
        double unit = Double.parseDouble(unitString);
        if (strategyName.isEmpty() || threshold < 0 || unit < 0)
            throw new RuntimeException(SystemInfoConstant.PRICEBREAK_INVALID_ARGUMENT);

        this.strategyName = strategyName;
        this.threshold = threshold;
        this.unit = unit;
    }

    @Override
    public DiscountPair getDiscount(double priceBeforeDiscount, UserInfoConstant infoConstant, List<String> msgs, String currencyName) {
        int count = (int) (priceBeforeDiscount / threshold);
        double discount = unit * count;
        msgs.add(MessageFormat.format(infoConstant.getPricebreakMsg(), strategyName, Currency.getSymbol(currencyName), unit, threshold, discount));
        return new DiscountPair(discount, 0);
    }

    @Override
    public String getStrategyName() {
        return strategyName;
    }
}
