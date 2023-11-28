package edu.object.java23object;

import javafx.beans.property.SimpleStringProperty;

public class Order {
    private final SimpleStringProperty orderDate;
    private final SimpleStringProperty region;
    private final SimpleStringProperty rep1;

    Order(String orderDate, String region, String rep1) {
        this.orderDate = new SimpleStringProperty(orderDate);
        this.region = new SimpleStringProperty(region);
        this.rep1 = new SimpleStringProperty(rep1);
    }

    public String getOrderDate() {
        return orderDate.get();
    }

    public String getRegion() {
        return region.get();
    }

    public String getRep1() {
        return rep1.get();
    }

    public void setOrderDate(String value) {
        orderDate.set(value);
    }

    public void setRegion(String value) {
        region.set(value);
    }

    public void setRep1(String value) {
        rep1.set(value);
    }
}