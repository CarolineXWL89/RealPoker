package com.example.caroline.realpoker;

import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Sam on 11/11/17.
 */

public class Hand {
    private ArrayList<Card> fullhand = new ArrayList<>();
    private HandComparer compare = new HandComparer();


    public Hand(ArrayList<Card> playerCards, ArrayList<Card> centerCards){
        fullhand.addAll(playerCards);
        fullhand.addAll(centerCards);
    }

    public ArrayList<Integer> getBestHand(){
        ArrayList<Integer> best = new ArrayList<>();
        best.add(-1);
        for(int i=0;i<7;i++){
            for(int j = i;j<6;j++){
                    ArrayList<Card> googleDocsSucks = new ArrayList<>();
                    googleDocsSucks.addAll(fullhand);
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
