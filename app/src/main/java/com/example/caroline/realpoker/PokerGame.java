package com.example.caroline.realpoker;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.example.caroline.realpoker.R.drawable.ic_menu_send;

@SuppressLint("ValidFragment")
public class PokerGame extends Fragment implements View.OnClickListener{

    private ArrayList<Card> cardsOnTheTable;
    private ArrayList<Card> deck;
    private int numOfPlayers,currentplayer;
    private int potMoney;
    private ArrayList<Player> players;
    private TextView player1View, player2View, player3View, player4View, player5View, player6View, bet;
    private Button raise, fold,callCheck;

    private Card myCard1, myCard2, tableCard1, tableCard2, tableCard3, tableCard4, tableCard5;
    private ImageView myCard1View, myCard2View, tableCard1View, tableCard2View, tableCard3View, tableCard4View, tableCard5View;
    private View rootView;
    private ArrayList<Card> hand;


    private Player emptyPlayer;

    @SuppressLint("ValidFragment")
    public PokerGame(int num, ArrayList<Player> p) {
        numOfPlayers = num;
        players = p;
    }

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        //Inflate the layout we made (one_fragment.xml)
        rootView = inflater.inflate(R.layout.activity_poker_game, container, false);
        deck = new ArrayList<>();
        numOfPlayers = 6;
        currentplayer=0;
        hand = new ArrayList<>();
        hand.add(new Card(0, "c"));
        hand.add(new Card(0, "c"));
        emptyPlayer=new Player("", 0, hand);
        startNewGame();

        //get any other initial set up done
        //in place of where you would normally say this,
        //you use getActivity() instead to get the context
        //todo do this for real stuff
        /*
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "I CLICKED THE THING",
                        Toast.LENGTH_SHORT).show();
            }
        });*/

