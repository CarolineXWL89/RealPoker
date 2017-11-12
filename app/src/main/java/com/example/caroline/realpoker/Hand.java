package com.example.caroline.realpoker;

import java.util.ArrayList;

/**
 * Created by Sam on 11/11/17.
 */

public class Hand {
    private ArrayList<Card> fullhand;
    private HandComparer compare = new HandComparer();


    public Hand(ArrayList<Card> playerCards, ArrayList<Card> centerCards){
        fullhand = new ArrayList<>();
        fullhand.addAll(playerCards);
        fullhand.addAll(centerCards);
    }

    public ArrayList<Card> getBestHand(ArrayList<Card> cards){
        ArrayList<Integer> best = new ArrayList<>();
        for(int i=0;i<7;i++){
            for(int j = i+1;j<7;j++){
                if(true){
                    ArrayList<Card> googleDocsSucks = new ArrayList<>();
                    googleDocsSucks.addAll(cards);
                    googleDocsSucks.remove(i);
                    googleDocsSucks.remove(j);
                    compare.getHandType(googleDocsSucks);
                    getHigherHand()
                }
            }
        }
    }

    public ArrayList<Integer> getHigherHand(){

    }


}
