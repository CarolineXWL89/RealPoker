package com.example.caroline.realpoker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class EditPlayers extends Fragment implements AdapterView.OnItemClickListener {
    private Context context;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private ArrayList<Player> playerList;
    private ArrayAdapter adapter;
    private Player[] players;

    public EditPlayers() {
    }

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_edit_players, container, false);

        context = getActivity();
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        playerList = new ArrayList<>();
        players = new Player[6];
        makePlayerList();

        GridView gridView = rootView.findViewById(R.id.grid);
        adapter = new ArrayAdapter<>(getActivity(), R.layout.player_list, playerList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
        registerForContextMenu(gridView);

        return rootView;
    }

    private void makePlayerList() {
        //removes all players before starting to add the next set
        int len = playerList.size();
        int j = 0;
        while(j < len){
            playerList.remove(0);
            j++;
        }
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        //creates all 6 players
        for(int i = 0; i < 6; i++){
            String name = sharedPref.getString("Player "+(i+1), "");
            int money = sharedPref.getInt("Player "+(i+1)+" Monnies", 10000);
            Player p = new Player(name, money, null);
            p.setSharedPref("Player "+(i+1));
            playerList.add(p);
            players[i] = p;
        }//bruh
        //removes empty ones
        for(int i = 0; i < playerList.size(); i++){
            Player p = playerList.get(i);
            if(p.getName().equals("")){
                playerList.remove(i);
                i--;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        //checks position, popu alert dialogue box to xhange name or delete player
        // also need to incorportate change typw of poker and themes...
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        final EditText editText = new EditText(context);
        editText.setText("");
        alertDialogBuilder.setTitle("Change Name or Delete "+sharedPref.getString(playerList.get(position).getSharedPref(), "")+"?");
        alertDialogBuilder.setView(editText);

        // set dialog message

        alertDialogBuilder.setCancelable(false).setPositiveButton("Change Name", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                editor.putString(playerList.get(position).getSharedPref(), editText.getText().toString());
                editor.commit();
                makePlayerList();
                adapter.notifyDataSetChanged();
                editText.setText("");
            }
        });

        alertDialogBuilder.setCancelable(false).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        alertDialogBuilder.setCancelable(false).setNeutralButton("Delete Player", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                editor.putString(playerList.get(position).getSharedPref(), "");
                editor.putInt(playerList.get(position).getSharedPref()+" Monnies", 0);
                editor.putInt("Number of Players", sharedPref.getInt("Number of Players", 6) -1);
                editor.commit();
                makePlayerList();
                adapter.notifyDataSetChanged();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
