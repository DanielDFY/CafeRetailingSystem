package fudan.se.lab5.discount.components.timeTriggerStrategy.impl;

import fudan.se.lab5.constant.SystemInfoConstant;
import fudan.se.lab5.constant.UserInfoConstant;
import fudan.se.lab5.discount.DiscountPair;
import fudan.se.lab5.discount.components.timeTriggerStrategy.TimeTriggerStrategy;
import org.w3c.dom.Node;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;

public class DefaultTimeTriggerStrategy implements TimeTriggerStrategy {
    private final String strategyName;
    private final MonthDay dateStart;
    private final MonthDay dateEnd;
    private final double discountPercent;

    public DefaultTimeTriggerStrategy(ArrayList<Node> argNodes) {
        String strategyName = argNodes.get(1).getTextContent();
        String dateStartString = argNodes.get(2).getTextContent();
        String dateEndString = argNodes.get(3).getTextContent();
        String discountPercentString = argNodes.get(4).getTextContent();

        MonthDay dateStart = MonthDay.parse(dateStartString);
        MonthDay dateEnd = MonthDay.parse(dateEndString);
        double discountPercent = Double.parseDouble(discountPercentString);

        if (discountPercent < 0 || discountPercent > 1 || dateStart.isAfter(dateEnd))
            throw new RuntimeException(SystemInfoConstant.TIME_INVALID_ARGUMENT);

        this.strategyName = strategyName;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.discountPercent = discountPercent;
    }

    @Override
    public DiscountPair getDiscount(UserInfoConstant infoConstant, List<String> msgs, String currencyName) {
        LocalDate now = LocalDate.now();
        MonthDay today = MonthDay.of(now.getMonth(), now.getDayOfMonth());

        if (!(dateStart.isAfter(today) || dateEnd.isBefore(today))) {
            msgs.add(MessageFormat.format(infoConstant.getTimetriggerMsg(), strategyName,  discountPercent * 100, dateStart.toString(), dateEnd.toString()));
            return new DiscountPair(0, discountPercent);
        } else {
            return new DiscountPair();
        }
    }

    @Override
    public String getStrategyName() {
        return strategyName;
    }
}
