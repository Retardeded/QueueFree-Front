package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemsAdapter extends
        RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private List<CartItem> mItems;

    public ItemsAdapter(List<CartItem> items) {
        mItems = items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View productView = inflater.inflate(R.layout.item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(productView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CartItem item = mItems.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;
        textView.setText(item.product.getName());
        TextView quantityView = holder.quantityTextView;
        //quantityView.setText(item.quantity);
        quantityView.setText("ilość : " + String.valueOf(item.quantity));
        Button removeButton = holder.removeButton;
        removeButton.setText("Usuń");
        removeButton.setId(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView quantityTextView;
        public Button removeButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.itemName);
            quantityTextView = (TextView) itemView.findViewById(R.id.itemQuantity);
            removeButton = (Button) itemView.findViewById(R.id.btnRemoveProduct);

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteProduct();
                }
            });
        }



        public void deleteProduct() {

            int position = removeButton.getId();
            String barcode = Shopping.products.get(position).barcode;


            Call<ShoppingCart> call = MainActivity.shopApi.deleteProduct(barcode);
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

                    List<CartItem> items = Shopping.shoppingCart.items;

                    for(CartItem item : items) {

                        Shopping.shoppingCartProducts.add(item);
                        // Notify the adapter that an item was inserted at position 0
                        Shopping.adapter.notifyItemInserted(0);
                    }

                }

                @Override
                public void onFailure(Call<ShoppingCart> call, Throwable t) {
                    System.out.println("my on failure2");
                }
            });

        }

    }
}