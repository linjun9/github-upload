package com.jun.a2048.gameDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.jun.a2048.game.Board;

public class Driver {

  public static void main(String[] argv) {
    // set up the board
    Board board = Board.getINSTANCE();
    System.out.println(board);
    BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
    while (!board.gameOver()) {
    System.out.println("please enter an action: (u,d,l,r)");
      try {
        String action = buf.readLine();
        
        if (action.equals("l")) {
          System.out.println("action applied");
          board.shift("l");
          System.out.println(board);
        } else if (action.equals("r")) {
          System.out.println("action applied");
          board.shift("r");
          System.out.println(board);
        } else if (action.equals("u")) {
          System.out.println("action applied");
          board.shift("u");
          System.out.println(board);
        } else if (action.equals("d")) {
          System.out.println("action applied");
          board.shift("d");
          System.out.println(board);
        } else {
          System.out.println("please enter a valid action:");
        }
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    }
    try {
      buf.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
