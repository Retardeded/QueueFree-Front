package com.example.myapplication.model;

import com.example.myapplication.User;

import java.util.ArrayList;
import java.util.List;

public class Receipt {

    public long id;
    public User user;
    public ArrayList<ReceiptItem> items;
    public int total;

    public Receipt(ArrayList<ReceiptItem> subItemList) {
        this.items = subItemList;
    }

    public String getItemTitle() {
        return "Koszt paragonu " + String.valueOf((float)total/100) + "zł";
    }

    public ArrayList<ReceiptItem> getSubItemList() {
        return items;
    }

}
