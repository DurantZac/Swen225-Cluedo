import java.net.URL;

public class WeaponCard implements Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WeaponCard Attributes
  private String weapon;
  public Tile location;
  public java.net.URL image;
  public java.net.URL icon;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  /**
   * Constructor, makes a weapon card with a given string
   * Adds the image for the room and the card
   * @param aWeapon the weapon as a string
   */
  public WeaponCard(String aWeapon)
  {
    weapon = aWeapon;
    switch(aWeapon){
      case "Dagger":
        image = getClass().getResource("card_dagger.jpg");
        icon = getClass().getResource("dagger_Room.jpg");
        break;
      case "Rope":
        image = getClass().getResource("card_rope.jpg");
        icon = getClass().getResource("rope_Room.jpg");
        break;
      case "CandleStick":
        image = getClass().getResource("card_candlestick.jpg");
        icon = getClass().getResource("candlestick_Room.jpg");
        break;
      case "Revolver":
        image = getClass().getResource("card_revolver.jpg");
        icon = getClass().getResource("revolver_Room.jpg");
        break;
      case "Spanner":
        image = getClass().getResource("card_spanner.jpg");
        icon = getClass().getResource("spanner_Room.jpg");
        break;
      case "Lead Pipe":
        image = getClass().getResource("card_lead.jpg");
        icon = getClass().getResource("lead_Room.jpg");
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
    return location.getIsPartOf();
  }

  /**
   * Sets the location of the weapon to the room r
   * @param t
   */
  public void setLocation(Tile t){
    if(location != null)location.removeWeapon();
    location = t;
    t.setWeapon(this);
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

  @Override
  public URL getImage() {
    return image;
  }
}