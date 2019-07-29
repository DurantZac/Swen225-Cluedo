/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4584.3d417815a modeling language!*/


import java.util.*;

// line 89 "model.ump"
// line 135 "model.ump"
public class Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Room Associations
  private RoomCard roomCard;
  private String name;
  private WeaponCard Contains;
  private List<Tile> entrances;
  private List<Tile> openSquares = new ArrayList<>();

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Room(RoomCard aRoomCard)
  {
    if (aRoomCard == null || aRoomCard.getRoom() != null)
    {
      throw new RuntimeException("Unable to create Room due to aRoomCard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    roomCard = aRoomCard;
    entrances = new ArrayList<Tile>();
  }

  public Room(String n)
  {
    roomCard = new RoomCard(this);
    entrances = new ArrayList<Tile>();
    name  = n;
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public RoomCard getRoomCard()
  {
    return roomCard;
  }
  /* Code from template association_GetOne */
  public WeaponCard getContains()
  {
    return Contains;
  }

  public boolean hasContains()
  {
    boolean has = Contains != null;
    return has;
  }
  /* Code from template association_GetMany */
  public Tile getEntrance(int index)
  {
    Tile aEntrance = entrances.get(index);
    return aEntrance;
  }

  public List<Tile> getEntrances()
  {
    List<Tile> newEntrances = Collections.unmodifiableList(entrances);
    return newEntrances;
  }

  public int numberOfEntrances()
  {
    int number = entrances.size();
    return number;
  }

  public boolean hasEntrances()
  {
    boolean has = entrances.size() > 0;
    return has;
  }

  public int indexOfEntrance(Tile aEntrance)
  {
    int index = entrances.indexOf(aEntrance);
    return index;
  }

  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setContains(WeaponCard aNewContains)
  {
    boolean wasSet = false;
    Contains = aNewContains;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfEntrances()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addEntrance(Tile aEntrance)
  {
    boolean wasAdded = false;
    if (entrances.contains(aEntrance)) { return false; }
    entrances.add(aEntrance);
    wasAdded = true;
    aEntrance.setIsPartOf(this);
    return wasAdded;
  }

  public boolean removeEntrance(Tile aEntrance)
  {
    boolean wasRemoved = false;
    if (entrances.contains(aEntrance))
    {
      entrances.remove(aEntrance);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addEntranceAt(Tile aEntrance, int index)
  {  
    boolean wasAdded = false;
    if(addEntrance(aEntrance))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEntrances()) { index = numberOfEntrances() - 1; }
      entrances.remove(aEntrance);
      entrances.add(index, aEntrance);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveEntranceAt(Tile aEntrance, int index)
  {
    boolean wasAdded = false;
    if(entrances.contains(aEntrance))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEntrances()) { index = numberOfEntrances() - 1; }
      entrances.remove(aEntrance);
      entrances.add(index, aEntrance);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addEntranceAt(aEntrance, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayerReserved()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWeaponReserved()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */

  public void addEmptySpace(Tile t){
    openSquares.add(t);
  }

  public void removeEmptySpace(Tile t){
    openSquares.remove(t);
  }

  public Tile getEmptySpace(){
    Collections.shuffle(openSquares);
    Tile t = openSquares.get(0);
    removeEmptySpace(t);
    return t;
  }
  @Override
  public String toString() {
    return  name;
  }
}