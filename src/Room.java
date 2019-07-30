import java.util.*;

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

  /**
   * Constructor, makes a new room card, and sets the name
   * @param n the name of the room
   */
  public Room(String n)
  {
    roomCard = new RoomCard(this);
    entrances = new ArrayList<Tile>();
    name  = n;
  }

  //------------------------
  // INTERFACE
  //------------------------
  /***
   * @return the associated room card
   */
  public RoomCard getRoomCard()
  {
    return roomCard;
  }

  /**
   * @return a list of all the Entrances
   */
  public List<Tile> getEntrances()
  {
    List<Tile> newEntrances = Collections.unmodifiableList(entrances);
    return newEntrances;
  }

  /**
   * @return the number of entrances a room has
   */
  public int numberOfEntrances()
  {
    int number = entrances.size();
    return number;
  }

  /**
   * Adds a given tile as an entrance
   * @param aEntrance the Tile to mark as an entrance
   * @return if adding was successful
   */
  public boolean addEntrance(Tile aEntrance)
  {
    boolean wasAdded = false;
    if (entrances.contains(aEntrance)) { return false; }
    entrances.add(aEntrance);
    wasAdded = true;
    aEntrance.setIsPartOf(this);
    return wasAdded;
  }

  /**
   * Adds an empty space to a room
   * @param t the empty tile to add
   */
  public void addEmptySpace(Tile t){
    openSquares.add(t);
  }
  /**
   * Removes an empty space from a room
   * @param t the empty tile to remove
   */
  public void removeEmptySpace(Tile t){
    openSquares.remove(t);
  }

  /**
   * Gets any random empty space in the room and removes this room from the list
   */
  public Tile getEmptySpace(){
    Collections.shuffle(openSquares);
    Tile t = openSquares.get(0);
    removeEmptySpace(t);
    return t;
  }

  /**
   * Simple toString
   * @return the name of the room
   */
  @Override
  public String toString() {
    return  name;
  }
}