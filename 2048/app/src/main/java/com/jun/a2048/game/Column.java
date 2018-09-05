package com.jun.a2048.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Column implements NodesInRow, Serializable {


  private int index;// the smallest index is the most left column
  private List<Node> contents = new ArrayList<Node>();
  
  public Column(int index) {
    this.setIndex(index);
    int counter = 0;
    while (counter < 4) {
      Node newNode = new Node(counter+1, index);
      this.contents.add(newNode);
      counter++;
    }
  }
  
  public Column(int index, List<Node> contents) {
    this.setIndex(index);
    this.setContents(contents);
  }

  public void setContents(List<Node> contents) {
    this.contents = contents;
  }
  
  public List<Node> getSpareNodes() {
    List<Node> spareList = new ArrayList<Node>();
    for (Node node : this.getContents()) {
      if (node.getContent() == 0) {
        spareList.add(node);
      }
    }
    return spareList;
  }
  
  public Node getNode(int i) {
    return this.contents.get(i-1);
  }
  
  @Override
  public List<Node> getContents() {
    return this.contents;
  }

  @Override
  public int getIndex() {
    // TODO Auto-generated method stub
    return this.index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
  
  @Override
  public void reset() {
    for (Node node : this.contents) {
      node.reset();
    }
  }
  
  private List<Node> getNonZeroNodes() {
    Node node;
    List<Node> nonZeroList = new ArrayList<Node>();
    int i;
    // the first node in the list is the most left
    i = 1;
    while(i < 5) {
      node = this.getNode(i);
      if (node.getContent() != 0) {
        nonZeroList.add(node);
      }
      i++;
    }
    return nonZeroList;
  }
  

  private void applyAddition(String action) {
    // get the nonzero list
    List<Node> nonZero = this.getNonZeroNodes();
    int size = nonZero.size();
    Node node1, node2, node3, node4;
    switch (size) {
    case 1:// do not need to apply any addition
      break;
    case 2:// compare the two nodes if they are the same add them
      node1 = nonZero.get(0);
      node2 = nonZero.get(1);
      if (node1.compare(node2)) {
        if (action.equals("u")) {
          node2.add(node1);
        } else {
          node1.add(node2);
        }
      }
      break;
    case 3://
      node1 = nonZero.get(0);
      node2 = nonZero.get(1);
      node3 = nonZero.get(2);
      if (action.equals("u")) {// left
        if (node1.compare(node2)) {// if the node1==node2
          node2.add(node1);
        } else if (node2.compare(node3)) { // if node1!= node2 node2 == node 3
          node3.add(node2);
        } else {// if node1 != node 2 and node 2!= node3
        }
      } else {// right
        if (node3.compare(node2)) {
          node2.add(node3);
        } else if (node2.compare(node1)) {
          node1.add(node2);
        } else {
        }
      }
      break;
    case 4:
      node1 = nonZero.get(0);
      node2 = nonZero.get(1);
      node3 = nonZero.get(2);
      node4 = nonZero.get(3);
      if (action.equals("l")) {
        if (node1.compare(node2)) {// if the node1 == node2
          node2.add(node1);
          if (node3.compare(node4)) {
            node4.add(node3);
            node3.add(node2);
          } else {
            node3.add(node2);
            node4.add(node2);
          }
        } else { // node1 != node2
          if (node3.compare(node4)) {
            node4.add(node3);
          } else {// do nothing
          }
        }
      } else {// shift right
        if (node3.compare(node4)) {
          node3.add(node4);
          if (node1.compare(node2)) {
            node1.add(node2);
            node2.add(node3);
          } else {
            node2.add(node3);
            node1.add(node2);
          }
        } else {
          if (node1.compare(node2)) {
            node1.add(node2);
          } else {
          }
        }
        
      }
      break;
    }
  }
  
  @Override
  public void shift(String action) {
    int startingIndex;
    applyAddition(action);
    // two cases: left and right
    if (action.equals("u")) { //left
      // shifting the node to the left side without empty node
      startingIndex = findNextZeroNode(1, action);
      while (startingIndex != -1 && startingIndex != 4) {
        // shift the node after the zero node forward one position
        shiftHelper(startingIndex + 1, action);
        startingIndex = findNextZeroNode(1, action);
        if (checkFinish(startingIndex, action)) {
          break;
        }
      }
      
    } else if (action.equals("d")) { //right
      
      startingIndex = findNextZeroNode(4, action);
      while (startingIndex != -1 && startingIndex != 1) {
        shiftHelper(startingIndex - 1, action);
        startingIndex = findNextZeroNode(4, action);
        if (checkFinish(startingIndex, action)) {
          break;
        }
      }
    }
  }
  
  public boolean compare(Column col) {
    if (this.getContents().equals(col.getContents())) {
      return true;
    }
    return false;
  }
  
  public Column copy() {
    List<Node> newColList = new ArrayList<Node>();
    for (Node node : this.getContents()) {
      Node newNode = node.copy();
      newColList.add(newNode);
    }
    Column newColumn = new Column(this.getIndex(), newColList);
    return newColumn;
  }
  
  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    } else if (other == this) {
      return true;
    } else if (!(other instanceof Column)) {
      return false;
    } else {
      int i = 1;
      while (i < 5) {
        if (!this.getNode(i).equals( ((Column)other).getNode(i) )) {
          return false;
        }
        i++;
      }
      return true;
    }
  }
  
  public boolean gameOver() {
    if (this.getNonZeroNodes().size() == 4) {
      int i = 2;
      Node preNode, afterNode;
      do {
        preNode = this.getNode(i-1);
        afterNode = this.getNode(i);
        if (preNode.compare(afterNode)) {
          return false;
        }
        i++;
      } while((i!= 5) && (!preNode.compare(afterNode)));
      return true;
    }
    return false;
  }
  
  private int findNextZeroNode(int startingIndex, String action) {
    int result = startingIndex;
    Node node;
    if (action.equals("u")) {
      do {
        node = this.getNode(result);
        if (node.getContent() == 0) {
          return result;
        }
      result++;
      } while (result <= 4);
    } else if (action.equals("d")) {
      do {
        node = this.getNode(result);
        if (node.getContent() == 0) {
          return result;
        }
      result--;
      } while (result >= 1);
    }
    return -1;
  }
  
  private void shiftHelper(int startingIndex, String action) {
    Node preNode;
    Node currNode;
    if (action.equals("u")) {
      preNode = this.getNode(startingIndex - 1);//zero node
      currNode = this.getNode(startingIndex);
      while (startingIndex <= 4) {
        currNode.add(preNode);
        startingIndex++;
        if (startingIndex < 5) {
          preNode = currNode;
          currNode = this.getNode(startingIndex);
        }
      }
    } else if (action.equals("d")) {
      preNode = this.getNode(startingIndex + 1);//zero node
      currNode = this.getNode(startingIndex);
      while (startingIndex >= 1) {
        currNode.add(preNode);
        startingIndex--;
        if (startingIndex > 0) {
          preNode = currNode;
          currNode = this.getNode(startingIndex);
        }
      }
    }
  }
  
  private boolean checkFinish(int startingIndex, String action) {
    // get the number of zero in the row
    int numOf0 = 0;
    for (Node node : this.getContents()) {
      if (node.getContent() == 0) {
        numOf0++;
      }
    }
    
    if (action.equals("u")) {
      // if the starting index + number of zero greater than 4, done  
      if (numOf0 + startingIndex > 4) {
        return true;
      } else {
        return false;
      }
    } else {
   // if the starting index + number of zero less than 4, done  
      if ((startingIndex - numOf0) == 0) {
        return true;
      } else {
        return false;
      } 
    }
  }
  

