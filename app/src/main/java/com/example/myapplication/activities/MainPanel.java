package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.ApiException;
import com.example.myapplication.model.CartItem;
import com.example.myapplication.model.ShoppingCart;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPanel extends AppCompatActivity {

    private Button buttonClientPanel;
    private Button buttonStartShopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_panel);

        buttonClientPanel = findViewById(R.id.btnClientPanel);
        buttonStartShopping = findViewById(R.id.btnStartShopping);

        buttonClientPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewClientPanel();
            }
        });

        buttonStartShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startShopping();
            }
        });
    }

    public void viewClientPanel() {

        Intent intent = new Intent(getApplicationContext(), ClientPanel.class);
        startActivity(intent);
    }

    public void startShopping() {
        Call<ShoppingCart> call = MainActivity.shopApi.enterShop();
        call.enqueue(new Callback<ShoppingCart>() {

            @Override
            public void onResponse(Call<ShoppingCart> call, Response<ShoppingCart> response) {
                if (!response.isSuccessful()) {
                    try {
                        Gson gson = new Gson();
                        ApiException apiException = gson.fromJson(response.errorBody().string(), ApiException.class);
                        Toast.makeText(MainPanel.this, apiException.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), Shopping.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ShoppingCart> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error. Check your Internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}