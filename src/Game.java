/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4584.3d417815a modeling language!*/


import java.util.*;
// line 2 "model.ump"
// line 96 "model.ump"
public class Game
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Associations
  private Board board;
  private List<Card> murderScenario;
  private List<Player> players;

  //------------------------
  // CONSTRUCTOR
  //------------------------


  public Game() {
      String gameBoard = "|X|X|X|X|X|X|X|X|X|W|#|#|#|#|G|X|X|X|X|X|X|X|X|X|"  + "\n" +
            "|#|#|#|#|#|#|X|_|_|_|#|_|_|#|_|_|_|X|#|#|#|#|#|#|"  + "\n" +
            "|#|K|_|_|_|#|_|_|#|#|#|_|_|#|#|#|_|_|#|_|_|_|C|#|"  + "\n" +
            "|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|#|_|_|_|_|#|"  + "\n" +
            "|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|v|#|_|_|_|#|"  + "\n" +
            "|#|#|_|_|_|#|_|_|<|_|_|_|_|_|_|>|_|_|_|#|#|#|#|X|"  + "\n" +
            "|X|#|#|#|v|#|_|_|#|_|_|_|_|_|_|#|_|_|_|_|_|_|_|T|"  + "\n" +
            "|_|_|_|_|_|_|_|_|#|v|#|#|#|#|v|#|_|_|_|_|_|_|_|X|"  + "\n" +
            "|X|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|"  + "\n" +
            "|#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|<|_|_|_|E|#|"  + "\n" +
            "|#|_|_|_|#|#|#|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|"  + "\n" +
            "|#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|"  + "\n" +
            "|#|_|_|_|_|_|_|>|_|_|X|X|X|X|X|_|_|_|#|#|#|#|^|#|"  + "\n" +
            "|#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|_|_|_|_|_|X|"  + "\n" +
            "|#|D|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|#|^|#|#|X|"  + "\n" +
            "|#|#|#|#|#|#|v|#|_|_|X|X|X|X|X|_|_|#|#|_|_|_|B|#|"  + "\n" +
            "|X|_|_|_|_|_|_|_|_|_|X|X|X|X|X|_|_|<|_|_|_|_|_|#|"  + "\n" +
            "|M|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|_|_|_|#|#|"  + "\n" +
            "|X|_|_|_|_|_|_|_|_|#|#|^|^|#|#|_|_|_|#|#|#|#|#|X|"  + "\n" +
            "|#|#|#|#|#|#|^|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|P|"  + "\n" +
            "|#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|X|"  + "\n" +
            "|#|_|_|_|_|_|#|_|_|#|_|_|_|_|>|_|_|^|#|#|#|#|#|#|"  + "\n" +
            "|#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|#|"  + "\n" +
            "|#|L|_|_|_|_|#|_|_|#|H|_|_|_|#|_|_|#|_|_|_|_|S|#|"  + "\n" +
            "|#|#|#|#|#|#|#|R|X|#|#|#|#|#|#|X|_|#|#|#|#|#|#|#|"  + "\n";
      List<Tile> tiles = new ArrayList<>();
      String[] lines = gameBoard.split("\n");
      for (String l : lines){
          String[] values = l.split("|");
          for(int i = 0; i < values.length; i++ ){
              if(i % 2 != 0){
                  Tile t = new Tile(values[i].charAt(0));
                  tiles.add(t);
              }
          }
      }

      Board b = new Board(this, tiles);

  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_GetMany */
  public Card getMurderScenario(int index)
  {
    Card aMurderScenario = murderScenario.get(index);
    return aMurderScenario;
  }

  public List<Card> getMurderScenario()
  {
    List<Card> newMurderScenario = Collections.unmodifiableList(murderScenario);
    return newMurderScenario;
  }

  public int numberOfMurderScenario()
  {
    int number = murderScenario.size();
    return number;
  }

  public boolean hasMurderScenario()
  {
    boolean has = murderScenario.size() > 0;
    return has;
  }

  public int indexOfMurderScenario(Card aMurderScenario)
  {
    int index = murderScenario.indexOf(aMurderScenario);
    return index;
  }
  /* Code from template association_GetMany */
  public Player getPlayer(int index)
  {
    Player aStore = players.get(index);
    return aStore;
  }

  public List<Player> getPlayers()
  {
    List<Player> newStores = Collections.unmodifiableList(players);
    return newStores;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aStore)
  {
    int index = players.indexOf(aStore);
    return index;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfMurderScenario()
  {
    return 3;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMurderScenario()
  {
    return 3;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfMurderScenario()
  {
    return 3;
  }
  /* Code from template association_SetUnidirectionalN */
  public boolean setMurderScenario(Card... newMurderScenario)
  {
    boolean wasSet = false;
    ArrayList<Card> verifiedMurderScenario = new ArrayList<Card>();
    for (Card aMurderScenario : newMurderScenario)
    {
      if (verifiedMurderScenario.contains(aMurderScenario))
      {
        continue;
      }
      verifiedMurderScenario.add(aMurderScenario);
    }

    if (verifiedMurderScenario.size() != newMurderScenario.length || verifiedMurderScenario.size() != requiredNumberOfMurderScenario())
    {
      return wasSet;
    }

    murderScenario.clear();
    murderScenario.addAll(verifiedMurderScenario);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfStores()
  {
    return 3;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfStores()
  {
    return 6;
  }
  /* Code from template association_AddUnidirectionalMN */
  public boolean addStore(Player aStore)
  {
    boolean wasAdded = false;
    if (players.contains(aStore)) { return false; }
    if (numberOfPlayers() < maximumNumberOfStores())
    {
      players.add(aStore);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean removeStore(Player aStore)
  {
    boolean wasRemoved = false;
    if (!players.contains(aStore))
    {
      return wasRemoved;
    }

    if (numberOfPlayers() <= minimumNumberOfStores())
    {
      return wasRemoved;
    }

    players.remove(aStore);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalMN */
  public boolean setStores(Player... newStores)
  {
    boolean wasSet = false;
    ArrayList<Player> verifiedStores = new ArrayList<Player>();
    for (Player aStore : newStores)
    {
      if (verifiedStores.contains(aStore))
      {
        continue;
      }
      verifiedStores.add(aStore);
    }

    if (verifiedStores.size() != newStores.length || verifiedStores.size() < minimumNumberOfStores() || verifiedStores.size() > maximumNumberOfStores())
    {
      return wasSet;
    }

    players.clear();
    players.addAll(verifiedStores);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addStoreAt(Player aStore, int index)
  {  
    boolean wasAdded = false;
    if(addStore(aStore))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aStore);
      players.add(index, aStore);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveStoreAt(Player aStore, int index)
  {
    boolean wasAdded = false;
    if(players.contains(aStore))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aStore);
      players.add(index, aStore);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addStoreAt(aStore, index);
    }
    return wasAdded;
  }


  /**
   * Creates two "dice" which pick a random number from 1-6
   * @return sum of roll
   */
   private int RollDice(){
     Random dice1 = new Random();
     Random dice2 = new Random();

     int roll1 = dice1.nextInt(6); //random int from 0-5
     int roll2 = dice2.nextInt(6);

     roll1+=1; // add 1 to make interval 1-6
     roll2+=1;

     return roll1+roll2;
   }

  // line 9 "model.ump"
   private void playGame(){
    
  }

  // line 10 "model.ump"
   private void dealCards(){
    
  }

  // line 11 "model.ump"
   private void checkSuggestion(){
    
  }

  // line 12 "model.ump"
   private boolean checkAccusation(){
    return false;
  }

  public static void main(String args[]){
    new Game();
  }
}


