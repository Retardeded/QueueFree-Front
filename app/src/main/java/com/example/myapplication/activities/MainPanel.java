package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.ApiException;
import com.example.myapplication.model.CartItem;
import com.example.myapplication.model.ShoppingCart;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPanel extends AppCompatActivity {

    private Button buttonClientPanel;
    private Button buttonStartShopping;
    private Button buttonLogout;
    private static ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_panel);

        buttonClientPanel = findViewById(R.id.btnClientPanel);
        buttonStartShopping = findViewById(R.id.btnStartShopping);
        buttonLogout = findViewById(R.id.btnLogOut);
        imageView = findViewById(R.id.qrImg);

        //showQr();

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

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    public static void showQr() {
        String text = Long.toString(MainActivity.userObj.getId()); // Whatever you need to encode in the QR code
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 300, 300);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public void viewClientPanel() {

        Intent intent = new Intent(getApplicationContext(), ClientPanel.class);
        startActivity(intent);
        //finish();
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
                finish();
            }

            @Override
            public void onFailure(Call<ShoppingCart> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error. Check your Internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void logout() {
        Call<Void> call = MainActivity.shopApi.logout();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
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

                MainActivity.okHttpClient.dispatcher().executorService().shutdownNow();
                MainActivity.okHttpClient.dispatcher().cancelAll();
                MainActivity.okHttpClient = null;

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error. Check your Internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}