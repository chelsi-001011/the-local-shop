package com.example.thelocalshopfinal;

public class StoresModel {
    private String StoreName, category, email, phone;
    private Double latitude, longitude;

    private StoresModel(){}

    private StoresModel(String storeName, String category, String email, String phone, Double latitude, Double longitude) {
        StoreName = storeName;
        this.category = category;
        this.email = email;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
