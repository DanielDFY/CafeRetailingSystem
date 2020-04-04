package fudan.se.lab5.constant;

public class SystemInfoConstant {
    // framework info
    public static final String Entity_EXIST = "{0} already exists, name: {1}";
    public static final String Entity_NOT_FOUND = "Object not found, name: {0}";
    public static final String FILE_CANNOT_BE_DIR = "Failed to create file, {0} is not a valid name";
    public static final String CREATING_PARENT_DIR = "Creating parent directory ...";
    public static final String FAILED_CREAT_DIR = "Failed to create target directory";
    public static final String FAILED_TO_CREATE_FILE = "Failed to create file: {0}";
    public static final String SUCCESS_TO_CREATE_FILE = "Success to create file: {0}";
    public static final String FAILED_TO_CREATE_FILE_REASON = "Failed to create file: {0}, because {1}";

    // order info constant
    public static final String UNKNOWN_LANGUAGE = "Unknown language name";
    public static final String UNKNOWN_INGREDIENT_NAME = "Unknown ingredient name";
    public static final String UNKNOWN_DRINK_NAME = "Unknown drink name";

    // discount info constant
    public static final String PRICEBREAK_FAILINFO = "Failed to calculate price-break discount, because {0}";
    public static final String COMBO_FAILINFO = "Failed to calculate combo discount, because {0}";
    public static final String TIMETRIGGER_FAILINFO = "Failed to calculate time-trigger discount, because {0}";
    public static final String ITEMTRIGGER_FAILINFO = "Failed to calculate item-trigger discount, because {0}";
    public static final String NEGATIVE_PRICE_INPUT = "Input price can't be negative";
    public static final String NULL_ORDERITEMS_INPUT = "Input order item list can't be null";
    public static final String EXISTED_STRATEGY_NAME = "Existed strategy name";
    public static final String UNKNOWN_STRATEGY_NAME = "Failed to remove unknown strategy";
    public static final String INVALID_ITEMTRIGGER_ARGUMENTS = "Order list shouldn't be null, price before discount should be non-negative";
    public static final String COMBO_INVALID_ARGUMENT = "Invalid combo strategy arguments";
    public static final String PRICEBREAK_INVALID_ARGUMENT = "Invalid arguments, threshold and unit discount should be non-negative";
    public static final String TIME_INVALID_ARGUMENT = "Invalid arguments, start date should be after end date, discount percent should be between 0 and 1";
    public static final String ITEM_INVALID_ARGUMENT = "Invalid arguments, requirements should'n be null, discount percent should be between 0 and 1";

    // payment info constant
    public static final String NULL_ORDER = "Order is null";
    public static final String FAILED_TO_PAY = "Failed to return payment info, because {0}";
    public static final String UNKNOWN_CURRENCY_NAME = "Unknown currency name";
    public static final String SUCCESS_TO_CREATE_ORDER = "Success to create order {0}: price {1}, discount {2}, discount price {3}";

    // config info
    public static final String DISCOUNTCONFIG_NOTFOUND = "cannot open discountConfig";
}
