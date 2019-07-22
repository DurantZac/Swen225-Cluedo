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
  private WeaponCard Contains;
  private List<Tile> entrances;
  private List<Tile> playerReserved;
  private List<Tile> weaponReserved;

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
    playerReserved = new ArrayList<Tile>();
    weaponReserved = new ArrayList<Tile>();
  }

  public Room()
  {
    roomCard = new RoomCard(this);
    entrances = new ArrayList<Tile>();
    playerReserved = new ArrayList<Tile>();
    weaponReserved = new ArrayList<Tile>();
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
  /* Code from template association_GetMany */
  public Tile getPlayerReserved(int index)
  {
    Tile aPlayerReserved = playerReserved.get(index);
    return aPlayerReserved;
  }

  public List<Tile> getPlayerReserved()
  {
    List<Tile> newPlayerReserved = Collections.unmodifiableList(playerReserved);
    return newPlayerReserved;
  }

  public int numberOfPlayerReserved()
  {
    int number = playerReserved.size();
    return number;
  }

  public boolean hasPlayerReserved()
  {
    boolean has = playerReserved.size() > 0;
    return has;
  }

  public int indexOfPlayerReserved(Tile aPlayerReserved)
  {
    int index = playerReserved.indexOf(aPlayerReserved);
    return index;
  }
  /* Code from template association_GetMany */
  public Tile getWeaponReserved(int index)
  {
    Tile aWeaponReserved = weaponReserved.get(index);
    return aWeaponReserved;
  }

  public List<Tile> getWeaponReserved()
  {
    List<Tile> newWeaponReserved = Collections.unmodifiableList(weaponReserved);
    return newWeaponReserved;
  }

  public int numberOfWeaponReserved()
  {
    int number = weaponReserved.size();
    return number;
  }

  public boolean hasWeaponReserved()
  {
    boolean has = weaponReserved.size() > 0;
    return has;
  }

  public int indexOfWeaponReserved(Tile aWeaponReserved)
  {
    int index = weaponReserved.indexOf(aWeaponReserved);
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
  public boolean addPlayerReserved(Tile aPlayerReserved)
  {
    boolean wasAdded = false;
    if (playerReserved.contains(aPlayerReserved)) { return false; }
    playerReserved.add(aPlayerReserved);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayerReserved(Tile aPlayerReserved)
  {
    boolean wasRemoved = false;
    if (playerReserved.contains(aPlayerReserved))
    {
      playerReserved.remove(aPlayerReserved);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayerReservedAt(Tile aPlayerReserved, int index)
  {  
    boolean wasAdded = false;
    if(addPlayerReserved(aPlayerReserved))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayerReserved()) { index = numberOfPlayerReserved() - 1; }
      playerReserved.remove(aPlayerReserved);
      playerReserved.add(index, aPlayerReserved);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayerReservedAt(Tile aPlayerReserved, int index)
  {
    boolean wasAdded = false;
    if(playerReserved.contains(aPlayerReserved))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayerReserved()) { index = numberOfPlayerReserved() - 1; }
      playerReserved.remove(aPlayerReserved);
      playerReserved.add(index, aPlayerReserved);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayerReservedAt(aPlayerReserved, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWeaponReserved()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addWeaponReserved(Tile aWeaponReserved)
  {
    boolean wasAdded = false;
    if (weaponReserved.contains(aWeaponReserved)) { return false; }
    weaponReserved.add(aWeaponReserved);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWeaponReserved(Tile aWeaponReserved)
  {
    boolean wasRemoved = false;
    if (weaponReserved.contains(aWeaponReserved))
    {
      weaponReserved.remove(aWeaponReserved);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWeaponReservedAt(Tile aWeaponReserved, int index)
  {  
    boolean wasAdded = false;
    if(addWeaponReserved(aWeaponReserved))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWeaponReserved()) { index = numberOfWeaponReserved() - 1; }
      weaponReserved.remove(aWeaponReserved);
      weaponReserved.add(index, aWeaponReserved);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWeaponReservedAt(Tile aWeaponReserved, int index)
  {
    boolean wasAdded = false;
    if(weaponReserved.contains(aWeaponReserved))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWeaponReserved()) { index = numberOfWeaponReserved() - 1; }
      weaponReserved.remove(aWeaponReserved);
      weaponReserved.add(index, aWeaponReserved);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWeaponReservedAt(aWeaponReserved, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    RoomCard existingRoomCard = roomCard;
    roomCard = null;
    if (existingRoomCard != null)
    {
      existingRoomCard.delete();
    }
    Contains = null;
    entrances.clear();
    playerReserved.clear();
    weaponReserved.clear();
  }

}