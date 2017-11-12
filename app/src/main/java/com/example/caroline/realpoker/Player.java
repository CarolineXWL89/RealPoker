package com.example.caroline.realpoker;


import java.util.ArrayList;

/**
 * Created by maylisw on 11/11/17.
 */

public class Player {
    //Player (Name, Monnies, hand (ARRAY))
    private String name;
    private int monnies;
    private ArrayList<Card> hand;


    public Player(String name, int monnies, ArrayList<Card> hand) {
        this.name = name;
        this.monnies = monnies;
        this.hand = hand;
    }

    public String getName() {
        return name;
    }

    public int getMonnies() {
        return monnies;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMonnies(int monnies) {
        this.monnies = monnies;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }



}
