package fudan.se.lab5.entity;

import fudan.se.lab5.constant.StyleConstant;

public class IngredientEntity {
    private String name;
    private int number;
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static boolean validDrinkNameStyle(String name) {
        return !StyleConstant.INVALID_DRINKNAME_STYLE.equals(name);
    }

    public double cost() {
        return this.getPrice() * number;
    }
}
