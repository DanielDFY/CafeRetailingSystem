package fudan.se.lab5.discount.components.comboStrategy.impl;

import fudan.se.lab5.constant.SystemInfoConstant;
import fudan.se.lab5.constant.UserInfoConstant;
import fudan.se.lab5.discount.DiscountPair;
import fudan.se.lab5.discount.components.comboStrategy.ComboStrategy;
import fudan.se.lab5.entity.Drink;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemTriggerStrategy implements ComboStrategy {
    private final String strategyName;
    private final Set<String> targetSet;
    private final double discountPercent;

    public ItemTriggerStrategy(ArrayList<Node> argNodes) {
        String strategyName = argNodes.get(1).getTextContent();
        NodeList targetNodes = argNodes.get(2).getChildNodes();
        String discountPercentString = argNodes.get(3).getTextContent();

        double discountPercent = Double.parseDouble(discountPercentString);
        Set<String> targetSet = new HashSet<>();
        for (int i = 0; i < targetNodes.getLength(); ++i) {
            Node targetNode = targetNodes.item(i);
            if (targetNode instanceof Element) {
                targetSet.add(targetNode.getTextContent());
            }
        }


        if (targetSet.isEmpty() || discountPercent < 0 || discountPercent > 1)
            throw new RuntimeException(SystemInfoConstant.ITEM_INVALID_ARGUMENT);

        this.strategyName = strategyName;
        this.targetSet = targetSet;
        this.discountPercent = discountPercent;
    }

    @Override
    public String getStrategyName() {
        return strategyName;
    }

    @Override
    public DiscountPair getDiscount(List<Drink> orderItems, double priceBeforeDiscount, UserInfoConstant infoConstant, List<String> msgs, String currencyName) {
        double remainPercent = 1;
        for (Drink orderItem : orderItems) {
            if (targetSet.contains(orderItem.getName())) {
                remainPercent *= (1 - discountPercent);
                msgs.add(MessageFormat.format(infoConstant.getItemtriggerMsg(), strategyName, discountPercent));
                break;
            }
        }
        return new DiscountPair(0, (1 - remainPercent));
    }
}
