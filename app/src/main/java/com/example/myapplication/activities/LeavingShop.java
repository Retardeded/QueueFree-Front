package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.ApiException;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeavingShop extends AppCompatActivity {


    Button buttonLeaveShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaving_shop);

        buttonLeaveShop = findViewById(R.id.btnConfirmExit);
        buttonLeaveShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmExit();
            }
        });
    }

    public void confirmExit() {
        Call<Void> call = MainActivity.shopApi.confirmExit();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
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
                launchMainPanel();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error. Check your Internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void launchMainPanel() {
        Intent intent = new Intent(getApplicationContext(), MainPanel.class);
        startActivity(intent);
        finish();
    }
}