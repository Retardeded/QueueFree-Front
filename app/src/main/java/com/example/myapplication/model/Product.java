package com.example.myapplication.model;

public class Product {
    public long id;
    public String barcode;
    public String name;
    public int price;
    public String imageUrl;

    Product(long id, String barcode, String name, int price, String imageUrl){
        this.id = id;
        this.barcode = barcode;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }
    public String getName() {
        return name;
    }

    public String getBarCode() {
        return barcode;
    }

    private static int lastProductId = 0;

}
