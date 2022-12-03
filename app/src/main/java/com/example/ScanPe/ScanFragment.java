package com.example.ScanPe;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ScanPe.R;


public class ScanFragment extends Fragment {

    private Button btn_scan;
    TextView u_name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.fragment_scan,container,false);
                ///Global class fetching code ///
//        GlobalClass globalClass = (GlobalClass) getActivity().getApplicationContext();
//        u_name=view.findViewById(R.id.u_name);
//        u_name.setText(globalClass.getUserID());
            ////Global class fetchhing code///
        btn_scan = (Button) view.findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(),scanresults.class));
            }
        });

        return view;

    }



}
