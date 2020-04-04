package fudan.se.lab5.discount;

import java.util.ArrayList;
import java.util.List;

public class DiscountInfo {
    private final double priceBeforeDiscount;
    private final double discount;
    private final String discountType;
    private final List<String> msgs;

    public DiscountInfo(double priceBeforeDiscount) {
        this.priceBeforeDiscount = priceBeforeDiscount;
        this.discount = 0;
        this.discountType = "None";
        this.msgs = new ArrayList<>();
    }

    public DiscountInfo(double priceBeforeDiscount, double discount, String discountType, List<String> msgs) {
        this.priceBeforeDiscount = priceBeforeDiscount;
        this.discount = discount;
        this.discountType = discountType;
        this.msgs = msgs;
    }

    public double getPriceBeforeDiscount() { return priceBeforeDiscount; }

    public double getDiscount() {
        return discount;
    }

    public String getDiscountType() {
        return discountType;
    }

    public List<String> getMsgs() {
        return msgs;
    }
}