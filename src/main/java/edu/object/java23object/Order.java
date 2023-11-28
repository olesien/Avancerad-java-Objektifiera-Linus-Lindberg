package edu.object.java23object;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Order {
    private final SimpleStringProperty orderDate;
    private final SimpleStringProperty region;
    private final SimpleStringProperty rep1;

    private final SimpleStringProperty rep2;
    private final SimpleStringProperty item;
    private final SimpleStringProperty units;
    private final SimpleStringProperty unitCost;
    private final SimpleStringProperty total;

    Order(String orderDate, String region, String rep1, String rep2, String item, String units, String unitCost, String total) {
        this.orderDate = new SimpleStringProperty(orderDate);
        this.region = new SimpleStringProperty(region);
        this.rep1 = new SimpleStringProperty(rep1);
        this.rep2 = new SimpleStringProperty(rep2);
        this.item = new SimpleStringProperty(item);
        this.units = new SimpleStringProperty(units);
        this.unitCost = new SimpleStringProperty(unitCost);
        this.total = new SimpleStringProperty(total);}

    public String getOrderDate() {
        return orderDate.get();
    }

    public String getRegion() {
        return region.get();
    }

    public String getRep1() {
        return rep1.get();
    }

    public String getRep2() {
        return rep2.get();
    }

    public String getItem() {
        return item.get();
    }

    public String getUnits() {
        return units.get();
    }

    public String getUnitCost() {
        return unitCost.get();
    }

    public String getTotal() {
        return total.get();
    }

    public String[] toCustomArray() {
        String[] list = {orderDate.get(),region.get(), rep1.get(), rep2.get(), item.get(), units.get(), unitCost.get(), total.get() };

        return list;
    }


}