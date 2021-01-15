package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapters.ReceiptAdapter;
import com.example.myapplication.adapters.ReceiptItemAdapter;
import com.example.myapplication.model.ApiException;
import com.example.myapplication.model.Receipt;
import com.example.myapplication.model.ReceiptItem;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientPanel extends AppCompatActivity {

    Button btnGoMainPanel;
    Button btnShowLatestReceipts;
    TextView tvId;
    TextView tvUser;

    ArrayList<Receipt> receipts = new ArrayList<>();
    RecyclerView rvItem;
    LinearLayoutManager layoutManager;
    ReceiptAdapter receiptAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_panel);

        rvItem = findViewById(R.id.rv_item);
        layoutManager = new LinearLayoutManager(this);
        receiptAdapter = new ReceiptAdapter(receipts);
        rvItem.setAdapter(receiptAdapter);
        rvItem.setLayoutManager(layoutManager);

        tvUser = findViewById(R.id.tv_user_name);
        String text = "Nazwa użytkownika: " + MainActivity.userObj.getUsername();
        tvUser.setText(text);
        tvId = findViewById(R.id.tv_user_id);
        tvId.setText("Twoje id to: " + MainActivity.userObj.getId());
        btnGoMainPanel = findViewById(R.id.btnGoToMainPanel);

        btnGoMainPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClientPanel.this, MainPanel.class);
                startActivity(i);
                //finish();
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
                if(receipts.size() != array.size())
                {
                    receipts.clear();
                    receiptAdapter.notifyDataSetChanged();

                    for(int i = 0; i < array.size(); i++) {
                        receipts.add(array.get(i));
                        receiptAdapter.notifyItemInserted(receipts.size()-1);
                    }
                }


            }

            @Override
            public void onFailure(Call<List<Receipt>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error. Check your Internet connection", Toast.LENGTH_SHORT).show();
            }
        });

    }
}