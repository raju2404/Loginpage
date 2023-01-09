package com.example.ScanPe;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

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
        holder.product_price_txt_view.setText(String.format( "Price :%s",productItem.getPRICE()) );
        holder.btn_scan_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product_name=productItem.getPRODUCTNAME();
                String product_price=productItem.getPRICE();
                String product_id=productItem.getPRODUCTID();
                SharedPreferences preferences= context.getSharedPreferences("checkbox", MODE_PRIVATE);
                String name=preferences.getString("name","");

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
        return ProductItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView product_image_view;
        public TextView product_name_txt_view;
        public TextView product_price_txt_view;
        public Button btn_scan_addtocart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            product_image_view = (ImageView) itemView.findViewById(R.id.product_image_view);
            product_name_txt_view = (TextView) itemView.findViewById(R.id.product_name_txt_view);
            product_price_txt_view = (TextView) itemView.findViewById(R.id.product_price_txt_view);
            btn_scan_addtocart=(Button) itemView.findViewById(R.id.btn_scan_addtocart);
        }

    }
}
