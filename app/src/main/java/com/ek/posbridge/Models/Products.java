package com.ek.posbridge.Models;

public class Products {
    private String sku;
    private String upc;
    private Double price;

    public Products(String sku, String upc, Double price) {
        this.sku = sku;
        this.upc = upc;
        this.price = price;
    }


    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "sku: '" + this.sku + "', upc: '" + this.upc + "', price: '" + this.price + "'";
    }


}

