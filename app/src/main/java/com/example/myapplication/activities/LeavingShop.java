package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeavingShop extends AppCompatActivity {


    Button leaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaving_shop);

        leaveButton = findViewById(R.id.btnConfirmExit);
        leaveButton.setOnClickListener(new View.OnClickListener() {
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
                    System.out.println("my on failure");
                    return;
                }

                Toast.makeText(LeavingShop.this, "Pomyślne wyjście", Toast.LENGTH_SHORT).show();
                leaveButton.setText("Pomyślne wyjście");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("my on failure2");
            }
        });
    }
}