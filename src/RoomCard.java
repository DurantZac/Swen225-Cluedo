import java.net.URL;

public class RoomCard implements Card
{
  private Room room;
  public java.net.URL image;

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
        image = getClass().getResource("card_entertainment.jpg");
        break;
      case "Study":
        image = getClass().getResource("card_study.jpg");
        break;
      case "Lounge":
        image = getClass().getResource("card_lounge.jpg");
        break;
      case "Kitchen":
        image = getClass().getResource("card_kitchen.jpg");
        break;
      case "Conservatory":
        image = getClass().getResource("card_conservatory.jpg");
        break;
      case "Dining Room":
        image = getClass().getResource("card_dining.jpg");
        break;
      case "Hall":
        image = getClass().getResource("card_hall.jpg");
        break;
      case "Auditorium":
        image = getClass().getResource("card_auditorium.jpg");
        break;
      case "Book Room":
        image = getClass().getResource("card_book.jpg");
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
    return room.toString();
  }

  @Override
  public URL getImage() {
    return image;
  }
}