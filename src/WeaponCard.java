/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4584.3d417815a modeling language!*/



// line 84 "model.ump"
// line 130 "model.ump"
public class WeaponCard implements Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WeaponCard Attributes
  private String weapon;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WeaponCard(String aWeapon)
  {
    weapon = aWeapon;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setWeapon(String aWeapon)
  {
    boolean wasSet = false;
    weapon = aWeapon;
    wasSet = true;
    return wasSet;
  }

  public String getWeapon()
  {
    return weapon;
  }

  public void delete()
  {}


  public String toString()
  {
    return getWeapon();
  }
}