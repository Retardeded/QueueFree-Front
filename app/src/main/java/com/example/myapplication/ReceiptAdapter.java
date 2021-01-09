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

        View productView = inflater.inflate(R.layout.product, parent, false);

        ViewHolder viewHolder = new ViewHolder(productView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Integer productPrize = mItems.get(position).price;
        String productName = mItems.get(position).productName;
        Integer productQuantity = mItems.get(position).quantity;

        TextView nameView = holder.nameTextView;
        nameView.setText(productName);
        TextView prizeView = holder.prizeTextView;
        String text = (productPrize) + "zł";
        prizeView.setText(text);
        TextView quantityView = holder.quantityTextView;
        text = "ilość : " + (productQuantity);
        quantityView.setText(text);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView quantityTextView;
        public TextView prizeTextView;

        public ViewHolder(View productView) {

            super(productView);

            nameTextView = (TextView) itemView.findViewById(R.id.productName);
            quantityTextView = (TextView) itemView.findViewById(R.id.productQuantity);
            prizeTextView = (TextView) itemView.findViewById(R.id.productCost);


        }
    }
}