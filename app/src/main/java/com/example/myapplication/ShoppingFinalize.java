package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingFinalize extends AppCompatActivity {

    Receipt receipt;
    public ArrayList<ReceiptItem> receiptItems;
    public ReceiptAdapter receiptAdapter;
    TextView billView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_finalize);

        billView = findViewById(R.id.et_bill_cost);

        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.et_receipt_list);
        receiptItems = ReceiptItem.createItemList(0);
        receiptAdapter = new ReceiptAdapter(receiptItems);
        rvContacts.setAdapter(receiptAdapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));

        shoppingFinalize();
    }


    public void shoppingFinalize() {

        Call<Receipt> call = MainActivity.shopApi.finalizeShop();
        call.enqueue(new Callback<Receipt>() {

            @Override
            public void onResponse(Call<Receipt> call, Response<Receipt> response) {
                if (!response.isSuccessful()) {
                    System.out.println("my on failure");
                    return;
                }
                receipt = response.body();
                receiptItems.clear();
                receiptAdapter.notifyDataSetChanged();
                int cost = 0;

                for(int i = 0; i < receipt.productsNames.size(); i++) {

                    String name = receipt.productsNames.get(i);
                    Integer price = receipt.productsPrices.get(i);
                    Integer quantity = receipt.productsQuantities.get(i);

                    cost += quantity * price;

                    ReceiptItem item = new ReceiptItem(name, price, quantity);
                    receiptItems.add(item);
                    receiptAdapter.notifyItemInserted(0);
                }

                billView.setText("Do zapłaty: " + cost + "zł");
            }

            @Override
            public void onFailure(Call<Receipt> call, Throwable t) {
                System.out.println("my on failure2");
            }
        });

    }
}