        //return the view that we inflated.
        return rootView;
    }

    private void startNewGame() {
        createDeck();
        createPlayerHands();
        createCardsOnTheTable();
        createCards();
        wireWidgets();
        //checkingHand();
    }

    private void checkingHand() {
        Hand hand1 = new Hand(players.get(0).getHand(), cardsOnTheTable);
        ArrayList<Integer> intstuff =  new ArrayList<>();
                intstuff.addAll(hand1.getBestHand());
        Log.d(TAG, "checkingHand: "+intstuff.toString());
    }

    private void createCards() {
        myCard1 = players.get(0).getHand().get(0);
        myCard2 = players.get(0).getHand().get(1);
        tableCard1 = cardsOnTheTable.get(0);
        tableCard2 = cardsOnTheTable.get(1);
        tableCard3 = cardsOnTheTable.get(2);
        tableCard4 = cardsOnTheTable.get(3);
        tableCard5 = cardsOnTheTable.get(4);
    }

    private void createCardsOnTheTable() {
        int cardPlace = (int)(Math.random()*deck.size());
        cardsOnTheTable = new ArrayList<>();
        for(int i = 0; i<5;i++){
            cardsOnTheTable.add(deck.get(cardPlace));
            deck.remove(cardPlace);
            cardPlace = (int)(Math.random()*deck.size());
        }
    }

    private void createDeck() {
        String[] suits = {"c", "d", "h", "s"};
        for(String s:suits){
            for(int i = 2; i < 15;i++){
                deck.add(new Card(i,s));
            }
        }
    }
    //todo get player name from a dialogue we will write later
    private void createPlayerHands() {
        for(Player p:players){
           p.setHand(getHand());
        }
    }

    private void wireWidgets() {

        myCard1=players.get(0).getHand().get(0);
        myCard2=players.get(0).getHand().get(1);

        //Wire any widgets -- must use rootView.findViewById
        myCard1View = (ImageView) rootView.findViewById(R.id.my_card_1);
        myCard1View.setContentDescription(myCard1.getCardNumber()+ " of " + myCard1.getSuitName());
        showCard(myCard1);

        myCard2View = (ImageView) rootView.findViewById(R.id.my_card_2);
        myCard2View.setContentDescription(myCard2.getCardNumber()+ " of " + myCard2.getSuitName());
        showCard(myCard2);

        tableCard1View = (ImageView) rootView.findViewById(R.id.table_card_1);
        tableCard1View.setContentDescription(tableCard1.getCardNumber()+ " of " + tableCard1.getSuitName());

        tableCard2View = (ImageView) rootView.findViewById(R.id.table_card_2);
        tableCard2View.setContentDescription(tableCard2.getCardNumber()+ " of " + tableCard2.getSuitName());

        tableCard3View = (ImageView) rootView.findViewById(R.id.table_card_3);
        tableCard3View.setContentDescription(tableCard3.getCardNumber()+ " of " + tableCard3.getSuitName());

        tableCard4View = (ImageView) rootView.findViewById(R.id.table_card_4);
        tableCard4View.setContentDescription(tableCard4.getCardNumber()+ " of " + tableCard4.getSuitName());

        tableCard5View = (ImageView) rootView.findViewById(R.id.table_card_5);
        tableCard5View.setContentDescription(tableCard5.getCardNumber()+ " of " + tableCard5.getSuitName());

        player1View = (TextView) rootView.findViewById(R.id.player_1);
        String p1 = players.get(1).getName() + ": $" + players.get(1).getMonnies();
        player1View.setText(p1);

        player2View = (TextView) rootView.findViewById(R.id.player_2);
        String p2 = players.get(2).getName() + ": $" + players.get(2).getMonnies();
        player2View.setText(p2);

        player3View = (TextView) rootView.findViewById(R.id.player_3);
        String p3 = players.get(3).getName() + ": $" + players.get(3).getMonnies();
        player3View.setText(p3);

        player4View = (TextView) rootView.findViewById(R.id.player_4);
        String p4 = players.get(4).getName() + ": $" + players.get(4).getMonnies();
        player4View.setText(p4);

        player5View = (TextView) rootView.findViewById(R.id.player_5);
        String p5 = players.get(5).getName() + ": $" + players.get(5).getMonnies();
        player5View.setText(p5);

        player6View = (TextView) rootView.findViewById(R.id.user);
        String p6 = players.get(0).getName() + ": $" + players.get(0).getMonnies();
        player6View.setText(p6);

        bet = (TextView) rootView.findViewById(R.id.bet);
        bet.setText("$"+potMoney);

        raise = (Button) rootView.findViewById(R.id.raise);//todo
        raise.setOnClickListener(this);
        fold = (Button) rootView.findViewById(R.id.fold);
        fold.setOnClickListener(this);
        callCheck = (Button) rootView.findViewById(R.id.call_check);
        callCheck.setText("Start Game");
        callCheck.setOnClickListener(this);
    }
    public void raiseBet(){
         final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        // set title
        alertDialogBuilder.setTitle("Input Raise amount");
        alertDialogBuilder.setView(input);
        //alertDialogBuilder.setIcon(R.drawable.ic_delete);
        // set dialog message


        alertDialogBuilder.setCancelable(true).setPositiveButton("ok",new DialogInterface.OnClickListener() {
            int amountRaised;
            public void onClick(DialogInterface dialog,int id) {

                                try{
                                    if(Integer.parseInt(input.getText().toString())>0 &&(Integer.parseInt(input.getText().toString())<players.get(0).getMonnies()))
                                    {
                                        amountRaised=Integer.parseInt(input.getText().toString());
                                        potMoney+=amountRaised;
                                        players.get(0).setMonnies(players.get(0).getMonnies()-amountRaised);
                                        bet.setText("$"+potMoney);
                                        player6View.setText(players.get(0).getName()+": $"+players.get(0).getMonnies());
                                        nextGuy();
                                    }

                                }
                                catch(NumberFormatException e) {
                                    Toast.makeText(getActivity(), "please enter a number", Toast.LENGTH_SHORT).show();


                                }
                                // if this button is clicked, close
                                // current activity

                            }
                        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();



    }
    public void endTurn(){//todo add fold method

        nextGuy();

        Log.d(TAG, "endTurn: ");
    }

    public void nextGuy(){

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        // set title
        int i=0;
        if(currentplayer < players.size() - 1){
            i = currentplayer +1;
        }
        alertDialogBuilder.setTitle("Are you "+players.get(i).getName() +"?");

        // set dialog message

        alertDialogBuilder.setCancelable(false).setPositiveButton("yes",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                changePlayer();
                // current activity

            }
        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        Log.d(TAG, "nextGuy: ");

    } //todo move to next player

    private void changePlayer() {
        Player p= players.remove(0);
        players.add(p);
        wireWidgets();
    }


    private void showCard(Card myCard) {
        int res = getResources().getIdentifier(myCard.getSuit()+"_"+myCard.getNumber(), "drawable", "com.example.caroline.realpoker");
        if(myCard.equals(myCard1)) {
            myCard1View.setImageResource(res);
        } else if(myCard.equals(myCard2)){
            myCard2View.setImageResource(res);
        } else if(myCard.equals(tableCard1)) {
            tableCard1View.setImageResource(res);
        } else if(myCard.equals(tableCard2)){
            tableCard2View.setImageResource(res);
        } else if(myCard.equals(tableCard3)) {
            tableCard3View.setImageResource(res);
        } else if(myCard.equals(tableCard4)){
            tableCard4View.setImageResource(res);
        } else if(myCard.equals(tableCard5)){
            tableCard5View.setImageResource(res);
        }
    }

    public ArrayList<Card> getHand() {
        int cardPlace = (int)( Math.random()*deck.size());
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(deck.get(cardPlace));
        deck.remove(cardPlace);
        cardPlace = (int) (Math.random()*deck.size());
        hand.add(deck.get(cardPlace));
        deck.remove(cardPlace);
        return hand;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: "+v.getId());
        Log.d(TAG, "onClick: "+R.id.raise);
        switch(v.getId()){
            case R.id.raise:
                raiseBet();
                break;
            case R.id.call_check:
                callCheck.setText("call/check");
                nextGuy();
                break;
            case R.id.fold:
                endTurn();
                break;
        }
    }

    /** public Player getWinner(){
        ArrayList<Integer> best = new ArrayList<>();
        best.add(-1);
        int bestPlayer = -1;
        for(int i = 0; i < players.length;i++) {
            Hand hand1 = new Hand(players[i].getHand(), cardsOnTheTable);
            ArrayList<Integer> intstuff1 = new ArrayList<>();
            intstuff1.addAll(hand1.getBestHand());
            if(hand1.getHigherHand(best,intstuff1)==intstuff1){
                bestPlayer = i;
            }
            best = hand1.getHigherHand(best,intstuff1);
        }

        return players[bestPlayer];
    }
    */

}

/**
 *Hand hand1 = new Hand(players[0].getHand(), cardsOnTheTable);
 ArrayList<Integer> intstuff1 =  new ArrayList<>();
 intstuff1.addAll(hand1.getBestHand());

 Hand hand2 = new Hand(players[1].getHand(), cardsOnTheTable);
 ArrayList<Integer> intstuff2 =  new ArrayList<>();
 intstuff2.addAll(hand2.getBestHand());

 Hand hand3 = new Hand(players[2].getHand(), cardsOnTheTable);
 ArrayList<Integer> intstuff3 =  new ArrayList<>();
 intstuff3.addAll(hand3.getBestHand());
 if(players.length>3) {
 Hand hand4 = new Hand(players[3].getHand(), cardsOnTheTable);
 ArrayList<Integer> intstuff4 = new ArrayList<>();
 intstuff4.addAll(hand4.getBestHand());
 hand1.getHigherHand(hand1.getHigherHand(intstuff1,intstuff2),hand1.getHigherHand(intstuff3,intstuff4));
 }
 else if(players.length>4) {
 Hand hand5 = new Hand(players[4].getHand(), cardsOnTheTable);
 ArrayList<Integer> intstuff5 = new ArrayList<>();
 intstuff5.addAll(hand5.getBestHand());
 }

 else if(players.length>5) {
 Hand hand6 = new Hand(players[5].getHand(), cardsOnTheTable);
 ArrayList<Integer> intstuff6 = new ArrayList<>();
 intstuff6.addAll(hand6.getBestHand());
 }
 else{
 return;
 }
 */