package com.example.ScanPe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ScanPe.R;

public class payment extends AppCompatActivity {

    TextView amount_tv;

    EditText upi_id,name,note;
    Button btn_send_payment;
    final int UPI_PAYMENT = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        name = (EditText) findViewById(R.id.name);
        note = (EditText) findViewById(R.id.note);
        btn_send_payment = (Button) findViewById(R.id.btn_send_payment);
        amount_tv = findViewById(R.id.amount_tv);
        //Toast.makeText(payment.this, String.valueOf(getIntent().getIntExtra("prodids",0) ), Toast.LENGTH_LONG).show();
        amount_tv.setText( String.valueOf(getIntent().getStringExtra("Totalamount"))) ;

        upi_id = findViewById(R.id.upi_id);
        upi_id.setText("dream11upi@kotak");
        //upi_id.setText("my11circle.rzp@sbi");
        btn_send_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount =amount_tv.getText().toString();
                String upiId = upi_id.getText().toString();
                String note_text = note.getText().toString();
                String name_text = name.getText().toString();
                payUsingUpi(amount, upiId, name_text, note_text);
            }
        });


    }

    private void payUsingUpi(String amount, String upiId, String name_text, String note_text) {
        Calendar c = Calendar.getInstance();
        int Year=c.get(Calendar.YEAR);int month = c.get(Calendar.MONTH) + 1;
        int date=c.get(Calendar.DATE);int milliseconds = c.get(Calendar.MILLISECOND);
        String transactionid = Year+""+month+""+date+""+milliseconds;
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name_text)
                .appendQueryParameter("tn", note_text)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .appendQueryParameter("mc","")
                .appendQueryParameter("tr",transactionid)
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(payment.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(payment.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(payment.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Toast.makeText(payment.this, approvalRefNo, Toast.LENGTH_SHORT).show();

//                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
//                        AppDatabase.class, "cart_db").allowMainThreadQueries().build();
//                ProductDao productDao = db.ProductDao();
//                List<Product> products=productDao.getallproduct();
//                int sum=0,j;
//                for(j=0;j< products.size();j++)
//                    sum=sum+(products.get(j).getPrice()*products.get(j).getQnt());
//
//                ArrayList<String> prod_ids = new ArrayList<>();
//                ArrayList<String> prod_qtys = new ArrayList<>();
//                ArrayList<String> prod_price = new ArrayList<>();
//
//                for(int i=0;i< products.size();i++) {
//                    prod_ids.add(String.valueOf(products.get(i).getPid()));
//                    prod_qtys.add(String.valueOf(products.get(i).getQnt()));
//                    prod_price.add(String.valueOf(products.get(i).getPrice()));
//
//                }
//                Intent intent = new Intent(payment.this, order_summary.class);
//                intent.putExtra("IDs",prod_ids);
//                intent.putExtra("QTYs",prod_qtys);
//                intent.putExtra("PRICEs",prod_price);
//                intent.putExtra("Amount",sum) ;
//
//                startActivity(intent);

                    Log.d("UPI", "responseStr: "+approvalRefNo);

            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(payment.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(payment.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(payment.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
}