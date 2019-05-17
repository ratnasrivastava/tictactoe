package com.example.a51006705.tictactoe;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
   private TextView player1pointstv;
    private TextView player2pointstv;
   private Button button_reset;
   private boolean player1turn = true;
   private int player1points;
   private int player2points;
   private int roundcount;
   private Button button[][] = new Button[3][3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player1pointstv = (TextView)findViewById(R.id.player1points);
        player2pointstv = (TextView)findViewById(R.id.player2points);
        button_reset = (Button)findViewById(R.id.button_reset);
        //dynamic id:
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                String buttonId = "button_"+i+j;
                int resId = getResources().getIdentifier(buttonId,"id",getPackageName());
                button[i][j] = findViewById(resId);
                button[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            if(!((Button)v).getText().toString().equals("")){
                                return;
                            }
                            if(player1turn) {
                                ((Button)v).setText("X");
                            }
                            else{
                                ((Button)v).setText("O");
                            }
                            roundcount++;
                            if(checkForWin()){
                                if(player1turn){
                                    player1wins();
                                }
                                else{
                                    player2wins();
                                }
                            }
                            else if(roundcount == 9){
                                draw();
                            }
                            else{
                                player1turn = !player1turn;
                            }
                        }

                });
            }
        }
        
        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    private void resetGame() {
        player1points = 0;
        player2points = 0;
        updatePlayersPoints();
        resetBoard();

    }

    private void draw() {
        Toast.makeText(this, "Match is draw",Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void resetBoard() {
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                button[i][j].setText("");
            }
        }
        roundcount = 0;
        player1turn = true;
    }

    private void player1wins() {
        player1points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePlayersPoints();
        resetBoard();
    }
    private void player2wins() {
        player2points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePlayersPoints();
        resetBoard();
    }
    private void updatePlayersPoints() {

        player1pointstv.setText("Player1: "+player1points);
        player2pointstv.setText("Player2: "+player2points);
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                field[i][j] = button[i][j].getText().toString();
            }
        }
        for(int i=0;i<3;i++){
            if(field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !(field[i][0].equals(""))){
                return true;
            }
        }
        for(int j=0;j<3;j++){
            if(field[0][j].equals(field[1][j]) && field[0][j].equals(field[2][j]) && !(field[0][j].equals(""))){
                return true;
            }
        }
        if(field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !(field[0][0].equals(""))){
            return true;
        }
        if(field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !(field[0][2].equals(""))){
            return true;
        }
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("player1points", player1points);
        outState.putInt("player2points", player2points);
        outState.putInt("roundCount", roundcount);
        outState.putBoolean("player1turn", player1turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundcount = savedInstanceState.getInt("roundCount");
        player1points = savedInstanceState.getInt("player1points");
        player2points = savedInstanceState.getInt("player2points");
        player1turn = savedInstanceState.getBoolean("player1turn");
    }
}
