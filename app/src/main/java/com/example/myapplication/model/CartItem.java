package com.example.myapplication.model;

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
}
