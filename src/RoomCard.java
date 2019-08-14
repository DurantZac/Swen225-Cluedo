import java.net.URL;

public class RoomCard implements Card
{
  private Room room;
  private int index;
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
        index = 7;
        break;
      case "Study":
        image = getClass().getResource("card_study.jpg");
        index = 5;
        break;
      case "Lounge":
        image = getClass().getResource("card_lounge.jpg");
        index = 3;
        break;
      case "Kitchen":
        image = getClass().getResource("card_kitchen.jpg");
        index = 1;
        break;
      case "Conservatory":
        image = getClass().getResource("card_conservatory.jpg");
        index = 8;
        break;
      case "Dining Room":
        image = getClass().getResource("card_dining.jpg");
        index = 2;
        break;
      case "Hall":
        image = getClass().getResource("card_hall.jpg");
        index = 4;
        break;
      case "Auditorium":
        image = getClass().getResource("card_auditorium.jpg");
        index = 9;
        break;
      case "Book Room":
        image = getClass().getResource("card_book.jpg");
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

  @Override
  public URL getImage() {
    return image;
  }
}