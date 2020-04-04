package fudan.se.lab5.discount.components;

import fudan.se.lab5.constant.DiscountConstant;
import fudan.se.lab5.constant.UserInfoConstant;
import fudan.se.lab5.discount.DiscountComponent;
import fudan.se.lab5.discount.DiscountInfo;
import fudan.se.lab5.discount.DiscountPair;
import fudan.se.lab5.discount.components.timeTriggerStrategy.TimeTriggerStrategy;
import fudan.se.lab5.entity.Drink;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import static fudan.se.lab5.util.LogUtil.sysLogger;

public class TimeTriggerDiscountComponent implements DiscountComponent {
    private List<TimeTriggerStrategy> strategies;

    public TimeTriggerDiscountComponent(NodeList strategyList) {
        strategies = new ArrayList<>();

        for (int i = 0; i < strategyList.getLength(); ++i) {
            Node strategy = strategyList.item(i);
            if (strategy instanceof Element) {
                NodeList nodes = strategy.getChildNodes();
                ArrayList<Node> argNodes = new ArrayList<>();
                for (int j = 0; j < nodes.getLength(); ++j) {
                    Node node = nodes.item(j);
                    if (node instanceof Element) {
                        argNodes.add(node);
                    }
                }

                String className = DiscountConstant.TIMETRIGGER_STRATEGYPATH + argNodes.get(0).getTextContent();
                try {
                    Class<?> strategyClass = Class.forName(className);
                    Constructor<?> constructor = strategyClass.getConstructor(argNodes.getClass());
                    strategies.add((TimeTriggerStrategy)constructor.newInstance(argNodes));
                } catch (Exception e) {
                    sysLogger.error(" " + e.getMessage());
                    throw new RuntimeException("Failed to add time-trigger strategy, because: " + e.getMessage());
                }

            }
        }
    }

    @Override
    public String getComponentName() {
        return DiscountConstant.TIMETRIGGER_NAME;
    }

    @Override
    public DiscountInfo getDiscountObj(List<Drink> orderItems, double priceBeforeDiscount, String currencyName, UserInfoConstant infoConstant) {
        String discountType = DiscountConstant.TIMETRIGGER_NAME;
        List<String> msgs = new ArrayList<>();
        double discount = 0;
        double remainPercent = 1;

        if (!strategies.isEmpty()) {
            for (TimeTriggerStrategy strategy : strategies) {
                DiscountPair discountPair = strategy.getDiscount(infoConstant, msgs, currencyName);
                discount += discountPair.getDiscount();
                remainPercent *= (1 - discountPair.getDiscountPercent());
            }
        }

        return new DiscountInfo(priceBeforeDiscount, discount + (1 - remainPercent) * priceBeforeDiscount, discountType, msgs);
    }
}
