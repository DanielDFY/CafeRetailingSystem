package fudan.se.lab5.entity;

import fudan.se.lab5.constant.SystemInfoConstant;

public class Currency {
    private static final String symbolUS = "$";
    private static final String symbolRMB = "￥";
    private static final String symbolHK = "HK$";


    public static String getSymbol(String name) {
        switch (name) {
            case "USDollar":
                return symbolUS;
            case "RMB":
                return symbolRMB;
            case "HKDollar":
                return symbolHK;
            default:
                throw new RuntimeException(SystemInfoConstant.UNKNOWN_CURRENCY_NAME);
        }
    }
}
