/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4584.3d417815a modeling language!*/


import java.util.Objects;

// line 72 "model.ump"
// line 125 "model.ump"
public class CharacterCard implements Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CharacterCard Attributes
  private String character;
  private char characterSymbol;
  private Tile startPos;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CharacterCard(String aCharacter,Tile t)
  {
    character = aCharacter;
    startPos = t;

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

  public boolean setCharacter(String aCharacter)
  {
    boolean wasSet = false;
    character = aCharacter;
    wasSet = true;
    return wasSet;
  }

  public String getCharacter()
  {
    return character;
  }

  public char getCharacterSymbol(){return characterSymbol;}

  public void delete()
  {}


  public String toString()
  {
    return getCharacter();
  }

  public Tile getStartPos(){
    return startPos;
  }


}