public class RoomCard implements Card
{
  private Room room;
  private int index;

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
    switch(aRoom.toString()){
      case "Entertainment Room":
        index = 7;
        break;
      case "Study":
        index = 5;
        break;
      case "Lounge":
        index = 3;
        break;
      case "Kitchen":
        index = 1;
        break;
      case "Conservatory":
        index = 8;
        break;
      case "Dining Room":
        index = 2;
        break;
      case "Hall":
        index = 4;
        break;
      case "Auditorium":
        index = 9;
        break;
      case "Book Room":
        index = 6;
        break;
    }
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

  public int getIndex(){
    return index;
  }
}