package com.example.myapplication.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.activities.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.ShoppingCart;
import com.example.myapplication.activities.Shopping;
import com.example.myapplication.model.CartItem;

import java.io.InputStream;
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

        View productView = inflater.inflate(R.layout.item, parent, false);

        ViewHolder viewHolder = new ViewHolder(productView);
        return viewHolder;
    }

    /**
     * Set up what is to be shown in a single row
     */

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CartItem item = mItems.get(position);

        TextView textView = holder.nameTextView;
        textView.setText(item.product.getName());
        String text = "ilość : " + (item.quantity);
        TextView quantityView = holder.quantityTextView;
        quantityView.setText(text);
        TextView priceView = holder.priceTextView;
        text = ((float)(item.product.price)/100 * item.quantity) + " zl";
        priceView.setText(text);
        ImageView imgView = holder.img;

        new DownloadImageTask((ImageView) imgView)
                .execute(item.product.imageUrl);

        Button removeButton = holder.removeButton;
        removeButton.setText("Usuń");
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Override
        public void onClick(View v) {

        }
        public TextView nameTextView;
        public TextView quantityTextView;
        public TextView priceTextView;
        public ImageView img;
        public Button removeButton;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.itemName);
            quantityTextView = (TextView) itemView.findViewById(R.id.itemQuantity);
            priceTextView = (TextView) itemView.findViewById(R.id.itemPrice);
            img = itemView.findViewById(R.id.itemImg);
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

            Call<ShoppingCart> call = MainActivity.shopApi.deleteProduct(barcode);
            call.enqueue(new Callback<ShoppingCart>() {
                @Override
                public void onResponse(Call<ShoppingCart> call, Response<ShoppingCart> response) {

                    if (!response.isSuccessful()) {
                        return;
                    }

                    /**
                     * Check if there is more than one this type of product in shoppingCart, if so only decerasing it's quantity is needed
                     */
                    if(Shopping.shoppingCartProducts.get(position).quantity > 1)
                    {
                        CartItem item = Shopping.shoppingCartProducts.get(position);
                        item.quantity = item.quantity-1;
                        Shopping.itemsAdapter.notifyItemChanged(position);
                        Shopping.productListHash.remove(item.product.barcode);
                        Shopping.productListHash.put(item.product.barcode, new Shopping.CartProductInfo(position, item.quantity));
                    }
                    /**
                     * Otherwise we need to remove whole product
                     */
                    else
                    {
                        Shopping.productListHash.remove(barcode);
                        Shopping.shoppingCartProducts.remove(position);
                        Shopping.itemsAdapter.notifyItemRemoved(position);
                        Shopping.itemsAdapter.notifyItemRangeChanged(position, mItems.size());

                    }

                }

                @Override
                public void onFailure(Call<ShoppingCart> call, Throwable t) {
                }
            });

        }

    }
}