package com.example.vegdoframer.model;

public class VegetableListModel {
    String vegetableName, vegetablePrice;

    public VegetableListModel(String vegetableName, String vegetablePrice) {
        this.vegetableName = vegetableName;
        this.vegetablePrice = vegetablePrice;
    }

    public String getVegetableName() {
        return vegetableName;
    }

    public void setVegetableName(String vegetableName) {
        this.vegetableName = vegetableName;
    }

    public String getVegetablePrice() {
        return vegetablePrice;
    }

    public void setVegetablePrice(String vegetablePrice) {
        this.vegetablePrice = vegetablePrice;
    }
}
