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

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.example.caroline.realpoker.R.drawable.ic_menu_send;

public class PokerGame extends Fragment {

    private ArrayList<Card> cardsOnTheTable;
    private ArrayList<Card> deck;
    private int numOfPlayers;
    private Player[] players;
    private TextView player1View, player2View, player3View, player4View, player5View, player6View, bet;
    private Button fold, callCheck, raise;

    private Card myCard1, myCard2, tableCard1, tableCard2, tableCard3, tableCard4, tableCard5;
    private ImageView myCard1View, myCard2View, tableCard1View, tableCard2View, tableCard3View, tableCard4View, tableCard5View;
    private View rootView;

    public PokerGame() {
    }

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        //Inflate the layout we made (one_fragment.xml)
        rootView = inflater.inflate(R.layout.activity_poker_game, container, false);
        deck = new ArrayList<>();
        numOfPlayers = 6;
        createDeck();
        createPlayers();
        createCardsOnTheTable();
        createCards();
        wireWidgets();
        checkingHand();



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

    private void checkingHand() {
        Hand hand1 = new Hand(players[0].getHand(), cardsOnTheTable);
        ArrayList<Integer> intstuff =  new ArrayList<>();
                intstuff.addAll(hand1.getBestHand());
        Log.d(TAG, "checkingHand: "+intstuff.toString());
    }

    private void createCards() {
        myCard1 = players[0].getHand().get(0);
        myCard2 = players[0].getHand().get(1);
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
    private void createPlayers() {
        players = new Player[numOfPlayers];
        for(int i = 0; i< numOfPlayers; i++){
            players[i] = new Player("Player"+i,10000, getHand());
        }
    }

    private void wireWidgets() {

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
        String p1 = players[1].getName() + ": $" + players[1].getMonnies();
        player1View.setText(p1);

        player2View = (TextView) rootView.findViewById(R.id.player_2);
        String p2 = players[2].getName() + ": $" + players[2].getMonnies();
        player2View.setText(p2);

        player3View = (TextView) rootView.findViewById(R.id.player_3);
        String p3 = players[3].getName() + ": $" + players[3].getMonnies();
        player3View.setText(p3);

        player4View = (TextView) rootView.findViewById(R.id.player_4);
        String p4 = players[4].getName() + ": $" + players[4].getMonnies();
        player4View.setText(p4);

        player5View = (TextView) rootView.findViewById(R.id.player_5);
        String p5 = players[5].getName() + ": $" + players[5].getMonnies();
        player5View.setText(p5);

        player6View = (TextView) rootView.findViewById(R.id.user);
        String p6 = players[0].getName() + ": $" + players[0].getMonnies();
        player6View.setText(p6);

        Button callCheck = (Button) rootView.findViewById(R.id.call_check);
        Button raise = (Button) rootView.findViewById(R.id.raise);
        Button fold = (Button) rootView.findViewById(R.id.fold);
        TextView bet = (TextView) rootView.findViewById((R.id.bet);

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
    public void startTurn(){

    }
    public void callCheck(View view) {

    }
    public void fold(View view){
        endTurn();
    }
    public void raise(View view){

    }
    public void endTurn()
    {

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