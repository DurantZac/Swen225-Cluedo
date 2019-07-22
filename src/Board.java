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
    int i = 0;
    for(int row = 0; row < 25; row++){
      for(int col = 0; col < 24; col++){
        boardTiles[row][col] = allBoardTiles.get(i); // converts list of tiles to 2D array
        i++;
      }
    }

    game = aGame;
    printBoard();
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
          return boardTiles[x][y];
        }
        throw new moveInvalidException ("Move invalid.");
      }

      //Case Y followed by X
      else if (Character.isUpperCase(letter2)) {
        if (Character.isLowerCase(letter1)) {
          int y = letter1 - 'a';
          int x = letter2 - 'A';
          return boardTiles[x][y];
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

  public List<Tile> getBoardTiles()
  {
    return null;
  }

  public int numberOfBoardTiles()
  {
    int number = boardTiles.length;
    return 0;
  }

  public boolean hasBoardTiles()
  {
    boolean has = boardTiles.length > 0;
    return false;
  }

  public int indexOfBoardTile(Tile aBoardTile)
  {
    return 0;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfBoardTiles()
  {
    return 600;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBoardTiles()
  {
    return 600;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfBoardTiles()
  {
    return 600;
  }
  /* Code from template association_SetUnidirectionalN */


  /**
   * Prints the board based on the 2D array of tiles.
   */
  public void printBoard(){
    String s = "";
    char c = 'a';
    for(int row = 0; row < 25; row++){
      for(int col = 0; col < 24; col++){
        String str = "";
        if(col == 0) System.out.print(c++ + " ");
        str += boardTiles[row][col];
        System.out.print(str);
      }
      System.out.println("|");
    }
    System.out.println("   A B C D E F G H I J K L M N O P Q R S T U V W X");
  }

  // line 24 "model.ump"
   public boolean movePlayer(Player p, Tile t, int moves){
    return false;
  }



  private class moveInvalidException extends Throwable {
    public moveInvalidException(String s) {
    }
  }
}