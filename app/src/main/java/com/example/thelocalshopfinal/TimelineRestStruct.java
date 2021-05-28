package com.example.thelocalshopfinal;

public class TimelineRestStruct {
    String cost;
    String status;
    String store;

    public TimelineRestStruct(String cost, String status, String store) {
        this.cost = cost;
        this.status = status;
        this.store = store;
    }
    public TimelineRestStruct() {

    }


    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }
}
