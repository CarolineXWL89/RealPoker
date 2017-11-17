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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
public class PokerGame2 extends Fragment implements View.OnClickListener {

    private ArrayList<Card> cardsOnTheTable;
    private ArrayList<Card> deck;
    private int numOfPlayers, currentplayer;
    private int potMoney;
    private ArrayList<Player> players, playersPlaying; //first one keeps track for purposes of showing cards when player's fold, second one keeps track for shiftign from playre one to player two etc...
    private TextView player1View, player2View, player3View, player4View, player5View, player6View, bet;
    private boolean hasRaised = false;
    private int round, turn;
    private Player emptyPlayer;
    private Card myCard1, myCard2, tableCard1, tableCard2, tableCard3, tableCard4, tableCard5;
    private ImageView myCard1View, myCard2View, tableCard1View, tableCard2View, tableCard3View, tableCard4View, tableCard5View,
            player1Card1View, player1Card2View, player2Card1View, player2Card2View, player3Card1View, player3Card2View, player4Card1View, player4Card2View,
            player5Card1View, player5Card2View;
    private View rootView;
    private Context context;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private Button callCheck, fold, raise;

    public PokerGame2() {
    }

    //TODO Overall:
    //TODO #1 debug raise and fold w/nic (also check why flipping over at end is so f*cked up)
    //TODO #2 fix call check method and ask nic whats up with it w/nic
    //TODO #3 include blinds (make one method call for first and second) NOtfiy player they are blind before just screwing them
    //TODO #4 FIX SHARED PREFRENCES so you can keep players all the time (ie keep them when app is closed and restarts)
    //todo #5 set up end screen with options fro new game, change players, im doen or change certain players
    //todo #6 have settings use shared preferences so you can delete players


    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Inflate the layout we made (one_fragment.xml)
        rootView = inflater.inflate(R.layout.activity_poker_game, container, false);
        deck = new ArrayList<>();
        numOfPlayers = 6;
        currentplayer = 0;

        context = getContext();
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putBoolean("hasPlayers?", false);
        editor.commit();

        ArrayList<Card> emptyHand = new ArrayList<>();
        emptyHand.add(new Card(0, "c"));
        emptyHand.add(new Card(0, "c"));
        emptyPlayer = new Player("folded", 0, emptyHand);
        players = new ArrayList<>();
        Log.d(TAG, "onCreateView: ");

        createDeck();
        createCardsOnTheTable();
        areNewPlayers(); //either creates new players or gets the old ones and assigns them to their spots on the array list

        return rootView;
    }
}
