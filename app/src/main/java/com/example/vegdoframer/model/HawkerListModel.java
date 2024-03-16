package com.example.vegdoframer.model;

public class HawkerListModel {
    String name, price, hawkerLocation;

    public HawkerListModel() {

    }

    public HawkerListModel(String name, String price, String hawkerLocation) {
        this.name = name;
        this.price = price;
        this.hawkerLocation = hawkerLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHawkerLocation() {
        return hawkerLocation;
    }

    public void setHawkerLocation(String hawkerLocation) {
        this.hawkerLocation = hawkerLocation;
    }
}
