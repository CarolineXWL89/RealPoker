package com.example.caroline.realpoker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.SubMenu;
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
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.MalformedInputException;
import java.util.ArrayList;

public class PlayPoker extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "blah";
    private Fragment currentGame, currentFragment;
    private Context context;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        //todo add themes
        String theme = sharedPref.getString("Theme","Standard");
        switch(theme){
            case "Standard":
                setTheme(R.style.AppTheme);
                break;
            case "Red":
                setTheme(R.style.Red);
                break;
            case "Orange":
                setTheme(R.style.Orange);
                break;
            case "Yellow":
                setTheme(R.style.Yellow);
                break;
            case "LightGreen":
                setTheme(R.style.LightGreen);
                break;
            case "Green":
                setTheme(R.style.Green);
                break;
            case "Teal":
                setTheme(R.style.Teal);
                break;
            case "LightBlue":
                setTheme(R.style.LightBlue);
                break;
            case "Blue":
                setTheme(R.style.Blue);
                break;
            case "DarkBlue":
                setTheme(R.style.DarkBlue);
                break;
            case "Indigo":
                setTheme(R.style.Indigo);
                break;
            case "Purple":
                setTheme(R.style.Purple);
                break;
            case "LightPink":
                setTheme(R.style.LightPink);
                break;
            case "BrightPink":
                setTheme(R.style.BrightPink);
                break;
            case "Black":
                setTheme(R.style.Black);
                break;
            default:
                setTheme(R.style.AppTheme);
                break;
        }
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

        context = this;
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        if(sharedPref.getString("Player 1", null) == null ){
            editor.putBoolean("hasPlayers?", false);
            editor.commit();
        }

        fm = getSupportFragmentManager();
        if(currentGame == null){
            currentGame = new PokerGame2();
        }

        editor.putString("FragmentName","PokerGame");
        editor.commit();
        if(currentFragment == null){
            currentFragment = currentGame;
            fm.beginTransaction()
                    .replace(R.id.fragment_container, currentFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        String fragment = sharedPref.getString("FragmentName", "");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(fragment.equals("Themes") || fragment.equals("EditPlayers")){ //todo add change poker type class when its created
            currentFragment = new Settings();
            editor.putString("FragmentName","Settings");
            editor.commit();
            fm.beginTransaction()
                    .replace(R.id.fragment_container, currentFragment)
                    .commit();
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        if(currentFragment.getClass() == PokerGame2.class ){
            areYouSure(item.getItemId());
        } else {
            leave(item.getItemId());
        }
        return true;
    }

    private void areYouSure(final int identification) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final TextView input = new TextView(this);
        input.setText("Leaving now will lose progress for this hand. Do you want to leave?");
        // set title
        alertDialogBuilder.setTitle("Are you sure?");
        alertDialogBuilder.setView(input);

        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                leave(identification);
            }
        });

        alertDialogBuilder.setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                stay();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void leave(int id) {
        if (id == R.id.nav_game) {
            currentFragment = currentGame;
            editor.putString("FragmentName","PokerGame");
            editor.commit();
        } else if (id == R.id.nav_reference) {
            currentFragment = new Reference();
            editor.putString("FragmentName","Reference");
            editor.commit();
        } else if (id == R.id.nav_settings) {
            currentFragment = new Settings();
            editor.putString("FragmentName","Settings");
            editor.commit();
        }
        if(currentFragment != null){
            fm.beginTransaction()
                    .replace(R.id.fragment_container, currentFragment)
                    .commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void stay() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    } //todo fix selection for the side menu
}
