package fudan.se.lab5.constant;

public class DiscountConstant {
    // config path
    public static final String CONFIG_FILEPATH = "config/discountConfig.xml";

    // component path
    public static final String COMPONENT_PATH = "fudan.se.lab5.discount.components.";
    public static final String COMBO_STRATEGYPATH = "fudan.se.lab5.discount.components.comboStrategy.impl.";
    public static final String PRICEBREAK_STRATEGYPATH = "fudan.se.lab5.discount.components.pricebreakStrategy.impl.";
    public static final String TIMETRIGGER_STRATEGYPATH = "fudan.se.lab5.discount.components.timeTriggerStrategy.impl.";

    // price-break discount constant
    public static final String PRICEBREAK_NAME = "price-break";

    // combo discount constant
    public static final String COMBO_NAME = "combo";
    public static final int SMALL = 1;
    public static final int MEDIUM = 2;
    public static final int LARGE = 3;
    public static final int ALL = 4;

    // time-trigger discount constant
    public static final String TIMETRIGGER_NAME = "time-trigger";
}
