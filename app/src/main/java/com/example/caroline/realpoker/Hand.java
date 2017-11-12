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

    public ArrayList<Integer> getBestHand(ArrayList<Card> cards){
        ArrayList<Integer> best = new ArrayList<>();
        for(int i=0;i<7;i++){
            for(int j = i+1;j<7;j++){
                    ArrayList<Card> googleDocsSucks = new ArrayList<>();
                    googleDocsSucks.addAll(cards);
                    googleDocsSucks.remove(i);
                    googleDocsSucks.remove(j);
                    ArrayList<Integer> samsPuns=compare.getHandType(googleDocsSucks);
                    best = getHigherHand(samsPuns, best);

            }
        }
        return best;
    }

    public ArrayList<Integer> getHigherHand(ArrayList<Integer> one, ArrayList<Integer> two){
        if((one.get(0)>two.get(0))){
            return one;
        }
        else if(one.get(0)==two.get(0)){
            for(int i = 1; i <one.size();i++){
                if(one.get(i)>two.get(i)){
                    return one;
                }
                else if(one.get(i)<two.get(i)){
                    return two;
                }
                else{

                }
            }
            return one;
        }
        else{
            return two;
        }
    }


}
