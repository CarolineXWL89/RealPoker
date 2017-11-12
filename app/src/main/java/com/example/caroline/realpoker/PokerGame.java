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

import static com.example.caroline.realpoker.R.drawable.ic_menu_send;

public class PokerGame extends Fragment {

    private ArrayList<Card> cardsOnTheTable;
    private ArrayList<Card> deck;
    private int numOfPlayers;
    private Player[] players;

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
        int cardPlace = (int) (Math.random()*deck.size());
        cardsOnTheTable = new ArrayList<>();
        for(int i = 0; i<5;i++){
            cardsOnTheTable.add(deck.get(cardPlace));
            deck.remove(cardPlace);
            cardPlace = (int) (Math.random()*deck.size());
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
            players[0] = new Player("Player"+i,10000, getHand());
        }
    }

    private void wireWidgets() {

        //Wire any widgets -- must use rootView.findViewById
        myCard1View = (ImageView) rootView.findViewById(R.id.my_card_1);
        myCard1View.setContentDescription(myCard1.getCardNumber()+ " of " + myCard1.getSuitName());
        int resA = getResources().getIdentifier(myCard1.getSuit()+"_"+myCard1.getNumber(), "drawable", "com.example.caroline.realpoker");
        myCard1View.setImageResource(resA);

        myCard2View = (ImageView) rootView.findViewById(R.id.my_card_2);
        myCard2View.setContentDescription(myCard2.getCardNumber()+ " of " + myCard2.getSuitName());
        int resB = getResources().getIdentifier(myCard2.getSuit()+"_"+myCard2.getNumber(), "drawable", "com.example.caroline.realpoker");
        myCard1View.setImageResource(resB);

        tableCard1View = (ImageView) rootView.findViewById(R.id.table_card_1);
        tableCard1View.setContentDescription(tableCard1.getCardNumber()+ " of " + tableCard1.getSuitName());
        int res1 = getResources().getIdentifier(tableCard1.getSuit()+"_"+tableCard1.getNumber(), "drawable", "com.example.caroline.realpoker");
        myCard1View.setImageResource(res1);

        tableCard2View = (ImageView) rootView.findViewById(R.id.table_card_2);
        tableCard2View.setContentDescription(tableCard2.getCardNumber()+ " of " + tableCard2.getSuitName());
        int res2 = getResources().getIdentifier(tableCard2.getSuit()+"_"+tableCard2.getNumber(), "drawable", "com.example.caroline.realpoker");
        myCard1View.setImageResource(res2);

        tableCard3View = (ImageView) rootView.findViewById(R.id.table_card_3);
        tableCard3View.setContentDescription(tableCard3.getCardNumber()+ " of " + tableCard3.getSuitName());
        int res3 = getResources().getIdentifier(tableCard1.getSuit()+"_"+tableCard1.getNumber(), "drawable", "com.example.caroline.realpoker");
        myCard1View.setImageResource(res3);

        tableCard4View = (ImageView) rootView.findViewById(R.id.table_card_4);
        tableCard4View.setContentDescription(tableCard4.getCardNumber()+ " of " + tableCard4.getSuitName());
        int res4 = getResources().getIdentifier(tableCard4.getSuit()+"_"+tableCard4.getNumber(), "drawable", "com.example.caroline.realpoker");
        myCard1View.setImageResource(res4);

        tableCard5View = (ImageView) rootView.findViewById(R.id.table_card_5);
        tableCard5View.setContentDescription(tableCard5.getCardNumber()+ " of " + tableCard5.getSuitName());
        int res5 = getResources().getIdentifier(tableCard5.getSuit()+"_"+tableCard5.getNumber(), "drawable", "com.example.caroline.realpoker");
        myCard1View.setImageResource(res5);

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

}
