package com.example.ScanPe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import ScanPe.R;

public class product_cart_data extends AppCompatActivity {

    RecyclerView recview;
    TextView rateview;
    Button btn_checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_product_cart_data);
        getSupportActionBar().hide();
        rateview=findViewById(R.id.rateview);
        btn_checkout= (Button) findViewById(R.id.btn_checkout);

        getroomdata();


        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String str = rateview.getText().toString();
                String intValue = str.replaceAll("[^0-9]", "");
                //System.out.print(Integer.parseInt(intValue));
//                Intent intent = new Intent(product_cart_data.this, payment.class)
//                        .putExtra("Totalamount",intValue );
//                startActivity(intent);
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "cart_db").allowMainThreadQueries().build();
                ProductDao productDao = db.ProductDao();
                List<Product> products = productDao.getallproduct();
                ArrayList<String> prod_ids = new ArrayList<>();
                ArrayList<String> prod_qtys = new ArrayList<>();
                ArrayList<String> prod_price = new ArrayList<>();
                if (products.size() != 0) {
                    for (int i = 0; i < products.size(); i++) {
                        prod_ids.add(String.valueOf(products.get(i).getPid()));
                        prod_qtys.add(String.valueOf(products.get(i).getQnt()));
                        prod_price.add(String.valueOf(products.get(i).getPrice()));

                    }
                    Intent intent = new Intent(product_cart_data.this, order_summary.class);
                    intent.putExtra("IDs", prod_ids);
                    intent.putExtra("QTYs", prod_qtys);
                    intent.putExtra("PRICEs", prod_price);
                    intent.putExtra("Amount", intValue);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(product_cart_data.this, "Cart is Empty", Toast.LENGTH_LONG).show();
                }
            }

        });


    }


    private void getroomdata() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "cart_db").allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();

        recview=findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        List<Product> products=productDao.getallproduct();

        AddtoCartAdapter adapter=new AddtoCartAdapter(products, rateview);
        recview.setAdapter(adapter);

        int sum=0,i;
        for(i=0;i< products.size();i++)
            sum=sum+(products.get(i).getPrice()*products.get(i).getQnt());


        rateview.setText("Total Amount : INR "+sum);
    }


}