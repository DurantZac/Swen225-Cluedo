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
  private Tile[][] BoardTiles = new Tile[25][24];
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board(Game aGame, List<Tile>allBoardTiles)
  {
    int i = 0;
    for(int row = 0; row < 25; row++){
      for(int col = 0; col < 24; col++){
        BoardTiles[row][col] = allBoardTiles.get(i);
        i++;
      }
    }
    game = aGame;
    printBoard();
  }


  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Tile getBoardTile(int index)
  {
    return null;
  }

  public List<Tile> getBoardTiles()
  {
    return null;
  }

  public int numberOfBoardTiles()
  {
    int number = BoardTiles.length;
    return 0;
  }

  public boolean hasBoardTiles()
  {
    boolean has = BoardTiles.length > 0;
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



  public void printBoard(){
    String s = "";
    char c = 'a';
    for(int row = 0; row < 25; row++){
      for(int col = 0; col < 24; col++){
        String str = "";
        if(col == 0) System.out.print(c++ + " ");
        str += BoardTiles[row][col];
        System.out.print(str);
      }
      System.out.println();
    }
    System.out.println("   A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  P  Q  R  S  T  U  V  W  X");
  }

  // line 24 "model.ump"
   public boolean movePlayer(Player p, Tile t, int moves){
    return false;
  }

}