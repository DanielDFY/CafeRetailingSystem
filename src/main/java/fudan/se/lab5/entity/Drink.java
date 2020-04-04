package fudan.se.lab5.entity;

import fudan.se.lab5.constant.StyleConstant;
import fudan.se.lab5.dto.Ingredient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fudan.se.lab5.constant.DiscountConstant.*;

public class Drink {
    private String name;
    private String description;
    private double price;
    private String type;
    private Map<Integer, Double> sizePrizeMap;
    private List<Ingredient> ingredients;

    public static String getSizeName(int size) {
        switch (size) {
            case 1:
                return "small";
            case 2:
                return "normal";
            case 3:
                return "large";
            default:
                return "all";
        }
    }

    private int size;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double cost() {
        return this.getPrice() + sizePrizeMap.get(this.getSize());
    }

    public void setSizePrizeMap(double small, double medium, double large) {
        sizePrizeMap = new HashMap<>();
        this.sizePrizeMap.put(SMALL, small);
        this.sizePrizeMap.put(MEDIUM, medium);
        this.sizePrizeMap.put(LARGE, large);
    }

    public Map<Integer, Double> getSizePrizeMap() {
        return sizePrizeMap;
    }

    public static boolean validDrinkNameStyle(String name) {
        return !StyleConstant.INVALID_DRINKNAME_STYLE.equals(name);
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

}
