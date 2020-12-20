package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class CartItem {



        private long id;

        private ShoppingCart shoppingCart;

        public Product product;

        public int quantity;

        public CartItem(ShoppingCart shoppingCart, Product product, int quantity) {
                this.shoppingCart = shoppingCart;
                this.product = product;
                this.quantity = quantity;
        }

        public static List<CartItem> createItemList(int num) {
                List<CartItem> items = new ArrayList<CartItem>();

                for (int i = 1; i <= num; i++) {
                        items.add(new CartItem(new ShoppingCart(), new Product(1, "323432", "chleb", 10), 1));
                }

                return items;
        }
}
