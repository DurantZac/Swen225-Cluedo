/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4584.3d417815a modeling language!*/



// line 67 "model.ump"
// line 120 "model.ump"
public class RoomCard implements Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RoomCard Associations
  private Room room;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RoomCard(Room aRoom)
  {
    if (aRoom == null || aRoom.getRoomCard() != null)
    {
      throw new RuntimeException("Unable to create RoomCard due to aRoom. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    room = aRoom;
  }

  public RoomCard()
  {
    room = new Room(this);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Room getRoom()
  {
    return room;
  }

  public void delete()
  {
    Room existingRoom = room;
    room = null;
    if (existingRoom != null)
    {
      existingRoom.delete();
    }
  }

  @Override
  public String toString() {
    return room.toString();}
}