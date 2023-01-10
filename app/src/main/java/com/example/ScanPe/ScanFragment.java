package com.example.ScanPe;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


public class ScanFragment extends Fragment  {

    private Button btn_scan;
    TextView u_name;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private locationsearchAdapter mAdapter;
    private List<ProductItem> productItems;
    EditText search_location_text;
    private static final String url = "http://10.0.2.2:5000/getProducts";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        openAlertDialogue();

        View view= inflater.inflate(R.layout.fragment_scan,container,false);
                ///Global class fetching code ///
//        GlobalClass globalClass = (GlobalClass) getActivity().getApplicationContext();
//        u_name=view.findViewById(R.id.u_name);
//        u_name.setText(globalClass.getUserID());
            ////Global class fetchhing code///

        search_location_text = view.findViewById(R.id.ed_tv_location_products);
        search_location_text.addTextChangedListener(new TextWatcher() {
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
        btn_scan = (Button) view.findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getActivity().getApplicationContext(),scanresults.class));

            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.location_prod_recyclerview1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));


        productItems = new ArrayList<>();
        loadRecyclerviewData();
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
                            adapter = new locationsearchAdapter(productItems, getActivity().getApplicationContext());
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

    private void openAlertDialogue() {
        new AlertDialog.Builder(getContext())
                .setTitle("Location Alert")
                .setMessage("Are you in Store Location?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //startActivity(new Intent(getActivity().getApplicationContext(),scanresults.class));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getActivity().getApplicationContext(),HomeFragment.class));
                    }
                })
                .show();
    }

    private void filter(String text) {
        mAdapter = new locationsearchAdapter(productItems, getActivity().getApplicationContext());
        ArrayList<ProductItem> filteredlist = new ArrayList<>();

        for (ProductItem item : productItems) {
            if (item.getPRODUCTNAME().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        mAdapter.filterList(filteredlist);
    }


}
