/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4584.3d417815a modeling language!*/


import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
// line 2 "model.ump"
// line 96 "model.ump"
public class Game
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Associations
  private Board board;
  private List<Card> murderScenario = new ArrayList<>();
  private List<Player> players = new ArrayList<>();
  private List<Room> rooms = new ArrayList<>();
  private int playerNum;

  private Map <String, WeaponCard> weaponMap = new HashMap<>();
  private Map <String, CharacterCard> characterMap = new HashMap<>();
  private Map <String, RoomCard> roomMap = new HashMap<>();


  //------------------------
  // CONSTRUCTOR
  //------------------------

  /**
   * Create new game by initialising board,
   */
  public Game() {
    board = createBoard();
    while (true) { //Try to find out how many players there are
      try {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("How many players are playing? (3-6) ");
        int numberOfPlayers = Integer.parseInt(input.readLine());
        if(numberOfPlayers < minimumNumberOfPlayers() || numberOfPlayers > maximumNumberOfPlayers()){ throw new IncorrectNumberOfPlayersException(); }
        playerNum=numberOfPlayers;
        break;
      } catch (NumberFormatException n) {
        System.out.println("Please enter a number between 3-6 only");
      }
      catch (IOException e){
        System.out.println("Error on input, please try again" + e);
      }
      catch (IncorrectNumberOfPlayersException i){
        System.out.println("Please enter a number between 3-6 only");
      }
    }

    List<CharacterCard> unusedCharacters= new ArrayList<>();


    //Make all the characters
    unusedCharacters.add(new CharacterCard("Col. Mustard",board.getBoardTile("Ar")));
    unusedCharacters.add(new CharacterCard("Mrs White",board.getBoardTile("Ja")));
    unusedCharacters.add(new CharacterCard("Rev. Green",board.getBoardTile("Oa")));
    unusedCharacters.add(new CharacterCard("Prof. Plum",board.getBoardTile("Xt")));
    unusedCharacters.add(new CharacterCard("Ms Turquoise",board.getBoardTile("Xg")));
    unusedCharacters.add(new CharacterCard("Miss Red", board.getBoardTile("Hy")));

    List<Card> cardsToBeDealt = createCards(unusedCharacters);

    unusedCharacters.stream().forEach(j -> characterMap.put(j.toString().toLowerCase(),j));

    for (int i = 0; i < playerNum; i++){
      System.out.println("Player "+ (i+1) + ". Please select your character");
      System.out.println("The available players are:");
      unusedCharacters.stream().forEach(j -> System.out.println(j.toString()));
      System.out.println("\nWhat player would you like to be?");

      validityCheck: while (true) { //Check who they want to be
        try {
          BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
          String characterToPlay = input.readLine();

          for (CharacterCard c: unusedCharacters) {
            if (c.getCharacter().equalsIgnoreCase(characterToPlay)) {

              Player p = new Player(c);
              players.add(p);
              unusedCharacters.remove(c);

              System.out.println("You have selected the character: " + c + "\n");
              break validityCheck;
            }
          }
          throw new InvalidCharacterException();
        }
        catch (IOException e){
          System.out.println("Error on input, please try again" + e);
        }
        catch (InvalidCharacterException c){
          System.out.println("Please enter a valid character from the list");
        }
      }
    }

    //Deals hand
    dealCards(cardsToBeDealt);


//    getMurderScenario().stream().forEach(j -> System.out.println(j.toString()));
//    System.out.println();
//    System.out.println();
//
//    for (Player p : players){
//      System.out.println(p.returnHand());
//    }

    //Game play begins
    playGame();


    //close buffer
  }

  /** Generate board from string
   * @return boadrd
   */
  public Board createBoard(){

    String gameBoard = "|X|X|X|X|X|X|X|X|X|W|#|#|#|#|G|X|X|X|X|X|X|X|X|X|"  + "\n" +
            "|#|#|#|#|#|#|X|_|_|_|#|_|_|#|_|_|_|X|#|#|#|#|#|#|"  + "\n" +
            "|K|_|_|_|_|#|_|_|#|#|A|_|_|#|#|#|_|_|#|_|_|_|_|C|"  + "\n" +
            "|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|#|_|_|_|_|#|"  + "\n" +
            "|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|v|_|_|_|_|#|"  + "\n" +
            "|#|_|_|_|_|#|_|_|<|_|_|_|_|_|_|>|_|_|_|#|#|#|#|X|"  + "\n" +
            "|X|#|#|#|v|#|_|_|#|_|_|_|_|_|_|#|_|_|_|_|_|_|_|T|"  + "\n" +
            "|_|_|_|_|_|_|_|_|#|v|#|#|#|#|v|#|_|_|_|_|_|_|_|X|"  + "\n" +
            "|X|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|"  + "\n" +
            "|#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|<|_|_|_|_|E|"  + "\n" +
            "|#|_|_|_|#|#|#|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|"  + "\n" +
            "|#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|"  + "\n" +
            "|D|_|_|_|_|_|_|>|_|_|X|X|X|X|X|_|_|_|#|#|#|#|^|#|"  + "\n" +
            "|#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|_|_|_|_|_|X|"  + "\n" +
            "|#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|#|^|#|#|X|"  + "\n" +
            "|#|#|#|#|#|#|v|#|_|_|X|X|X|X|X|_|_|#|#|_|_|_|_|B|"  + "\n" +
            "|X|_|_|_|_|_|_|_|_|_|X|X|X|X|X|_|_|<|_|_|_|_|_|#|"  + "\n" +
            "|M|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|_|_|_|_|#|"  + "\n" +
            "|X|_|_|_|_|_|_|_|_|#|#|^|^|#|#|_|_|_|#|#|#|#|#|X|"  + "\n" +
            "|#|#|#|#|#|#|^|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|P|"  + "\n" +
            "|#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|X|"  + "\n" +
            "|L|_|_|_|_|_|#|_|_|#|_|_|_|_|>|_|_|^|S|#|#|#|#|#|"  + "\n" +
            "|#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|"  + "\n" +
            "|#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|"  + "\n" +
            "|#|#|#|#|#|#|#|R|X|#|#|H|#|#|#|X|_|#|#|#|#|#|#|#|"  + "\n";

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

    return new Board(this, tiles);
  }

  /**
   * Create character cards, room cards and weapon cards
   * Also generates rooms, marks entrances and chooses murder scenario
   * @return List of all cards after murder scenario removed
   */
  public List<Card> createCards(List <CharacterCard> characters){
    List<Card> allCards = new ArrayList<>();
    // Characters
      allCards.addAll(characters);
    Collections.shuffle(allCards);
    murderScenario.add(allCards.get(0));
    allCards.remove(0);

    //Rooms
    allCards.addAll(createRooms());

    //Weapons
    List<Card> weapons = new ArrayList<>();
    weapons.add(new WeaponCard("Dagger"));
    weapons.add(new WeaponCard("Rope"));
    weapons.add(new WeaponCard("CandleStick"));
    weapons.add(new WeaponCard("Revolver"));
    weapons.add(new WeaponCard("Spanner"));
    weapons.add(new WeaponCard("Lead Pipe"));

    weapons.stream().forEach(j -> weaponMap.put(j.toString().toLowerCase(),(WeaponCard)j)); // add all weapons to a map

    Collections.shuffle(rooms);
    for(int i = 0; i < weapons.size(); i++){
      WeaponCard w = (WeaponCard)(weapons.get(i));
      w.setLocation(rooms.get(i));
    }

    Collections.shuffle(weapons);
    murderScenario.add(weapons.get(0));
    weapons.remove(weapons.get(0));
    allCards.addAll(weapons);

    return allCards;
  }

  /**
   * Create rooms and add entrances
   * @return
   */
  public List<RoomCard> createRooms(){
    List<RoomCard> cards = new ArrayList<>();
    Room kitchen = new Room("Kitchen");
    kitchen.addEntrance(board.getBoardTile("Eg"));

    Room dining = new Room("Dining Room");
    dining.addEntrance(board.getBoardTile("Gp"));
    dining.addEntrance(board.getBoardTile("Hm"));

    Room lounge = new Room("Lounge");
    lounge.addEntrance(board.getBoardTile("Gt"));

    Room hall = new Room("Hall");
    hall.addEntrance(board.getBoardTile("Ls"));
    hall.addEntrance(board.getBoardTile("Ms"));
    hall.addEntrance(board.getBoardTile("Ov"));

    Room study = new Room("Study");
    study.addEntrance(board.getBoardTile("Rv"));

    Room bookRoom = new Room("Book Room");
    bookRoom.addEntrance(board.getBoardTile("Rq"));
    bookRoom.addEntrance(board.getBoardTile("Uo"));

    Room entertainmentRoom = new Room("Entertainment Room");
    entertainmentRoom.addEntrance(board.getBoardTile("Wm"));
    entertainmentRoom.addEntrance(board.getBoardTile("Sj"));

    Room cons = new Room("Conservatory");
    cons.addEntrance(board.getBoardTile("Se"));

    Room auditorium = new Room("Auditorium");
    auditorium.addEntrance(board.getBoardTile("Jh"));
    auditorium.addEntrance(board.getBoardTile("Oh"));

    cards.add(kitchen.getRoomCard());
    cards.add(dining.getRoomCard());
    cards.add(lounge.getRoomCard());
    cards.add(hall.getRoomCard());
    cards.add(study.getRoomCard());
    cards.add(bookRoom.getRoomCard());
    cards.add(entertainmentRoom.getRoomCard());
    cards.add(cons.getRoomCard());
    cards.add(auditorium.getRoomCard());
    markRoom(cards);

    cards.stream().forEach(j -> roomMap.put(j.toString().toLowerCase(),j)); // add all room to a map
    cards.stream().forEach(n -> rooms.add(n.getRoom()));

    Collections.shuffle(cards);
    murderScenario.add(cards.get(0));
    cards.remove(0);
    return cards;
  }

  /**
   * Mark room tiles as being part of a room
   */
  public void markRoom(List<RoomCard> roomCards){
    //Kitchen
    Room k = roomCards.get(0).getRoom();
    for(char r = 'c'; r < 'g'; r++){
      for(char c = 'b'; c < 'f'; c++){
        Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
        t.setIsPartOf(k);
        if(!k.getEntrances().contains(t)) k.addEmptySpace(t);
      }
    }

    //Dining
    Room d = roomCards.get(1).getRoom();
    for(char r = 'l'; r < 'p'; r++){
      for(char c = 'b'; c < 'h'; c++){
        Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
        t.setIsPartOf(d);
        if(!d.getEntrances().contains(t)) d.addEmptySpace(t);
      }
    }
    board.getBoardTile("Bk").setIsPartOf(roomCards.get(1).getRoom());
    board.getBoardTile("Ck").setIsPartOf(roomCards.get(1).getRoom());
    board.getBoardTile("Dk").setIsPartOf(roomCards.get(1).getRoom());

    //Lounge
    Room l = roomCards.get(2).getRoom();
    for(char r = 'u'; r < 'y'; r++){
      for(char c = 'b'; c < 'g'; c++){
        Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
        t.setIsPartOf(l);
        if(!l.getEntrances().contains(t)) l.addEmptySpace(t);
      }
    }

    //Hall
    Room h = roomCards.get(3).getRoom();
    for(char r = 't'; r < 'y'; r++){
      for(char c = 'k'; c < 'o'; c++){
        Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
        t.setIsPartOf(h);
        if(!h.getEntrances().contains(t)) h.addEmptySpace(t);
      }
    }

    //Study
    Room s = roomCards.get(4).getRoom();
    for(char r = 'w'; r < 'y'; r++){
      for(char c = 's'; c < 'y'; c++){
        Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
        t.setIsPartOf(s);
        if(!s.getEntrances().contains(t)) s.addEmptySpace(t);
      }
    }

    //BookRoom
    Room b = roomCards.get(5).getRoom();
    for(char r = 'p'; r < 's'; r++){
      for(char c = 't'; c < 'x'; c++){
        Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
        t.setIsPartOf(b);
        if(!b.getEntrances().contains(t)) b.addEmptySpace(t);
      }
    }
    board.getBoardTile("Sq").setIsPartOf(roomCards.get(5).getRoom());

    //EntertainmentRoom
    Room e = roomCards.get(6).getRoom();
    for(char r = 'j'; r < 'm'; r++){
      for(char c = 't'; c < 'x'; c++){
        Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
        t.setIsPartOf(e);
        if(!e.getEntrances().contains(t)) e.addEmptySpace(t);
      }
    }

    //Cons
    Room con = roomCards.get(7).getRoom();
    for(char r = 'c'; r < 'f'; r++){
      for(char c = 't'; c < 'x'; c++){
        Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
        t.setIsPartOf(con);
        if(!con.getEntrances().contains(t)) con.addEmptySpace(t);
      }
    }

    //Auditorium
    Room a = roomCards.get(8).getRoom();
    for(char r = 'd'; r < 'h'; r++){
      for(char c = 'J'; c < 'P'; c++){
        Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
        t.setIsPartOf(a);
        if(!a.getEntrances().contains(t)) a.addEmptySpace(t);
      }
    }
    board.getBoardTile("Lb").setIsPartOf(roomCards.get(8).getRoom());
    board.getBoardTile("Mb").setIsPartOf(roomCards.get(8).getRoom());
    board.getBoardTile("Lc").setIsPartOf(roomCards.get(8).getRoom());
    board.getBoardTile("Mc").setIsPartOf(roomCards.get(8).getRoom());
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

  public int indexOfMurderScenario(Card aMurderScenario)
  {
    int index = murderScenario.indexOf(aMurderScenario);
    return index;
  }

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
  public static int minimumNumberOfPlayers()
  {
    return 3;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfPlayers()
  {
    return 6;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    if (!players.contains(aPlayer))
    {
      return wasRemoved;
    }

    if (numberOfPlayers() <= minimumNumberOfPlayers())
    {
      return wasRemoved;
    }

    players.remove(aPlayer);
    wasRemoved = true;
    return wasRemoved;
  }

  /**
   * Creates two "dice" which pick a random number from 1-6
   * @return sum of roll
   */
   private int rollDice(){
     Random dice1 = new Random();
     Random dice2 = new Random();

     int roll1 = dice1.nextInt(6); //random int from 0-5
     int roll2 = dice2.nextInt(6);

     roll1+=1; // add 1 to make interval 1-6
     roll2+=1;

     return roll1+roll2;
   }

  /**
   * Runs the main game loop while the murder has not been guessed and there are still players in the game
   */
  private void playGame(){
    board.printBoard();
    int currentPlayer=0;
    for (int i=0; i<players.size();i++) {
      if (players.get(i).getCharacter().getCharacter().equalsIgnoreCase("Miss Red")) { // Rule "Miss Scarlet always goes first"
        currentPlayer=i;
        break;
      }
    }

    try {
      game:while (true) { // play the game
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(players.get(currentPlayer).getCharacter() + "'s turn.");
        int numMoves = rollDice();
        System.out.println("You have " + numMoves+" moves.");
        System.out.println("Where would you like to move to?");
        String move = input.readLine();

        Tile goal = board.getBoardTile(move);
        //Assuming move player actually moves the player
        boolean valid = board.movePlayer(players.get(currentPlayer),goal,numMoves);

        while (!valid){
          System.out.println("That move is not valid, please try a different move");
          move = input.readLine();
          goal = board.getBoardTile(move);
          valid = board.movePlayer(players.get(currentPlayer),goal,numMoves);
        }

        board.printBoard();

        System.out.println("Would you like to make a suggestion? (Y/N)");
        String suggest = input.readLine();
        if (suggest.equalsIgnoreCase("yes")||suggest.equalsIgnoreCase("y")){
          processSuggestion(players.get(currentPlayer), input);
        }




        System.out.println("Would you like to make an accusation?");
        //get input, call check accusation
        //Maybe remove player from game if wrong


        System.out.println();// blank line, maybe want to clear the screen later?
        currentPlayer = getNextCharacter(currentPlayer);
      }
    }
    catch (IOException e ){
      System.out.println("Error on input, please try again"+e);
    }
  }

  /**
   * Gets the next character
   * @param current the character currently playing
   * @return the next player
   */
  private int getNextCharacter(int current){
    if (current<players.size()-1)
      return current+1;
    else
     return 0;
  }

  /**
   * Deals all of the cards excluding the murder scenario to the players.
   * @param cardsToBeDealt the remaining cards
   */
   private void dealCards(List <Card> cardsToBeDealt){
     int currentPlayerIndex=0;
     Player currentPlayer = players.get(currentPlayerIndex);

     while (!cardsToBeDealt.isEmpty()){
       currentPlayer.addCardToHand(cardsToBeDealt.remove(0));

       if (currentPlayerIndex<players.size()-1)
         currentPlayerIndex++;
       else
         currentPlayerIndex=0;

       currentPlayer=players.get(currentPlayerIndex);
     }

  }

  /**
   * Checks the current player is able to make a suggestion, and gets the room, character and weapon they are suggesting
   * @param player the current input
   * @param input input stream
   */
  private void processSuggestion(Player player, BufferedReader input){
    Room room = player.getPosition().getIsPartOf();
    if (room==null) {
      System.out.println("You are not in a room, you cannot make a suggestion.");
      return;
    }
    WeaponCard weapon = checkWeapon(input);
    CharacterCard character = checkCharacter(input);

    boolean valid = checkSuggestion(weapon,character,room.getRoomCard());
    if (valid){

    }
  }


  private WeaponCard checkWeapon(BufferedReader input){
     try {
       System.out.println("What do you suggest is the murder weapon?");
       String weapon = input.readLine();
       WeaponCard suggestedWeapon = weaponMap.get(weapon.toLowerCase());
       while (suggestedWeapon == null) {
         System.out.println("Invalid Weapon, please try again:");
         weapon = input.readLine();
         suggestedWeapon = weaponMap.get(weapon.toLowerCase());
       }
       return suggestedWeapon;
     }
     catch (IOException e){
       System.out.println("Error on input" + e);
     }
    return null;
  }

  private CharacterCard checkCharacter(BufferedReader input){
    try {
      System.out.println("Who do you suggest is the murderer?");
      String murderer = input.readLine();
      CharacterCard suggestedmurderer = characterMap.get(murderer.toLowerCase());
      while (suggestedmurderer == null) {
        System.out.println("Invalid Character, please try again:");
        murderer = input.readLine();
        suggestedmurderer = characterMap.get(murderer.toLowerCase());
      }
      return suggestedmurderer;
    }
    catch (IOException e){
      System.out.println("Error on input" + e);
    }
    return null;
  }

  private RoomCard checkRoom(BufferedReader input){
    try {
      System.out.println("What do you suggest is the murder room?");
      String room = input.readLine();
      RoomCard suggestedRoom = roomMap.get(room.toLowerCase());
      while (suggestedRoom == null) {
        System.out.println("Invalid Room, please try again:");
        room = input.readLine();
        suggestedRoom = roomMap.get(room.toLowerCase());
      }
      return suggestedRoom;
    }
    catch (IOException e){
      System.out.println("Error on input" + e);
    }
    return null;
  }


  /**
   * 
   * @param weapon
   * @param character
   * @param room
   * @return
   */
   private boolean checkSuggestion( WeaponCard weapon,CharacterCard character, RoomCard room){
    return false;
  }

  public List<WeaponCard> getWeapons(){
     return new ArrayList<>(weaponMap.values());
  }

  // line 12 "model.ump"
   private boolean checkAccusation(){
    return false;
  }

  public static void main(String args[]){
    new Game();
  }

  private class IncorrectNumberOfPlayersException extends Throwable {
  }

  private class InvalidCharacterException extends Throwable {
  }
}