//  private void applyAddition(String action) {
//    // the smaller index means the closer to top 
//    Node node1 = this.getNode(1);
//    Node node2 = this.getNode(2);
//    Node node3 = this.getNode(3);
//    Node node4 = this.getNode(4);
//    
//    // two cases: up and down
//    if (action.equals("u")) { //up
//      if (node1.compare(node2)) { //node1 == node2
//        
//        if (node3.compare(node4)) {// node1 == node2 node3==node4
//          node2.add(node1);
//          node4.add(node3);
//          node3.add(node2);
//        } else { // node1 == node2 and node3!= node4
//          node2.add(node1);
//          node3.add(node2);
//          node4.add(node3);
//        }
//        
//      } else {//node1!=node2
//        
//        if (node2.compare(node3)) { // node2== node3
//          node3.add(node2);
//          node4.add(node3);
//        } else if (node3.compare(node4)){ //node2!= node3   node3==node4
//          node4.add(node3);
//        } else {// nothing changes
//        }
//      }
//      
//    } else if (action.equals("d")) { //down
//      if (node3.compare(node4)) { // node3==node4
//        if(node1.compare(node2)) {// node3==node4 and node1==node2
//          node3.add(node4);
//          node1.add(node2);
//          node2.add(node3);
//        } else {// node3==node4 and node1!=node2
//          node3.add(node4);
//          node2.add(node3);
//          node1.add(node2);
//        }
//      } else {//node3!= node4
//        if(node2.compare(node3)) { // node2==node3
//          node2.add(node3);
//          node1.add(node2);
//        } else if (node1.compare(node2)) { //node2!=node3 and node1==node2
//          node1.add(node2);
//        } else {// nothing changes
//        }
//      }
//    }
//  }

  @Override
  public String toString() {
    String str = "";
    for (Node node : this.getContents()) {
      str = str + node + "\n";
    }
    return str;
  }
}
