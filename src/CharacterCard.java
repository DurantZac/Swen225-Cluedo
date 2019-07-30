public class CharacterCard implements Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  private String character;
  private char characterSymbol;
  private Tile position;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  /**
   * The constructor, takes a string of a character and converts it to a symbol
   * also stores the position on the board
   *
   * @param aCharacter the character that this card represents
   * @param t the position of the character on this card
   */
  public CharacterCard(String aCharacter,Tile t)
  {
    character = aCharacter;
    position = t;

    // Finds the character and sets the symbol
    switch (character){
      case "Mrs White":
        characterSymbol = 'W';
        break;
      case "Rev. Green":
        characterSymbol = 'G';
        break;
      case "Col. Mustard":
        characterSymbol = 'M';
        break;
      case "Miss Red":
        characterSymbol = 'R';
        break;
      case "Prof. Plum":
        characterSymbol = 'P';
        break;
      case "Ms Turquoise":
        characterSymbol = 'T';
        break;
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  /**
   * @return the character this card represents
   */
  public String getCharacter()
  {
    return character;
  }

  /**
   * @return the symbol used to represent the character
   */
  public char getCharacterSymbol(){return characterSymbol;}

  /**
   * Sets the position of this character to tile t and also adds this character to that tile (2 way adding)
   * @param t
   */
  public void setPosition(Tile t){
    position = t;
    t.addCharacter(this);
  }


  /**
   * Simple toString
   * @return the string of the character
   */
  public String toString()
  {
    return getCharacter();
  }

  /**
   * @return the current position of this character
   */
  public Tile getPosition(){
    return position;
  }


}