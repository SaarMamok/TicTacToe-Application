package com.example.tictactoe_application;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneScore, playerTowScore, playerStatus;
    private Button[] buttons = new Button[9];
    private Button resetGame;
    private Button nextRound;
    private int playerOneScoreCount, playerTowScoreCount, rountCount;
    boolean activePlayer;

    // player 1 -> 0
    // player 2 -> 1
    // empty -> 2
    int[] gameState = {2,2,2,2,2,2,2,2,2};

    int[][] winningPositions = {
            {0,1,2}, {3,4,5}, {6,7,8}, //row
            {0,3,6}, {1,4,7}, {2,5,8}, // col
            {0,4,8}, {2,4,6} // cross
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTowScore = (TextView) findViewById(R.id.playerTwoScore);
        playerStatus = (TextView) findViewById(R.id.playerStatus);
        resetGame = (Button) findViewById(R.id.resetGame);
        nextRound = (Button) findViewById(R.id.nextRound);


        for(int i=0 ;i<buttons.length;i++){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        rountCount = 0;
        playerOneScoreCount = 0;
        playerTowScoreCount = 0;
        activePlayer = true;
        playerStatus.setText("Player one (X) make a move");
    }

    @Override
    public void onClick(View v) {
        //First we make sure that this btn is empty
        if(!((Button)v).getText().toString().equals(""))
            return;

        // get the pressed btn id
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

        // if it is X who playing now
        if(activePlayer){
            if(gameState[gameStatePointer] == 2) {
                ((Button) v).setText("X");
                ((Button) v).setTextColor(Color.parseColor("#FFC34A"));
                gameState[gameStatePointer] = 0;
            }
        }
        // if it is O who playing now
        else {
            if(gameState[gameStatePointer] == 2) {
                ((Button) v).setText("O");
                ((Button) v).setTextColor(Color.parseColor("#70FFEA"));
                gameState[gameStatePointer] = 1;
            }
        }
        rountCount++;

        if(gameState[gameStatePointer] == 3)
            playAgain();

        else if(checkWinner()){
            if(activePlayer){
                playerOneScoreCount++;
                updatePlayerScore();
                playerStatus.setText("Player One is Winning!");
                Block();
            }
            else {
                playerTowScoreCount++;
                updatePlayerScore();
                playerStatus.setText("Player Two is Winning!");
                Block();
            }
        }
        // if the game over without a winner
        else if(rountCount == 9){
            playerStatus.setText("No Winner!");
            Block();
        }
        else {
            if (activePlayer)
            {
                playerStatus.setText("Player two (O) make a move");
            }
            else
            {
                playerStatus.setText("Player one (X) make a move");
            }
            activePlayer = !activePlayer;
        }



        nextRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
            }
        });
        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                playerOneScoreCount = 0;
                playerTowScoreCount = 0;
                playerStatus.setText("Player one (X) make a move");
                updatePlayerScore();
            }
        });

    }

    public boolean checkWinner(){
        boolean winnerResult = false;
        for(int [] winningPosition: winningPositions){
            if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                gameState[winningPosition[0]] !=2){
                buttons[winningPosition[0]].setBackgroundColor(Color.parseColor("green"));
                buttons[winningPosition[0]].setTextColor(Color.parseColor("black"));
                buttons[winningPosition[1]].setBackgroundColor(Color.parseColor("green"));
                buttons[winningPosition[1]].setTextColor(Color.parseColor("black"));
                buttons[winningPosition[2]].setBackgroundColor(Color.parseColor("green"));
                buttons[winningPosition[2]].setTextColor(Color.parseColor("black"));
                winnerResult = true;
            }
        }
        return winnerResult;
    }

    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTowScore.setText(Integer.toString(playerTowScoreCount));
    }

    public void playAgain(){
        rountCount = 0;
        activePlayer = true;
        for(int i=0; i<buttons.length; i++){
            gameState[i] = 2;
            buttons[i].setText("");
            buttons[i].setBackgroundColor(Color.parseColor("#413F43"));
        }
        playerStatus.setText("Player one (X) make a move");
    }

    public void Block(){
        for(int i=0; i<buttons.length; i++){
            gameState[i] = 3;
        }
    }

}


