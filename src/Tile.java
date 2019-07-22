/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4584.3d417815a modeling language!*/



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

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tile(char symbol)
  {
    isAccessable = false;
    this.symbol=symbol;
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


  public String toStringFull()
  {
    return super.toString() + "["+
            "isAccessable" + ":" + getIsAccessable()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "isOccupiedBy = "+(getIsOccupiedBy()!=null?Integer.toHexString(System.identityHashCode(getIsOccupiedBy())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "isPartOf = "+(getIsPartOf()!=null?Integer.toHexString(System.identityHashCode(getIsPartOf())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "occupiedBy = "+(getOccupiedBy()!=null?Integer.toHexString(System.identityHashCode(getOccupiedBy())):"null");
  }

  public String toString(){
    String s="|";
    s+=symbol;
    s+="|";
    
    return s;
  }

}