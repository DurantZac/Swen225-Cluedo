/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4584.3d417815a modeling language!*/


import java.util.*;

// line 17 "model.ump"
// line 104 "model.ump"
public class Board
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  public static final String TITLE = "\n" +
          "   ____ _                _       \n" +
          "  / ___| |_   _  ___  __| | ___  \n" +
          " | |   | | | | |/ _ \\/ _` |/ _ \\ \n" +
          " | |___| | |_| |  __/ (_| | (_) |\n" +
          "  \\____|_|\\__,_|\\___|\\__,_|\\___/ \n" +
          "                                 \n";

  //Board Associations
  private Tile[][] boardTiles = new Tile[25][24];
  private Game game;


  //------------------------
  // CONSTRUCTOR
  //------------------------

  /**
   * Constructor of Board,
   * Makes a new game
   * @param aGame
   * @param allBoardTiles
   */
  public Board(Game aGame, List<Tile>allBoardTiles)
  {
    System.out.println(TITLE);
    int i = 0;
    for(int row = 0; row < 25; row++){
      for(int col = 0; col < 24; col++){
        boardTiles[row][col] = allBoardTiles.get(i); // converts list of tiles to 2D array
        i++;
        boardTiles[row][col].setRow(row);
        boardTiles[row][col].setCol(col);
      }
    }

    game = aGame;


    // Add adjacent tiles
    for(char row = 'a'; row < 'z'; row++){
      for(char col = 'A'; col < 'Y'; col++){
        Tile t  = getBoardTile(col + String.valueOf(row));
        if(row != 'a'){t.addAdjacent(getBoardTile(col + String.valueOf((char)(row-1))));}
        if(row != 'y'){t.addAdjacent(getBoardTile(col + String.valueOf((char)(row+1))));}
        if(col != 'A'){t.addAdjacent(getBoardTile((char)(col-1) + String.valueOf(row)));}
        if(col != 'X'){t.addAdjacent(getBoardTile((char)(col+1) + String.valueOf(row)));}
      }
    }
  }


  //------------------------
  // INTERFACE
  //------------------------

  /**
   *  Gets the tile corresponding to XY coordinates.
   *  String must be 2 letters, with one being upper case and one being lower
   * @param tile the tile to get according to its coordinates
   * @return the tile.
   */
  public Tile getBoardTile(String tile)
  {
    try {
      if(tile == null) throw new moveInvalidException("Null move");
      if (tile.length() != 2) { // needs to be exactly 2 letters long
        throw new moveInvalidException ("Move invalid.");
      }

      char letter1 = tile.charAt(0);
      char letter2 = tile.charAt(1);

      //Needs to be one upper case and one lower case

      //Case X followed by Y
      if (Character.isUpperCase(letter1)){
        if (Character.isLowerCase(letter2)){
          int x = letter1 - 'A';
          int y = letter2 - 'a';
          return boardTiles[y][x];
        }
        throw new moveInvalidException ("Move invalid.");
      }

      //Case Y followed by X
      else if (Character.isUpperCase(letter2)) {
        if (Character.isLowerCase(letter1)) {
          int y = letter1 - 'a';
          int x = letter2 - 'A';
          return boardTiles[y][x];
        }
        throw new moveInvalidException("Move invalid.");
      }

      //Neither letters upper case
      throw new moveInvalidException ("Move invalid.");
    }
    catch (moveInvalidException e){ //Invalid input.
      return null;
    }
  }
  /**
   * Prints the board based on the 2D array of tiles.
   */
  public void printBoard(){
    System.out.println(toString());
  }

  /**
   * Checks if move is valid, if so move player, update tiles and return true
   * If not return false
   * @param p player
   * @param goal node target
   * @param moves number of moves to use
   * @return success or fail
   */
  public boolean movePlayer(Player p, Tile goal, int moves) {
    Tile startTile = p.getPosition();
    if(goal.getIsAccessible() == false) return false;

    // Find if path available
    boolean valid = false;
    if(startTile.getIsPartOf() == null){
      valid = pathFinding(startTile,goal,moves,0,new ArrayList<Tile>());
    }
    else{ // If in room, try all exits
      if(startTile.getIsPartOf() == goal.getIsPartOf()) return false;
      for(Tile t : p.getPosition().getIsPartOf().getEntrances()){
        valid = valid || pathFinding(t,goal,moves,0,new ArrayList<Tile>());
      }
    }

    if(valid){
      // Move
      Tile oldPos = p.getPosition();
      if(oldPos.getIsPartOf() != null) oldPos.getIsPartOf().addEmptySpace(oldPos);
      oldPos.removeCharacter();
      if(goal.getIsPartOf() != null){
        Tile t = goal.getIsPartOf().getEmptySpace();
        p.getCharacter().setPosition(t);
      }
      else{
        p.getCharacter().setPosition(goal);
      }
      return true;
    }
    else{
      return false;
    }
  }

  /**
   * Recursive Depth first search to find path from start to end using required number of moves
   * @param node Current Node
   * @param goal End Node
   * @param moveGoal Moves to use
   * @param moveCount Moves used so far
   * @param visited List of nodes that have been visited
   * @return True if path found, false otherwise
   */
  public boolean pathFinding(Tile node, Tile goal, int moveGoal, int moveCount, List<Tile> visited){
    if(node == goal && moveCount == moveGoal) return true;
    if(node.getIsPartOf() != null && node.getIsPartOf() == goal.getIsPartOf() && moveCount <= moveGoal) return true;
    if(moveCount >= moveGoal) return false;
    visited.add(node);
    for(Tile neigh : node.getAdjacent()){
      if(!visited.contains(neigh) && neigh.getIsAccessibleFull(node)) {
        if (pathFinding(neigh, goal, moveGoal, moveCount + 1, new ArrayList(visited))) return true;
      }
    }
    return false;
  }

  /**
   * Teleports the player to a room they have been suggested in
   * @param c charactercard object
   * @param goal room to move to
   */
  public void teleportCharacter(CharacterCard c, Room goal){
    Tile oldPos = c.getPosition();
    if(oldPos.getIsPartOf() != null) oldPos.getIsPartOf().addEmptySpace(oldPos);
    oldPos.removeCharacter();
    Tile t = goal.getEmptySpace();
    c.setPosition(t);
  }

  /**
   * Teleports weapon to given room after suggestion
   * @param w
   * @param goal
   */
  public void teleportWeapon(WeaponCard w, Room goal){
    w.setLocation(goal);
  }

  public String toString(){
    String s = TITLE;
    char c = 'a';
    for(int row = 0; row < 25; row++){
      for(int col = 0; col < 24; col++){
        String str = "";
        if(col == 0) s+=(c++ + " ");
        str += boardTiles[row][col];
        s+=(str);
      }
      s+=("|\n");
    }
    s+= ("   A B C D E F G H I J K L M N O P Q R S T U V W X\n");
    s+=("Weapon locations: \n");
    for(WeaponCard w : game.getWeapons()){
      s+=("The " + w.getWeapon() + " is in the " + w.getLocation()+"\n");
    }
    s+= "\n";
    return s;
  }

  private class moveInvalidException extends Throwable {
    public moveInvalidException(String s) {
    }
  }
}