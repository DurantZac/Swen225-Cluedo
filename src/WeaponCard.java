import java.net.URL;

public class WeaponCard implements Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WeaponCard Attributes
  private String weapon;
  private Room location;
  private int index;
  public java.net.URL image;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  /**
   * Constructor, makes a weapon card with a given string
   * @param aWeapon
   */
  public WeaponCard(String aWeapon)
  {
    weapon = aWeapon;
    switch(aWeapon){
      case "Dagger":
        image = getClass().getResource("card_dagger.jpg");
        index = 1;
        break;
      case "Rope":
        image = getClass().getResource("card_rope.jpg");
        index = 2;
        break;
      case "CandleStick":
        image = getClass().getResource("card_candlestick.jpg");
        index = 3;
        break;
      case "Revolver":
        image = getClass().getResource("card_revolver.jpg");
        index = 4;
        break;
      case "Spanner":
        image = getClass().getResource("card_spanner.jpg");
        index = 5;
        break;
      case "Lead Pipe":
        image = getClass().getResource("card_lead.jpg");
        index = 6;
        break;
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  /**
   * @return the location the weapon is in
   */
  public Room getLocation(){
    return location;
  }

  /**
   * Sets the location of the weapon to the room r
   * @param r
   */
  public void setLocation(Room r){
    location = r;
  }

  /**
   * @return the weapon as a string
   */
  public String getWeapon()
  {
    return weapon;
  }

  /**
   * Simple to string returning the name of the weapon
   * @return
   */
  public String toString()
  {
    return weapon;
  }

  public int getIndex(){
    return index;
  }

  @Override
  public URL getImage() {
    return image;
  }
}