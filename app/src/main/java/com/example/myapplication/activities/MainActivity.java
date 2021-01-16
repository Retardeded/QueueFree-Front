package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.ShopApi;
import com.example.myapplication.model.User;
import com.example.myapplication.model.ApiException;
import com.example.myapplication.model.ShoppingCart;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
//import okhttp3.WebSocket;
//import okhttp3.WebSocketListener;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
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
    public String user = "user";
    public String password = "haslo";
    private static final String KEY_EMPTY = "";
    public static User userObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loginText = findViewById(R.id.etLoginUsername);
        passwordText = findViewById(R.id.etLoginPassword);

        autoFillCredentials(user, password);
        setUpButtons();

        createClient();

    }

    public void createClient() {
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

                if (validateInputs()) {
                    logIn();
                } else {
                    Toast.makeText(MainActivity.this, "Wypełnij pola loginu i hasła", Toast.LENGTH_SHORT).show();
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

    public void autoFillCredentials(String user, String password) {
        loginText.setText(user);
        passwordText.setText(password);
    }

    public void doRegister() {
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
    }

    public void logIn() {

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                //.baseUrl("http://localhost:8080/")
                //.baseUrl("http://10.0.0.5:8080/")
                .baseUrl("http://192.168.1.3:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(MainActivity.okHttpClient)
                .build();


        MainActivity.shopApi = retrofit.create(ShopApi.class);

        Call<Void> call = MainActivity.shopApi.login(user, password);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Bad credentials", Toast.LENGTH_SHORT).show();
                    return;
                }
                dispatchAfterLogin();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error. Check your Internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void dispatchAfterLogin() {
        Call<ShoppingCart> call = MainActivity.shopApi.getShoppingCart();
        call.enqueue(new Callback<ShoppingCart>() {
            @Override
            public void onResponse(Call<ShoppingCart> call, Response<ShoppingCart> response) {
                createSocket();
                setUser();

                Intent intent = null;

                if (!response.isSuccessful()) {
                    try {
                        Gson gson = new Gson();
                        ApiException apiException = gson.fromJson(response.errorBody().string(), ApiException.class);

                        switch (apiException.getMessage()) {
                            case "Customer not in shop":
                                intent = new Intent(MainActivity.this, MainPanel.class);
                                break;
                            case "Customer should head towards exit":
                                intent = new Intent(MainActivity.this, LeavingShop.class);
                                break;
                            case "Customer should make payment":
                                intent = new Intent(MainActivity.this, ShoppingFinalize.class);
                                break;
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    intent = new Intent(MainActivity.this, Shopping.class);
                }

                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<ShoppingCart> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error. Check your Internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void lanuchOfMainPanel() {
        Intent intent = new Intent(getApplicationContext(), MainPanel.class);
        startActivity(intent);
    }

    public void createSocket() {
        //Request request = new Request.Builder().url("ws://10.0.0.5:8080/websocket").build();
        Request request = new Request.Builder().url("ws://192.168.1.3:8080/websocket").build();

        WebSocketListener webSocketListenerCoinPrice = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, okhttp3.Response response) {
                webSocket.send("CONNECT\naccept-version:1.1,1.0\n" +
                        "heart-beat:10000,10000\n\n\0");
                webSocket.send("SUBSCRIBE\nid:sub-0\ndestination:/user/notification\n\n\0");
            }

            @Override
            public void onMessage(WebSocket webSocket, String body) {
                String[] split = body.split("\n\n");
                if (split.length < 2)
                    return;

                String message = split[1];
                if (message.length() == 1)
                    return;

                System.out.println(body);
                System.out.println(message.length());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                });
            }

        };
        MainActivity.okHttpClient.newWebSocket(request, webSocketListenerCoinPrice);
        MainActivity.okHttpClient.dispatcher().executorService();
    }

    public void setUser() {
        Call<User> call = MainActivity.shopApi.getUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                MainActivity.userObj = response.body();
                if(MainActivity.userObj != null)
                    MainPanel.showQr(Long.toString(MainActivity.userObj.getId()));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error. Check your Internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean validateInputs() {

        if (KEY_EMPTY.equals(user)) {
            return false;
        }
        if (KEY_EMPTY.equals(password)) {
            return false;
        }

        return true;
    }
}