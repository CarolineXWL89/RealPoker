package com.example.caroline.realpoker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
public class PokerGame2 extends Fragment implements View.OnClickListener {

    private ArrayList<Card> cardsOnTheTable;
    private ArrayList<Card> deck;

    private int numOfPlayers, currentplayer;
    private int potMoney;
    private Player[] players;
    private ArrayList<Card> emptyHand = new ArrayList<>();
    private TextView player1View, player2View, player3View, player4View, player5View, player6View, bet;
    private boolean hasRaised = false;
    private int round, turn, currentBet;
    private Player emptyPlayer;
    private Card myCard1, myCard2, tableCard1, tableCard2, tableCard3, tableCard4, tableCard5;
    private ImageView myCard1View, myCard2View, tableCard1View, tableCard2View, tableCard3View, tableCard4View, tableCard5View,
            player1Card1View, player1Card2View, player2Card1View, player2Card2View, player3Card1View, player3Card2View, player4Card1View, player4Card2View,
            player5Card1View, player5Card2View;
    private View rootView;
    private Context context;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private Button callCheck, fold, raise;
    private boolean everyoneHasFolded;


    public PokerGame2() {

    }

    //TODO Overall:
    //todo debug raise and winners
    //todo fix blinds so they can work if player in teh middle hasalready folded ()
    //todo ui
        //todo add textedits for blah raised or folded
        //todo fix constraints overall for all phones etc... (poker game xml file)
        //todo  refrence needs some clean up, just at the bottom
        //todo SETTINGS UI for players needs to be fixed, preferably have a double coulmned thing but idk
        //todo create themes/brainstrom what that would look like or how it would work (saved instance state?)
        //todo change icon

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.activity_poker_game, container, false);
        newGame(true);
        return rootView;
    }

    private void newGame(boolean ask) {
        deck = new ArrayList<>();
        currentplayer = 0;
        currentBet=0;
        Log.d(TAG, "newGame: currentBet"+currentBet);
        round=0;
        context = getContext();

        sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        emptyHand.add(new Card(0, "c"));
        emptyHand.add(new Card(0, "c"));

        players = new Player[6];
        everyoneHasFolded = false;

        createDeck();
        createCardsOnTheTable();
        if(ask) {
            areNewPlayers(); //either creates new players or gets the old ones then starts the game
        } else{
            useOldPlayers();
        }
        
    }

    //sets the table card view and calls function to change other views
    private void setUpCards() {
        //Sets the table cards but doesnt show them
        tableCard1View = (ImageView) rootView.findViewById(R.id.table_card_1);
        tableCard1View.setContentDescription(tableCard1.getCardNumber() + " of " + tableCard1.getSuitName());
        tableCard1View.setImageResource(R.color.colorCardBack);

        tableCard2View = (ImageView) rootView.findViewById(R.id.table_card_2);
        tableCard2View.setContentDescription(tableCard2.getCardNumber() + " of " + tableCard2.getSuitName());
        tableCard2View.setImageResource(R.color.colorCardBack);

        tableCard3View = (ImageView) rootView.findViewById(R.id.table_card_3);
        tableCard3View.setContentDescription(tableCard3.getCardNumber() + " of " + tableCard3.getSuitName());
        tableCard3View.setImageResource(R.color.colorCardBack);

        tableCard4View = (ImageView) rootView.findViewById(R.id.table_card_4);
        tableCard4View.setContentDescription(tableCard4.getCardNumber() + " of " + tableCard4.getSuitName());
        tableCard4View.setImageResource(R.color.colorCardBack);

        tableCard5View = (ImageView) rootView.findViewById(R.id.table_card_5);
        tableCard5View.setContentDescription(tableCard5.getCardNumber() + " of " + tableCard5.getSuitName());
        tableCard5View.setImageResource(R.color.colorCardBack);

        createOtherPlayersCards();
        changePlayerView();

    }

    //sets up cards
    private void createOtherPlayersCards() {
        //Creates other player's cards and sets visability to invisible
        Card player1Card1 = players[1].getHand().get(0);
        player1Card1View = (ImageView) rootView.findViewById(R.id.player1_card_1);
        player1Card1View.setContentDescription(player1Card1.getCardNumber()+ " of " + player1Card1.getSuitName());
        showCard(player1Card1);
        player1Card1View.setVisibility(View.INVISIBLE);

        Card player1Card2 = players[1].getHand().get(1);
        player1Card2View = (ImageView) rootView.findViewById(R.id.player1_card_2);
        player1Card2View.setContentDescription(player1Card2.getCardNumber()+ " of " + player1Card2.getSuitName());
        showCard(player1Card2);
        player1Card2View.setVisibility(View.INVISIBLE);

        Card player2Card1 = players[2].getHand().get(0);
        player2Card1View = (ImageView) rootView.findViewById(R.id.player2_card_1);
        player2Card1View.setContentDescription(player2Card1.getCardNumber()+ " of " + player2Card1.getSuitName());
        showCard(player2Card1);
        player2Card1View.setVisibility(View.INVISIBLE);

        Card player2Card2 = players[2].getHand().get(1);
        player2Card2View = (ImageView) rootView.findViewById(R.id.player2_card_2);
        player2Card2View.setContentDescription(player2Card2.getCardNumber()+ " of " + player2Card2.getSuitName());
        showCard(player2Card2);
        player2Card2View.setVisibility(View.INVISIBLE);

        Card player3Card1 = players[3].getHand().get(0);
        player3Card1View = (ImageView) rootView.findViewById(R.id.player3_card_1);
        player3Card1View.setContentDescription(player3Card1.getCardNumber()+ " of " + player3Card1.getSuitName());
        showCard(player3Card1);
        player3Card1View.setVisibility(View.INVISIBLE);

        Card player3Card2 = players[3].getHand().get(1);
        player3Card2View = (ImageView) rootView.findViewById(R.id.player3_card_2);
        player3Card2View.setContentDescription(player3Card2.getCardNumber()+ " of " + player3Card2.getSuitName());
        showCard(player3Card2);
        player3Card2View.setVisibility(View.INVISIBLE);

        Card player4Card1 = players[4].getHand().get(0);
        player4Card1View = (ImageView) rootView.findViewById(R.id.player4_card_1);
        player4Card1View.setContentDescription(player4Card1.getCardNumber()+ " of " + player4Card1.getSuitName());
        showCard(player4Card1);
        player4Card1View.setVisibility(View.INVISIBLE);

        Card player4Card2 = players[4].getHand().get(1);
        player4Card2View = (ImageView) rootView.findViewById(R.id.player4_card_2);
        player4Card2View.setContentDescription(player4Card2.getCardNumber()+ " of " + player4Card2.getSuitName());
        showCard(player4Card2);
        player4Card2View.setVisibility(View.INVISIBLE);

        Card player5Card1 = players[5].getHand().get(0);
        player5Card1View = (ImageView) rootView.findViewById(R.id.player5_card_1);
        player5Card1View.setContentDescription(player5Card1.getCardNumber()+ " of " + player5Card1.getSuitName());
        showCard(player5Card1);
        player5Card1View.setVisibility(View.INVISIBLE);

        Card player5Card2 = players[5].getHand().get(1);
        player5Card2View = (ImageView) rootView.findViewById(R.id.player5_card_2);
        player5Card2View.setContentDescription(player5Card2.getCardNumber()+ " of " + player5Card2.getSuitName());
        showCard(player5Card2);
        player5Card2View.setVisibility(View.INVISIBLE);
    }

    //switches names
    private void changePlayerView(){
        int index = currentplayer;
        //cahnges cards to curretn players cards
        myCard1 = players[index].getHand().get(0);
        myCard2 = players[index].getHand().get(1);

        myCard1View = (ImageView) rootView.findViewById(R.id.my_card_1);
        myCard1View.setContentDescription(myCard1.getCardNumber() + " of " + myCard1.getSuitName());
        showCard(myCard1);

        myCard2View = (ImageView) rootView.findViewById(R.id.my_card_2);
        myCard2View.setContentDescription(myCard2.getCardNumber() + " of " + myCard2.getSuitName());
        showCard(myCard2);

        //Sets the player views (name and monnies)

        int[] indices = new int[5];
        switch(index){
            case 0:
                indices[0] = 1;
                indices[1] = 2;
                indices[2] = 3;
                indices[3] = 4;
                indices[4] = 5;
                break;
            case 1:
                indices[0] = 1;
                indices[1] = 2;
                indices[2] = 3;
                indices[3] = 4;
                indices[4] = -1;
                break;
            case 2:
                indices[0] = 1;
                indices[1] = 2;
                indices[2] = 3;
                indices[3] = -2;
                indices[4] = -1;
                break;
            case 3:
                indices[0] = 1;
                indices[1] = 2;
                indices[2] = -3;
                indices[3] = -2;
                indices[4] = -1;
                break;
            case 4:
                indices[0] = 1;
                indices[1] = -4;
                indices[2] = -3;
                indices[3] = -2;
                indices[4] = -1;
                break;
            case 5:
                indices[0] = -5;
                indices[1] = -4;
                indices[2] = -3;
                indices[3] = -2;
                indices[4] = -1;
                break;
        }
        //rotates player textview
        player1View = (TextView) rootView.findViewById(R.id.player_1);
        String p1 ="";
        Log.d(TAG, "changePlayerView: "+((int)index+ (int)indices[0]));
        Log.d(TAG, "changePlayerView: "+(index+indices[0]));
        if (!players[(index +indices[0])].hasFolded()) {
            p1 = players[(index +indices[0])].getName() + ": $" + players[(index +indices[0])].getMonnies();
        }
        player1View.setText(p1);

        player2View = (TextView) rootView.findViewById(R.id.player_2);
        String p2 ="";
        if (!players[(index +indices[1])].hasFolded()) {
                p2 = players[(index +indices[1])].getName() + ": $" + players[(index +indices[1])].getMonnies();
        }
        player2View.setText(p2);


        player3View = (TextView) rootView.findViewById(R.id.player_3);
        String p3 = "";
        if (!players[(index +indices[2])].hasFolded()) {
            p3 = players[(index +indices[2])].getName() + ": $" + players[(index +indices[2])].getMonnies();
        }
        player3View.setText(p3);


        player4View = (TextView) rootView.findViewById(R.id.player_4);
        String p4 = "";
        if (!players[(index +indices[3])].hasFolded()) {
            p4 = players[(index +indices[3])].getName() + ": $" + players[(index +indices[3])].getMonnies();
        }
        player4View.setText(p4);


        player5View = (TextView) rootView.findViewById(R.id.player_5);
        String p5 = "";
        Log.d(TAG, "changePlayerView: "+players[index+indices[4]].getName());
        if (!players[(index +indices[4])].hasFolded()) {
            p5 = players[(index + indices[4])].getName() + ": $" + players[(index + indices[4])].getMonnies();
        }
        player5View.setText(p5);


        player6View = (TextView) rootView.findViewById(R.id.user);
        String p6 = players[index].getName() + ": $" + players[index].getMonnies();
        player6View.setText(p6);
    }

    //sets up buttons
    private void wireWidgets() {
        bet = (TextView) rootView.findViewById(R.id.bet);
        bet.setVisibility(View.VISIBLE);
        bet.setText("$" + potMoney);

        raise = (Button) rootView.findViewById(R.id.raise);
        raise.setVisibility(View.VISIBLE);
        raise.setOnClickListener(this);

        fold = (Button) rootView.findViewById(R.id.fold);
        fold.setVisibility(View.VISIBLE);
        fold.setOnClickListener(this);

        callCheck = (Button) rootView.findViewById(R.id.call_check);
        callCheck.setVisibility(View.VISIBLE);
        callCheck.setOnClickListener(this);
        callCheck.setText("Call");
    }

    //displays the cards in the right card view
    private void showCard(Card myCard) {
        int res = getResources().getIdentifier(myCard.getSuit() + "_" + myCard.getNumber(), "drawable", "com.example.caroline.realpoker");
        if (myCard.equals(myCard1)) {
            myCard1View.setImageResource(res);
            myCard1View.setVisibility(View.VISIBLE);
        } else if (myCard.equals(myCard2)) {
            myCard2View.setImageResource(res);
            myCard2View.setVisibility(View.VISIBLE);
        } else if (myCard.equals(players[1].getHand().get(0))) {
            player1Card1View.setImageResource(res);
        } else if (myCard.equals(players[1].getHand().get(1))) {
            player1Card2View.setImageResource(res);
        } else if (myCard.equals(players[2].getHand().get(0))) {
            player2Card1View.setImageResource(res);
        } else if (myCard.equals(players[2].getHand().get(1))) {
            player2Card2View.setImageResource(res);
        } else if (myCard.equals(players[3].getHand().get(0))) {
            player3Card1View.setImageResource(res);
        } else if (myCard.equals(players[3].getHand().get(1))) {
            player3Card2View.setImageResource(res);
        } else if (myCard.equals(players[4].getHand().get(0))) {
            player4Card1View.setImageResource(res);
        } else if (myCard.equals(players[4].getHand().get(1))) {
            player4Card2View.setImageResource(res);
        } else if (myCard.equals(players[5].getHand().get(0))) {
            player5Card1View.setImageResource(res);
        } else if (myCard.equals(players[5].getHand().get(1))) {
            player5Card2View.setImageResource(res);
        } else if (myCard.equals(tableCard1)) {
            tableCard1View.setImageResource(res);
        } else if (myCard.equals(tableCard2)) {
            tableCard2View.setImageResource(res);
        } else if (myCard.equals(tableCard3)) {
            tableCard3View.setImageResource(res);
        } else if (myCard.equals(tableCard4)) {
            tableCard4View.setImageResource(res);
        } else if (myCard.equals(tableCard5)) {
            tableCard5View.setImageResource(res);
        }
    }

    //draws cards for the table
    private void createCardsOnTheTable() {
        int cardPlace = (int) (Math.random() * deck.size());
        cardsOnTheTable = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            cardsOnTheTable.add(deck.get(cardPlace));
            deck.remove(cardPlace);
            cardPlace = (int) (Math.random() * deck.size());
        }
    }

    //creates 52 cards in the arraylist
    private void createDeck() {
        deck= new ArrayList<>();
        String[] suits = {"c", "d", "h", "s"};
        for (String s : suits) {
            for (int i = 2; i < 15; i++) {
                deck.add(new Card(i, s));
            }
        }
    }

    //picks the hand for each player form the deck
    public ArrayList<Card> getHand() {
        int cardPlace = (int) (Math.random() * deck.size());
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(deck.get(cardPlace));
        deck.remove(cardPlace);
        cardPlace = (int) (Math.random() * deck.size());
        hand.add(deck.get(cardPlace));
        deck.remove(cardPlace);
        return hand;
    }

    //checks if new or use old
    private void areNewPlayers() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Same players as last time?");

        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                useOldPlayers();
            }
        });

        alertDialogBuilder.setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                howManyPlayers();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    //sets the name if nothing is entered
    private void editPlayerName(int i, String name) {
        if(name != null && !name.equals("")) {
            players[i-1].setName(name);
        }
        if(i == numOfPlayers) {
            startGame();
        }
    }

    //calls methods for starting game
    private void startGame() {
        addPlayersToSharedPref();
        createCards();
        wireWidgets();
        setUpCards();
        checkingHand();
        setUpBlinds();
    }

    //alerts players regading blinds
    private void setUpBlinds() {
        int sb =100;
        int bb=200;
        int i = 5;
        while(players[i].getName().equals("")){
            i--;
        }
        players[i].setBet(sb);
        String smallBlindName = players[i].getName();
        i--;
        while(players[i].getName().equals("")){
            i--;
        }
        players[i].setBet(bb);
        String largeBlindName = players[i].getName();
        currentBet=bb;
        Log.d(TAG, "setUpBlinds: currentBet"+currentBet);
        potMoney=sb+bb;
        bet.setText("$"+potMoney);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        final TextView t = new TextView(context);

        //todo change size and style to look good
        t.setText(smallBlindName+ " has the small blind. \n "+largeBlindName+ " has the big blind. \n "+"It is "+ players[0].getName() +"'s turn");
        t.setTextSize(30);
        t.setGravity(Gravity.CENTER);
        alertDialogBuilder.setView(t);
        alertDialogBuilder.setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                changePlayerView();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        myCard1View.setVisibility(View.INVISIBLE);
        myCard2View.setVisibility(View.INVISIBLE);
        alertDialog.show();
    }

    //adds players to shared prefrences
    private void addPlayersToSharedPref() {
        //adds all players to shared preferences to be used later
        editor.putBoolean("hasPlayers?", true);

        editor.putInt("Number of Players", numOfPlayers);

        for(int i = 0; i< players.length; i++){
            players[i].setSharedPref("Player "+(i+1));
        }

        editor.putString(players[0].getSharedPref(),players[0].getName());
        editor.putInt(players[0].getSharedPref() +" Monnies", players[0].getMonnies());

        editor.putString(players[1].getSharedPref(),players[1].getName());
        editor.putInt(players[1].getSharedPref()+" Monnies", players[1].getMonnies());

        editor.putString(players[2].getSharedPref(),players[2].getName());
        editor.putInt(players[2].getSharedPref()+" Monnies", players[2].getMonnies());

        editor.putString(players[3].getSharedPref(),players[3].getName());
        editor.putInt(players[3].getSharedPref()+ " Monnies", players[3].getMonnies());

        editor.putString(players[4].getSharedPref(),players[4].getName());
        editor.putInt(players[4].getSharedPref()+" Monnies", players[4].getMonnies());

        editor.putString(players[5].getSharedPref(),players[5].getName());
        editor.putInt(players[5].getSharedPref()+" Monnies", players[5].getMonnies());

        editor.commit();
    }

    //lets user use last rounds players if they exist else creates new ones
    private void useOldPlayers() {
        if(sharedPref.getBoolean("hasPlayers?", false)) { //if there are players
            numOfPlayers = sharedPref.getInt("Number of Players", 6);
            for (int i = 1; i < 7; i++) {
                String name = sharedPref.getString("Player " + i, "Player " + i);
                int monney = sharedPref.getInt("Player " + i + " Monnies", 10000);
                Log.d(TAG, "useOldPlayers: "+monney);
                Player p;
                if(name.equals("")){
                    p = new Player("", 0, emptyHand);
                    p.setHasFolded(true);
                    p.setSharedPref("Player "+i);
                } else {
                    p = new Player(name, monney, getHand());
                    p.setSharedPref("Player "+i);
                }
                players[i-1] = p;
            }
            startGame();
        } else { //otherwise create new ones
            Toast.makeText(context, "You don't have any saved players", Toast.LENGTH_LONG).show();
            howManyPlayers();
        }
    }

    //popup for # of players from 2-6
    private void howManyPlayers() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        // set title
        alertDialogBuilder.setTitle("How many players?");
        alertDialogBuilder.setView(input);

        CharSequence[] items = {"2","3","4","5","6"};
        alertDialogBuilder.setCancelable(false).setItems(items,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                numOfPlayers = id + 2;
                createNewPlayers();
            }
        });

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    //adds players to players arraylist
    private void createNewPlayers() {
        if(numOfPlayers != -1){
            for(int i= 0; i<6; i++){
                players[i] = new Player("", 0, emptyHand);
                players[i].setHasFolded(true);
                players[i].setSharedPref("Player "+(i+1));
            }
            int index=numOfPlayers;
            while(index > 0){
                players[index-1] = createPlayingPlayers(index);
                index --;
            }
        }
    }

    //gets player's best hand before cards are flipped
    private void checkingHand() {
        for(int i = 0; i < players.length;i++){
            Hand hand1 = new Hand(players[i].getHand(), cardsOnTheTable);
            ArrayList<Integer> intstuff = new ArrayList<>();
            intstuff.addAll(hand1.getBestHand());
            Log.d(TAG, "checkingHand: " + intstuff.toString());
        }

    }

    //alert dialog boxes for setting plaayer names
    public Player createPlayingPlayers(int i) { //i is player #
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(context);
        final int playerNumber = i; //player 1 is at index
        // set title
        alertDialogBuilder.setTitle("Set Player #" + playerNumber + "'s name");
        alertDialogBuilder.setView(input);

        // set dialog message
        Player p = new Player("Player #"+i,10000, getHand());
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String name =input.getText().toString();
                Log.d(TAG, "createPlayingPlayers: "+input.getText().toString());
                editPlayerName(playerNumber, name);
            }
        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        return p;
    }

    //sets the card objects
    private void createCards() {
        myCard1 = players[0].getHand().get(0);
        myCard2 = players[0].getHand().get(1);
        tableCard1 = cardsOnTheTable.get(0);
        tableCard2 = cardsOnTheTable.get(1);
        tableCard3 = cardsOnTheTable.get(2);
        tableCard4 = cardsOnTheTable.get(3);
        tableCard5 = cardsOnTheTable.get(4);
    }

    //sets functions for buttons
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.raise:
                raiseBet();
                break;
            case R.id.call_check:
                if( callCheck.getText().equals("Start Next Round")){
                    newGame(false);
                } else {
                    if(needsToCall()) {
                        call();
                    }
                    else {
                        players[currentplayer].setHasCalled(true);
                        nextGuy();
                    }
                }
                break;
            case R.id.fold:
                fold();
                break;
        }
    }

    //folds a player
    private void fold() {
        players[currentplayer].setHasFolded(true);
        nextGuy();
    }

    //calls if someone has placed a bet
    private void call() {
        int cb=currentBet;
        players[currentplayer].setHasCalled(true);
        if (players[currentplayer].getMonnies()>currentBet) {
            potMoney+=cb-players[currentplayer].getBet();
            players[currentplayer].setBet(cb);
            bet.setText("$"+potMoney);
            player6View.setText(players[currentplayer].getName() + ": $" + players[currentplayer].getMonnies());
            nextGuy();
        }
        else {
            Toast.makeText(context, "you have gone all in", Toast.LENGTH_SHORT).show();
            players[currentplayer].setBet(players[currentplayer].getMonnies());
            potMoney+=players[currentplayer].getMonnies();
            bet.setText("$"+potMoney);
            player6View.setText(players[currentplayer].getName() + ": $" + players[currentplayer].getMonnies());
            nextGuy();

        }
    }

    //raises the bet
    public void raiseBet() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alertDialogBuilder.setTitle("The current bet is "+currentBet+"\n input the amount you want to raise to");
        alertDialogBuilder.setView(input);
        alertDialogBuilder.setCancelable(true).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            int amountRaised;
            public void onClick(DialogInterface dialog, int id) {
                try {
                    if ((Integer.parseInt(input.getText().toString()) <= players[currentplayer].getMonnies())) {
                        amountRaised = Integer.parseInt(input.getText().toString());
                        //players[currentplayer].setBet(amountRaised);
                        if(amountRaised<currentBet){
                            Toast.makeText(getActivity(), "Please enter a number larger than the bet", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Log.d(TAG, "onClick: amountRaised: "+amountRaised);
                            potMoney += amountRaised;
                            players[currentplayer].setBet(amountRaised);
                            currentBet=amountRaised;
                            Log.d(TAG, "onClick: currentBet: "+currentBet);
                            hasRaised = true;
                            bet.setText("$" + potMoney);
                            player6View.setText(players[currentplayer].getName() + ": $" + players[currentplayer].getMonnies());
                            for(int i=0; i<players.length;i++) {
                                    players[i].setHasCalled(false);
                            }
                            players[currentplayer].setHasCalled(true);
                            nextGuy();
                        }
                    }
                    else{
                        Toast.makeText(getActivity(), "You don't have that much money", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Please enter a number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    //switches to next person
    public void nextGuy() {
            if(checkIfEveryoneElseFolded()) {
                currentplayer=nextNonFoldedGuy();
                everyoneHasFolded = true;
                endGame();
            }
            else {
                currentplayer=nextNonFoldedGuy();
                if(players[currentplayer].hasCalled())
                {
                    endRound();
                }
                else {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    //sets message
                    final TextView question = new TextView(context);
                    //todo change size and style to look good
                    question.setText("Are you " + players[currentplayer].getName() + "?");
                    // set title
                    alertDialogBuilder.setTitle(players[currentplayer].getName() + "'s turn. Please pass to " + players[currentplayer].getName());
                    alertDialogBuilder.setView(question);

                    alertDialogBuilder.setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            needsToCall();
                            changePlayerView();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    myCard1View.setVisibility(View.INVISIBLE);
                    myCard2View.setVisibility(View.INVISIBLE);
                    alertDialog.show();
                }
            }
    }

    private void endRound() {
        for(int i=0; i<players.length; i++)
        {
            players[i].resetBet();
            players[i].setHasCalled(false);
            //Log.d(TAG, "endRound: "+players[i].getName()+":"+players[i].getBet());
        }
        currentBet=0;
        Log.d(TAG, "endRound: currentBet"+currentBet);
        if(round==0) {
            showCard(cardsOnTheTable.get(0));
            showCard(cardsOnTheTable.get(1));
            showCard(cardsOnTheTable.get(2));
            round++;
            currentplayer=5;
            nextGuy();
        }
        else if (round==1){
            showCard(cardsOnTheTable.get(3));
            round++;
            currentplayer=5;
            nextGuy();
        }
        else if(round==2){
            showCard(cardsOnTheTable.get(4));
            round++;
            currentplayer=5;
            nextGuy();
        }
        else if(round==3)
        {
            endGame();
        }


    }

    private void endGame() {
        flipOverCards(); //creates winners dialog and gives winner money
        for(int i = 0; i < players.length; i++) {
            editor.putInt(players[i].getSharedPref() + " Monnies", players[i].getMonnies());
            editor.commit();
        }
    }

    public void flipOverCards(){
        int lastPlayer = currentplayer;
        currentplayer = 0;

        bet.setVisibility(View.INVISIBLE);
        raise.setVisibility(View.INVISIBLE);
        fold.setVisibility(View.INVISIBLE);
        callCheck.setText("Start Next Round");

        changePlayerView();
        ArrayList<Integer> winners = this.getWinner();
        if(players[0].hasFolded()){
            myCard1View.setVisibility(View.INVISIBLE);
            myCard1View.setVisibility(View.INVISIBLE);
            showCard(myCard1);
            showCard(myCard2);
        } else{
            showCard(myCard1);
            showCard(myCard2);
        }
        if(!players[1].hasFolded()){
            player1Card1View.setVisibility(View.VISIBLE);
            player1Card2View.setVisibility(View.VISIBLE);
        }
        if(!players[2].hasFolded()) {
            player2Card1View.setVisibility(View.VISIBLE);
            player2Card2View.setVisibility(View.VISIBLE);
        }
        if(!players[3].hasFolded()) {
            player3Card1View.setVisibility(View.VISIBLE);
            player3Card2View.setVisibility(View.VISIBLE);
        }
        if(!players[4].hasFolded()) {
            player4Card1View.setVisibility(View.VISIBLE);
            player4Card2View.setVisibility(View.VISIBLE);
        }
        if(!players[5].hasFolded()) {
            player5Card1View.setVisibility(View.VISIBLE);
            player5Card2View.setVisibility(View.VISIBLE);
        }
        Log.d(TAG, "endGame: "+winnerTitle(winners, lastPlayer));
        Toast.makeText(getActivity(), winnerTitle(winners, lastPlayer), Toast.LENGTH_LONG).show();
        for(int i = 0; i < winners.size(); i++){
            int index = winners.get(i);
            Log.d(TAG, "flipOverCards: "+potMoney/winners.size());
            Log.d(TAG, "flipOverCards: "+players[index].getMonnies());
            Log.d(TAG, "flipOverCards: "+players[index].getName());
            players[index].setMonnies(players[index].getMonnies()+potMoney/winners.size());
            Log.d(TAG, "flipOverCards: "+players[index].getMonnies());
        }
        changePlayerView();
        potMoney = 0;
    }

    //makes string of winners
    public String winnerTitle(ArrayList<Integer> winners, int lastPlayer){
        String samIsTired="";
        if(everyoneHasFolded){
            samIsTired = players[lastPlayer].getName();
        } else {
            for(int i = 0; i < winners.size(); i++){
                if(winners.size() - 1 == i && i !=0){
                    samIsTired=samIsTired +" and ";
                }
                else if(i != 0){
                    samIsTired=samIsTired+", ";
                }
                samIsTired = samIsTired + players[winners.get(i)].getName();
            }
        }
        return samIsTired + " won!";
    }

    //gets winners
    public ArrayList<Integer> getWinner() {
        ArrayList<Integer> best = new ArrayList<>();
        ArrayList<Integer> winners = new ArrayList<>();
        best.add(-1);
        int bestPlayer = -1;
        for (int i = 0; i < players.length; i++) {
            if(players[i].hasFolded()){

            } else {
                Hand hand1 = new Hand(players[i].getHand(), cardsOnTheTable);
                ArrayList<Integer> intstuff1 = new ArrayList<>();
                intstuff1.addAll(hand1.getBestHand());
                if (hand1.getHigherHand(best, intstuff1).equals(intstuff1)) {
                    bestPlayer = i;
                }
                best = hand1.getHigherHand(best, intstuff1);
            }
        }
        for(int i = 0; i < players.length; i++){
            if(players[i].hasFolded()){

            } else {
                Hand hand1 = new Hand(players[i].getHand(), cardsOnTheTable);
                ArrayList<Integer> intstuff1 = new ArrayList<>();
                intstuff1.addAll(hand1.getBestHand());
                if (hand1.getHigherHand(best, intstuff1).equals(intstuff1)) {
                    winners.add(i);
                }
            }
        }
        return winners;
    } //todo fix so that it checks high card

    private boolean needsToCall() {
        if(currentBet>players[currentplayer].getBet()){
            callCheck.setText("Call");
            return true;
        }
        else
        {
            Log.d(TAG, "needsToCall: "+players[currentplayer].getName()+":"+players[currentplayer].getBet()+":"+currentBet);
            callCheck.setText("Check");
            return false;
        }
    }

    public boolean checkIfEveryoneElseFolded() {
        int pplNotFolded=0;
        for (Player p: players) {
            if (!p.hasFolded()) {
                pplNotFolded++;
            }
        }
        return (pplNotFolded<2);
    }

    public int nextNonFoldedGuy() {
        int n=whosNext(currentplayer);
        while(players[n].hasFolded()|| players[n].getHand().get(0).getNumber()==0) {
            n=whosNext(n);
        }
        return n;
    }

    public int whosNext(int currentGuy) {
        int n=currentGuy;
        if (n == 5) {
            n = 0;
        } else {
            n++;
        }
        return n;

    }
}