package com.example.caroline.realpoker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Settings extends Fragment implements AdapterView.OnItemClickListener {
    private View rootView;
    private Context context;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private ArrayList<Player> players;
    private ArrayAdapter adapter;
    private GridView gridView;

    public Settings() {
    }
    //todo have settings use shared preferences so you can delete players NEEDS FIXING
    //todo change type of poker
    //todo have themes
        //either they can change card background and overall backgorund however they want
        //OR we create a list of different ones they can change

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.activity_settings, container, false);

        context = getActivity();
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        players = new ArrayList<>();
        makePlayerList();

        gridView = rootView.findViewById(R.id.grid);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, players); //todo change to gridlayout
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
        registerForContextMenu(gridView);

        return rootView;
    }

    private void makePlayerList() {
        players = new ArrayList<>();
        int numOfPlayers = sharedPref.getInt("Number of Players",0);
        for(int i = 0; i < numOfPlayers; i++){
            players.add(new Player(sharedPref.getString("Player "+(i+1), null), sharedPref.getInt("Player "+(i+1)+" Monnies", 10000), null));
            Log.d(TAG, "makePlayerList: " + players.get(i).getName());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        //checks position, popu alert dialogue box to xhange name or delete player
        // also need to incorportate change typw of poker and themes...
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        final EditText editText = new EditText(context);
        editText.setText(sharedPref.getString("Player "+position, ""));
        alertDialogBuilder.setTitle("Change Name or Reset Players?");
        alertDialogBuilder.setView(editText);

        // set dialog message

        alertDialogBuilder.setCancelable(false).setPositiveButton("Change Name", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.d(TAG, "onClick: "+ editText.getText().toString());
                editor.putString("Player "+position, editText.getText().toString());
                editor.commit();
                Log.d(TAG, "onClick: "+ sharedPref.getString("Player "+position,"default"));
                makePlayerList();
                adapter.notifyDataSetChanged();
            }
        });

        alertDialogBuilder.setCancelable(false).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        alertDialogBuilder.setCancelable(false).setNeutralButton("Delete Player", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                editor.putString("Player "+position, "");
                editor.putInt("Player "+position+" Monnies", 0);
                editor.putInt("Number of Players", sharedPref.getInt("Number of Players", 6) -1);
                editor.commit();
                makePlayerList();
                adapter.notifyDataSetChanged();
            }
        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
