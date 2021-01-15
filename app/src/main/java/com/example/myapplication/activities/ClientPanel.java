package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapters.ItemAdapter;
import com.example.myapplication.adapters.ReceiptAdapter;
import com.example.myapplication.model.ApiException;
import com.example.myapplication.model.Receipt;
import com.example.myapplication.model.ReceiptItem;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientPanel extends AppCompatActivity {

    Button btnGoMainPanel;
    Button btnShowLatestReceipts;
    ArrayList<RecyclerView> rvs= new ArrayList<>();
    RecyclerView rv1;
    RecyclerView rv2;
    RecyclerView rv3;
    int[] ids = new int[]{1000018, 1000016, 1000017};
    int maxLastReceipts = 3;
    public ArrayList<ArrayList<ReceiptItem>> listOfReceiptItems = new ArrayList<>();
    public ArrayList<ReceiptAdapter> receiptAdapters = new ArrayList<>();


    ArrayList<Receipt> receipts = new ArrayList<>();
    RecyclerView rvItem;
    LinearLayoutManager layoutManager;
    ItemAdapter itemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_panel);
        /*
        rv1 = findViewById(R.id.et_receipt1);
        rv2 = findViewById(R.id.et_receipt2);
        rv3 = findViewById(R.id.et_receipt3);
        rvs.add(rv1);
        rvs.add(rv2);
        rvs.add(rv3);


         for(int i = 0; i < maxLastReceipts; i++) {
            //RecyclerView rvReceipt = (RecyclerView) findViewById(idsArray[i]);
            listOfReceiptItems.add(new ArrayList<ReceiptItem>());
            ReceiptAdapter receiptAdapter = new ReceiptAdapter(listOfReceiptItems.get(i));
            receiptAdapters.add(receiptAdapter);
            //RecyclerView rv = findViewById(ids[i]);
            //rv.setLayoutManager(new LinearLayoutManager(this));
            rvs.get(i).setLayoutManager(new LinearLayoutManager(this));
            if(rvs.get(i) == null)
            {
                System.out.println("bad stuf");
            }
            else
            {
                rvs.get(i).setAdapter(receiptAdapter);
            }
        }

         */

        rvItem = findViewById(R.id.rv_item);
        layoutManager = new LinearLayoutManager(this);
        itemAdapter = new ItemAdapter(receipts);
        rvItem.setAdapter(itemAdapter);
        rvItem.setLayoutManager(layoutManager);



        btnGoMainPanel = findViewById(R.id.btnGoToMainPanel);

        btnGoMainPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(ClientPanel.this, MainPanel.class);
                //startActivity(i);
                finish();
            }
        });

        btnShowLatestReceipts = findViewById(R.id.btnShowLatestReceipts);

        btnShowLatestReceipts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getReceipts();
            }
        });
    }


    public void getReceipts() {

        Call<List<Receipt>> call = MainActivity.shopApi.getReceipts();
        call.enqueue(new Callback<List<Receipt>>() {

            @Override
            public void onResponse(Call<List<Receipt>> call, Response<List<Receipt>> response) {
                if (!response.isSuccessful()) {
                    try {
                        Gson gson = new Gson();
                        ApiException apiException = gson.fromJson(response.errorBody().string(), ApiException.class);
                        Toast.makeText(getApplicationContext(), apiException.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                ArrayList<Receipt> array = (ArrayList<Receipt>) response.body();

                //List<Receipt> items = receipts.items;
                //int cost = receipt.total;

                for(int i = 0; i < array.size(); i++) {
                    receipts.add(array.get(i));
                    itemAdapter.notifyItemInserted(receipts.size()-1);
                }

                /*
                for (int i=0; i<Math.min(receipts.size(),maxLastReceipts); i++) {

                    Receipt item = new Receipt("title",buildSubItemList());
                    itemList.add(item);
                }

                for (int i = 0; i < Math.min(receipts.size(),maxLastReceipts); i++) {

                    Receipt receipt = receipts.get(receipts.size()-1-i);
                    List<ReceiptItem> items = receipt.items;
                    int cost = receipt.total;

                    for(int j = 0; j < items.size(); j++) {
                        listOfReceiptItems.get(j).add(items.get(j));
                        receiptAdapters.get(j).notifyItemInserted(listOfReceiptItems.size()-1);
                    }
                }

                 */
            }

            @Override
            public void onFailure(Call<List<Receipt>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error. Check your Internet connection", Toast.LENGTH_SHORT).show();
            }
        });

    }
}