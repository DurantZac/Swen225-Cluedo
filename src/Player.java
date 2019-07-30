
import java.util.HashSet;
import java.util.Set;

public class Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  private CharacterCard character;
  private boolean isStillPlaying;
  private Set <Card> hand = new HashSet<>();

  //------------------------
  // CONSTRUCTOR
  //------------------------

  /**
   * Creates a player, assigning a character to them.
   * @param aCharacter
   */
  public Player(CharacterCard aCharacter)
  {
    character = aCharacter;
    isStillPlaying = true;
  }

  //------------------------
  // INTERFACE
  //------------------------


  /**
   * Changes the IsStillPlaying field,
   * this is used when accusations are wrong to remove a player from the game loop
   * @param aIsStillPlaying
   * @return if setting was successful
   */
  public boolean setIsStillPlaying(boolean aIsStillPlaying)
  {
    boolean wasSet;
    isStillPlaying = aIsStillPlaying;
    wasSet = true;
    return wasSet;
  }

  /**
   * @return the card the player is playing as
   */
  public CharacterCard getCharacter()
  {
    return character;
  }

  /**
   * Checks if the player is still part of the game loop
   * @return
   */
  public boolean getIsStillPlaying()
  {
    return isStillPlaying;
  }

  /**
   * @return the current position of the player
   */
  public Tile getPosition(){
    return character.getPosition();
  }


  /**
   * @param c card to add to the hand when cards are dealt
   */
  public void addCardToHand(Card c){
    hand.add(c);
  }

  /**
   * Formatted string, showing what cards a player has in their hand
   * @return the finished string
   */
  public String returnHand(){
    String s="You have "+ hand.size()+" cards, they are: ";
    boolean first=true;

    for (Card c: hand){
      if (!first)
        s+=", ";
      s+=c.toString();
      first=false;
    }
    return s;
  }

  /**
   * @return the hand as a set which can be iterated through
   */
  public Set<Card> getHand(){
    return hand;
  }

  /**
   * ToString used for debugging purposes
   * @return all characteristics of the player
   */
  public String toString()
  {
    return super.toString() + "["+
            "isStillPlaying" + ":" + getIsStillPlaying()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "character" + "=" + (getCharacter() != null ? !getCharacter().equals(this)  ? getCharacter().toString().replaceAll("  ","    ") : "this" : "null");
  }
}