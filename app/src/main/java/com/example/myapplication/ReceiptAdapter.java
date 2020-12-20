package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiptAdapter extends
        RecyclerView.Adapter<ReceiptAdapter.ViewHolder> {

    private List<ReceiptItem> mItems;

    public ReceiptAdapter(ArrayList<ReceiptItem> items) {

        mItems = items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View productView = inflater.inflate(R.layout.product, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(productView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Integer productPrize = mItems.get(position).productPrice;
        String productName = mItems.get(position).productName;
        Integer productQuantity = mItems.get(position).productQuantity;

        // Set item views based on your views and data model
        TextView nameView = holder.nameTextView;
        nameView.setText(productName);
        TextView prizeView = holder.prizeTextView;
        prizeView.setText(String.valueOf(productPrize));
        TextView quantityView = holder.quantityTextView;
        quantityView.setText(String.valueOf(productQuantity));
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
        public TextView prizeTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View productView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(productView);

            nameTextView = (TextView) itemView.findViewById(R.id.productName);
            quantityTextView = (TextView) itemView.findViewById(R.id.productQuantity);
            prizeTextView = (TextView) itemView.findViewById(R.id.productCost);


        }
    }
}