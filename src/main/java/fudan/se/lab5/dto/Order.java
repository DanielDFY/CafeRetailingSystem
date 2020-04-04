package fudan.se.lab5.dto;

import fudan.se.lab5.entity.AccountAssistant;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    private static final long serialVersionUID = 6442456165785725948L;
    private String currency;
    private String language;

    public String getCurrency() {
        return currency;
    }

    private String id;
    private List<OrderItem> orderItems;

    public Order(String language, String currency, List<OrderItem> orderItems) {
        SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String strDate = sfDate.format(new Date());
        this.id = strDate + AccountAssistant.getUserOnlineID();
        this.language = language;
        this.currency = currency;
        this.orderItems = orderItems;
    }

    public Order() {
        SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String strDate = sfDate.format(new Date());
        this.id = strDate + AccountAssistant.getUserOnlineID();
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
