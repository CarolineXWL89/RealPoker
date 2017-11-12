package com.example.caroline.realpoker;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class RealSettings extends Fragment {
    private View rootView;
    private ListView list;

    public RealSettings() {
    }

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        //Inflate the layout we made (one_fragment.xml)
        rootView = inflater.inflate(R.layout.activity_real_settings, container, false);
        wireWidgets();
        return rootView;
    }

    private void wireWidgets() {
        list = (ListView) rootView.findViewById(R.id.settings_menu);
    }


}
