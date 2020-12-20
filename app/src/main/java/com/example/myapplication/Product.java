package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Product {
    long id;
    //@SerializedName("body")
    String barcode;
    String name;
    int cost;

    Product(long id, String barcode, String name, int cost){
        this.id = id;
        this.barcode = barcode;
        this.name = name;
        this.cost = cost;
    }
    public String getName() {
        return name;
    }

    public String getBarCode() {
        return barcode;
    }

    private static int lastProductId = 0;

    public static List<Product> createProductList(int num) {
        List<Product> products = new ArrayList<Product>();

        for (int i = 1; i <= num; i++) {
            products.add(new Product(1, "323432", "chleb", 10));
        }

        return products;
    }
}
