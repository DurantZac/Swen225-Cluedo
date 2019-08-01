/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4584.3d417815a modeling language!*/


import java.util.ArrayList;
import java.util.List;

// line 29 "model.ump"
// line 110 "model.ump"
public class Tile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tile Attributes
  private boolean isAccessible;
  private char symbol;
  private int row;
  private int col;

  //Tile Associations
  private Room isPartOf;
  private List<Tile> adjacent = new ArrayList<>();

  //Colours

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";


  /**
   * Constructor to create tile from string representation on board
   * @param symbol
   */
  public Tile(char symbol)
  {
    this.symbol=symbol;

    switch (this.symbol){
      case '_':
        isAccessible = true;
        break;
      case '>':
        isAccessible =true;
        break;
      case '<':
        isAccessible =true;
        break;
      case '^':
        isAccessible =true;
        break;
      case 'v':
        isAccessible =true;
        break;
      default: // must be a wall or a person
        isAccessible =false;
    }

  }


  /**
   * Set value of isAccessible to allow or disallow movement on tile
   * @param aIsAccessable
   */
  public void setIsAccessible(boolean aIsAccessable)
  {
    isAccessible = aIsAccessable;
  }

  /**
   * Get value of isAccessible
   * @return value
   */
  public boolean getIsAccessible()
  {
    return isAccessible;
  }

  /**
   * Checks if this tile is a doorway and checks direction of entry
   * @param t Tile direction
   * @return
   */
  public boolean getIsAccessibleFull(Tile t){
    switch(this.symbol){
      case '^':
        return t.row == this.row-1 && getIsAccessible();
      case '>':
        return t.col == this.col +1 && getIsAccessible();
      case '<':
        return t.col == this.col -1 && getIsAccessible();
      case 'v':
        return t.row == this.row +1 && getIsAccessible();
      default:
        return getIsAccessible();
    }
  }

  /**
   * Return room to which this tile belongs
   * @return Room
   */
  public Room getIsPartOf()
  {
    return isPartOf;
  }

  /**
   * Set this tile as part of a room
   * @param aNewIsPartOf
   */
  public void setIsPartOf(Room aNewIsPartOf)
  {
    isPartOf = aNewIsPartOf;
  }

  /**
   * Return list of adjacent tiles
   * @return
   */
  public List<Tile> getAdjacent(){
    return adjacent;
  }

  /**
   * Add adjacent tile
   * @param t Tile to add
   */
  public void addAdjacent(Tile t){
    adjacent.add(t);
  }

  /**
   * Add Character to this tile.
   * Changes tile's visual representation and marks as inaccessible
   * @param c
   */
  public void addCharacter(CharacterCard c){
    symbol = c.getCharacterSymbol();
    setIsAccessible(false);
  }

  /**
   * Remove character from this tile
   * Changes tile's visual representation and marks as accessible
   */
  public void removeCharacter(){
    symbol = '_';
    setIsAccessible(true);
  }

  /**
   * To String used for printing the board to the console
   * @return The symbol of this tile with a divider
   */
  public String toString(){
    String s="|";
    switch(this.symbol){
      case 'R':
        return "|" + ANSI_RED + symbol + ANSI_RESET;
      case 'G':
        return "|" + ANSI_GREEN + symbol + ANSI_RESET;
      case 'T':
        return "|" + ANSI_CYAN + symbol + ANSI_RESET;
      case 'W':
        return "|" + ANSI_BLUE + symbol + ANSI_RESET;
      case 'M':
        return "|" + ANSI_YELLOW + symbol + ANSI_RESET;
      case 'P':
        return "|" + ANSI_PURPLE + symbol + ANSI_RESET;
    }
    s+=symbol;

    return s;
  }

  /**
   * Get the row of this tile
   * @return row
   */
  public int getRow(){
    return row;
  }

  /**
   * Get the column of this Tile
   * @return col
   */
  public int getCol(){
    return col;
  }

  /**
   * Set row to parameter r
   * @param r
   */
  public void setRow(int r){
   row = r;
  }

  /**
   * Set col to parameter r
   * @param c
   */
  public void setCol(int c){
    col = c;
  }

}