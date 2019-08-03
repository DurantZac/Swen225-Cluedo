public class WeaponCard implements Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WeaponCard Attributes
  private String weapon;
  private Room location;
  private int index;

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
        index = 1;
        break;
      case "Rope":
        index = 2;
        break;
      case "CandleStick":
        index = 3;
        break;
      case "Revolver":
        index = 4;
        break;
      case "Spanner":
        index = 5;
        break;
      case "Lead Pipe":
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
}