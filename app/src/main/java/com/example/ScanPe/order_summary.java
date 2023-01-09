package com.example.ScanPe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ScanPe.R;

public class order_summary extends AppCompatActivity {
    TextView txt_view_product_count;
    TextView txt_view_orderid;
    private RequestQueue requestQueue;
    //private static final String url = "http://10.0.2.2:5000/getInvoiceItem/20221222970";
    TextView rateview;
    private RecyclerView recview_invoice;
    private RecyclerView.Adapter adapter;

    private ProductSearchAdapter mAdapter;
    private List<InvoiceItem> InvoiceItems;
    //20221222970




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        SharedPreferences preferences= getSharedPreferences("checkbox", MODE_PRIVATE);
        String userid=preferences.getString("name","");
        rateview=findViewById(R.id.rateview);

        txt_view_product_count=findViewById(R.id.txt_view_product_count);
        txt_view_orderid=findViewById(R.id.txt_view_orderid);
        ArrayList<String> Prod_ids_list = (ArrayList<String>) getIntent().getSerializableExtra("IDs");
        ArrayList<String> Prod_qtys_list = (ArrayList<String>) getIntent().getSerializableExtra("QTYs");
        ArrayList<String> Prod_price_list = (ArrayList<String>) getIntent().getSerializableExtra("PRICEs");
        int Totalamount= getIntent().getIntExtra("Amount",1)  ;
        ///Need to append with discount logic
        int Finalamount=10;
        ///

        Calendar c = Calendar.getInstance();
        int Year=c.get(Calendar.YEAR);int month = c.get(Calendar.MONTH) + 1;
        int date=c.get(Calendar.DATE);int milliseconds = c.get(Calendar.MILLISECOND);
        String orderid = Year+""+month+""+date+""+milliseconds;
        txt_view_orderid.setText(String.valueOf(orderid));
        CreateOrder(orderid,userid,Totalamount,Finalamount);

        for(int j=0;j< Prod_ids_list.size();j++)
            SaveTransaction(orderid,Prod_ids_list.get(j), Prod_qtys_list.get(j),Prod_price_list.get(j) );
        txt_view_product_count.setText("Number of Items Purchased"+String.valueOf(Prod_ids_list.size()));
        deleteCartItems();


        recview_invoice = (RecyclerView) findViewById(R.id.recview_invoice);
        recview_invoice.setHasFixedSize(true);
        recview_invoice.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        InvoiceItems = new ArrayList<>();
        loadRecyclerviewData(orderid);



    }

    private void loadRecyclerviewData(String orderid) {
        Toast.makeText(order_summary.this, orderid, Toast.LENGTH_LONG).show();
        String url =String.format( "http://10.0.2.2:5000/getInvoiceItem/%s", orderid);
        //String url = String.format("http://cca0-2405-201-d005-a06d-e0d3-a4a8-a635-4b66.ngrok.io/getInvoiceItem/%s",orderid);
        final ProgressDialog progressDialog = new ProgressDialog(this);
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
                                InvoiceItem item = new InvoiceItem(
                                        o.getString("ORDERID"),
                                        o.getString("PRODUCTID"),
                                        o.getString("PRODUCTNAME"),
                                        o.getString("QUANTITY"),
                                        o.getString("PRICE")
                                );
                                InvoiceItems.add(item);

                            }
                            adapter = new InvoiceAdapter(InvoiceItems, getApplicationContext());
                            recview_invoice.setAdapter(adapter);
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void CreateOrder(String orderid, String userid, int totalamount, int finalamount) {
        try{
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            String updateURL="http://10.0.2.2:5000/createOrder";
            //String updateURL = " http://cca0-2405-201-d005-a06d-e0d3-a4a8-a635-4b66.ngrok.io/createOrder";
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
            String updateURL="http://10.0.2.2:5000/createOrderDetails";
            //String updateURL = "  http://cca0-2405-201-d005-a06d-e0d3-a4a8-a635-4b66.ngrok.io/createOrderDetails";
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