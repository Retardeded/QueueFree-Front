package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.myapplication.R;
import com.example.myapplication.ShopApi;
import com.example.myapplication.model.ApiException;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    private static final String KEY_EMPTY = "";
    public EditText etUsername;
    public EditText etPassword;
    public EditText etConfirmPassword;
    public EditText etFullName;
    public String user;
    public String password;
    public String confirmPassword;
    public String fullName;
    private ProgressDialog pDialog;
    public String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUpEditTexts();
        Button buttonRegister = findViewById(R.id.btnRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = etUsername.getText().toString();
                password = etPassword.getText().toString();
                confirmPassword = etConfirmPassword.getText().toString();
                fullName = etFullName.getText().toString();

                msg = "Wypełnij wymagane pola";
                if (validateInputs()) {
                    registerUser();
                } else {
                    Toast.makeText(Register.this, msg, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void setUpEditTexts() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etFullName = findViewById(R.id.etFullName);
    }

    /**
     * Display Progress bar while registering
     */
    private void displayLoader() {
        pDialog = new ProgressDialog(Register.this);
        pDialog.setMessage("Signing Up.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    /**
     * Launch Dashboard Activity on Successful Sign Up
     */
    public void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), DashBoard.class);
        i.putExtra("userInfo", user + " " + password);
        startActivity(i);

    }

    private void registerUser() {
        displayLoader();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + MainActivity.ipString)
                .addConverterFactory(GsonConverterFactory.create())
                .client(MainActivity.okHttpClient)
                .build();

        MainActivity.shopApi = retrofit.create(ShopApi.class);
        HashMap<String, Object> hashMap  = new HashMap<>();
        hashMap.put("username", user);
        hashMap.put("password", password);

        Call<Integer> call = MainActivity.shopApi.register(hashMap);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {

                pDialog.dismiss();
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
                loadDashboard();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Error. Check your Internet connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Validates inputs
     */
    public boolean validateInputs() {
        if (KEY_EMPTY.equals(fullName)) {
            return false;

        }
        if (KEY_EMPTY.equals(user)) {
            return false;
        }
        if (KEY_EMPTY.equals(password)) {
            return false;
        }

        if (KEY_EMPTY.equals(confirmPassword)) {
            return false;
        }
        if (!password.equals(confirmPassword)) {
            msg = "Pola hasło i potwierdź hasło nie są takie same";
            return false;
        }
        return true;
    }
}