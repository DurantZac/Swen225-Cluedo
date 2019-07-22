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
  private List<Tile> BoardTiles;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board(Game aGame, Tile... allBoardTiles)
  {
    BoardTiles = new ArrayList<Tile>();
    boolean didAddBoardTiles = setBoardTiles(allBoardTiles);
    if (!didAddBoardTiles)
    {
      throw new RuntimeException("Unable to create Board, must have 600 BoardTiles. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (aGame == null || aGame.getContains() != null)
    {
      throw new RuntimeException("Unable to create Board due to aGame. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    game = aGame;
  }

  public Board(Card[] allMurderScenarioForGame, Player[] allStoresForGame, Tile... allBoardTiles)
  {
    BoardTiles = new ArrayList<Tile>();
    game = new Game(this, allMurderScenarioForGame, allStoresForGame);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Tile getBoardTile(int index)
  {
    Tile aBoardTile = BoardTiles.get(index);
    return aBoardTile;
  }

  public List<Tile> getBoardTiles()
  {
    List<Tile> newBoardTiles = Collections.unmodifiableList(BoardTiles);
    return newBoardTiles;
  }

  public int numberOfBoardTiles()
  {
    int number = BoardTiles.size();
    return number;
  }

  public boolean hasBoardTiles()
  {
    boolean has = BoardTiles.size() > 0;
    return has;
  }

  public int indexOfBoardTile(Tile aBoardTile)
  {
    int index = BoardTiles.indexOf(aBoardTile);
    return index;
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
  public boolean setBoardTiles(Tile... newBoardTiles)
  {
    boolean wasSet = false;
    ArrayList<Tile> verifiedBoardTiles = new ArrayList<Tile>();
    for (Tile aBoardTile : newBoardTiles)
    {
      if (verifiedBoardTiles.contains(aBoardTile))
      {
        continue;
      }
      verifiedBoardTiles.add(aBoardTile);
    }

    if (verifiedBoardTiles.size() != newBoardTiles.length || verifiedBoardTiles.size() != requiredNumberOfBoardTiles())
    {
      return wasSet;
    }

    BoardTiles.clear();
    BoardTiles.addAll(verifiedBoardTiles);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    BoardTiles.clear();
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
  }

  // line 24 "model.ump"
   public boolean movePlayer(Player p, Tile t, int moves){
    return false;
  }

}