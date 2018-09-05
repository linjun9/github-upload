package com.jun.a2048.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board implements Serializable{
  
  private static Board INSTANCE = null;
  private List<Row> rows = null;
  private List<Column> columns = null;
  
  private Board() {//set up 4*4 board
    setUp();
  }

  private void setUp() {
    this.rows = new ArrayList<Row>();
    this.columns = new ArrayList<Column>();
    // create 4 rows and add them to the rows
    for(int i = 1; i <= 4; i++) {
      Row row = new Row(i);
      this.rows.add(row);
    }
    int counter = 1;
    while (counter <= 4) {
      List<Node> columnContents = new ArrayList<Node>();
      for(Row row : this.getRows()) {
        Node node = row.getNode(counter);
        columnContents.add(node);
      }
      Column column = new Column(counter, columnContents);
      this.columns.add(column);
      counter++;
    }
    try {
      this.addRandom();
      this.addRandom();
    } catch (NoMoreSpaceException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }
  
  public static Board getINSTANCE() {
    if (INSTANCE == null) {
      return new Board();
    }
    return INSTANCE;
  }
  
  public static void reset() {
    INSTANCE = null;
    getINSTANCE();
  }
  
  private List<Node> getSpareNodes() {
    List<Node> spareNodes = new ArrayList<Node>();
    for (Row row : this.getRows()) {
      spareNodes.addAll(row.getSpareNodes());
    }
    return spareNodes;
  }
  
  public void addRandom() throws NoMoreSpaceException {
    List<Node> spareNodes = this.getSpareNodes();
    int max = spareNodes.size();
    if (max == 0) {
      throw new NoMoreSpaceException();
    }
    System.out.println(max);
    Random rand = new Random();
    int index = rand.nextInt(max); // choose a node to be add
    Node nodeBeChosen = spareNodes.get(index);
    int randomNum = pickNum();
    nodeBeChosen.setContent(randomNum);
  }
  
  private int pickNum() {
    Random rand = new Random();
    int num = rand.nextInt(3);
    if (num == 0 || num == 1) {
      return 2;
    } else {
      return 4;
    }
  }
  
  public boolean shift(String action) {
    List<Row> cloneRows = new ArrayList<Row>();
    for (Row row : this.getRows()) {
      Row cloneRow = row.copy();
      cloneRows.add(cloneRow);
    }
    
    if (action.equals("l")) { // left 
      for (Row row : this.getRows()) {
        row.shift("l");
      }
    } else if (action.equals("r")) { // right
      for (Row row : this.getRows()) {
        row.shift("r");
      }
    } else if (action.equals("u")) { // up
      for (Column column : this.getColumns()) {
        column.shift("u");
      }
    } else if (action.equals("d")) { // down
      for (Column column : this.getColumns()) {
        column.shift("d");
      }
    }
    try {
      int i = 0;
      while (i < 4) {
        if (!this.getRows().get(i).equals(cloneRows.get(i))) {
          addRandom();
          return true;
        }
        i++;
      }
      return false;
    } catch (NoMoreSpaceException e) {
      if (gameOver()) {
        System.out.println("Game Over");
      }
      return false;
    }
  }

  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    } else if (other == this) {
      return true;
    } else if (!(other instanceof Board)) {
      return false;
    } else {
      int i = 0;
      while (i < 4) {
        if (!(this.getRows().get(i)).equals(
              ((Board)other).getRows().get(i) )) {
          return false;
        }
        i++;
      }
      return true;
    }
  }
  
  public boolean gameOver() {
    for (Row row : this.getRows()) {
      if (!row.gameOver()) {// if there is one row is not over
        return false;
      }
    }
    for (Column col : this.getColumns()) {
      if (!col.gameOver()) {
        return false;
      }
    }
    return true;
  }
  @Override
  public String toString() {
    String str = "";
    for (Row row : this.getRows()) {
      str = str + row + "\n";
    }
    return str;
  }

  /**
   * @return the columns
   */
  public List<Column> getColumns() {
    return columns;
  }

  /**
   * @param columns the columns to set
   */
  public void setColumns(List<Column> columns) {
    this.columns = columns;
  }

  public List<Row> getRows() {
    return rows;
  }

  public void setRows(List<Row> rows) {
    this.rows = rows;
  }
  
  
}
