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

  private static final String ANSI_RESET = "\u001B[0m";
  private static final String ANSI_RED = "\u001B[31m";
  private static final String ANSI_GREEN = "\u001B[32m";
  private static final String ANSI_YELLOW = "\u001B[33m";
  private static final String ANSI_BLUE = "\u001B[34m";
  private static final String ANSI_PURPLE = "\u001B[35m";
  private static final String ANSI_CYAN = "\u001B[36m";


  /**
   * Constructor to create tile from string representation on board
   * @param symbol symbol of tile
   */
  Tile(char symbol)
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
   * @param aIsAccessible boolean to set isAccessible to
   */
  private void setIsAccessible(boolean aIsAccessible)
  {
    isAccessible = aIsAccessible;
  }

  /**
   * Get value of isAccessible
   * @return value
   */
  boolean getIsAccessible()
  {
    return isAccessible;
  }

  /**
   * Checks if this tile is a doorway and checks direction of entry
   * @param t Tile direction
   * @return if tile is accessible
   */
  boolean getIsAccessibleFull(Tile t){
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
  Room getIsPartOf()
  {
    return isPartOf;
  }

  /**
   * Set this tile as part of a room
   * @param aNewIsPartOf room to set this tile as a part of
   */
   void setIsPartOf(Room aNewIsPartOf)
  {
    isPartOf = aNewIsPartOf;
  }

  /**
   * Return list of adjacent tiles
   * @return the adjacent tiles
   */
   List<Tile> getAdjacent(){
    return adjacent;
  }

  /**
   * Add adjacent tile
   * @param t Tile to add
   */
   void addAdjacent(Tile t){
    adjacent.add(t);
  }

  /**
   * Add Character to this tile.
   * Changes tile's visual representation and marks as inaccessible
   * @param c the character that this tile contains
   */
   void addCharacter(CharacterCard c){
    symbol = c.getCharacterSymbol();
    setIsAccessible(false);
  }

  /**
   * Remove character from this tile
   * Changes tile's visual representation and marks as accessible
   */
   void removeCharacter(){
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
   * @param r the row to set this as
   */
   void setRow(int r){
   row = r;
  }

  /**
   * Set col to parameter r
   * @param c the col to set this as
   */
   void setCol(int c){
    col = c;
  }

}