package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static ShopApi shopApi;
    public static OkHttpClient okHttpClient;
    private EditText loginText;
    private EditText passwordText;

    private Button buttonLogIn;
    private Button buttonRegister;
    String user = "user";
    String password = "haslo";
    private static final String KEY_EMPTY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loginText = findViewById(R.id.etLoginUsername);
        passwordText = findViewById(R.id.etLoginPassword);

        autoFillCredentials();
        setUpButtons();

        createClient();

    }

    void createClient() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        MainActivity.okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request newRequest = originalRequest.newBuilder()
                                .header("Interceptor-Header", "xyz")
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(loggingInterceptor)
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();
    }

    private void setUpButtons() {
        buttonLogIn = findViewById(R.id.btnLogin);
        buttonRegister = findViewById(R.id.btnDoRegister);
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = loginText.getText().toString();
                password = passwordText.getText().toString();

                if(validateInputs()) {
                    logIn();
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });
    }

    private void autoFillCredentials() {
        loginText.setText(user);
        passwordText.setText(password);
    }

    public void doRegister() {
        Intent intent = new Intent(getApplicationContext(), Register.class);
        startActivity(intent);
    }

    public void logIn() {

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                //.baseUrl("http://localhost:8080/")
                .baseUrl("http://10.0.0.5:8080/")
                //.baseUrl("http://192.168.1.3:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(MainActivity.okHttpClient)
                .build();


        MainActivity.shopApi = retrofit.create(ShopApi.class);

        Call<Void> call = MainActivity.shopApi.login(user, password);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    System.out.println("my on failure");
                    return;
                }
                System.out.println("login succes");
                Intent intent = new Intent(getApplicationContext(), MainPanel.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("my on failure2");
            }
        });
    }

    boolean validateInputs() {

        if (KEY_EMPTY.equals(user)) {
            return false;
        }
        if (KEY_EMPTY.equals(password)) {
            return false;
        }

        return true;
    }
}