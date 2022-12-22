package com.example.ScanPe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
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
import java.util.Calendar;
import java.util.List;

import ScanPe.R;

public class order_summary extends AppCompatActivity {
    TextView txt_view_prodids,txt_view_quantities,txt_view_product_count;
    private RequestQueue requestQueue;
    RecyclerView recview;
    TextView rateview;
    GridView gv_prodid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        SharedPreferences preferences= getSharedPreferences("checkbox", MODE_PRIVATE);
        String userid=preferences.getString("name","");
        rateview=findViewById(R.id.rateview);
        recview=findViewById(R.id.recview);
        gv_prodid=(GridView) findViewById(R.id.gv_prodid);
        txt_view_prodids= findViewById(R.id.txt_view_prodids);
        txt_view_quantities=findViewById(R.id.txt_view_quantities);
        txt_view_product_count=findViewById(R.id.txt_view_product_count);
        ArrayList<String> Prod_ids_list = (ArrayList<String>) getIntent().getSerializableExtra("IDs");
        ArrayList<String> Prod_qtys_list = (ArrayList<String>) getIntent().getSerializableExtra("QTYs");
        ArrayList<String> Prod_price_list = (ArrayList<String>) getIntent().getSerializableExtra("PRICEs");
        int Totalamount= getIntent().getIntExtra("Amount",1)  ;
        ///Need to append with discount logic
        int Finalamount=10;
        ///
        txt_view_prodids.setText(String.valueOf(Prod_ids_list));
        txt_view_quantities.setText(String.valueOf(Prod_qtys_list));
        Toast.makeText(order_summary.this, userid, Toast.LENGTH_LONG).show();

        Calendar c = Calendar.getInstance();
        int Year=c.get(Calendar.YEAR);int month = c.get(Calendar.MONTH) + 1;
        int date=c.get(Calendar.DATE);int milliseconds = c.get(Calendar.MILLISECOND);
        String orderid = Year+""+month+""+date+""+milliseconds;
        CreateOrder(orderid,userid,Totalamount,Finalamount);

        for(int j=0;j< Prod_ids_list.size();j++)
            SaveTransaction(orderid,Prod_ids_list.get(j), Prod_qtys_list.get(j),Prod_price_list.get(j) );
        txt_view_product_count.setText("Number of Items Purchased"+String.valueOf(Prod_ids_list.size()));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,Prod_ids_list);
        gv_prodid.setAdapter(adapter);

        deleteCartItems();


    }

    private void CreateOrder(String orderid, String userid, int totalamount, int finalamount) {
        try{
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            //String updateURL="http://10.0.2.2:5000/createOrder";
            String updateURL = " http://68ab-2405-201-d005-a06d-596a-3dd5-5a0f-ab23.ngrok.io/createOrder";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("ORDERID",orderid);
            jsonBody.put("USERID", userid);
            jsonBody.put("TOTALAMOUNT", totalamount);
            jsonBody.put("FINALAMOUNT", finalamount);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, updateURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                            Toast.makeText(order_summary.this, "Order Created successfully", Toast.LENGTH_LONG).show();
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

    private void deleteCartItems() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "cart_db").allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();

        List<Product> products=productDao.getallproduct();
        List<Product> products1=productDao.getallproduct();


        for (int k=0;k<products1.size();k++) {
            productDao.deleteById(products.get(0).getPid());
            products.remove(0);
            //notifyItemRemoved(position);
        }


        int sum=0,i;
        for(i=0;i< products.size();i++)
            sum=sum+(products.get(i).getPrice()*products.get(i).getQnt());

    }


    private void SaveTransaction(String orderid, String prodid, String prodqty,String price) {
        try{
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            //String updateURL="http://10.0.2.2:5000/createOrderDetails";
            String updateURL = "  http://68ab-2405-201-d005-a06d-596a-3dd5-5a0f-ab23.ngrok.io/createOrderDetails";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("ORDERID", orderid);
            jsonBody.put("PRODUCTID", prodid);
            jsonBody.put("QUANTITY", prodqty);
            jsonBody.put("PRICE",price);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, updateURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                            Toast.makeText(order_summary.this, "Order details saved successfully", Toast.LENGTH_LONG).show();
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