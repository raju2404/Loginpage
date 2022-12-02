package com.example.ScanPe;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;
import ScanPe.R;

public class AccountFragment extends Fragment {

    private Button btn_logout;
    TextView u_name;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_account,container,false);
//        GlobalClass globalClass = (GlobalClass) getActivity().getApplicationContext();
//        u_name=view.findViewById(R.id.u_name);
//        u_name.setText(globalClass.getUserID());

        u_name=view.findViewById(R.id.u_name);
        SharedPreferences preferences=getActivity().getSharedPreferences("checkbox",MODE_PRIVATE);
        //SharedPreferences.Editor editor= preferences.edit();

        String u_name=preferences.getString("name","");
        Toast.makeText(getActivity().getApplicationContext(), u_name, Toast.LENGTH_LONG).show();

        btn_logout = (Button) view.findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences=getActivity().getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor= preferences.edit();

                editor.putString("remember","false");


                editor.apply();
                getActivity().finish();
                Toast.makeText(getActivity().getApplicationContext(), "Logged out", Toast.LENGTH_LONG).show();

            }
        });


        return view;

    }
}
