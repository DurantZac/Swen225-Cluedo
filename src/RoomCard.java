public class RoomCard implements Card
{
  private Room room;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  /**
   * Constructor making a new card and assigning the room to it
   * @param aRoom the room associated with this card
   */
  public RoomCard(Room aRoom)
  {
    if (aRoom == null || aRoom.getRoomCard() != null)
    {
      throw new RuntimeException("Unable to create RoomCard due to aRoom. ");
    }
    room = aRoom;
  }

  //------------------------
  // INTERFACE
  //------------------------

  /**
   * @return the room associated with this card
   */
  public Room getRoom()
  {
    return room;
  }


  /**
   * Simple to string
   * @return the to string of its room 
   */
  @Override
  public String toString() {
    return room.toString();}
}