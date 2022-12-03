package com.example.ScanPe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ScanPe.R;

public class scanpage extends AppCompatActivity {


    public static TextView txt_result_scan;

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

    }
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
                        case R.id.nav_account:
                            selectFragement = new AccountFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,selectFragement).commit();
                    return true;
                }
            };



}