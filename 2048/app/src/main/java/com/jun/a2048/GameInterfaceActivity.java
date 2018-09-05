package com.jun.a2048;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import com.jun.a2048.game.Board;

public class GameInterfaceActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    private Board board = null;
    private GestureDetectorCompat gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_interface);
        Bundle boardData = getIntent().getExtras();
        if (boardData == null) {
            return;
        }

        Board board = (Board) boardData.get("board");
        this.board = board;
        TextView boardView = (TextView) findViewById(R.id.boardView);
        String boardText = board.toString();
        boardView.setText(boardText);

        this.gestureDetector = new GestureDetectorCompat(this, this);

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        if (e1.getX() < e2.getX()){
//            board.shift("r");
//            // right
//        } else if (e1.getX() > e2.getX()){
//            // left
//            board.shift("l");
//        } else if (e1.getY() < e2.getY()){
//            // up
//            board.shift("u");
//        } else if (e1.getY() > e2.getY()){
//            // down
//            board.shift("d");
//        }
//        TextView boardView = (TextView) findViewById(R.id.boardView);
//        boardView.setText(board.toString());
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if ((Math.abs(velocityX) > 150) && ((Math.abs(e2.getX() - e1.getX()) > Math.abs(e2.getY() - e1.getY())))){
            if (e2.getX() - e1.getX() > 100 ){
                board.shift("r");
             // right
            } else {
                // left
                board.shift("l");
            }

        }else if ((Math.abs(velocityY) > 150)&& ((Math.abs(e2.getY() - e1.getY()) > Math.abs(e2.getX() - e1.getX())))) {
            if (e2.getY() - e1.getY() > 100){
                // down
                board.shift("d");
            } else {
                // up
                board.shift("u");
            }
        }
        TextView boardView = (TextView) findViewById(R.id.boardView);
        boardView.setText(board.toString());
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
