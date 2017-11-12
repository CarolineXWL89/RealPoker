package com.example.caroline.realpoker;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FinalScreen extends Fragment {

    private Player[] players;
    private TextView player1View, player2View, player3View, player4View, player5View, player6View;
    private Card myCard1, myCard2, tableCard1, tableCard2, tableCard3, tableCard4, tableCard5;
    private ImageView myCard1View, myCard2View, tableCard1View, tableCard2View, tableCard3View, tableCard4View, tableCard5View,
            player1Card1View, player1Card2View, player2Card1View, player2Card2View, player3Card1View, player3Card2View, player4Card1View, player4Card2View,
            player5Card1View, player5Card2View;
    private View rootView;

    public FinalScreen() {
    }

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        //Inflate the layout we made (one_fragment.xml)
        rootView = inflater.inflate(R.layout.activity_poker_game, container, false);
        wireWidgets();
        return rootView;
    }

    private void wireWidgets() {

        //Wire any widgets -- must use rootView.findViewById
        myCard1View = (ImageView) rootView.findViewById(R.id.my_card_1);
        myCard1View.setContentDescription(myCard1.getCardNumber()+ " of " + myCard1.getSuitName());
        showCard(myCard1);

        myCard2View = (ImageView) rootView.findViewById(R.id.my_card_2);
        myCard2View.setContentDescription(myCard2.getCardNumber()+ " of " + myCard2.getSuitName());
        showCard(myCard2);

        tableCard1View = (ImageView) rootView.findViewById(R.id.table_card_1);
        tableCard1View.setContentDescription(tableCard1.getCardNumber()+ " of " + tableCard1.getSuitName());
        showCard(tableCard1);

        tableCard2View = (ImageView) rootView.findViewById(R.id.table_card_2);
        tableCard2View.setContentDescription(tableCard2.getCardNumber()+ " of " + tableCard2.getSuitName());
        showCard(tableCard2);

        tableCard3View = (ImageView) rootView.findViewById(R.id.table_card_3);
        tableCard3View.setContentDescription(tableCard3.getCardNumber()+ " of " + tableCard3.getSuitName());
        showCard(tableCard3);

        tableCard4View = (ImageView) rootView.findViewById(R.id.table_card_4);
        tableCard4View.setContentDescription(tableCard4.getCardNumber()+ " of " + tableCard4.getSuitName());
        showCard(tableCard4);

        tableCard5View = (ImageView) rootView.findViewById(R.id.table_card_5);
        tableCard5View.setContentDescription(tableCard5.getCardNumber()+ " of " + tableCard5.getSuitName());
        showCard(tableCard5);

        player1View = (TextView) rootView.findViewById(R.id.player_1);
        String p1 = players[1].getName() + ": $" + players[1].getMonnies();
        player1View.setText(p1);

        player2View = (TextView) rootView.findViewById(R.id.player_2);
        String p2 = players[2].getName() + ": $" + players[2].getMonnies();
        player2View.setText(p2);

        player3View = (TextView) rootView.findViewById(R.id.player_3);
        String p3 = players[3].getName() + ": $" + players[3].getMonnies();
        player3View.setText(p3);

        player4View = (TextView) rootView.findViewById(R.id.player_4);
        String p4 = players[4].getName() + ": $" + players[4].getMonnies();
        player4View.setText(p4);

        player5View = (TextView) rootView.findViewById(R.id.player_5);
        String p5 = players[5].getName() + ": $" + players[5].getMonnies();
        player5View.setText(p5);

        player6View = (TextView) rootView.findViewById(R.id.user);
        String p6 = players[0].getName() + ": $" + players[0].getMonnies();
        player6View.setText(p6);

        Card player1Card1 = players[1].getHand().get(0);
        player1Card1View = (ImageView) rootView.findViewById(R.id.player1_card_1);
        player1Card1View.setContentDescription(player1Card1.getCardNumber()+ " of " + player1Card1.getSuitName());
        showCard(player1Card1);

        Card player1Card2 = players[1].getHand().get(1);
        player1Card2View = (ImageView) rootView.findViewById(R.id.player1_card_2);
        player1Card2View.setContentDescription(player1Card2.getCardNumber()+ " of " + player1Card2.getSuitName());
        showCard(player1Card2);

        Card player2Card1 = players[2].getHand().get(0);
        player2Card1View = (ImageView) rootView.findViewById(R.id.player2_card_1);
        player2Card1View.setContentDescription(player2Card1.getCardNumber()+ " of " + player2Card1.getSuitName());
        showCard(player2Card1);

        Card player2Card2 = players[2].getHand().get(1);
        player2Card2View = (ImageView) rootView.findViewById(R.id.player2_card_2);
        player2Card2View.setContentDescription(player2Card2.getCardNumber()+ " of " + player2Card2.getSuitName());
        showCard(player2Card2);

        Card player3Card1 = players[3].getHand().get(0);
        player3Card1View = (ImageView) rootView.findViewById(R.id.player3_card_1);
        player3Card1View.setContentDescription(player3Card1.getCardNumber()+ " of " + player3Card1.getSuitName());
        showCard(player3Card1);

        Card player3Card2 = players[3].getHand().get(1);
        player3Card2View = (ImageView) rootView.findViewById(R.id.player3_card_2);
        player3Card2View.setContentDescription(player3Card2.getCardNumber()+ " of " + player3Card2.getSuitName());
        showCard(player3Card2);

        Card player4Card1 = players[4].getHand().get(0);
        player4Card1View = (ImageView) rootView.findViewById(R.id.player4_card_1);
        player4Card1View.setContentDescription(player4Card1.getCardNumber()+ " of " + player4Card1.getSuitName());
        showCard(player4Card1);

        Card player4Card2 = players[4].getHand().get(1);
        player4Card2View = (ImageView) rootView.findViewById(R.id.player4_card_2);
        player4Card2View.setContentDescription(player4Card2.getCardNumber()+ " of " + player4Card2.getSuitName());
        showCard(player4Card2);

        Card player5Card1 = players[5].getHand().get(0);
        player5Card1View = (ImageView) rootView.findViewById(R.id.player5_card_1);
        player5Card1View.setContentDescription(player5Card1.getCardNumber()+ " of " + player5Card1.getSuitName());
        showCard(player5Card1);

        Card player5Card2 = players[5].getHand().get(1);
        player5Card2View = (ImageView) rootView.findViewById(R.id.player5_card_2);
        player5Card2View.setContentDescription(player5Card2.getCardNumber()+ " of " + player5Card2.getSuitName());
        showCard(player5Card2);
    }

    private void showCard(Card myCard) {
        int res = getResources().getIdentifier(myCard.getSuit()+"_"+myCard.getNumber(), "drawable", "com.example.caroline.realpoker");
        if(myCard.equals(myCard1)) {
            myCard1View.setImageResource(res);
        } else if(myCard.equals(myCard2)){
            myCard2View.setImageResource(res);
        } else if(myCard.equals(players[1].getHand().get(0))) {
            player1Card1View.setImageResource(res);
        } else if(myCard.equals(players[1].getHand().get(1))){
            player1Card2View.setImageResource(res);
        } else if(myCard.equals(players[2].getHand().get(0))) {
            player2Card1View.setImageResource(res);
        } else if(myCard.equals(players[2].getHand().get(1))){
            player2Card2View.setImageResource(res);
        } else if(myCard.equals(players[3].getHand().get(0))) {
            player3Card1View.setImageResource(res);
        } else if(myCard.equals(players[3].getHand().get(1))){
            player3Card2View.setImageResource(res);
        } else if(myCard.equals(players[4].getHand().get(0))) {
            player4Card1View.setImageResource(res);
        } else if(myCard.equals(players[4].getHand().get(1))){
            player4Card2View.setImageResource(res);
        } else if(myCard.equals(players[5].getHand().get(0))) {
            player5Card1View.setImageResource(res);
        } else if(myCard.equals(players[5].getHand().get(1))){
            player5Card2View.setImageResource(res);
        }  else if(myCard.equals(tableCard1)) {
            tableCard1View.setImageResource(res);
        } else if(myCard.equals(tableCard2)){
            tableCard2View.setImageResource(res);
        } else if(myCard.equals(tableCard3)) {
            tableCard3View.setImageResource(res);
        } else if(myCard.equals(tableCard4)){
            tableCard4View.setImageResource(res);
        } else if(myCard.equals(tableCard5)){
            tableCard5View.setImageResource(res);
        }
    }

    public void endScreen(){
        showCard(players[1].getHand().get(0));
        showCard(players[1].getHand().get(1));
        showCard(players[2].getHand().get(0));
        showCard(players[2].getHand().get(1));
        showCard(players[3].getHand().get(0));
        showCard(players[3].getHand().get(1));
        showCard(players[4].getHand().get(0));
        showCard(players[4].getHand().get(1));
        showCard(players[5].getHand().get(0));
        showCard(players[5].getHand().get(1));
    }
}
