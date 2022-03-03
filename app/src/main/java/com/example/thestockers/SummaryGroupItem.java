package com.example.thestockers;

public class SummaryGroupItem {
    private String item, cost;

    public SummaryGroupItem (String item, String cost) {
        this.cost = cost;
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
