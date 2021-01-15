package com.example.myapplication.model;

import com.example.myapplication.User;

import java.util.ArrayList;
import java.util.List;

public class Receipt {

    public long id;
    public User user;
    public ArrayList<ReceiptItem> items;
    public int total;
    private String itemTitle;

    public Receipt(String itemTitle, ArrayList<ReceiptItem> subItemList) {
        this.itemTitle = itemTitle;
        this.items = subItemList;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public ArrayList<ReceiptItem> getSubItemList() {
        return items;
    }

    public void setSubItemList(ArrayList<ReceiptItem> subItemList) {
        this.items = subItemList;
    }

}
