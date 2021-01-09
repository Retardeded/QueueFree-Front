package com.example.myapplication.model;

import com.example.myapplication.User;

import java.util.List;

public class Receipt {

    public long id;
    public User user;
    public List<ReceiptItem> items;
    public int total;
}
