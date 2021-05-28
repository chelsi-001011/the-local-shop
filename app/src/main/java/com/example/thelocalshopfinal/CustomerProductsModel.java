package com.example.thelocalshopfinal;

public class CustomerProductsModel {

    private String product_name;
    private String product_cost;

    public CustomerProductsModel() {}

    public CustomerProductsModel(String product_name, String product_cost) {
        this.product_name = product_name;
        this.product_cost = product_cost;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_cost() {
        return product_cost;
    }

    public void setProduct_cost(String product_cost) {
        this.product_cost = product_cost;
    }
}
