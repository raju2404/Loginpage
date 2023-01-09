package com.example.ScanPe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ScanPe.R;

public class SearchFragment extends Fragment {

    //
    private static final String url = "http://10.0.2.2:5000/getProducts";
    //private static final String url= "    http://cca0-2405-201-d005-a06d-e0d3-a4a8-a635-4b66.ngrok.io/getProducts";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private ProductSearchAdapter mAdapter;
    EditText searchtext;
    private List<ProductItem> productItems;
    //Button btn_checkcart;
    ImageButton img_showcart;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        //btn_checkcart=view.findViewById(R.id.btn_checkcart);
        img_showcart= view.findViewById(R.id.img_showcart);
        searchtext = view.findViewById(R.id.ed_tv_products);
        searchtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

                recyclerView.setAdapter(mAdapter);
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.search_prod_recyclerview1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));


        productItems = new ArrayList<>();
        loadRecyclerviewData();

//        btn_checkcart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity().getApplicationContext(),product_cart_data.class));
//            }
//        });

        img_showcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(),product_cart_data.class));
            }
        });

        return view;
    }



    private void loadRecyclerviewData() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
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
                                        o.getString("PRICE"),
                                        o.getString("LOCATION")
                                );
                                productItems.add(item);

                            }
                            adapter = new ProductSearchAdapter(productItems, getActivity().getApplicationContext());
                            recyclerView.setAdapter(adapter);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }

    private void filter(String text) {
        mAdapter = new ProductSearchAdapter(productItems, getActivity().getApplicationContext());
        ArrayList<ProductItem> filteredlist = new ArrayList<>();

        for (ProductItem item : productItems) {
            if (item.getPRODUCTNAME().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        mAdapter.filterList(filteredlist);
    }


}
