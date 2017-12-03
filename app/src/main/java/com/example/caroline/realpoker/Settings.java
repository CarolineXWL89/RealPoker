package com.example.caroline.realpoker;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Settings extends Fragment {
    private View rootView;

    public Settings() {
    }
    //todo have settings use shared preferences so you can delete players
    //todo change type of poker
    //todo have themes
        //either they can change card background and overall backgorund however they want
        //OR we create a list of different ones they can change

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.activity_settings, container, false);
        return rootView;
    }

}
