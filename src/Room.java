import java.util.*;

public class Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Room Associations
  private RoomCard roomCard;
  private String name;
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
    name  = n;
    roomCard = new RoomCard(this);
    entrances = new ArrayList<>();
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
   * Adds a given tile as an entrance
   * @param aEntrance the Tile to mark as an entrance
   */
  public void addEntrance(Tile aEntrance)
  {
    if (entrances.contains(aEntrance)) { return; }
    entrances.add(aEntrance);
    aEntrance.setIsPartOf(this);
    List<Tile> removeAdajcent = new ArrayList<>();
    for(Tile t : aEntrance.getAdjacent()){
      if(!aEntrance.getIsAccessibleFull(t)){
        removeAdajcent.add(t);
      }
    }
    aEntrance.removeAdjacent(removeAdajcent);
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