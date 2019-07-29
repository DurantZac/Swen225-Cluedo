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
  private boolean isAccessable;

  //Tile Associations
  private CharacterCard isOccupiedBy;
  private Room isPartOf;
  private WeaponCard occupiedBy;
  private char symbol;
  private List<Tile> adjacent = new ArrayList<>();
  private int row;
  private int col;

  //Colours
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tile(char symbol)
  {
    this.symbol=symbol;

    switch (this.symbol){
      case '_':
        isAccessable = true;
        break;
      case '>':
        isAccessable=true;
        break;
      case '<':
        isAccessable=true;
        break;
      case '^':
        isAccessable=true;
        break;
      case 'v':
        isAccessable=true;
        break;
      default: // must be a wall or a person
        isAccessable=false;
    }

  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsAccessable(boolean aIsAccessable)
  {
    boolean wasSet = false;
    isAccessable = aIsAccessable;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsAccessable()
  {
    return isAccessable;
  }
  /* Code from template association_GetOne */
  public CharacterCard getIsOccupiedBy()
  {
    return isOccupiedBy;
  }

  public boolean hasIsOccupiedBy()
  {
    boolean has = isOccupiedBy != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Room getIsPartOf()
  {
    return isPartOf;
  }

  public boolean hasIsPartOf()
  {
    boolean has = isPartOf != null;
    return has;
  }
  /* Code from template association_GetOne */
  public WeaponCard getOccupiedBy()
  {
    return occupiedBy;
  }

  public boolean hasOccupiedBy()
  {
    boolean has = occupiedBy != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setIsOccupiedBy(CharacterCard aNewIsOccupiedBy)
  {
    boolean wasSet = false;
    isOccupiedBy = aNewIsOccupiedBy;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setIsPartOf(Room aNewIsPartOf)
  {
    boolean wasSet = false;
    isPartOf = aNewIsPartOf;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setOccupiedBy(WeaponCard aNewOccupiedBy)
  {
    boolean wasSet = false;
    occupiedBy = aNewOccupiedBy;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    isOccupiedBy = null;
    isPartOf = null;
    occupiedBy = null;
  }

  public List<Tile> getAdjacent(){
    return adjacent;
  }

  public void addAdjacent(Tile t){
    adjacent.add(t);
  }

  public int getRow(){
    return row;
  }

  public int getCol(){
    return col;
  }

  public void setRow(int r){
    row = r;
  }

  public void setCol(int c){
    col = c;
  }

  public void addCharacter(CharacterCard c){
    symbol = c.getCharacterSymbol();
  }

  public void removeCharacter(){
    symbol = '_';
  }

  public String toStringFull()
  {
    return super.toString() + "["+
            "isAccessable" + ":" + getIsAccessable()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "isOccupiedBy = "+(getIsOccupiedBy()!=null?Integer.toHexString(System.identityHashCode(getIsOccupiedBy())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "isPartOf = "+(getIsPartOf()!=null?Integer.toHexString(System.identityHashCode(getIsPartOf())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "occupiedBy = "+(getOccupiedBy()!=null?Integer.toHexString(System.identityHashCode(getOccupiedBy())):"null");
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

}