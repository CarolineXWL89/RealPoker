package com.example.caroline.realpoker;

import java.util.ArrayList;

/**
 * Created by Sam on 11/11/17.
 */

public class HandComparer {

    // 0 = high card
    // 1 = pair
    // 2 = 2 pair
    // 3 = three of a kind
    // 4 = straight
    // 5 = flush
    //6 = full house
    //7 = four of a kind
    //8 Straight flush


    public ArrayList<Integer> getHandType(ArrayList<Card> cards){

        ArrayList<Integer> ans = new ArrayList<>();
        boolean flush= checkForFlush(cards);
        boolean straight=checkForStraight(cards);
        ArrayList<Integer> match = checkForCrap(cards);

        if (flush && straight)
        {
            ans.add(8);
        }
        else if (flush)
        {
            ans.add(5);
        }
        // Straight might be a WIP
        else if (straight)
        {
            ans.add(4);
        }
        // Straight might be a WIP
        else{
            return match;
        }










    }

    private boolean checkForStraight(ArrayList<Card> cards) {
        int n=cards.size();
        for(int i=0; i<n; i++)
        {
            for (int j=1;j<n-i; j++)
            {
                if(cards.get(j-1).getNumber()>cards.get(j).getNumber())
                {
                    cards.add(j-1, cards.remove(j));

                }

            }

        }
        if(cards.get(0).getNumber() + 1 == cards.get(1).getNumber()
                && cards.get(1).getNumber() +1 == cards.get(2).getNumber()
                && cards.get(2).getNumber() +1 == cards.get(3).getNumber()
                && cards.get(3).getNumber() +1 == cards.get(4).getNumber()) {
            return true;

        }
        else if(cards.get(4).getNumber() == 14
                && 2 == cards.get(1).getNumber()
                && 3 == cards.get(2).getNumber()
                && 4 == cards.get(3).getNumber()
                && 5 == cards.get(4).getNumber()) {
            return true;

        }
        return false;

    }


    public boolean checkForFlush(ArrayList<Card> cards)
    {
        if(cards.get(0).getSuit().equals(cards.get(1).getSuit())
                && cards.get(2).getSuit().equals(cards.get(1).getSuit())
                && cards.get(2).getSuit().equals(cards.get(3).getSuit())
                && cards.get(3).getSuit().equals(cards.get(4).getSuit()))
        {
            return true;
        }
        return false;

    }

