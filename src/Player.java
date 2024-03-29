
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
  String notes = "";
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
   */
  public void setIsStillPlaying(boolean aIsStillPlaying)
  {
    isStillPlaying = aIsStillPlaying;
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

  /**
   * @return The current notes the player has made.
   */
  public String getNotes() {
    return notes;
  }

  /**
   * Add a note to the current notes
   * Appends a new line, plus the new note
   * @param notes the note to append
   */
  public void setNotes(String notes) {
    StringBuilder sb = new StringBuilder();
    sb.append(this.notes);
    sb.append("<br/>");
    sb.append(notes);

    this.notes = sb.toString();
  }
}