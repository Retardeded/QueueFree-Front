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

import java.util.ArrayList;

public class ReceiptItemAdapter extends
        RecyclerView.Adapter<ReceiptItemAdapter.ViewHolder> {

    private ArrayList<ReceiptItem> mItems;

    public ReceiptItemAdapter(ArrayList<ReceiptItem> subItemList) {
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

    /**
     * Set up what is to be shown in a single row
     */

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int productPrize = mItems.get(position).price;
        String productName = mItems.get(position).productName;
        int productQuantity = mItems.get(position).quantity;

        TextView nameView = holder.nameTextView;
        nameView.setText(productName);
        TextView prizeView = holder.prizeTextView;
        String text = (float)(productPrize) / 100 * productQuantity + "zł";
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