    public ArrayList<Integer> checkForCrap(ArrayList<Card> cards){
        ArrayList<Integer> list = new ArrayList<>();
        int matches=0;
        ArrayList<Integer> storedOne=new ArrayList<>();
        ArrayList<Integer> storedTwo=new ArrayList<>();
        for (int i=0; i< cards.size()-1;i++ ){
            for(int j=i+1; j<cards.size();j++){
                if(cards.get(i).getNumber()==cards.get(j).getNumber()) {
                    matches++;
                    if (storedOne.size()==0){
                        storedOne.add(cards.get(i).getNumber());
                    }
                    else if(cards.get(i).getNumber() == (int)storedOne.get(0)){
                        storedOne.add(cards.get(i).getNumber());
                    }
                    else{
                        storedTwo.add(cards.get(i).getNumber());
                    }
                }
            }
        }
        if(matches ==6){
            matches = 7;
        }
        else if(matches == 4){
            matches = 6;
        }
        list.add(matches);
        if(storedOne.size()>storedTwo.size()) {
            list.add(storedOne.get(0));
            if (matches == 6) {
                list.add(storedTwo.get(0));
            } else if (matches == 7) {
                int b = 4;
                for (int i = 0; i < 3; i++) {
                    if (cards.get(i).getNumber() != cards.get(i + 1).getNumber()) {
                        if (cards.get(i).getNumber() != cards.get(4).getNumber()) {
                            b = i;
                        } else {
                            b = i + 1;
                        }
                    }
                }
                list.add(cards.get(b).getNumber());
            } else if (matches == 3) {
                int a = Math.max(cards.get(0).getNumber(), cards.get(1).getNumber());
                int b = Math.max(Math.max(cards.get(2).getNumber(), cards.get(3).getNumber()), (cards.get(4).getNumber()));
                if (a > b) {
                    list.add(a);
                } else if (b > a) {
                    list.add(b);
                } else {
                    if (cards.get(0).getNumber() != cards.get(1).getNumber()) {
                        list.add(Math.max(Math.min(cards.get(0).getNumber(), cards.get(1).getNumber()), Math.min(Math.min(cards.get(2).getNumber()
                                , cards.get(3).getNumber()), (cards.get(4).getNumber()))));
                    } else {
                        if (b == cards.get(2).getNumber()) {
                            list.add(Math.max(cards.get(3).getNumber(), cards.get(4).getNumber()));
                        } else if (b == cards.get(3).getNumber()) {
                            list.add(Math.max(cards.get(2).getNumber(), cards.get(4).getNumber()));
                        } else {
                            list.add(Math.max(cards.get(3).getNumber(), cards.get(2).getNumber()));
                        }
                    }
                }
            }
            else{
                int n=cards.size();
                for(int i=0; i<n; i++)
                {
                    for (int j=1;j<n-i; j++)
                    {
                        if(cards.get(j-1).getNumber()>cards.get(j).getNumber())
                        {
                            cards.add(j-1, cards.remove(j));

                        }

                    }

                }
                if(cards.get(4).getNumber()==cards.get(3).getNumber()){
                    list.add(cards.get(2).getNumber());
                }
                else{
                    list.add(cards.get(4).getNumber());
                }
            }
        }
        else if(storedTwo.size()==storedOne.size() && storedOne.size()>0){
            list.add(Math.max(storedOne.get(0),storedTwo.get(0)));
            list.add(Math.min(storedOne.get(0),storedTwo.get(0)));
            int n=cards.size();
            for(int i=0; i<n; i++)
            {
                for (int j=1;j<n-i; j++)
                {
                    if(cards.get(j-1).getNumber()>cards.get(j).getNumber())
                    {
                        cards.add(j-1, cards.remove(j));

                    }

                }

            }
            if(cards.get(4).getNumber()!= cards.get(3).getNumber()){
                list.add(cards.get(4).getNumber());
            }
            else if(cards.get(2).getNumber()!=cards.get(1).getNumber()){
                list.add(cards.get(2).getNumber());
            }
            else{
                list.add(cards.get(0).getNumber());
            }
        }
        else if(storedTwo.size()>storedOne.size() && storedTwo.size()>0){
            list.add(storedTwo.get(0));
        }
        else{
            int a = Math.max(cards.get(0).getNumber(),cards.get(1).getNumber());
            int b = Math.max(Math.max(a,cards.get(2).getNumber()),cards.get(3).getNumber());
            int c = Math.max(b,cards.get(4).getNumber());
            list.add(c);
        }
        return list;


    }


}


/**if(storedOne.size()>storedTwo.size()){
        list.add(storedOne.get(0));
        for(){

        }
        }
        else if(storedTwo.size()==storedOne.size() && storedOne.size()>0){
        list.add(Math.max(storedOne.get(0),storedTwo.get(0)));
        list.add(Math.min(storedOne.get(0),storedTwo.get(0)));
        }
        else if(storedTwo.size()>storedOne.size() && storedTwo.size()>0){
        list.add(storedTwo.get(0));
        }
        else{
        int a = Math.max(cards.get(0).getNumber(),cards.get(1).getNumber());
        int b = Math.max(Math.max(a,cards.get(2).getNumber()),cards.get(3).getNumber());
        int c = Math.max(b,cards.get(4).getNumber());
        list.add(c);
        }*/

/**
 * while(i){
 if(i+1<5 && cards.get(i).getNumber() != cards.get(i+1).getNumber()){
 if(i<4){
 if(cards.get(i+1).getNumber()==cards.get(0).getNumber()){
 b = i;
 }
 else{
 b= i+1;
 }
 }
 else{
 if(cards.get(i).getNumber() == cards.get(0).getNumber()){
 b = i;
 }
 }
 }

 }
 */