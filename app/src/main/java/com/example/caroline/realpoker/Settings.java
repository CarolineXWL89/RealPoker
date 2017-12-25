package com.example.caroline.realpoker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Settings extends Fragment implements AdapterView.OnItemClickListener {

    private SharedPreferences.Editor editor;
    public Settings() {
    }
    //todo change type of poker

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_settings, container, false);


        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        ArrayList<String> settings = new ArrayList<>();
        settings.add("Change Theme");
        settings.add("Edit Players");
        settings.add("Change Type of Poker");

        ListView gridView = rootView.findViewById(R.id.list);
        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, settings);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
        registerForContextMenu(gridView);

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment currentFragment = null;
        switch(position){
            case 0:
                currentFragment = new Themes();
                editor.putString("FragmentName","Themes");
                editor.commit();
                break;
            case 1:
                editor.putString("FragmentName","EditPlayers");
                editor.commit();
                currentFragment = new EditPlayers();
                break;
            case 2:
                //currentFragment = new changeType();
                break;
        }
        if(currentFragment != null){
            fm.beginTransaction().replace(R.id.fragment_container, currentFragment).commit();
        }
    }
}
