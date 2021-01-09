package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapters.ReceiptAdapter;
import com.example.myapplication.model.Receipt;
import com.example.myapplication.model.ReceiptItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingFinalize extends AppCompatActivity {

    Receipt receipt;
    public ArrayList<ReceiptItem> receiptItems = new ArrayList<>();
    public ReceiptAdapter receiptAdapter;
    TextView billView;
    Button buttonPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_finalize);

        billView = findViewById(R.id.et_bill_cost);

        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.et_receipt_list);
        receiptAdapter = new ReceiptAdapter(receiptItems);
        rvContacts.setAdapter(receiptAdapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));

        shoppingFinalize();

        buttonPay = findViewById(R.id.btnPay);
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payForProducts();
            }
        });
    }

    public void payForProducts() {

        Call<Void> call = MainActivity.shopApi.payBill();
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    System.out.println("my on failure");
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), LeavingShop.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("my on failure2");
            }
        });
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
                List<ReceiptItem> items = receipt.items;
                int cost = receipt.total;

                System.out.println("TUUU" + items);
                System.out.println("TUU2" + items.get(0));
                for(int i = 0; i < items.size(); i++) {
                    receiptItems.add(items.get(i));
                    //System.out.println("STATY:: " + items.get(i).productPrice + " " + items.get(i).productQuantity + " " + items.get(i).productName);
                    receiptAdapter.notifyItemInserted(receiptItems.size()-1);
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