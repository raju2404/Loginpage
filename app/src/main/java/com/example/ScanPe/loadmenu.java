package com.example.ScanPe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import ScanPe.R;

//.app.package.R;


public class loadmenu extends AppCompatActivity {

    private static TextView scannedurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_loadmenu);
        TextView scannedurl = (TextView) findViewById(R.id.txt_scan_url);
        Intent intent = getIntent();
        String var = intent.getStringExtra("ScannedURL");

        scannedurl.setText(var);
    }
}