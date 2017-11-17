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
        emptyPlayer = new Player(null, 0, emptyHand);
        players = new ArrayList<>();
        Log.d(TAG, "onCreateView: ");

        createDeck();
        createCardsOnTheTable();
        areNewPlayers(); //either creates new players or gets the old ones and assigns them to their spots on the array list

        return rootView;
    }

    private void setUpCards(){
        //Sets the table cards but doesnt show them
        tableCard1View = (ImageView) rootView.findViewById(R.id.table_card_1);
        tableCard1View.setContentDescription(tableCard1.getCardNumber() + " of " + tableCard1.getSuitName());

        tableCard2View = (ImageView) rootView.findViewById(R.id.table_card_2);
        tableCard2View.setContentDescription(tableCard2.getCardNumber() + " of " + tableCard2.getSuitName());

        tableCard3View = (ImageView) rootView.findViewById(R.id.table_card_3);
        tableCard3View.setContentDescription(tableCard3.getCardNumber() + " of " + tableCard3.getSuitName());

        tableCard4View = (ImageView) rootView.findViewById(R.id.table_card_4);
        tableCard4View.setContentDescription(tableCard4.getCardNumber() + " of " + tableCard4.getSuitName());

        tableCard5View = (ImageView) rootView.findViewById(R.id.table_card_5);
        tableCard5View.setContentDescription(tableCard5.getCardNumber() + " of " + tableCard5.getSuitName());

        createOtherPlayersCards();
        changePlayerView(0);
    }

    private void createOtherPlayersCards() {
        //Creates other player's cards and sets visability to invisible
        Card player1Card1 = players.get(1).getHand().get(0);
        player1Card1View = (ImageView) rootView.findViewById(R.id.player1_card_1);
        player1Card1View.setContentDescription(player1Card1.getCardNumber()+ " of " + player1Card1.getSuitName());
        showCard(player1Card1);
        player1Card1View.setVisibility(View.INVISIBLE);

        Card player1Card2 = players.get(1).getHand().get(1);
        player1Card2View = (ImageView) rootView.findViewById(R.id.player1_card_2);
        player1Card2View.setContentDescription(player1Card2.getCardNumber()+ " of " + player1Card2.getSuitName());
        showCard(player1Card2);
        player1Card2View.setVisibility(View.INVISIBLE);

        Card player2Card1 = players.get(2).getHand().get(0);
        player2Card1View = (ImageView) rootView.findViewById(R.id.player2_card_1);
        player2Card1View.setContentDescription(player2Card1.getCardNumber()+ " of " + player2Card1.getSuitName());
        showCard(player2Card1);
        player2Card1View.setVisibility(View.INVISIBLE);

        Card player2Card2 = players.get(2).getHand().get(1);
        player2Card2View = (ImageView) rootView.findViewById(R.id.player2_card_2);
        player2Card2View.setContentDescription(player2Card2.getCardNumber()+ " of " + player2Card2.getSuitName());
        showCard(player2Card2);
        player2Card2View.setVisibility(View.INVISIBLE);

        Card player3Card1 = players.get(3).getHand().get(0);
        player3Card1View = (ImageView) rootView.findViewById(R.id.player3_card_1);
        player3Card1View.setContentDescription(player3Card1.getCardNumber()+ " of " + player3Card1.getSuitName());
        showCard(player3Card1);
        player3Card1View.setVisibility(View.INVISIBLE);

        Card player3Card2 = players.get(3).getHand().get(1);
        player3Card2View = (ImageView) rootView.findViewById(R.id.player3_card_2);
        player3Card2View.setContentDescription(player3Card2.getCardNumber()+ " of " + player3Card2.getSuitName());
        showCard(player3Card2);
        player3Card2View.setVisibility(View.INVISIBLE);

        Card player4Card1 = players.get(4).getHand().get(0);
        player4Card1View = (ImageView) rootView.findViewById(R.id.player4_card_1);
        player4Card1View.setContentDescription(player4Card1.getCardNumber()+ " of " + player4Card1.getSuitName());
        showCard(player4Card1);
        player4Card1View.setVisibility(View.INVISIBLE);

        Card player4Card2 = players.get(4).getHand().get(1);
        player4Card2View = (ImageView) rootView.findViewById(R.id.player4_card_2);
        player4Card2View.setContentDescription(player4Card2.getCardNumber()+ " of " + player4Card2.getSuitName());
        showCard(player4Card2);
        player4Card2View.setVisibility(View.INVISIBLE);

        Card player5Card1 = players.get(5).getHand().get(0);
        player5Card1View = (ImageView) rootView.findViewById(R.id.player5_card_1);
        player5Card1View.setContentDescription(player5Card1.getCardNumber()+ " of " + player5Card1.getSuitName());
        showCard(player5Card1);
        player5Card1View.setVisibility(View.INVISIBLE);

        Card player5Card2 = players.get(5).getHand().get(1);
        player5Card2View = (ImageView) rootView.findViewById(R.id.player5_card_2);
        player5Card2View.setContentDescription(player5Card2.getCardNumber()+ " of " + player5Card2.getSuitName());
        showCard(player5Card2);
        player5Card2View.setVisibility(View.INVISIBLE);
    }

    private void changePlayerView(int index){
        //cahnges cards to curretn players cards
        myCard1 = players.get(index).getHand().get(0);
        myCard2 = players.get(index).getHand().get(1);

        myCard1View = (ImageView) rootView.findViewById(R.id.my_card_1);
        myCard1View.setContentDescription(myCard1.getCardNumber() + " of " + myCard1.getSuitName());
        showCard(myCard1);

        myCard2View = (ImageView) rootView.findViewById(R.id.my_card_2);
        myCard2View.setContentDescription(myCard2.getCardNumber() + " of " + myCard2.getSuitName());
        showCard(myCard2);

        //Sets the player views (name and monnies)

        int[] indices = new int[5];
        switch(index){
            case 1:
                indices[0] = 1;
                indices[1] = 2;
                indices[2] = 3;
                indices[3] = 4;
                indices[4] = -1;
                break;
            case 2:
                indices[0] = 1;
                indices[1] = 2;
                indices[2] = 3;
                indices[3] = -1;
                indices[4] = -2;
                break;
            case 3:
                indices[0] = 1;
                indices[1] = 2;
                indices[2] = -1;
                indices[3] = -2;
                indices[4] = -3;
                break;
            case 4:
                indices[0] = 1;
                indices[1] = -1;
                indices[2] = -2;
                indices[3] = -3;
                indices[4] = -4;
                break;
            case 5:
                indices[0] = -1;
                indices[1] = -2;
                indices[2] = -3;
                indices[3] = -4;
                indices[4] = -5;
                break;
            case 0:
                indices[0] = 0;
                indices[1] = 1;
                indices[2] = 2;
                indices[3] = 3;
                indices[4] = 4;
                break;
        }
        //rotates player textview
        player1View = (TextView) rootView.findViewById(R.id.player_1);
        String p1 ="";
        if (players.get(index +indices[0]).getName() != null) {
            p1 = players.get(index +indices[0]).getName() + ": $" + players.get(index +indices[0]).getMonnies();
        }
        player1View.setText(p1);

        player2View = (TextView) rootView.findViewById(R.id.player_2);
        String p2 ="";
        if (players.get(index +indices[1]).getName() != null) {
            p2 = players.get(index +indices[1]).getName() + ": $" + players.get(index +indices[1]).getMonnies();
        }
        player2View.setText(p2);


        player3View = (TextView) rootView.findViewById(R.id.player_3);
        String p3 = "";
        if (players.get(index +indices[2]).getName() != null) {
            p3 = players.get(index +indices[2]).getName() + ": $" + players.get(index +indices[2]).getMonnies();
        }
        player3View.setText(p3);


        player4View = (TextView) rootView.findViewById(R.id.player_4);
        String p4 = "";
        if (players.get(index +indices[3]).getName() != null) {
            p4 = players.get(index +indices[3]).getName() + ": $" + players.get(index +indices[3]).getMonnies();
        }
        player4View.setText(p4);


        player5View = (TextView) rootView.findViewById(R.id.player_5);
        String p5 = "";
        if (players.get(index +indices[4]).getName() != null) {
            p5 = players.get(index + indices[4]).getName() + ": $" + players.get(index + indices[4]).getMonnies();
        }
        player5View.setText(p5);


        player6View = (TextView) rootView.findViewById(R.id.user);
        String p6 = players.get(index).getName() + ": $" + players.get(index).getMonnies();
        player6View.setText(p6);
    }

    private void wireWidgets() {
        bet = (TextView) rootView.findViewById(R.id.bet);
        bet.setText("$" + potMoney);

        raise = (Button) rootView.findViewById(R.id.raise);//todo
        raise.setOnClickListener(this);

        fold = (Button) rootView.findViewById(R.id.fold);
        fold.setOnClickListener(this);

        callCheck = (Button) rootView.findViewById(R.id.call_check);
        callCheck.setOnClickListener(this);
    }

    //displays the cards in the right card view
    private void showCard(Card myCard) {
        int res = getResources().getIdentifier(myCard.getSuit() + "_" + myCard.getNumber(), "drawable", "com.example.caroline.realpoker");
        if (myCard.equals(myCard1)) {
            myCard1View.setImageResource(res);
            myCard1View.setVisibility(View.VISIBLE);
        } else if (myCard.equals(myCard2)) {
            myCard2View.setImageResource(res);
            myCard2View.setVisibility(View.VISIBLE);
        } else if (myCard.equals(players.get(1).getHand().get(0))) {
            player1Card1View.setImageResource(res);
        } else if (myCard.equals(players.get(1).getHand().get(1))) {
            player1Card2View.setImageResource(res);
        } else if (myCard.equals(players.get(2).getHand().get(0))) {
            player2Card1View.setImageResource(res);
        } else if (myCard.equals(players.get(2).getHand().get(1))) {
            player2Card2View.setImageResource(res);
        } else if (myCard.equals(players.get(3).getHand().get(0))) {
            player3Card1View.setImageResource(res);
        } else if (myCard.equals(players.get(3).getHand().get(1))) {
            player3Card2View.setImageResource(res);
        } else if (myCard.equals(players.get(4).getHand().get(0))) {
            player4Card1View.setImageResource(res);
        } else if (myCard.equals(players.get(4).getHand().get(1))) {
            player4Card2View.setImageResource(res);
        } else if (myCard.equals(players.get(5).getHand().get(0))) {
            player5Card1View.setImageResource(res);
        } else if (myCard.equals(players.get(5).getHand().get(1))) {
            player5Card2View.setImageResource(res);
        } else if (myCard.equals(tableCard1)) {
            tableCard1View.setImageResource(res);
        } else if (myCard.equals(tableCard2)) {
            tableCard2View.setImageResource(res);
        } else if (myCard.equals(tableCard3)) {
            tableCard3View.setImageResource(res);
        } else if (myCard.equals(tableCard4)) {
            tableCard4View.setImageResource(res);
        } else if (myCard.equals(tableCard5)) {
            tableCard5View.setImageResource(res);
        }
    }

}
