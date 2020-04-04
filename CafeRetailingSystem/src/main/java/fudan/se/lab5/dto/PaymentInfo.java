package fudan.se.lab5.dto;

import java.io.Serializable;
import java.util.List;

public class PaymentInfo implements Serializable {
    private static final long serialVersionUID = -5743364759168621824L;
    private String orderID;
    private double price;
    private double discount;
    private double discountPrice;
    private List<String> msgs;

    public PaymentInfo(String orderID, double price, double discount, double discountPrice, List<String> msgs) {
        this.orderID = orderID;
        this.price = price;
        this.discount = discount;
        this.discountPrice = discountPrice;
        this.msgs = msgs;
    }

    public PaymentInfo() {
    }

    public String getID() {
        return orderID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public List<String> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<String> msgs) {
        this.msgs = msgs;
    }
}
