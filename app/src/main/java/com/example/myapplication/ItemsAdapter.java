package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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
    private AdapterView.OnItemClickListener mItemClickListener;

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
        //Button removeButton = holder.removeButton;
        //removeButton.setText("Usuń");
        //removeButton.setId(position);

        System.out.println("pozycjaBUT: " + position);


    }



    @Override
    public int getItemCount() {
        return mItems.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access


    /*
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nameTextView;
        public TextView quantityTextView;
        public ImageView mImageViewContentPic;

        public ImageView imgViewRemoveIcon;
        public ViewHolder(View v) {
            super(v);
            //mCardView = (CardView) v.findViewById(R.id.card_view);
            nameTextView = (TextView) v.findViewById(R.id.itemName);
            quantityTextView = (TextView) v.findViewById(R.id.itemQuantity);
            //mImageViewContentPic = (ImageView) v.findViewById(R.id.item_content_pic);
            //......
            //imgViewRemoveIcon = (ImageView) v.findViewById(R.id.remove_icon);

            quantityTextView.setOnClickListener(this);
            imgViewRemoveIcon.setOnClickListener(this);
            v.setOnClickListener(this);
            quantityTextView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(view, getAdapterPosition());
                    }
                    return false;
                }
            });
        }


        @Override
        public void onClick(View v) {
            //Log.d("View: ", v.toString());
            //Toast.makeText(v.getContext(), mTextViewTitle.getText() + " position = " + getPosition(), Toast.LENGTH_SHORT).show();
            if(v.equals(imgViewRemoveIcon)){
                removeAt(getAdapterPosition());
            }else if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public void setOnItemClickListener(final AdapterView.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    public void removeAt(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mItems.size());
    }

     */

    public void removeAt(int position) {

        mItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mItems.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            //Log.d("View: ", v.toString());
            //Toast.makeText(v.getContext(), mTextViewTitle.getText() + " position = " + getPosition(), Toast.LENGTH_SHORT).show();
            //removeAt(getAdapterPosition());
            //deleteProduct();

        }
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

            int position = getAdapterPosition();
            String barcode = Shopping.shoppingCartProducts.get(position).product.barcode;
            System.out.println("CODEBAR: " + barcode);
            System.out.println("POZYCJA: " + position);


            Call<ShoppingCart> call = MainActivity.shopApi.deleteProduct(barcode);
            call.enqueue(new Callback<ShoppingCart>() {
                @Override
                public void onResponse(Call<ShoppingCart> call, Response<ShoppingCart> response) {

                    if (!response.isSuccessful()) {
                        System.out.println("my on failure");
                        return;
                    }

                    if(Shopping.shoppingCartProducts.get(position).quantity > 1)
                    {
                        CartItem item = Shopping.shoppingCartProducts.get(position);

                        item.quantity = item.quantity-1;
                        Shopping.adapter.notifyItemChanged(position);

                        Shopping.productListHash.remove(item.product.barcode);
                        Shopping.productListHash.put(item.product.barcode, new Shopping.CartProductInfo(position, item.quantity));
                    }
                    else
                    {
                        Shopping.productListHash.remove(barcode);
                        Shopping.shoppingCartProducts.remove(position);
                        Shopping.adapter.notifyItemRemoved(position);
                        Shopping.adapter.notifyItemRangeChanged(position, mItems.size());

                    }

                    /*
                    Shopping.shoppingCart = response.body();
                    //Shopping.shoppingCartProducts.clear();
                    //Shopping.adapter.notifyDataSetChanged();

                    List<CartItem> items = Shopping.shoppingCart.items;

                        for(CartItem item : items) {

                        Shopping.shoppingCartProducts.add(item);
                        // Notify the adapter that an item was inserted at position 0
                        Shopping.adapter.notifyItemInserted(0);
                    }

                     */

                }

                @Override
                public void onFailure(Call<ShoppingCart> call, Throwable t) {
                    System.out.println("my on failure2");
                }
            });

        }

    }
}