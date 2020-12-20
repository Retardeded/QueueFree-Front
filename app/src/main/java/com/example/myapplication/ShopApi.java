package com.example.myapplication;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ShopApi {
    @GET("products")
    Call<List<Product>> getProducts();

    @GET("shoppingCart")
    Call<ShoppingCart> getShoppingCart();

    //@PostMapping("/finalize")

    @POST("shoppingCart")
    Call<ShoppingCart> addProduct(@Query("barcode") String barcode);

    @POST("shoppingCart/enter")
    Call<ShoppingCart> enterShop();

    @POST("shoppingCart/finalize")
    Call<Receipt> finalizeShop();

    @DELETE("shoppingCart")
    Call<ShoppingCart> deleteProduct(@Query("barcode") String barcode);

    @POST("login")
    @FormUrlEncoded
    Call<Void> login(@Field("username") String name, @Field("password") String password);

    @POST("register")
    Call<Integer> register(@Body HashMap<String, Object> body);
}
