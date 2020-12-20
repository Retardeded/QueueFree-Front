package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class ReceiptItem {

    String productName;

    Integer productPrice;

    Integer productQuantity;

    ReceiptItem (String name, Integer price, Integer quantity) {
        productName = name;
        productPrice = price;
        productQuantity = quantity;
    }

    public static ArrayList<ReceiptItem> createItemList(int num) {
        ArrayList<ReceiptItem> items = new ArrayList<>();

        for (int i = 1; i <= num; i++) {
            items.add(new ReceiptItem("a", 1, 1));
        }

        return items;
    }
}
