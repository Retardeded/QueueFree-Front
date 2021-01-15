package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.ReceiptItem;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class ReceiptAdapter extends
        RecyclerView.Adapter<ReceiptAdapter.ViewHolder> {

    private ArrayList<ReceiptItem> mItems;

    public ReceiptAdapter(ArrayList<ReceiptItem> subItemList) {
        this.mItems = subItemList;
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