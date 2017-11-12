package com.example.caroline.realpoker;

/**
 * Created by maylisw on 11/11/17.
 */

public class Card {
    private int number;
    private String suit;

    public Card(int number, String suit) {
        this.number = number;
        this.suit = suit;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSuit() {
        return suit;
    }

    public String getSuitName() {
        String s = suit;
        switch(suit){
            case "C":
                s = "Clubs";
                break;
            case "H":
                s = "Hearts";
                break;
            case "D":
                s = "Diamonds";
                break;
            case "S":
                s = "Spades";
                break;
        }
        return s;
    }

    public String getCardNumber() {
        String n = "";
        switch(number) {
            case 2:
                n = "2";
                break;
            case 3:
                n = "3";
                break;
            case 4:
                n = "4";
                break;
            case 5:
                n = "5";
                break;
            case 6:
                n = "6";
                break;
            case 7:
                n = "7";
                break;
            case 8:
                n = "8";
                break;
            case 9:
                n = "9";
                break;
            case 10:
                n = "10";
                break;
            case 11:
                n = "Jack";
                break;
            case 12:
                n = "Queen";
                break;
            case 13:
                n = "King";
                break;
            case 14:
                n = "Ace";
                break;
        }
        return n;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }


}

