package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Shopping extends AppCompatActivity {


    public static List<Product> products;
    public static List<CartItem> shoppingCartProducts;
    public static ShoppingCart shoppingCart;
    public static ItemsAdapter adapter;
    private Button buttonAdd;
    private Button buttonEndShopping;

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

        products = Product.createProductList(0);

        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.et_product_list);
        shoppingCartProducts = CartItem.createItemList(0);
        // Create adapter passing in the sample user data
        adapter = new ItemsAdapter(shoppingCartProducts);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        getProducts();
        getShoppingCart();

    }

    public void finalizeShopping () {

        Intent intent = new Intent(getApplicationContext(), ShoppingFinalize.class);
        startActivity(intent);
    }

    public void addProduct() {

        final int random = new Random().nextInt(products.size());

        String barcode = Shopping.products.get(random).barcode;

        Call<ShoppingCart> call = MainActivity.shopApi.addProduct(barcode);
        call.enqueue(new Callback<ShoppingCart>() {
            @Override
            public void onResponse(Call<ShoppingCart> call, Response<ShoppingCart> response) {

                if (!response.isSuccessful()) {
                    System.out.println("my on failure");
                    return;
                }
                Shopping.shoppingCart = response.body();
                Shopping.shoppingCartProducts.clear();
                Shopping.adapter.notifyDataSetChanged();

                List<CartItem> items = shoppingCart.items;

                for(CartItem item : items) {

                    Shopping.shoppingCartProducts.add(item);
                    Shopping.adapter.notifyItemInserted(0);
                }

            }

            @Override
            public void onFailure(Call<ShoppingCart> call, Throwable t) {
                System.out.println("my on failure2");
            }
        });


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