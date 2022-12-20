package com.example.ScanPe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ScanPe.R;

//.app.package.R;


public class loadmenu extends AppCompatActivity {

    //scannedurl="https://ac2d-2405-201-d005-a06d-c1be-5bd7-c25-56e.ap.ngrok.io/getProducts/"
    private static TextView scannedurl;

    private RecyclerView product_recycler_view;
    private RecyclerView.Adapter adapter;
    //private LinearLayoutManager linearLayoutManager;
    private List<ProductItem> ProductItems;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_loadmenu);
        //TextView scannedurl = (TextView) findViewById(R.id.txt_scan_url);
        Intent intent = getIntent();
        String bar_code = intent.getStringExtra("BARCODE");
        //String url=String.format("https://ac2d-2405-201-d005-a06d-c1be-5bd7-c25-56e.ap.ngrok.io/getProducts/%s ", var);

        //Toast.makeText(loadmenu.this, url, Toast.LENGTH_LONG).show();

        //scannedurl.setText(bar_code);

        product_recycler_view = (RecyclerView) findViewById(R.id.product_recycler_view);
        product_recycler_view.setHasFixedSize(true);
        //product_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        product_recycler_view.setLayoutManager(mLayoutManager);

        ProductItems = new ArrayList<>();
        loadRecyclerviewData( bar_code);
    }

    private void loadRecyclerviewData(String bar_code) {
        try {


            String complete_url = String.format("  http://9d0a-2405-201-d005-a06d-c139-dbc3-3496-96f8.ngrok.io/getProducts/%s",bar_code);
            //String complete_url = "  http://9d0a-2405-201-d005-a06d-c139-dbc3-3496-96f8.ngrok.io/getProducts";
            final ProgressDialog progressDialog = new ProgressDialog(loadmenu.this);
            progressDialog.setMessage("Loading Data...");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, complete_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray array = jsonObject.getJSONArray("myCollection");

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject o = array.getJSONObject(i);

                                    ProductItem item = new ProductItem(
                                            o.getString("PRODUCTID"),
                                            o.getString("IMAGE"),
                                            o.getString("PRODUCTNAME"),
                                            o.getString("PRICE")

                                    );
                                    ProductItems.add(item);

                                }
                                adapter = new ProductItemAdapter(ProductItems, getApplicationContext());
                                product_recycler_view.setAdapter(adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            //Toast.makeText(getApplicationContext(),)

                        }
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(loadmenu.this);
            requestQueue.add(stringRequest);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}