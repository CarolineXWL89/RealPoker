package com.example.caroline.realpoker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.charset.MalformedInputException;
import java.util.ArrayList;

public class PlayPoker extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment currentGame;
    private int numOfPlayers;
    private ArrayList<Player> players;
    private Player player;

    //TODO se up setting acticity maybe make it fragment or somtheing nice like that
    //TOdo fix game so that it starts by askign for player
    //todo fix horizontal xml file
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_poker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        players = new ArrayList<>();
        getNumOfPlayers();
    }

    private void createPlayers() {
        for(int i = 0; i< numOfPlayers; i++){
            players.add(createPlayer());
        }
        currentGame = new PokerGame(numOfPlayers, players);
        startGame();
    }

    private Player createPlayer() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        // set title
        alertDialogBuilder.setTitle("Input name of player");
        alertDialogBuilder.setView(input);
        //alertDialogBuilder.setIcon(R.drawable.ic_delete);
        // set dialog message
        final Player p = new Player("", 10000,null);
        alertDialogBuilder.setCancelable(false).setPositiveButton("ok",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
               p.setName(input.getText().toString());
            }
        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        return p;
    }

    private void startGame() {
        Fragment currentFragment = currentGame;
        FragmentManager fm = getSupportFragmentManager();
        if(currentFragment != null)
        {
            fm.beginTransaction()
                    .replace(R.id.fragment_container, currentFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.play_poker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment currentFragment = null;
        if (id == R.id.nav_game) {
            currentFragment = currentGame;
        } else if (id == R.id.nav_reference) {
            currentFragment = new Reference();
        } else if (id == R.id.nav_settings) {
            currentFragment = new RealSettings();
        }

        FragmentManager fm = getSupportFragmentManager();
        if(currentFragment != null)
        {
            fm.beginTransaction()
                    .replace(R.id.fragment_container, currentFragment)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getNumOfPlayers() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        // set title
        alertDialogBuilder.setTitle("Input number of players");
        alertDialogBuilder.setView(input);
        //alertDialogBuilder.setIcon(R.drawable.ic_delete);
        // set dialog message

        alertDialogBuilder.setCancelable(true).setPositiveButton("ok",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                int num;
                try{
                    if(Integer.parseInt(input.getText().toString())>2 &&(Integer.parseInt(input.getText().toString())<= 6))
                    {
                        num = Integer.parseInt(input.getText().toString());
                    } else {
                        num = 6;
                        Toast.makeText(PlayPoker.this, "SUCKS TO SUCK!!! \n You are playing with six players", Toast.LENGTH_LONG).show();
                    }
                } catch(NumberFormatException e){
                    num = 6;
                    Toast.makeText( PlayPoker.this , "SUCKS TO SUCK!!! \n You are playing with six players", Toast.LENGTH_LONG).show();
                }
                numOfPlayers = num;
                createPlayers();
            }
        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
