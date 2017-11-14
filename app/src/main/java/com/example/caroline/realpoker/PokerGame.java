package com.example.caroline.realpoker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
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

public class PokerGame extends Fragment implements View.OnClickListener {

    private ArrayList<Card> cardsOnTheTable;
    private ArrayList<Card> deck;
    private int numOfPlayers, currentplayer;
    private int potMoney;
    private ArrayList<Player> players;
    private ArrayList<Player> totalPlayers;
    private TextView player1View, player2View, player3View, player4View, player5View, player6View, bet;
    private boolean hasRaised = false;
    private int round, turn;
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

    public PokerGame() {
    }

    //todo Overall:
        //todo include blinds (make one method call for first and second) NOtfiy player they are blind before just screwing them
        //todo change call/check button text/undersatnd that code
        //todo debug stuff
        //todo debug/implement folding
        //todo set up end screen with options fro new game, change players, im doen or change certain players
        //todo have settings use shared preferences so you can delete players
        //todo FIX SHARED PREFRENCES so you can keep players all the time (ie keep them when app is closed and restarts)


    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Inflate the layout we made (one_fragment.xml)
        rootView = inflater.inflate(R.layout.activity_poker_game, container, false);
        deck = new ArrayList<>();
        numOfPlayers = 6;
        currentplayer = 0;

        context = getContext();
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putBoolean("hasPlayers?", false);
        editor.commit();

        ArrayList<Card> emptyHand = new ArrayList<>();
        emptyHand.add(new Card(0, "c"));
        emptyHand.add(new Card(0, "c"));
        emptyPlayer = new Player("folded",0, emptyHand);
        players = new ArrayList<>();
        Log.d(TAG, "onCreateView: ");

        createDeck();
        createCardsOnTheTable();
        areNewPlayers(); //either creates new players or gets the old ones and assigns them to their spots on the array list

