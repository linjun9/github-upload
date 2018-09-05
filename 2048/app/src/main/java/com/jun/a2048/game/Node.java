package com.jun.a2048.game;

import java.io.Serializable;

public class Node implements Serializable {
  
  private int content;
  private int row;
  private int column;
  
  
  public Node(int row, int column) {
    this.setContent(0);
    this.setRow(row);
    this.setColumn(column);
  }

  public void add(Node newNode) {
    int newContent = newNode.getContent() + this.getContent();
    this.setContent(0);
    newNode.setContent(newContent);
  }

  public int getContent() {
    return content;
  }

  public void setContent(int content) {
    this.content = content;
  }
  
  public boolean compare(Node newNode) {
    if (this.getContent() == newNode.getContent()) {
      return true;
    } else {
      return false;
    }
  }
  
  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    } else if (other == this) {
      return true;
    } else if (!(other instanceof Node)) {
      return false;
    } else {
      if (this.getContent() == ((Node)other).getContent()) {
        return true;
      } else {
        return false;
      }
    }
    
  }
  
  public Node copy() {
    Node newNode = new Node(this.getRow(), this.getColumn());
    newNode.setContent(this.getContent());
    return newNode;
  }
  
  public void reset() {
    this.setContent(0);
  }
  
  @Override
  public String toString() {
    String str = "[ ";
    if (this.content == 0) {
      str = str + "  ]";
    } else {
      str = str + this.getContent() + " ]";
    }
    return str;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public int getColumn() {
    return column;
  }

  public void setColumn(int column) {
    this.column = column;
  }
}