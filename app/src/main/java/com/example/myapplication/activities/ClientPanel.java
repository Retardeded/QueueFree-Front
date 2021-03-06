package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapters.ReceiptAdapter;
import com.example.myapplication.model.ApiException;
import com.example.myapplication.model.Receipt;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientPanel extends AppCompatActivity {

    Button buttonGoMainPanel;
    Button buttonShowLatestReceipts;
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
        tvId = findViewById(R.id.tv_user_id);
        if(MainActivity.userObj != null)
        {
            setUserData(MainActivity.userObj.getUsername(), MainActivity.userObj.getId());
        }
        buttonGoMainPanel = findViewById(R.id.btnGoToMainPanel);

        buttonGoMainPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonShowLatestReceipts = findViewById(R.id.btnShowLatestReceipts);
        buttonShowLatestReceipts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getReceipts();
            }
        });
    }

    public void setUserData(String userName, long id) {
        String text = "Nazwa użytkownika: " + userName;
        tvId.setText("Twoje id to: " + id);
        tvUser.setText(text);
    }

    /**
     * Get your receipts history from server
     */
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

                    /**
                     * Show receipts in reverse order from latest to oldest
                     */
                    for(int i = array.size()-1; i >= 0; i--) {
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