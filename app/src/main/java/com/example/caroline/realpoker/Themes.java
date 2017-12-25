package com.example.caroline.realpoker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Themes extends Fragment implements AdapterView.OnItemClickListener {
    private View rootView;
    private Context context;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private ArrayList<String> themes;
    private ArrayAdapter adapter;
    private GridView gridView;
    private Fragment currentFragment;
    private FragmentManager fm;

    public Themes() {
    }

    //todo add a dark/ light toggel and reacreate all themes for dark vs light (figure out text color too)
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.activity_themes, container, false);

        context = getActivity();
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        themes = new ArrayList<>();
        themes.add("Standard");
        themes.add("Red");
        themes.add("Orange");
        themes.add("Yellow");
        themes.add("Lime Green");
        themes.add("Green");
        themes.add("Teal");
        themes.add("Light Blue");
        themes.add("Blue");
        themes.add("Dark Blue");
        themes.add("Indigo");
        themes.add("Purple");
        themes.add("Light Pink");
        themes.add("Bright Pink");
        themes.add("Black");

        gridView = rootView.findViewById(R.id.grid);
        //View view = inflater.inflate(R.layout.themes_view,container);
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.themes_view, themes){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);
                switch(position){
                    case 0:
                        view.setBackgroundColor(getResources().getColor(R.color.cardColorAppTheme));
                        break;
                    case 1:
                        view.setBackgroundColor(getResources().getColor(R.color.cardColorRed));
                        break;
                    case 2:
                        view.setBackgroundColor(getResources().getColor(R.color.cardColorOrange));
                        break;
                    case 3:
                        view.setBackgroundColor(getResources().getColor(R.color.cardColorYellow));
                        break;
                    case 4:
                        view.setBackgroundColor(getResources().getColor(R.color.cardColorLightGreen));
                        break;
                    case 5:
                        view.setBackgroundColor(getResources().getColor(R.color.cardColorGreen));
                        break;
                    case 6:
                        view.setBackgroundColor(getResources().getColor(R.color.cardColorTeal));
                        break;
                    case 7:
                        view.setBackgroundColor(getResources().getColor(R.color.cardColorLightBlue));
                        break;
                    case 8:
                        view.setBackgroundColor(getResources().getColor(R.color.cardColorBlue));
                        break;
                    case 9:
                        view.setBackgroundColor(getResources().getColor(R.color.cardColorDarkBlue));
                        break;
                    case 10:
                        view.setBackgroundColor(getResources().getColor(R.color.cardColorIndigo));
                        break;
                    case 11:
                        view.setBackgroundColor(getResources().getColor(R.color.cardColorPurple));
                        break;
                    case 12:
                        view.setBackgroundColor(getResources().getColor(R.color.cardColorLightPink));
                        break;
                    case 13:
                        view.setBackgroundColor(getResources().getColor(R.color.cardColorBrightPink));
                        break;
                    case 14:
                        view.setBackgroundColor(getResources().getColor(R.color.cardColorBlack));
                        break;
                    default:
                        view.setBackgroundColor(getResources().getColor(R.color.cardColorAppTheme));
                        break;
                }
                return view;
            }
        };

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
        registerForContextMenu(gridView);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(position){
            case 0:
                editor.putString("Theme", "AppTheme");
                editor.commit();
                break;
            case 1:
                editor.putString("Theme", "Red");
                editor.commit();
                break;
            case 2:
                editor.putString("Theme", "Orange");
                editor.commit();
                break;
            case 3:
                editor.putString("Theme", "Yellow");
                editor.commit();
                break;
            case 4:
                editor.putString("Theme", "LightGreen");
                editor.commit();
                break;
            case 5:
                editor.putString("Theme", "Green");
                editor.commit();
                break;
            case 6:
                editor.putString("Theme", "Teal");
                editor.commit();
                break;
            case 7:
                editor.putString("Theme", "LightBlue");
                editor.commit();
                break;
            case 8:
                editor.putString("Theme", "Blue");
                editor.commit();
                break;
            case 9:
                editor.putString("Theme", "DarkBlue");
                editor.commit();
                break;
            case 10:
                editor.putString("Theme", "Indigo");
                editor.commit();
                break;
            case 11:
                editor.putString("Theme", "Purple");
                editor.commit();
                break;
            case 12:
                editor.putString("Theme", "LightPink");
                editor.commit();
                break;
            case 13:
                editor.putString("Theme", "BrightPink");
                editor.commit();
                break;
            case 14:
                editor.putString("Theme", "Black");
                editor.commit();
                break;
            default:
                editor.putString("Theme", "Standard");
                editor.commit();
                break;
        }
        Intent i = new Intent(getActivity(),PlayPoker.class);
        startActivity(i);
    }

}
