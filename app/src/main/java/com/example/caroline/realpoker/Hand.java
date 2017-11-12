package com.example.caroline.realpoker;

import java.util.ArrayList;

/**
 * Created by Sam on 11/11/17.
 */

public class Hand {
    private ArrayList<Card> fullhand;


    public Hand(ArrayList<Card> playerCards, ArrayList<Card> centerCards){
        fullhand = new ArrayList<>();
        fullhand.addAll(playerCards);
        fullhand.addAll(centerCards);
    }

    public ArrayList<Card> getBestHand(){

        ;
    }


}
