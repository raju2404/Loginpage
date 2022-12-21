package com.example.ScanPe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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

import ScanPe.R;

public class MainActivity extends AppCompatActivity {

    private Button button,btn_login;
    private TextView txtforgotpassword;
    //public static final String url_login = "http://10.0.2.2:5000/checkUser";
    EditText txt_userid,txt_password;
    private RequestQueue requestQueue;
    private CheckBox checkbox_remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        txt_userid = (EditText) findViewById(R.id.txt_userid);
        txt_password = (EditText) findViewById(R.id.txt_password);
        txtforgotpassword = findViewById(R.id.txt_forgotpwd);
        checkbox_remember=findViewById(R.id.chk_box_remember);
        btn_login = (Button) findViewById(R.id.btn_login);
        button = findViewById(R.id.btn_signup);

        SharedPreferences preferences= getSharedPreferences("checkbox",MODE_PRIVATE);
        checkbox_remember.setChecked(true);//newly added
        String checkbox=preferences.getString("remember","");
        SharedPreferences.Editor editor= preferences.edit();//newly added
        editor.putString("remember","true");//newly added
        editor.apply();//newly added
        String user_name=preferences.getString("name",txt_userid.getText().toString());
        if(checkbox.equals("true")){
            //Toast.makeText(MainActivity.this, "Checked", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, scanpage.class);
            startActivity(intent);
        }else if(checkbox.equals("false")){
            Toast.makeText(MainActivity.this, "Please sign in", Toast.LENGTH_LONG).show();
        }




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final String userid=txt_userid.getText().toString();
                    final String password= txt_password.getText().toString();
                    //final String repeatpassword=repeatpassword.getText().toString();
                    LoginUser(userid,password);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        txtforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Pop.class));
            }
        });

        checkbox_remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(buttonView.isChecked())
                {
                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor= preferences.edit();
                    editor.putString("remember","true");
                    editor.putString("name",txt_userid.getText().toString() );
                    editor.commit();
                    Toast.makeText(MainActivity.this, "Your credentials will be remembered in this device", Toast.LENGTH_LONG).show();

                } else if(!buttonView.isChecked())
                {
                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor= preferences.edit();
                    editor.putString("remember","false");
                    editor.putString("name",txt_userid.getText().toString() );
                    editor.commit();
                    Toast.makeText(MainActivity.this, "Unchecked", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void LoginUser(String userid, String password) {
        try{

            requestQueue = Volley.newRequestQueue(getApplicationContext());
            String URL="   http://e463-2405-201-d005-a06d-61dd-cac0-fff0-14e7.ngrok.io/checkUser";
            //String URL="http://10.0.2.2:5000/checkUser";
            //String URL= "http://192.168.29.225:8080/checkUser";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("USERID", userid);
            jsonBody.put("PASSWORD",password);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("200")) {
                                Log.i("VOLLEY", response);
                                String name=userid;
                                String success_message = String.format("Welcome %s ", name);
                                Toast.makeText(MainActivity.this, success_message, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainActivity.this, scanpage.class).putExtra("name",name);
                                startActivity(intent);
                                SharedPreferences preferences= getSharedPreferences("checkbox",MODE_PRIVATE);
                                SharedPreferences.Editor editor= preferences.edit();
                                editor.putString("name",userid);
                                editor.apply();
                                String user_name=preferences.getString("name",userid);
                                //Toast.makeText(MainActivity.this, user_name, Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                    //Log.i("VOLLEY", response);
                    if(error.toString().contains("com.android.volley.ServerError")) {
                        Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
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