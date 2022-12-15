package com.example.ScanPe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ScanPe.R;

public class ProductSearchAdapter extends RecyclerView.Adapter<ProductSearchAdapter.ViewHolder> {

    private List<ProductItem> productItems;
    private Context context;

    public ProductSearchAdapter(List<ProductItem> productItems, Context context) {
        this.productItems = productItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_product_item_design,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductItem productItem = productItems.get(position);

        holder.txtProduct.setText(productItem.getPRODUCTNAME());
        //holder.txtLocation.setText("Location : " + listItem.getLocation());
    }

    @Override
    public int getItemCount() {
        return productItems.size();
    }
    public void filterList(ArrayList<ProductItem> filteredList) {
        productItems = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtProduct;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtProduct = (TextView) itemView.findViewById(R.id.txtProduct);

        }
    }
}
