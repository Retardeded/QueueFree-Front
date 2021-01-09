package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Shopping extends AppCompatActivity {

    static class CartProductInfo
    {
        public Integer index;
        public Integer quantity;

        CartProductInfo(Integer index, Integer quantity){
            this.index = index;
            this.quantity = quantity;
        }
    };

    public static ArrayList<Product> products = new ArrayList<>();
    public static ArrayList<CartItem> shoppingCartProducts = new ArrayList<>();
    public static HashMap<String, CartProductInfo> productListHash = new HashMap<>();

    public static ShoppingCart shoppingCart;
    public static ItemsAdapter adapter;
    private Button buttonAdd;
    private Button buttonEndShopping;

    public static TextView resulttextview;
    Button scanbutton, buttontoast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        TableRow tableRow = (TableRow) findViewById(R.id.TableRow001);
        tableRow.setVisibility(View.INVISIBLE);

        buttonAdd = findViewById(R.id.btnAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetRandomProduct();
                //resulttextview.setText("5900394006235");
                addProduct();
            }
        });

        buttonEndShopping = findViewById(R.id.btnEndShopping);
        buttonEndShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizeShopping();
            }
        });

        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.et_product_list);
        adapter = new ItemsAdapter(shoppingCartProducts);
        rvContacts.setAdapter(adapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        getProducts();
        getShoppingCart();

        resulttextview = findViewById(R.id.barcodetextview);
        scanbutton = findViewById(R.id.buttonscan);
        buttontoast = findViewById(R.id.buttontoast);

        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ScanCodeActivity.class));
            }
        });

        buttontoast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Shopping.this, resulttextview.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void finalizeShopping () {

        Intent intent = new Intent(getApplicationContext(), ShoppingFinalize.class);
        startActivity(intent);
    }

    public static void addProduct() {

        String barcode = resulttextview.getText().toString();
        Call<ShoppingCart> call = MainActivity.shopApi.addProduct(barcode);
        call.enqueue(new Callback<ShoppingCart>() {
            @Override
            public void onResponse(Call<ShoppingCart> call, Response<ShoppingCart> response) {

                if (!response.isSuccessful()) {
                    System.out.println("my on failure");
                    return;
                }
                Shopping.shoppingCart = response.body();

                List<CartItem> items = shoppingCart.items;

                if(Shopping.productListHash.containsKey(barcode))
                {
                    int index = Shopping.productListHash.get(barcode).index;

                    Shopping.shoppingCartProducts.get(index).quantity += 1;
                    Shopping.adapter.notifyItemChanged(index);

                    Shopping.productListHash.remove(barcode);
                    Shopping.productListHash.put(barcode, new CartProductInfo(index, Shopping.shoppingCartProducts.get(index).quantity));
                }

                for(int i = 0; i < items.size(); i++) {

                    CartItem item = items.get(i);

                    if(!Shopping.productListHash.containsKey(item.product.barcode))
                    {
                        Shopping.shoppingCartProducts.add(item);
                        Shopping.adapter.notifyItemInserted(shoppingCartProducts.size()-1);

                        Shopping.productListHash.put(item.product.barcode, new CartProductInfo(shoppingCartProducts.size()-1, item.quantity));
                    }

                }

            }

            @Override
            public void onFailure(Call<ShoppingCart> call, Throwable t) {
                System.out.println("my on failure2");
            }
        });


    }

    private void SetRandomProduct() {
        int random = new Random().nextInt(10);
        random += 10;
        String barcode = Shopping.products.get(random).barcode;
        resulttextview.setText(barcode);
    }

    public void getShoppingCart() {

        Call<ShoppingCart> call = MainActivity.shopApi.enterShop();
        call.enqueue(new Callback<ShoppingCart>() {

            @Override
            public void onResponse(Call<ShoppingCart> call, Response<ShoppingCart> response) {
                if (!response.isSuccessful()) {
                    System.out.println("my on failure");
                    return;
                }
                ShoppingCart cart = response.body();
                shoppingCartProducts.clear();
                adapter.notifyDataSetChanged();
                List<CartItem> items = cart.items;

                for(CartItem item : items) {
                    shoppingCartProducts.add(item);
                    adapter.notifyItemInserted(0);
                }

            }

            @Override
            public void onFailure(Call<ShoppingCart> call, Throwable t) {
                System.out.println("my on failure2");
            }
        });

    }

    public void getProducts() {
        Call<List<Product>> call = MainActivity.shopApi.getProducts();
        call.enqueue(new Callback<List<Product>>() {

            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("my on failure");
                    return;
                }
                List<Product> productsList = response.body();

                for(Product product : productsList) {
                    products.add(product);
                }

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println("my on failure2");
            }
        });

    }

}