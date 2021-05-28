package com.example.thelocalshopfinal;

public class ProductsModel {
    private  String ProductName;
    private String ProductCost;

    public ProductsModel()
    {}

    public ProductsModel(String productName, String productCost){
        ProductName = productName;
        ProductCost = productCost;
    }
    public String getProductName(){ return ProductName; }
    public String getProductCost(){ return ProductCost; }
}