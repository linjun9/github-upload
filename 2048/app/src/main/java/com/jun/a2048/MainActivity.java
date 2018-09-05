package com.jun.a2048;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import com.jun.a2048.game.Board;
import com.jun.a2048.gameDriver.Driver;

public class MainActivity extends AppCompatActivity {

    Board board = Board.getINSTANCE();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = (Button) findViewById(R.id.start);
        startButton.setOnClickListener(
                new Button.OnClickListener(){
                    //call back method
                    public void onClick(View v){

                    }
                }
        );
    }


    public void newGame(View view) {
        Board.reset();
        this.board = Board.getINSTANCE();
        this.start(view);
    }

    public void start(View view) {
        this.board = Board.getINSTANCE();
        Intent i = new Intent(this, GameInterfaceActivity.class);
        i.putExtra("board", board);
        startActivity(i);
    }
}
