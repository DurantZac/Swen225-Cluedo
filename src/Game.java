/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4584.3d417815a modeling language!*/


import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
// line 2 "model.ump"
// line 96 "model.ump"
public class Game {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Game Associations
    private Board board;
    private List<Card> murderScenario = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private List<CharacterCard> characters = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List <WeaponCard> weapons = new ArrayList<>();
    private int playerNum;

    public static final String CLEAR_SCREEN = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
            "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";

    //------------------------
    // CONSTRUCTOR
    //------------------------

    /**
     * Create new game by initialising board,
     */
    public Game() {
        board = createBoard();

    BufferedReader input;
    while (true) { //Try to find out how many players there are
      try {
        input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("How many players are playing? (3-6) ");
        int numberOfPlayers = Integer.parseInt(input.readLine());
        if (numberOfPlayers < minimumNumberOfPlayers() || numberOfPlayers > maximumNumberOfPlayers()) {
          throw new IncorrectNumberOfPlayersException();
        }
        playerNum = numberOfPlayers;
        break;
      } catch (NumberFormatException n) {
        System.out.println("Please enter a number between 3-6 only");
      } catch (IOException e) {
        System.out.println("Error on input, please try again" + e);
      } catch (IncorrectNumberOfPlayersException i) {
        System.out.println("Please enter a number between 3-6 only");
      }
    }
        //Make all the characters
        characters.add(new CharacterCard("Col. Mustard", board.getBoardTile("Ar")));
        characters.add(new CharacterCard("Mrs White", board.getBoardTile("Ja")));
        characters.add(new CharacterCard("Rev. Green", board.getBoardTile("Oa")));
        characters.add(new CharacterCard("Prof. Plum", board.getBoardTile("Xt")));
        characters.add(new CharacterCard("Ms Turquoise", board.getBoardTile("Xg")));
        characters.add(new CharacterCard("Miss Red", board.getBoardTile("Hy")));

        List<CharacterCard> unusedCharacters = new ArrayList<>(characters);
        List<Card> cardsToBeDealt = createCards(characters);

        for (int i = 0; i < playerNum; i++) {
            System.out.println("Player " + (i + 1) + ". Please select your character");
            System.out.println("The available players are:");

            for (int characterCounter = 1; characterCounter <= unusedCharacters.size(); characterCounter++) {
                System.out.println("[" + characterCounter + "] " + unusedCharacters.get(characterCounter - 1).toString());
            }

            System.out.println("\nWhat player would you like to be? Please enter the number: ");

            validityCheck:
            while (true) { //Check who they want to be
                try {
                    input = new BufferedReader(new InputStreamReader(System.in));
                    int characterToPlay = Integer.parseInt(input.readLine());
                    Player p = new Player(unusedCharacters.get(characterToPlay - 1));
                    players.add(p);
                    unusedCharacters.remove(characterToPlay - 1);
                    System.out.println("You have selected the character: " + p.getCharacter() + "\n");
                    break validityCheck;
                } catch (IOException e) {
                    System.out.println("Error on input, please try again" + e);
                } catch (NumberFormatException n) {
                    System.out.println("Please enter the number of your character");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Please enter an available number between 1 and 6");
                }
            }
        }

        //Deals hand
        dealCards(cardsToBeDealt);

        //Game play begins
        playGame(input);

        try {
            input.close();
        } catch (IOException e) {
            System.out.println("Error closing input" + e);
        }
    }


