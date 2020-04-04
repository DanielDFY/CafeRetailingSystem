package fudan.se.lab5.discount.components.comboStrategy.impl;

import fudan.se.lab5.constant.DiscountConstant;
import fudan.se.lab5.constant.SystemInfoConstant;
import fudan.se.lab5.constant.UserInfoConstant;
import fudan.se.lab5.discount.DiscountPair;
import fudan.se.lab5.discount.components.comboStrategy.ComboStrategy;
import fudan.se.lab5.entity.Currency;
import fudan.se.lab5.entity.Drink;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class OneFreePerSetStrategy implements ComboStrategy {
    private final String strategyName;
    private final Set<String> targetSet;
    private final int targetSize;
    private final int setAmount;

    public OneFreePerSetStrategy(ArrayList<Node> argNodes) {
        String strategyName = argNodes.get(1).getTextContent();
        NodeList targetNodes = argNodes.get(2).getChildNodes();
        String targetSizeSting = argNodes.get(3).getTextContent();
        String setAmountString = argNodes.get(4).getTextContent();

        int targetSize = Integer.parseInt(targetSizeSting);
        int setAmount = Integer.parseInt(setAmountString);
        Set<String> targetSet = new HashSet<>();
        for (int i = 0; i < targetNodes.getLength(); ++i) {
            Node targetNode = targetNodes.item(i);
            if (targetNode instanceof Element) {
                targetSet.add(targetNode.getTextContent());
            }
        }

        if (targetSet.isEmpty() || targetSize < 0 || targetSize > 4 || setAmount < 1) {
            throw new RuntimeException(SystemInfoConstant.COMBO_INVALID_ARGUMENT);
        }


        this.strategyName = strategyName;
        this.targetSet = targetSet;
        this.targetSize = targetSize;
        this.setAmount = setAmount;
    }

    @Override
    public String getStrategyName() {
        return strategyName;
    }

    @Override
    public DiscountPair getDiscount(List<Drink> orderItems, double priceBeforeDiscount, UserInfoConstant infoConstant, List<String> msgs, String currencyName) {
        int freeNum;
        List<Double> list = new ArrayList<>();

        for (Drink orderItem : orderItems) {
            if (targetSet.contains(orderItem.getName()) && (targetSize == DiscountConstant.ALL || targetSize == orderItem.getSize())) {
                list.add(orderItem.getPrice());
            }
        }
        freeNum = list.size() / (setAmount + 1);
        list.sort(Collections.reverseOrder());
        double discount = 0.0;
        for (int i = 0; i < freeNum && i < list.size(); ++i) {
            discount += list.get(i) ;
        }

        msgs.add(MessageFormat.format(infoConstant.getComboOneFreePerSet(), strategyName, Drink.getSizeName(targetSize), setAmount, Currency.getSymbol(currencyName), discount));
        return new DiscountPair(discount, 0);
    }
}
