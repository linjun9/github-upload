package com.jun.a2048.game;

import java.io.Serializable;
import java.util.List;

public interface NodesInRow {

  public void shift(String action);
  
  public List<Node> getContents();
  
  public int getIndex();
  
  public void reset();
  
  public List<Node> getSpareNodes();
}
