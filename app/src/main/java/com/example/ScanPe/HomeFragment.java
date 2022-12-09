package com.example.ScanPe;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

import ScanPe.R;

public class HomeFragment extends Fragment {
    TextView home_u_name;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home,container,false);

        ImageSlider imageSlider=view.findViewById(R.id.image_slider);
        List<SlideModel> slideModels=new ArrayList<>();


        //slideModels.add(new SlideModel(R.drawable.imagesliderpic, ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://picsum.photos/id/237/200/300",ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://picsum.photos/seed/picsum/200/300",ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://picsum.photos/200/300?grayscale",ScaleTypes.FIT));
        imageSlider.setImageList(slideModels);

        return view;
    }
}
