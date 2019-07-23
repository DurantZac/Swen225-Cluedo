/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4584.3d417815a modeling language!*/



// line 58 "model.ump"
// line 115 "model.ump"
public class Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private CharacterCard character;
  private boolean isStillPlaying;
  private Tile position;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(CharacterCard aCharacter)
  {
    character = aCharacter;
    isStillPlaying = true;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCharacter(CharacterCard aCharacter)
  {
    boolean wasSet = false;
    character = aCharacter;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsStillPlaying(boolean aIsStillPlaying)
  {
    boolean wasSet = false;
    isStillPlaying = aIsStillPlaying;
    wasSet = true;
    return wasSet;
  }

  public CharacterCard getCharacter()
  {
    return character;
  }

  public boolean getIsStillPlaying()
  {
    return isStillPlaying;
  }

  public void delete()
  {}

  public Tile getPosition(){
    return position;
  }

  // line 63 "model.ump"
   public Card refute(RoomCard r, CharacterCard c, WeaponCard w){
    return null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "isStillPlaying" + ":" + getIsStillPlaying()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "character" + "=" + (getCharacter() != null ? !getCharacter().equals(this)  ? getCharacter().toString().replaceAll("  ","    ") : "this" : "null");
  }
}