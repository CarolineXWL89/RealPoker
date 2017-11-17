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
public class PokerGame2 extends Fragment implements View.OnClickListener {

    private ArrayList<Card> cardsOnTheTable;
    private ArrayList<Card> deck;
    private int numOfPlayers, currentplayer;
    private int potMoney;
    private ArrayList<Player> players, playersPlaying; //first one keeps track for purposes of showing cards when player's fold, second one keeps track for shiftign from playre one to player two etc...
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

    public PokerGame2() {
    }

    //TODO Overall:
    //TODO #1 debug raise, fold, call/check and nextguy
    //TODO #3 write blind method using logic for switching players
    //TODO #4 FIX SHARED PREFRENCES (debug logic behind them)
    //todo #5 set up end screen with options fro new game, change players, im doen or change certain players
    //todo #6 have settings use shared preferences so you can delete players


    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Inflate the layout we made (one_fragment.xml)
        rootView = inflater.inflate(R.layout.activity_poker_game, container, false);
        deck = new ArrayList<>();
        currentplayer = 0;

        context = getContext();
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        editor = sharedPref.edit();

        ArrayList<Card> emptyHand = new ArrayList<>();
        emptyHand.add(new Card(0, "c"));
        emptyHand.add(new Card(0, "c"));

        emptyPlayer = new Player(null, 0, emptyHand);
        players = new ArrayList<>();

        createDeck();
        createCardsOnTheTable();
        areNewPlayers(); //either creates new players or gets the old ones then starts the game

        return rootView;
    }

    private void setUpCards(){
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

        createOtherPlayersCards();
        changePlayerView();
    }

    private void createOtherPlayersCards() {
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

    private void changePlayerView(){
        int index = currentplayer;
        //cahnges cards to curretn players cards
        myCard1 = players.get(index).getHand().get(0);
        myCard2 = players.get(index).getHand().get(1);

        myCard1View = (ImageView) rootView.findViewById(R.id.my_card_1);
        myCard1View.setContentDescription(myCard1.getCardNumber() + " of " + myCard1.getSuitName());
        showCard(myCard1);

        myCard2View = (ImageView) rootView.findViewById(R.id.my_card_2);
        myCard2View.setContentDescription(myCard2.getCardNumber() + " of " + myCard2.getSuitName());
        showCard(myCard2);

        //Sets the player views (name and monnies)

        int[] indices = new int[5];
        switch(index){
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
                indices[3] = -1;
                indices[4] = -2;
                break;
            case 3:
                indices[0] = 1;
                indices[1] = 2;
                indices[2] = -1;
                indices[3] = -2;
                indices[4] = -3;
                break;
            case 4:
                indices[0] = 1;
                indices[1] = -1;
                indices[2] = -2;
                indices[3] = -3;
                indices[4] = -4;
                break;
            case 5:
                indices[0] = -1;
                indices[1] = -2;
                indices[2] = -3;
                indices[3] = -4;
                indices[4] = -5;
                break;
            case 0:
                indices[0] = 0;
                indices[1] = 1;
                indices[2] = 2;
                indices[3] = 3;
                indices[4] = 4;
                break;
        }
        //rotates player textview
        player1View = (TextView) rootView.findViewById(R.id.player_1);
        String p1 ="";
        if (players.get(index +indices[0]).getName() != null) {
            p1 = players.get(index +indices[0]).getName() + ": $" + players.get(index +indices[0]).getMonnies();
        }
        player1View.setText(p1);

        player2View = (TextView) rootView.findViewById(R.id.player_2);
        String p2 ="";
        if (players.get(index +indices[1]).getName() != null) {
            p2 = players.get(index +indices[1]).getName() + ": $" + players.get(index +indices[1]).getMonnies();
        }
        player2View.setText(p2);


        player3View = (TextView) rootView.findViewById(R.id.player_3);
        String p3 = "";
        if (players.get(index +indices[2]).getName() != null) {
            p3 = players.get(index +indices[2]).getName() + ": $" + players.get(index +indices[2]).getMonnies();
        }
        player3View.setText(p3);


        player4View = (TextView) rootView.findViewById(R.id.player_4);
        String p4 = "";
        if (players.get(index +indices[3]).getName() != null) {
            p4 = players.get(index +indices[3]).getName() + ": $" + players.get(index +indices[3]).getMonnies();
        }
        player4View.setText(p4);


        player5View = (TextView) rootView.findViewById(R.id.player_5);
        String p5 = "";
        if (players.get(index +indices[4]).getName() != null) {
            p5 = players.get(index + indices[4]).getName() + ": $" + players.get(index + indices[4]).getMonnies();
        }
        player5View.setText(p5);


        player6View = (TextView) rootView.findViewById(R.id.user);
        String p6 = players.get(index).getName() + ": $" + players.get(index).getMonnies();
        player6View.setText(p6);
    }

    private void wireWidgets() {
        bet = (TextView) rootView.findViewById(R.id.bet);
        bet.setText("$" + potMoney);

        raise = (Button) rootView.findViewById(R.id.raise);//todo
        raise.setOnClickListener(this);

        fold = (Button) rootView.findViewById(R.id.fold);
        fold.setOnClickListener(this);

        callCheck = (Button) rootView.findViewById(R.id.call_check);
        callCheck.setOnClickListener(this);
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

    private void editPlayerName(int i, String name) {
        players.get(i).setName(name);
        if(i == numOfPlayers-1) {
            startGame();

        }
    }

    //todo call blinds to start the game
    private void startGame() {
        addPlayersToSharedPref();
        createCards();
        wireWidgets();
        setUpCards();
        checkingHand();
        //firstBlind();
    }

    //todo set up
    private void firstBlind() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        final TextView question = new TextView(context);

        // set title
        alertDialogBuilder.setTitle("You are first blind");

        alertDialogBuilder.setView(question);
        question.setText("You bet $100");

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //todo call raise for first blind and change players and call second blind
            }
        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }
    //todo set up
    private void secondBlind(){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        final TextView question = new TextView(context);

        // set title
        alertDialogBuilder.setTitle("You are second blind");

        alertDialogBuilder.setView(question);
        question.setText("You bet $200");

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //todo call raise for second blind and change players then start game at third person with call/check
            }
        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    //todo FIX WAY ITS SHARED, also check that monnies are saved round by round
    private void addPlayersToSharedPref() {
        //adds all players to shared preferences to be used later
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

    //todo write raise bet check call and player folded
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.raise:
                //raiseBet();
                break;
            case R.id.call_check:
                //checkCall();
                break;
            case R.id.fold:
                //playerFolded();
                break;
        }
    }

}
