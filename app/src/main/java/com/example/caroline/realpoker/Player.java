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
    private int raiseBy = 0;
    private boolean hasFolded,hasCalled;
    ArrayList<Card> emptyHand = new ArrayList<>();
    private int bet;



    public Player(String name, int monnies, ArrayList<Card> hand) {
        this.name = name;
        this.monnies = monnies;
        this.hand = hand;
        emptyHand.add(new Card(0, "c"));
        emptyHand.add(new Card(0, "c"));
    }

    public int getRaiseBy() {
        return raiseBy;
    }

    public void setRaiseBy(int raiseBy) {
        this.raiseBy = raiseBy;
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

    public void subtractMonnies(int monnies) {

        this.monnies = monnies;
    }
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int newBetAmount) {
        monnies-=(newBetAmount-bet);
        bet=newBetAmount;
    }
  
    public void setHasFolded(boolean fold){
        hand = emptyHand;
        hasFolded = fold;
    }

    public boolean hasFolded(){
        return hasFolded;
    }

    public boolean hasCalled() {
        return hasCalled;
    }

    public void setHasCalled(boolean hasCalled) {
        this.hasCalled = hasCalled;
    }
}