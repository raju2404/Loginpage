package com.example.ScanPe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ScanPe.R;

public class AddtoCartAdapter extends RecyclerView.Adapter<AddtoCartAdapter.viewholder> {

    List<Product> products;
    TextView rateview;
    public AddtoCartAdapter(List<Product> products, TextView rateview) {
        this.products = products;
        this.rateview= rateview;
    }

    @NonNull
    @NotNull
    @Override
    public viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_row,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AddtoCartAdapter.viewholder holder, int position) {
        holder.recid.setText(String.valueOf(products.get(position).getPid()));
        holder.recpname.setText(products.get(position).getPname());
        holder.recpprice.setText(String.valueOf(products.get(position).getPrice()));
        holder.recqnt.setText(String.valueOf(products.get(position).getQnt()));

        holder.delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase db = Room.databaseBuilder(holder.recid.getContext(),
                        AppDatabase.class, "cart_db").allowMainThreadQueries().build();
                ProductDao productDao = db.ProductDao();

                productDao.deleteById(products.get(position).getPid());
                products.remove(position);
                notifyItemRemoved(position);
                updateprice();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class viewholder extends RecyclerView.ViewHolder
    {
        TextView recid,recpname,recqnt, recpprice;
        ImageButton delbtn;
        public viewholder(@NonNull @NotNull View itemView) {
            super(itemView);

            recid=itemView.findViewById(R.id.recid);
            recpname=itemView.findViewById(R.id.recpname);
            recpprice=itemView.findViewById(R.id.recpprice);
            recqnt=itemView.findViewById(R.id.recqnt);
            delbtn=itemView.findViewById(R.id.delbtn);
        }
    }

    public void updateprice()
    {
        int sum=0,i;
        for(i=0;i< products.size();i++)
            sum=sum+(products.get(i).getPrice()*products.get(i).getQnt());

        rateview.setText("Total Amount : INR "+sum);
    }



}
