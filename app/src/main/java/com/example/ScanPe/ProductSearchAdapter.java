package com.example.ScanPe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import static android.content.Context.MODE_PRIVATE;

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
        holder.txtPrice.setText(productItem.getPRICE());
        //holder.txtLocation.setText("Location : " + listItem.getLocation());
        holder.btn_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product_name=productItem.getPRODUCTNAME();
                String product_price=productItem.getPRICE();
                String product_id=productItem.getPRODUCTID();
                SharedPreferences preferences= context.getSharedPreferences("checkbox", MODE_PRIVATE);
                String name=preferences.getString("name","");

                //Toast.makeText(v.getContext(), name, Toast.LENGTH_LONG).show();

                AppDatabase db= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"cart_db").allowMainThreadQueries().build();
                ProductDao productDao=db.ProductDao();
                Boolean check=productDao.is_exist(Integer.parseInt(product_id));
                if(check==false)
                {

                    int pid=Integer.parseInt(product_id);
                    String userid=name;
                    String pname=product_name;
                    int price=Integer.parseInt(product_price);
                    int qnt=Integer.parseInt("1");
                    productDao.insertrecord(new Product(pid,pname,price,qnt));
                    String success_message=String.format("%s added to shopping cart",pname);
                    Toast.makeText(v.getContext(), success_message, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String Exists_message=String.format("%s already exists in the shopping cart, change the qty if you want",product_name);
                    Toast.makeText(v.getContext(), Exists_message, Toast.LENGTH_LONG).show();
                }

            }
        });

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
        public TextView txtProduct,txtPrice;
        public LinearLayoutCompat search_linear_layout;
        public Button btn_addtocart;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtProduct = (TextView) itemView.findViewById(R.id.txtProduct);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            search_linear_layout = (LinearLayoutCompat)itemView.findViewById(R.id.search_linear_layout);
            btn_addtocart= (Button) itemView.findViewById(R.id.btn_addtocart);


        }
    }
}
