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

    }

    @Override
    public void onClick(View v) {

        if(!((Button)v).getText().toString().equals(""))
            return;

        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

        if(activePlayer){
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
        }
        else {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1;
        }
        rountCount++;

        if(checkWinner()){
            if(activePlayer){
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
            else {
                playerTowScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        }
        else if(rountCount == 9){
           playAgain();
            Toast.makeText(this, "No Winner!", Toast.LENGTH_SHORT).show();
        }
        else {
            activePlayer = !activePlayer;
        }

        if(playerOneScoreCount > playerTowScoreCount)
            playerStatus.setText("Player One is Winning!");
        else if(playerTowScoreCount > playerOneScoreCount)
            playerStatus.setText("Player Two is Winning!");
        else
            playerStatus.setText("");

        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                playerOneScoreCount = 0;
                playerTowScoreCount = 0;
                playerStatus.setText("");
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
        }
    }

}