    /**
     * Generate board from string
     *
     * @return boadrd
     */
    public Board createBoard() {

        String gameBoard = "|X|X|X|X|X|X|X|X|X|W|#|#|#|#|G|X|X|X|X|X|X|X|X|X|" + "\n" +
                "|#|#|#|#|#|#|X|_|_|_|#|_|_|#|_|_|_|X|#|#|#|#|#|#|" + "\n" +
                "|K|_|_|_|_|#|_|_|#|#|A|_|_|#|#|#|_|_|#|_|_|_|_|C|" + "\n" +
                "|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|#|_|_|_|_|#|" + "\n" +
                "|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|v|_|_|_|_|#|" + "\n" +
                "|#|_|_|_|_|#|_|_|<|_|_|_|_|_|_|>|_|_|_|#|#|#|#|X|" + "\n" +
                "|X|#|#|#|v|#|_|_|#|_|_|_|_|_|_|#|_|_|_|_|_|_|_|T|" + "\n" +
                "|_|_|_|_|_|_|_|_|#|v|#|#|#|#|v|#|_|_|_|_|_|_|_|X|" + "\n" +
                "|X|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|" + "\n" +
                "|#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|<|_|_|_|_|E|" + "\n" +
                "|#|_|_|_|#|#|#|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|" + "\n" +
                "|#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|" + "\n" +
                "|D|_|_|_|_|_|_|>|_|_|X|X|X|X|X|_|_|_|#|#|#|#|^|#|" + "\n" +
                "|#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|_|_|_|_|_|X|" + "\n" +
                "|#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|#|^|#|#|X|" + "\n" +
                "|#|#|#|#|#|#|v|#|_|_|X|X|X|X|X|_|_|#|#|_|_|_|_|B|" + "\n" +
                "|X|_|_|_|_|_|_|_|_|_|X|X|X|X|X|_|_|<|_|_|_|_|_|#|" + "\n" +
                "|M|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|_|_|_|_|#|" + "\n" +
                "|X|_|_|_|_|_|_|_|_|#|#|^|^|#|#|_|_|_|#|#|#|#|#|X|" + "\n" +
                "|#|#|#|#|#|#|^|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|P|" + "\n" +
                "|#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|X|" + "\n" +
                "|L|_|_|_|_|_|#|_|_|#|_|_|_|_|>|_|_|^|S|#|#|#|#|#|" + "\n" +
                "|#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|" + "\n" +
                "|#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|" + "\n" +
                "|#|#|#|#|#|#|#|R|X|#|#|H|#|#|#|X|_|#|#|#|#|#|#|#|" + "\n";

        List<Tile> tiles = new ArrayList<>();
        String[] lines = gameBoard.split("\n");
        for (String l : lines) {
            String[] values = l.split("|");
            for (int i = 0; i < values.length; i++) {
                if (i % 2 != 0) {
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
     *
     * @return List of all cards after murder scenario removed
     */
    public List<Card> createCards(List<CharacterCard> characters) {
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

        weapons.stream().forEach(j -> this.weapons.add((WeaponCard) j)); // add all weapons to a map

        Collections.shuffle(rooms);
        for (int i = 0; i < weapons.size(); i++) {
            WeaponCard w = (WeaponCard) (weapons.get(i));
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
     *
     * @return
     */
    public List<RoomCard> createRooms() {
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
        auditorium.addEntrance(board.getBoardTile("Pf"));
        auditorium.addEntrance(board.getBoardTile("If"));

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

        cards.stream().forEach(n -> rooms.add(n.getRoom()));

        Collections.shuffle(cards);
        murderScenario.add(cards.get(0));
        cards.remove(0);
        return cards;
    }

    /**
     * Mark room tiles as being part of a room
     */
    public void markRoom(List<RoomCard> roomCards) {
        //Kitchen
        Room k = roomCards.get(0).getRoom();
        for (char r = 'c'; r < 'g'; r++) {
            for (char c = 'b'; c < 'f'; c++) {
                Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
                t.setIsPartOf(k);
                if (!k.getEntrances().contains(t)) k.addEmptySpace(t);
            }
        }

        //Dining
        Room d = roomCards.get(1).getRoom();
        for (char r = 'l'; r < 'p'; r++) {
            for (char c = 'b'; c < 'h'; c++) {
                Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
                t.setIsPartOf(d);
                if (!d.getEntrances().contains(t)) d.addEmptySpace(t);
            }
        }
        board.getBoardTile("Bk").setIsPartOf(roomCards.get(1).getRoom());
        board.getBoardTile("Ck").setIsPartOf(roomCards.get(1).getRoom());
        board.getBoardTile("Dk").setIsPartOf(roomCards.get(1).getRoom());

        //Lounge
        Room l = roomCards.get(2).getRoom();
        for (char r = 'u'; r < 'y'; r++) {
            for (char c = 'b'; c < 'g'; c++) {
                Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
                t.setIsPartOf(l);
                if (!l.getEntrances().contains(t)) l.addEmptySpace(t);
            }
        }

        //Hall
        Room h = roomCards.get(3).getRoom();
        for (char r = 't'; r < 'y'; r++) {
            for (char c = 'k'; c < 'o'; c++) {
                Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
                t.setIsPartOf(h);
                if (!h.getEntrances().contains(t)) h.addEmptySpace(t);
            }
        }

        //Study
        Room s = roomCards.get(4).getRoom();
        for (char r = 'w'; r < 'y'; r++) {
            for (char c = 's'; c < 'y'; c++) {
                Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
                t.setIsPartOf(s);
                if (!s.getEntrances().contains(t)) s.addEmptySpace(t);
            }
        }

        //BookRoom
        Room b = roomCards.get(5).getRoom();
        for (char r = 'p'; r < 's'; r++) {
            for (char c = 't'; c < 'x'; c++) {
                Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
                t.setIsPartOf(b);
                if (!b.getEntrances().contains(t)) b.addEmptySpace(t);
            }
        }
        board.getBoardTile("Sq").setIsPartOf(roomCards.get(5).getRoom());

        //EntertainmentRoom
        Room e = roomCards.get(6).getRoom();
        for (char r = 'j'; r < 'm'; r++) {
            for (char c = 't'; c < 'x'; c++) {
                Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
                t.setIsPartOf(e);
                if (!e.getEntrances().contains(t)) e.addEmptySpace(t);
            }
        }

        //Cons
        Room con = roomCards.get(7).getRoom();
        for (char r = 'c'; r < 'f'; r++) {
            for (char c = 't'; c < 'x'; c++) {
                Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
                t.setIsPartOf(con);
                if (!con.getEntrances().contains(t)) con.addEmptySpace(t);
            }
        }

        //Auditorium
        Room a = roomCards.get(8).getRoom();
        for (char r = 'd'; r < 'h'; r++) {
            for (char c = 'J'; c < 'P'; c++) {
                Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
                t.setIsPartOf(a);
                if (!a.getEntrances().contains(t)) a.addEmptySpace(t);
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

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfPlayers() {
        return 3;
    }

    /* Code from template association_MaximumNumberOfMethod */
    public static int maximumNumberOfPlayers() {
        return 6;
    }

    /**
     * Creates two "dice" which pick a random number from 1-6
     *
     * @return sum of roll
     */
    private int rollDice() {
        Random dice1 = new Random();
        Random dice2 = new Random();

        int roll1 = dice1.nextInt(6); //random int from 0-5
        int roll2 = dice2.nextInt(6);

        roll1 += 1; // add 1 to make interval 1-6
        roll2 += 1;

        return roll1 + roll2;
    }

    /**
     * Runs the main game loop while the murder has not been guessed and there are still players in the game
     */
    private void playGame(BufferedReader input) {
        int currentPlayer = 0;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getCharacter().getCharacter().equalsIgnoreCase("Miss Red")) { // Rule "Miss Scarlet always goes first"
                currentPlayer = i;
                break;
            }
        }

    try {
      game:while (true) { // play the game
        boolean validInput = false;
        System.out.println(CLEAR_SCREEN);
        board.printBoard();

        System.out.println(players.get(currentPlayer).getCharacter() + "'s turn.");

        Room room = players.get(currentPlayer).getPosition().getIsPartOf();
        if (room!=null) { // only allow them to make suggestions in a room
          System.out.println("You are currently in the "+room);
        }

        seeHand(input, players.get(currentPlayer));

        int numMoves = rollDice();
        System.out.println("You have " + numMoves+" moves.");
        System.out.println("Where would you like to move to?");
        String move = input.readLine();

        Tile goal = board.getBoardTile(move);
        while(goal == null){
          System.out.println("Invalid tile, please choose again");
          System.out.println("Where would you like to move to?");
          move = input.readLine();
          goal = board.getBoardTile(move);
        }

        //Assuming move player actually moves the player
         validInput = board.movePlayer(players.get(currentPlayer),goal,numMoves);

        while (!validInput){
          System.out.println("That move is not valid, please try a different move");
          move = input.readLine();
          goal = board.getBoardTile(move);
          validInput = board.movePlayer(players.get(currentPlayer),goal,numMoves);
        }

                board.printBoard();

                room = players.get(currentPlayer).getPosition().getIsPartOf();
                if (room != null) { // only allow them to make suggestions in a room
                    validInput = false;
                    while (!validInput) {
                        System.out.println("Would you like to make a suggestion? (Y/N)");
                        String suggest = input.readLine();
                        if (suggest.equalsIgnoreCase("yes") || suggest.equalsIgnoreCase("y")) {
                            seeHand(input, players.get(currentPlayer));
                            processSuggestion(players.get(currentPlayer), input);
                            validInput = true;
                        } else if (suggest.equalsIgnoreCase("no") || suggest.equalsIgnoreCase("n")) {
                            validInput = true;
                        }
                    }
                }

                processAccusation(input, players.get(currentPlayer));


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
     *
     * @param current the character currently playing
     * @return the next player
     */
    private int getNextCharacter(int current) {
        if (current < players.size() - 1) {
            if (!players.get(current + 1).getIsStillPlaying())
                return getNextCharacter(current + 1);
            else {
                return current + 1;
            }
        } else {
            if (!players.get(0).getIsStillPlaying())
                return getNextCharacter(0);
            else {
                return 0;
            }
        }
    }

    private void seeHand(BufferedReader input, Player p) {
        try {
            boolean validInput = false;
            while (!validInput) {
                System.out.println("Would you like to see your hand? (Y/N)");
                String hand = input.readLine();
                if (hand.equalsIgnoreCase("yes") || hand.equalsIgnoreCase("y")) {
                    System.out.println(p.returnHand());
                    validInput = true;
                } else if (hand.equalsIgnoreCase("no") || hand.equalsIgnoreCase("n")) {
                    validInput = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error printing hand" + e);
        }
    }


    /**
     * Deals all of the cards excluding the murder scenario to the players.
     *
     * @param cardsToBeDealt the remaining cards
     */
    private void dealCards(List<Card> cardsToBeDealt) {
        int currentPlayerIndex = 0;
        Player currentPlayer = players.get(currentPlayerIndex);

        while (!cardsToBeDealt.isEmpty()) {
            currentPlayer.addCardToHand(cardsToBeDealt.remove(0));

            if (currentPlayerIndex < players.size() - 1)
                currentPlayerIndex++;
            else
                currentPlayerIndex = 0;

            currentPlayer = players.get(currentPlayerIndex);
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
    System.out.println(CLEAR_SCREEN);
    board.teleportCharacter(character,room);
    board.teleportWeapon(weapon,room);

    Card dispute = checkSuggestion(player,weapon,character,room.getRoomCard(),input);
    if (dispute!=null){
        System.out.printf("%s, your suggestion has been refuted with the following card: %s. \n", player.getCharacter().toString(), dispute.toString());
    }
    else{
        System.out.printf("%s, your suggestion has not been refuted.", player.getCharacter().toString());
    }

  }


    private WeaponCard checkWeapon(BufferedReader input) {
        System.out.println("What do you think is the murder weapon?");
        List weapons = getWeapons();
        for (int weaponCount = 1; weaponCount <= weapons.size(); weaponCount++) {
            System.out.println("[" + weaponCount + "] " + weapons.get(weaponCount - 1).toString());
        }

        while (true) {
            try {
                int weapon = Integer.parseInt(input.readLine());
                WeaponCard suggestedWeapon = (WeaponCard) weapons.get(weapon - 1);
                System.out.println("You have selected the weapon: " + suggestedWeapon + "\n");
                return suggestedWeapon;
            } catch (IOException e) {
                System.out.println("Error on input, please try again" + e);
            } catch (NumberFormatException n) {
                System.out.println("Please enter the number of the weapon");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please enter a number between 1 and 6");
            }
        }
    }

    private CharacterCard checkCharacter(BufferedReader input) {
        System.out.println("Who do you think is the murderer?");
        for (int characterCount = 1; characterCount <= characters.size(); characterCount++) {
            System.out.println("[" + characterCount + "] " + characters.get(characterCount - 1).toString());
        }

        while (true) {
            try {
                int murderer = Integer.parseInt(input.readLine());
                CharacterCard suggestedmurderer = characters.get(murderer - 1);
                System.out.println("You have selected the weapon: " + suggestedmurderer + "\n");
                return suggestedmurderer;
            } catch (IOException e) {
                System.out.println("Error on input, please try again" + e);
            } catch (NumberFormatException n) {
                System.out.println("Please enter the number of the weapon");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please enter a number between 1 and 6");
            }
        }
    }

    private RoomCard checkRoom(BufferedReader input) {
        System.out.println("What do you think is the murder room?");
        for (int roomCount = 1; roomCount <= rooms.size(); roomCount++) {
            System.out.println("[" + roomCount + "] " + rooms.get(roomCount - 1).toString());
        }

        while (true) {
            try {
                int room = Integer.parseInt(input.readLine());
                RoomCard suggestedRoom  = rooms.get(room - 1).getRoomCard();
                System.out.println("You have selected the weapon: " + suggestedRoom + "\n");
                return suggestedRoom;
            } catch (IOException e) {
                System.out.println("Error on input, please try again" + e);
            } catch (NumberFormatException n) {
                System.out.println("Please enter the number of the weapon");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please enter a number between 1 and 6");
            }
        }
    }


   private Card checkSuggestion(Player player,WeaponCard weapon,CharacterCard character, RoomCard room, BufferedReader input){
     try {
       for (Player p : players) {
         if (p != player) {
           Set<Card> hand = p.getHand();
           List<Card> suggestions = new ArrayList<>();
           if (hand.contains(weapon)) suggestions.add(weapon);
           if (hand.contains(character)) suggestions.add(character);
           if (hand.contains(room)) suggestions.add(room);

           System.out.println(p.getCharacter() + "'s turn to check the suggestion:");
           System.out.println("Press any letter to continue");
           input.readLine();
           System.out.println(CLEAR_SCREEN);
           System.out.println(p.returnHand());
           System.out.printf("You have %d cards matching the suggestion\n", suggestions.size());
           if(suggestions.size() == 0) System.out.println(CLEAR_SCREEN);
           if (suggestions.size() == 1) {
             System.out.println("Since you only have one card, you must use the " + suggestions.get(0).toString() + " to disprove the suggestion");
             System.out.println("Press any letter to continue");
             input.readLine();
             System.out.println(CLEAR_SCREEN);
             return suggestions.get(0);
           }
           if (suggestions.size() > 1) {
             System.out.println("What card would you like to use to disprove the suggestion? ");
             for (int i = 0; i < suggestions.size(); i++) {
               System.out.printf("[%d] %s \n", i, suggestions.get(i).toString());
             }

             int dispute = -1;
             while (dispute == -1) {
               try {
                 dispute = Integer.parseInt(input.readLine());
               } catch (IOException e) {
                 System.out.println("Error on input" + e);
               } catch (NumberFormatException n) {
                 System.out.println("Please enter a whole number only");
               }
             }
             System.out.println(CLEAR_SCREEN);
             return suggestions.get(dispute);
           }
         }
       }
       return null; // no one could disprove the suggestion, so return null
     }
     catch (IOException e ){
       System.out.println(e);
     }
     return null;
   }

    public List<WeaponCard> getWeapons() {
        return new ArrayList<>(this.weapons);
    }

    // line 12 "model.ump"
    private boolean checkAccusation(BufferedReader input) {
        CharacterCard character = checkCharacter(input);
        WeaponCard weapon = checkWeapon(input);
        RoomCard room = checkRoom(input);

        return (character.equals(murderScenario.get(0)) && room.equals(murderScenario.get(1)) && weapon.equals(murderScenario.get(2)));
    }

    private void processAccusation (BufferedReader input, Player player){
        try {
            boolean validInput = false;
            while (!validInput) {
                System.out.println("Would you like to make an accusation? (Y/N)");
                String accuse = input.readLine();
                if (accuse.equalsIgnoreCase("yes") || accuse.equalsIgnoreCase("y")) {
                    boolean accusation = checkAccusation(input);
                    if (accusation) {
                        System.out.println("Congratulations, " + player.getCharacter().toString() + " has solved the murder!");
                        System.out.println("The murder occurred as follows:");
                        System.out.println(murderScenario.get(0) + " committed the crime in the " + murderScenario.get(1) + " with the " + murderScenario.get(2));
                        return;
                    } else {
                        System.out.println("The accusation is incorrect, " + player.getCharacter().toString());
                        System.out.println("You can no longer win the game");
                        player.setIsStillPlaying(false);
                        System.out.println("Press any letter to continue");
                        input.readLine();
                        System.out.println(CLEAR_SCREEN);
                    }
                    validInput = true;
                } else if (accuse.equalsIgnoreCase("no") || accuse.equalsIgnoreCase("n")) {
                    validInput = true;
                }
            }
        }catch (IOException e){
            System.out.println("Error processing Accusation"+e);
        }
    }


    public static void main(String args[]) {
        new Game();
    }

  private class IncorrectNumberOfPlayersException extends Throwable {
  }

}


