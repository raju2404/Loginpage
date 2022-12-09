package com.example.ScanPe;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ScanPe.R;

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ViewHolder> {
    private List<ProductItem> ProductItems;
    private Context context;

    public ProductItemAdapter(List<ProductItem> ProductItems, Context context) {
        this.ProductItems = ProductItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_design,parent,false);
        return new ProductItemAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemAdapter.ViewHolder holder, int position) {
        ProductItem productItem=ProductItems.get(position);

        Picasso.get()
                .load(productItem.getIMAGE())
                .into(holder.product_image_view);

        holder.product_name_txt_view.setText(productItem.getPRODUCTNAME());
        holder.product_price_txt_view.setText(productItem.getPRICE());


    }

    @Override
    public int getItemCount() {
        return ProductItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView product_image_view;
        public TextView product_name_txt_view;
        public TextView product_price_txt_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            product_image_view = (ImageView) itemView.findViewById(R.id.product_image_view);
            product_name_txt_view = (TextView) itemView.findViewById(R.id.product_name_txt_view);
            product_price_txt_view = (TextView) itemView.findViewById(R.id.product_price_txt_view);
        }

    }
}