        return rootView;
    }

    private void areNewPlayers() {
        Log.d(TAG, "areNewPlayers: ");
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        // set title
        alertDialogBuilder.setTitle("Same players as last time?");

        // set dialog message

        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                Log.d(TAG, "onClick: dismissed dialogue from yes");
                useOldPlayers();
            }
        });

        alertDialogBuilder.setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                Log.d(TAG, "onClick: dismissed dialogue from no");
                howManyPlayers();
            }
        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    //todo call blinds and start the game
    private void editPlayerName(int i, String name) {
        players.get(i).setName(name);
        if(i == numOfPlayers-1) {
            startGame();

        }
    }

    private void startGame() {
        addPlayersToSharedPref();
        createCards();
        wireWidgets();
        checkingHand();
    }

    //todo make it so saving actually works
    //saves players when they are created
    private void addPlayersToSharedPref() {
        //adds all players to shared preferences to be used later
        //todo update monnies at the end of the game and when people fold
        Log.d(TAG, "addPlayersToSharedPref: ive been called");
        editor.putBoolean("hasPlayers?", true);

        editor.putInt("Number of Players", numOfPlayers);

        editor.putString("Player 1",players.get(0).getName());
        editor.putInt("Player 1 Monnies", players.get(0).getMonnies());

        editor.putString("Player 2",players.get(1).getName());
        editor.putInt("Player 2 Monnies", players.get(1).getMonnies());

        editor.putString("Player 3",players.get(2).getName());
        editor.putInt("Player 3 Monnies", players.get(2).getMonnies());

        editor.putString("Player 4",players.get(3).getName());
        editor.putInt("Player 4 Monnies", players.get(3).getMonnies());

        editor.putString("Player 5",players.get(4).getName());
        editor.putInt("Player 5 Monnies", players.get(4).getMonnies());

        editor.putString("Player 6",players.get(5).getName());
        editor.putInt("Player 6 Monnies", players.get(5).getMonnies());

        editor.commit();
    }

    //lets user use last rounds players if they exist else creates new ones
    private void useOldPlayers() {
        if(sharedPref.getBoolean("hasPlayers?", false)) { //if there are players
            numOfPlayers = sharedPref.getInt("Number of Players", 6);
            for (int i = 1; i < 7; i++) {
                String name = sharedPref.getString("Player " + i, "Player " + i);
                int monney = sharedPref.getInt("Player " + i + " Monnies", 10000);
                Player p = new Player(name, monney, getHand());
                players.add(p);
            }
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
            for (int i = numOfPlayers; i >0; i--){
                players.add(createPlayingPlayers(i));
            }
            for(int i= 6-numOfPlayers; i>0; i--){
                players.add(emptyPlayer);
            }
        }
    }

    //gets player's best hand before cards are flipped
    private void checkingHand() {
        Hand hand1 = new Hand(players.get(0).getHand(), cardsOnTheTable);
        ArrayList<Integer> intstuff = new ArrayList<>();
        intstuff.addAll(hand1.getBestHand());
        Log.d(TAG, "checkingHand: " + intstuff.toString());
    }

    //sets the card objects
    private void createCards() {
        myCard1 = players.get(0).getHand().get(0);
        myCard2 = players.get(0).getHand().get(1);
        tableCard1 = cardsOnTheTable.get(0);
        tableCard2 = cardsOnTheTable.get(1);
        tableCard3 = cardsOnTheTable.get(2);
        tableCard4 = cardsOnTheTable.get(3);
        tableCard5 = cardsOnTheTable.get(4);
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
        String[] suits = {"c", "d", "h", "s"};
        for (String s : suits) {
            for (int i = 2; i < 15; i++) {
                deck.add(new Card(i, s));
            }
        }
    }
    
    //sets up cards and player names
    private void wireWidgets() {

        //Sets the players cards
        myCard1View = (ImageView) rootView.findViewById(R.id.my_card_1);
        myCard1View.setContentDescription(myCard1.getCardNumber() + " of " + myCard1.getSuitName());
        showCard(myCard1);
        myCard2View = (ImageView) rootView.findViewById(R.id.my_card_2);
        myCard2View.setContentDescription(myCard2.getCardNumber() + " of " + myCard2.getSuitName());
        showCard(myCard2);

        //Sets the table cards but doesnt show them
        tableCard1View = (ImageView) rootView.findViewById(R.id.table_card_1);
        tableCard1View.setContentDescription(tableCard1.getCardNumber() + " of " + tableCard1.getSuitName());

        tableCard2View = (ImageView) rootView.findViewById(R.id.table_card_2);
        tableCard2View.setContentDescription(tableCard2.getCardNumber() + " of " + tableCard2.getSuitName());

        tableCard3View = (ImageView) rootView.findViewById(R.id.table_card_3);
        tableCard3View.setContentDescription(tableCard3.getCardNumber() + " of " + tableCard3.getSuitName());

        tableCard4View = (ImageView) rootView.findViewById(R.id.table_card_4);
        tableCard4View.setContentDescription(tableCard4.getCardNumber() + " of " + tableCard4.getSuitName());

        tableCard5View = (ImageView) rootView.findViewById(R.id.table_card_5);
        tableCard5View.setContentDescription(tableCard5.getCardNumber() + " of " + tableCard5.getSuitName());

        //Sets the player views (name and monnies)
        player1View = (TextView) rootView.findViewById(R.id.player_1);
        String p1 = "";
        if (!(players.get(1).getName().equals("folded"))) {
            p1 = players.get(1).getName() + ": $" + players.get(1).getMonnies();
        }
        player1View.setText(p1);


        player2View = (TextView) rootView.findViewById(R.id.player_2);
        String p2 = "";
        if (!(players.get(2).getName().equals("folded"))) {
            p2 = players.get(2).getName() + ": $" + players.get(2).getMonnies();
        }
        player2View.setText(p2);


        player3View = (TextView) rootView.findViewById(R.id.player_3);
        String p3 = "";
        if (!(players.get(3).getName().equals("folded"))) {
            p3 = players.get(3).getName() + ": $" + players.get(3).getMonnies();
        }
        player3View.setText(p3);


        player4View = (TextView) rootView.findViewById(R.id.player_4);
        String p4 = "";
        if (!(players.get(4).getName().equals("folded"))) {
            p4 = players.get(4).getName() + ": $" + players.get(4).getMonnies();
        }
        player4View.setText(p4);


        player5View = (TextView) rootView.findViewById(R.id.player_5);
        String p5 = "";
        if (!(players.get(5).getName().equals("folded"))) {
            p5 = players.get(5).getName() + ": $" + players.get(3).getMonnies();
        }
        player5View.setText(p5);


        player6View = (TextView) rootView.findViewById(R.id.user);
        String p6 = players.get(0).getName() + ": $" + players.get(0).getMonnies();
        player6View.setText(p6);

        bet = (TextView) rootView.findViewById(R.id.bet);
        bet.setText("$" + potMoney);

        raise = (Button) rootView.findViewById(R.id.raise);//todo
        raise.setOnClickListener(this);

        fold = (Button) rootView.findViewById(R.id.fold);
        fold.setOnClickListener(this);

        callCheck = (Button) rootView.findViewById(R.id.call_check);
        callCheck.setText("Call");
        callCheck.setOnClickListener(this);

        //Creates other player's cards and sets visability to invisible
        Card player1Card1 = players.get(1).getHand().get(0);
        player1Card1View = (ImageView) rootView.findViewById(R.id.player1_card_1);
        player1Card1View.setContentDescription(player1Card1.getCardNumber()+ " of " + player1Card1.getSuitName());
        showCard(player1Card1);
        player1Card1View.setVisibility(View.INVISIBLE);

        Card player1Card2 = players.get(1).getHand().get(1);
        player1Card2View = (ImageView) rootView.findViewById(R.id.player1_card_2);
        player1Card2View.setContentDescription(player1Card2.getCardNumber()+ " of " + player1Card2.getSuitName());
        showCard(player1Card2);
        player1Card2View.setVisibility(View.INVISIBLE);

        Card player2Card1 = players.get(2).getHand().get(0);
        player2Card1View = (ImageView) rootView.findViewById(R.id.player2_card_1);
        player2Card1View.setContentDescription(player2Card1.getCardNumber()+ " of " + player2Card1.getSuitName());
        showCard(player2Card1);
        player2Card1View.setVisibility(View.INVISIBLE);

        Card player2Card2 = players.get(2).getHand().get(1);
        player2Card2View = (ImageView) rootView.findViewById(R.id.player2_card_2);
        player2Card2View.setContentDescription(player2Card2.getCardNumber()+ " of " + player2Card2.getSuitName());
        showCard(player2Card2);
        player2Card2View.setVisibility(View.INVISIBLE);

        Card player3Card1 = players.get(3).getHand().get(0);
        player3Card1View = (ImageView) rootView.findViewById(R.id.player3_card_1);
        player3Card1View.setContentDescription(player3Card1.getCardNumber()+ " of " + player3Card1.getSuitName());
        showCard(player3Card1);
        player3Card1View.setVisibility(View.INVISIBLE);

        Card player3Card2 = players.get(3).getHand().get(1);
        player3Card2View = (ImageView) rootView.findViewById(R.id.player3_card_2);
        player3Card2View.setContentDescription(player3Card2.getCardNumber()+ " of " + player3Card2.getSuitName());
        showCard(player3Card2);
        player3Card2View.setVisibility(View.INVISIBLE);

        Card player4Card1 = players.get(4).getHand().get(0);
        player4Card1View = (ImageView) rootView.findViewById(R.id.player4_card_1);
        player4Card1View.setContentDescription(player4Card1.getCardNumber()+ " of " + player4Card1.getSuitName());
        showCard(player4Card1);
        player4Card1View.setVisibility(View.INVISIBLE);

        Card player4Card2 = players.get(4).getHand().get(1);
        player4Card2View = (ImageView) rootView.findViewById(R.id.player4_card_2);
        player4Card2View.setContentDescription(player4Card2.getCardNumber()+ " of " + player4Card2.getSuitName());
        showCard(player4Card2);
        player4Card2View.setVisibility(View.INVISIBLE);

        Card player5Card1 = players.get(5).getHand().get(0);
        player5Card1View = (ImageView) rootView.findViewById(R.id.player5_card_1);
        player5Card1View.setContentDescription(player5Card1.getCardNumber()+ " of " + player5Card1.getSuitName());
        showCard(player5Card1);
        player5Card1View.setVisibility(View.INVISIBLE);

        Card player5Card2 = players.get(5).getHand().get(1);
        player5Card2View = (ImageView) rootView.findViewById(R.id.player5_card_2);
        player5Card2View.setContentDescription(player5Card2.getCardNumber()+ " of " + player5Card2.getSuitName());
        showCard(player5Card2);
        player5Card2View.setVisibility(View.INVISIBLE);
    }

    //todo change button to reflect checking or calling also read through and understand/make changes as necessary
    //todo ask nicolo what the hell is going on
    //lets player 0 check if nothing has been bet or call if stuff has
    public void checkCall(){
        if(players.size()==2&&!hasRaised&&players.get(0).getRaiseBy() == 0 && players.get(1).getRaiseBy() ==0){
            hasRaised = false;
            round = 0;
            turn++;
            endRound();
        }
        else if(players.get(1).getRaiseBy() == 0 && hasRaised){
            hasRaised = false;
            round = 0;
            turn++;
            endRound();
        }
        else if(!hasRaised && round +2>players.size()){
            hasRaised = false;
            round = 0;
            turn ++;
            endRound();
        }
        else{
            if(players.get(0).getMonnies()<players.get(0).getRaiseBy()){
                potMoney += players.get(0).getRaiseBy();
                players.get(0).setMonnies(0);
                players.get(0).setRaiseBy(0);
                round++;
                nextGuy();
            }
            else {
                potMoney += players.get(0).getRaiseBy();
                players.get(0).setMonnies(players.get(0).getMonnies() - players.get(0).getRaiseBy());
                players.get(0).setRaiseBy(0);
                round++;
                nextGuy();
            }
        }
    }

    //todo read through/ comment, understand and make any necessary changes
    //lets player 0 raise
    public void raiseBet() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        // set title
        alertDialogBuilder.setTitle("Input Raise amount");
        alertDialogBuilder.setView(input);

        alertDialogBuilder.setCancelable(true).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            int amountRaised;

            public void onClick(DialogInterface dialog, int id) {

                try {
                    if (Integer.parseInt(input.getText().toString()) > 0 && (Integer.parseInt(input.getText().toString()) < players.get(0).getMonnies())) {
                        amountRaised = Integer.parseInt(input.getText().toString());
                        if(amountRaised<=players.get(0).getRaiseBy()){
                            Toast.makeText(getActivity(), "Please enter a number larger than the call", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            potMoney += amountRaised;
                            players.get(1).setRaiseBy(players.get(1).getRaiseBy() + amountRaised);
                            players.get(2).setRaiseBy(players.get(2).getRaiseBy() + amountRaised);
                            players.get(3).setRaiseBy(players.get(3).getRaiseBy() + amountRaised);
                            players.get(4).setRaiseBy(players.get(4).getRaiseBy() + amountRaised);
                            players.get(5).setRaiseBy(players.get(5).getRaiseBy() + amountRaised);
                            players.get(0).setMonnies(players.get(0).getMonnies() - amountRaised);
                            hasRaised = true;
                            bet.setText("$" + potMoney);
                            player6View.setText(players.get(0).getName() + ": $" + players.get(0).getMonnies());
                            nextGuy();
                        }
                    }

                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Please enter a number", Toast.LENGTH_SHORT).show();

                }
            }
        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    //ends the round and flips middle cars as nesscary, calls
    public void endRound() {
        if(turn == 1){
            showCard(tableCard1);
            showCard(tableCard2);
            showCard(tableCard3);
            nextGuy();
        }
        else if(turn == 2){
            showCard(tableCard4);
            nextGuy();
        }
        else if(turn == 3){
            showCard(tableCard5);
            nextGuy();
        }
        else{
            endGame();
        }
    }

    //dialog for passing player
    //todo test how foldign is going
    public void nextGuy() {
        int nextPlayer = currentplayer + 1;

        while(players.get(nextPlayer).getName().equals("folded")){
            if(nextPlayer == 5){
                nextPlayer = 0;
            } else if(nextPlayer == currentplayer){ //check if everyone else folded
                Log.d(TAG, "nextGuy: everyoen else has folded");
                Toast.makeText(context, "Everyone else has folded, you win!",Toast.LENGTH_LONG);
                ArrayList<Player> p = new ArrayList<>();
                p.add(players.get(currentplayer));
                winnerTitle(p);
                endGame();
            }
            else {
                nextPlayer++;
            }
        }

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        //sets message
        final TextView question = new TextView(context);
        //todo change size and style to look good
        question.setText("Are you "+ players.get(nextPlayer).getName()+"?");
        // set title
        alertDialogBuilder.setTitle(players.get(nextPlayer).getName() + "'s turn. Please pass to "+players.get(nextPlayer).getName());
        alertDialogBuilder.setView(question);

        alertDialogBuilder.setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                changePlayer();
            }
        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        myCard1View.setVisibility(View.INVISIBLE);
        myCard2View.setVisibility(View.INVISIBLE);
        // show it
        alertDialog.show();

    }

    //switches order or array list to ensure player 0 is the next player
    //todo needs to reflect number of players still in (ie dont let empty player become player 0)
    //todo maybe create two arraylists? or just have number that number still in and check if playername  == folded
    private void changePlayer() {
        Player p = players.remove(0);
        players.add(p);
        wireWidgets();
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
        } else if (myCard.equals(players.get(1).getHand().get(0))) {
            player1Card1View.setImageResource(res);
        } else if (myCard.equals(players.get(1).getHand().get(1))) {
            player1Card2View.setImageResource(res);
        } else if (myCard.equals(players.get(2).getHand().get(0))) {
            player2Card1View.setImageResource(res);
        } else if (myCard.equals(players.get(2).getHand().get(1))) {
            player2Card2View.setImageResource(res);
        } else if (myCard.equals(players.get(3).getHand().get(0))) {
            player3Card1View.setImageResource(res);
        } else if (myCard.equals(players.get(3).getHand().get(1))) {
            player3Card2View.setImageResource(res);
        } else if (myCard.equals(players.get(4).getHand().get(0))) {
            player4Card1View.setImageResource(res);
        } else if (myCard.equals(players.get(4).getHand().get(1))) {
            player4Card2View.setImageResource(res);
        } else if (myCard.equals(players.get(5).getHand().get(0))) {
            player5Card1View.setImageResource(res);
        } else if (myCard.equals(players.get(5).getHand().get(1))) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.raise:
                raiseBet();
                break;
            case R.id.call_check:
                checkCall();
                break;
            case R.id.fold:
                playerFolded();
                break;
        }
    }

    //if you click fold sets you equal t
    //todo check if updated monnies works and make sure logic is good
    private void playerFolded() {
        //updates shared prefrences with player's money
        String player = "Player "+ players.get(0).getName();
        //check names for all six players to find out which number this guy is
        String player1 = sharedPref.getString("Player 1","");
        String player2 = sharedPref.getString("Player 2","");
        String player3 = sharedPref.getString("Player 3","");
        String player4 = sharedPref.getString("Player 4","");
        String player5 = sharedPref.getString("Player 5","");
        String player6 = sharedPref.getString("Player 6","");

        int playerNumber = 0;
        if(player.equals(player1)){
            playerNumber = 1;
        } else if(player.equals(player2)){
            playerNumber = 2;
        }else if(player.equals(player3)){
            playerNumber = 3;
        }else if(player.equals(player4)){
            playerNumber = 4;
        }else if(player.equals(player5)){
            playerNumber = 5;
        }else if(player.equals(player6)){
            playerNumber = 6;
        }
        editor.putInt("Player "+playerNumber+" Monnies", players.get(0).getMonnies());

        //folds player by setting them to an empty player
        players.remove(0);
        players.add(0, emptyPlayer);
        nextGuy();
    }

    //gets the next player and loops around if at player 6
    private int getNextGuy(int i) {
        int nextPlayer = i;
        if(i<players.size()-1){
            nextPlayer++;
        }else{
            nextPlayer = 0;
        }
        return nextPlayer;
    }


    //todo set up winner's dialog
    public ArrayList<Player> getWinner() {
        ArrayList<Integer> best = new ArrayList<>();
        ArrayList<Player> winners = new ArrayList<>();
        best.add(-1);
        int bestPlayer = -1;
        for (int i = 0; i < players.size(); i++) {
            Hand hand1 = new Hand(players.get(i).getHand(), cardsOnTheTable);
            ArrayList<Integer> intstuff1 = new ArrayList<>();
            intstuff1.addAll(hand1.getBestHand());
            if (hand1.getHigherHand(best, intstuff1).equals(intstuff1)) {
                bestPlayer = i;
            }
            best = hand1.getHigherHand(best, intstuff1);
        }
        for(int i = 0; i < players.size(); i++){
            Hand hand1 = new Hand(players.get(i).getHand(), cardsOnTheTable);
            ArrayList<Integer> intstuff1 = new ArrayList<>();
            intstuff1.addAll(hand1.getBestHand());
            if (hand1.getHigherHand(best, intstuff1).equals(intstuff1)) {
                winners.add(players.get(i));
            }
        }
        return winners;
    }

    //todo set up winner's dialog
    public String winnerTitle(ArrayList<Player> winners){
        String samIsTired="";
        for(int i = 0; i < winners.size(); i++){
            if(winners.size() - 1 == i && i !=0){
                samIsTired=samIsTired +" and ";
            }
            else if(i != 0){
                samIsTired=samIsTired+", ";
            }
            samIsTired = samIsTired + winners.get(i).getName();
        }
        return samIsTired + " won!";
    }
    //todo set up winner's dialog
    public void endGame(){
        ArrayList<Player> nute=this.getWinner();
        player1Card1View.setVisibility(View.VISIBLE);
        player1Card2View.setVisibility(View.VISIBLE);
        player2Card1View.setVisibility(View.VISIBLE);
        player2Card2View.setVisibility(View.VISIBLE);
        player3Card1View.setVisibility(View.VISIBLE);
        player3Card2View.setVisibility(View.VISIBLE);
        player4Card1View.setVisibility(View.VISIBLE);
        player4Card2View.setVisibility(View.VISIBLE);
        player5Card1View.setVisibility(View.VISIBLE);
        player5Card2View.setVisibility(View.VISIBLE);
        Log.d(TAG, "endGame: "+winnerTitle(nute));//TODO: Make a text box saying who won;
        Toast.makeText(getActivity(), winnerTitle(nute), Toast.LENGTH_LONG).show();
            for(int i=0;i<nute.size();i++){
                nute.get(i).setMonnies(nute.get(i).getMonnies()+potMoney/nute.size());
            }
            potMoney = 0;

    }

    public Player createPlayingPlayers(int i) { //i is index
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(context);
        final int playerNumber = i; //player 1 is at index 0
        // set title
        alertDialogBuilder.setTitle("Set Player #" + playerNumber + "'s name");
        alertDialogBuilder.setView(input);

        // set dialog message
        Player p = new Player("Player #"+(i+1),10000, getHand());
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String name =input.getText().toString();
                Log.d(TAG, "createPlayerI: "+input.getText().toString());
                editPlayerName(playerNumber-1, name);
            }
        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        return p;
    }

}
