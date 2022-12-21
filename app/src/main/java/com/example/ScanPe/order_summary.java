package com.example.ScanPe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import ScanPe.R;

public class order_summary extends AppCompatActivity {
    TextView txt_view_prodids,txt_view_quantities,txt_view_product_count;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        SharedPreferences preferences= getSharedPreferences("checkbox", MODE_PRIVATE);
        String userid=preferences.getString("name","");

        txt_view_prodids= findViewById(R.id.txt_view_prodids);
        txt_view_quantities=findViewById(R.id.txt_view_quantities);
        txt_view_product_count=findViewById(R.id.txt_view_product_count);
        ArrayList<String> Prod_ids_list = (ArrayList<String>) getIntent().getSerializableExtra("IDs");
        ArrayList<String> Prod_qtys_list = (ArrayList<String>) getIntent().getSerializableExtra("QTYs");
        txt_view_prodids.setText(String.valueOf(Prod_ids_list));
        txt_view_quantities.setText(String.valueOf(Prod_qtys_list));
        Toast.makeText(order_summary.this, userid, Toast.LENGTH_LONG).show();

        for(int j=0;j< Prod_ids_list.size();j++)
            SaveTransaction(userid,Prod_ids_list.get(j), Prod_qtys_list.get(j) );
        txt_view_product_count.setText(String.valueOf(Prod_ids_list.size()));
        //deleteCartItems();


    }



    private void SaveTransaction(String userid, String prodid, String prodqty) {
        try{
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            //String updateURL="http://10.0.2.2:5000/addOrder";
            String updateURL = " http://e463-2405-201-d005-a06d-61dd-cac0-fff0-14e7.ngrok.io/addOrder";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("USERID", userid);
            jsonBody.put("PRODUCTID", prodid);
            jsonBody.put("QUANTITY", prodqty);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, updateURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                            Toast.makeText(order_summary.this, "Transaction saved successfully", Toast.LENGTH_LONG).show();
                            //loadRecyclerviewData();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }
            )
            {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);

                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            } ;
            //RequestQueue requestQueue = Volley.newRequestQueue(order_summary.this);
            requestQueue.add(stringRequest);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}