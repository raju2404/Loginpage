package com.example.ScanPe;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

import ScanPe.R;

public class Register extends AppCompatActivity {

    Button btn_register;
    private RequestQueue requestQueue;
    EditText userid,username,password,repeatpassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        userid = (EditText) findViewById(R.id.text_userid);
        username = (EditText) findViewById(R.id.text_Name);
        password = (EditText) findViewById(R.id.text_password);
        repeatpassword = (EditText) findViewById(R.id.text_repeatpassword);


        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(Register.this, scanpage.class);
                //startActivity(intent);
                //SignupUser("JAY","JAYA","pass123");
                try {
                    final String UserID=userid.getText().toString();
                    final String UserName= username.getText().toString();
                    final String Password= password.getText().toString();
                    //final String repeatpassword=repeatpassword.getText().toString();
                    SignupUser(UserID,UserName,Password);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    private void SignupUser(String UserID,String UserName,String Password) {
        try{

            requestQueue = Volley.newRequestQueue(getApplicationContext());
            String URL="http://10.0.2.2:5000/addUser";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("USERID", UserID);
            jsonBody.put("USERNAME", UserName);
            jsonBody.put("MAILID", "RAJU.IMP25@GMAIL.COM");
            jsonBody.put("MOBILE",12);
            jsonBody.put("AGE", 2);
            jsonBody.put("CITY","Banglore");
            jsonBody.put("PASSWORD",Password);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                            Toast.makeText(Register.this, "User Registered successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                    if(error.toString().equals("com.android.volley.ClientError")) {
                        Toast.makeText(Register.this, "User already exists", Toast.LENGTH_LONG).show();
                    }
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
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            } ;
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}