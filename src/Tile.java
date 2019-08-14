/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4584.3d417815a modeling language!*/


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
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


  public URL getDefaultImage() {
    return defaultImage;
  }

  public void setDefaultImage(URL defaultImage) {
    this.defaultImage = defaultImage;
    this.activeImage = defaultImage;
  }

  public URL getActiveImage() {
    return activeImage;
  }

  public void setActiveImage(URL activeImage) {
    this.activeImage = activeImage;
  }

  //URL's to default and current image in resources folder

  private java.net.URL defaultImage;
  private java.net.URL activeImage;

  //Tile Associations
  private Room isPartOf;
  private List<Tile> adjacent = new ArrayList<>();

  //Colours
  private static String ANSI_RESET = "";
  private static String ANSI_RED = "";
  private static String ANSI_GREEN = "";
  private static String ANSI_YELLOW ="";
  private static String ANSI_BLUE= "";
  private static String ANSI_PURPLE= "";
  private static String ANSI_CYAN = "";


  /**
   * Constructor to create tile from string representation on board
   * @param symbol symbol of tile
   */
  Tile(char symbol){
    this.symbol=symbol;

    switch (this.symbol){
      case '_':
        isAccessible = true;
        defaultImage = getClass().getResource("Cor.jpg");
        this.activeImage = this.defaultImage;
        break;
      case '>':
        isAccessible =true;
        defaultImage = getClass().getResource("Room.jpg");
        this.activeImage = this.defaultImage;
        break;
      case '<':
        isAccessible =true;
        defaultImage = getClass().getResource("Room.jpg");
        this.activeImage = this.defaultImage;
        break;
      case '^':
        isAccessible =true;
        defaultImage = getClass().getResource("Room.jpg");
        this.activeImage = this.defaultImage;
        break;
      case 'v':
        isAccessible =true;
        defaultImage = getClass().getResource("Room.jpg");
        this.activeImage = this.defaultImage;
        break;
      case 'X':
        isAccessible = false;
        defaultImage = getClass().getResource("The_Void.jpg");
        this.activeImage = this.defaultImage;
        break;
      case 'M':
      case 'W':
      case 'G':
      case 'T':
      case 'P':
      case 'R':
        isAccessible = false;
        defaultImage = getClass().getResource("Cor.jpg");
        activeImage = getClass().getResource(symbol + "_Cor.jpg");
        break;
      default: // must be a wall
        isAccessible =false;
        defaultImage = getClass().getResource("WT.jpg");
        this.activeImage = this.defaultImage;
    }


    if ( System.console() != null && System.getenv().get("TERM") != null){
      ANSI_RESET = "\u001B[0m";
      ANSI_RED = "\u001B[31m";
      ANSI_GREEN = "\u001B[32m";
      ANSI_YELLOW = "\u001B[33m";
      ANSI_BLUE = "\u001B[34m";
      ANSI_PURPLE = "\u001B[35m";
      ANSI_CYAN = "\u001B[36m";
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
    defaultImage = getClass().getResource("Room.jpg");
    activeImage = defaultImage;
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
    if(isPartOf == null)
    activeImage = getClass().getResource(c.getCharacterSymbol() + "_Cor.jpg");
    else
      activeImage = getClass().getResource(c.getCharacterSymbol() + "_Room.jpg");
     setIsAccessible(false);
  }

  /**
   * Remove character from this tile
   * Changes tile's visual representation and marks as accessible
   */
   void removeCharacter(){
    symbol = '_';
    activeImage = defaultImage;
    setIsAccessible(true);
  }

  void setWeapon(WeaponCard w){
    activeImage = w.icon;
    setIsAccessible(false);
  }

  void removeWeapon(){
     activeImage = defaultImage;
     getIsPartOf().addEmptySpace(this);
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