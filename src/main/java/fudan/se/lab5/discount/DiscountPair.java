package fudan.se.lab5.discount;

public class DiscountPair {
    private double discount;
    private double discountPercent;

    public DiscountPair() {
        this.discount = 0;
        this.discountPercent = 0;
    }

    public DiscountPair(double discount, double discountPercent) {
        if (discount < 0 || discountPercent < 0 || discountPercent > 1) {
            throw new RuntimeException("Invalid discount arguments");
        }
        this.discount = discount;
        this.discountPercent = discountPercent;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        if (discount < 0) {
            throw new RuntimeException("Invalid discount argument");
        }
        this.discount = discount;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        if (discountPercent < 0 || discountPercent > 1) {
            throw new RuntimeException("Invalid discount percent argument");
        }
        this.discountPercent = discountPercent;
    }
}
