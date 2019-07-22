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
  private Board contains;
  private List<Card> murderScenario;
  private List<Player> stores;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(Board aContains, Card[] allMurderScenario, Player[] allStores)
  {
    if (aContains == null || aContains.getGame() != null)
    {
      throw new RuntimeException("Unable to create Game due to aContains. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    contains = aContains;
    murderScenario = new ArrayList<Card>();
    boolean didAddMurderScenario = setMurderScenario(allMurderScenario);
    if (!didAddMurderScenario)
    {
      throw new RuntimeException("Unable to create Game, must have 3 murderScenario. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    stores = new ArrayList<Player>();
    boolean didAddStores = setStores(allStores);
    if (!didAddStores)
    {
      throw new RuntimeException("Unable to create Game, must have 3 to 6 stores. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Game(Tile[] allBoardTilesForContains, Card[] allMurderScenario, Player[] allStores)
  {
    contains = new Board(this, allBoardTilesForContains);
    murderScenario = new ArrayList<Card>();
    stores = new ArrayList<Player>();
  }

  public Game() {

  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Board getContains()
  {
    return contains;
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
    Player aStore = stores.get(index);
    return aStore;
  }

  public List<Player> getStores()
  {
    List<Player> newStores = Collections.unmodifiableList(stores);
    return newStores;
  }

  public int numberOfStores()
  {
    int number = stores.size();
    return number;
  }

  public boolean hasStores()
  {
    boolean has = stores.size() > 0;
    return has;
  }

  public int indexOfStore(Player aStore)
  {
    int index = stores.indexOf(aStore);
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
    if (stores.contains(aStore)) { return false; }
    if (numberOfStores() < maximumNumberOfStores())
    {
      stores.add(aStore);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean removeStore(Player aStore)
  {
    boolean wasRemoved = false;
    if (!stores.contains(aStore))
    {
      return wasRemoved;
    }

    if (numberOfStores() <= minimumNumberOfStores())
    {
      return wasRemoved;
    }

    stores.remove(aStore);
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

    stores.clear();
    stores.addAll(verifiedStores);
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
      if(index > numberOfStores()) { index = numberOfStores() - 1; }
      stores.remove(aStore);
      stores.add(index, aStore);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveStoreAt(Player aStore, int index)
  {
    boolean wasAdded = false;
    if(stores.contains(aStore))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStores()) { index = numberOfStores() - 1; }
      stores.remove(aStore);
      stores.add(index, aStore);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addStoreAt(aStore, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Board existingContains = contains;
    contains = null;
    if (existingContains != null)
    {
      existingContains.delete();
    }
    murderScenario.clear();
    stores.clear();
  }

  // line 8 "model.ump"
   private int RollDice(){
    return 0;
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


