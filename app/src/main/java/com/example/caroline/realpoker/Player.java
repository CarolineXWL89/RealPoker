package com.example.caroline.realpoker;


/**
 * Created by maylisw on 11/11/17.
 */

public class Player {
    //Player (Name, Monnies, hand (ARRAY))
    private String name;
    private int monnies;
    private String[] hand;

    public Player(String name, int monnies, String[] hand) {
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

    public String[] getHand() {
        return hand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMonnies(int monnies) {
        this.monnies = monnies;
    }

    public void setHand(String[] hand) {
        this.hand = hand;
    }
}
