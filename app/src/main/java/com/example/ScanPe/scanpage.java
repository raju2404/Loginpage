package com.example.ScanPe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ScanPe.R;

public class scanpage extends AppCompatActivity {


    public static TextView txt_result_scan;

//    private static final String url= "http://10.0.2.2:5000/getProducts";
//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter adapter;
//
//    private ProductSearchAdapter mAdapter;
//    EditText searchtext;
//    private List<ProductItem> productItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_scanpage);
        BottomNavigationView bottomnav=findViewById(R.id.bottom_navigation);
        bottomnav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new HomeFragment()).commit();
        txt_result_scan = (TextView) findViewById(R.id.txt_result_scan);
        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
        String name=preferences.getString("name","");
        String welcome_message = String.format("Welcome %s ", name);
        txt_result_scan.setText(welcome_message);
//        searchtext=findViewById(R.id.search_product_view);
//        searchtext.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                filter(s.toString());
//
//                recyclerView.setAdapter(mAdapter);
//            }
//        });
//        recyclerView = (RecyclerView) findViewById(R.id.search_prod_recyclerview);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        productItems = new ArrayList<>();
//       loadRecyclerviewData();




    }
//    private void filter(String text) {
//        mAdapter = new ProductSearchAdapter(productItems,getApplicationContext());
//        ArrayList<ProductItem> filteredlist=new ArrayList<>();
//
//        for(ProductItem item:productItems){
//            if(item.getPRODUCTNAME().toLowerCase().contains(text.toLowerCase())){
//                filteredlist.add(item);
//            }
//        }
//
//        mAdapter.filterList(filteredlist);
//    }
//
//    private void loadRecyclerviewData() {
//        final ProgressDialog progressDialog = new ProgressDialog(scanpage.this);
//        progressDialog.setMessage("Loading Data...");
//        progressDialog.show();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        progressDialog.dismiss();
//                        try {
//                            JSONObject jsonObject=new JSONObject(response);
//                            JSONArray array= jsonObject.getJSONArray("myCollection");
//
//                            for (int i= 0; i<array.length() ; i++) {
//                                JSONObject o = array.getJSONObject(i);
//                                ProductItem item = new ProductItem(
//                                        o.getString("IMAGE"),
//                                        o.getString("PRODUCTNAME"),
//                                        o.getString("PRICE")
//                                );
//                                productItems.add(item);
//
//                            }
//                            adapter = new ProductSearchAdapter(productItems, getApplicationContext());
//                            recyclerView.setAdapter(adapter);
//                        } catch (JSONException e){
//                            e.printStackTrace();
//
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
//                        //Toast.makeText(getApplicationContext(),)
//
//                    }
//                });
//        RequestQueue requestQueue = Volley.newRequestQueue(scanpage.this);
//        requestQueue.add(stringRequest);
//
//
//    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectFragement = null;
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectFragement = new HomeFragment();
                            break;
                        case R.id.nav_scan:
                            selectFragement = new ScanFragment();
                            break;
                        case R.id.nav_home_delivery:
                            selectFragement=new SearchFragment();
                            break;

                        case R.id.nav_account:
                            selectFragement = new AccountFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,selectFragement).commit();
                    return true;
                }
            };



}