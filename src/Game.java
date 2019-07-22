import java.util.*;

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
    String s="|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|#|#|^|^|#|#|_|_|_|_|_|_|_|_|_|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|_|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|_|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|#|_|_|_|_|>|_|_|^|#|#|#|#|#|#|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|#|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|#|H|_|_|_|#|_|_|#|_|_|_|_|S|#|"  + "\n" +
            "|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|_|_|#|#|#|#|#|#|#|"  + "\n";
     // System.out.println(s);
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
  public Player getStore(int index)
  {
    Player aStore = players.get(index);
    return aStore;
  }

  public List<Player> getStores()
  {
    List<Player> newStores = Collections.unmodifiableList(players);
    return newStores;
  }

  public int numberOfStores()
  {
    int number = players.size();
    return number;
  }

  public boolean hasStores()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfStore(Player aStore)
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
  public boolean addStore(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    if (numberOfStores() < maximumNumberOfStores())
    {
      players.add(aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean removeStore(Player aPlayer)
  {
    boolean wasRemoved = false;
    if (!players.contains(aPlayer))
    {
      return wasRemoved;
    }

    if (numberOfStores() <= minimumNumberOfStores())
    {
      return wasRemoved;
    }

    players.remove(aPlayer);
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
  public boolean addStoreAt(Player aPlayer, int index)
  {  
    boolean wasAdded = false;
    if(addStore(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStores()) { index = numberOfStores() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveStoreAt(Player aPlayer, int index)
  {
    boolean wasAdded = false;
    if(players.contains(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStores()) { index = numberOfStores() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addStoreAt(aPlayer, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Board existingContains = board;
    board = null;
    if (existingContains != null)
    {
      existingContains.delete();
    }
    murderScenario.clear();
    players.clear();
  }

  /**
   * Creates 2 random number generators and "rolls" the dice. Returns the result.
   * @return result of dice roll
   */
  private int rollDice(){
     Random dice1 = new Random();
     Random dice2 = new Random();

     int roll1 = dice1.nextInt(6); // random number from 0-5
     int roll2 = dice2.nextInt(6);

     roll1+=1; // add 1 to get range 1-6.
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


