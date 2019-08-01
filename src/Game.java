
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
    private List<WeaponCard> weapons = new ArrayList<>();
    private int playerNum;

    public static final String CLEAR_SCREEN = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
            "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";


    //------------------------
    // CONSTRUCTOR
    //------------------------

    /**
     * Create new game by initialising board, assigning each player a character, and starting the game.
     */
    public Game() {
        board = createBoard();

        BufferedReader input ;
        while (true) { //Find out how many players there are
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


        List<CharacterCard> unusedCharacters = new ArrayList<>(characters); //List for players picking characters
        List<Card> cardsToBeDealt = createCards(characters); //All Cards

        for (int i = 0; i < playerNum; i++) {
            System.out.println("Player " + (i + 1) + ". Please select your character");
            System.out.println("The available players are:");

            //Prints out each of the characters
            for (int characterCounter = 1; characterCounter <= unusedCharacters.size(); characterCounter++) {
                System.out.println("[" + characterCounter + "] " + unusedCharacters.get(characterCounter - 1).toString());
            }

            System.out.println("\nWhat player would you like to be? Please enter the number: ");

            validityCheck:
            while (true) { //Keep asking who they want to be until there is a valid input
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

        try { //Close the input after the game ends.
            input.close();
        } catch (IOException e) {
            System.out.println("Error closing input" + e);
        }
    }


    /**
     * Generate board from string
     *
     * @return a new board matching the string
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

        //Splits the string into tiles, and stores these in a list, which is used to make the board.
        List<Tile> tiles = new ArrayList<>();
        String[] lines = gameBoard.split("\n"); // Split by lines first
        for (String l : lines) { // Then, going through each line...
            String[] values = l.split("|"); // we split by |, leaving us with only the tile values
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
     *
     * @param characters , a list of character cards which are created in the game constructor,
     *                  as these are required previously.
     * @return List of all cards after murder scenario removed
     */
    public List<Card> createCards(List<CharacterCard> characters) {
        List<Card> allCards = new ArrayList<>();
        // Characters
        allCards.addAll(characters);
        Collections.shuffle(allCards);
        murderScenario.add(allCards.get(0)); //Pick a random murderer
        allCards.remove(0); // remove that from the list of cards to deal

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

        // add all weapons to a list so they can be accessed easily later
        weapons.stream().forEach(j -> this.weapons.add((WeaponCard) j));

        // Randomly assign each weapon to a room
        Collections.shuffle(rooms);
        for (int i = 0; i < weapons.size(); i++) {
            WeaponCard w = (WeaponCard) (weapons.get(i));
            w.setLocation(rooms.get(i));
        }

        //Add a random weapon to the murder scenario and remove this from the list
        Collections.shuffle(weapons);
        murderScenario.add(weapons.get(0));
        weapons.remove(weapons.get(0));
        allCards.addAll(weapons);

        return allCards;
    }

    /**
     * Create rooms and add entrances
     * Each of the rooms is created by making the room, adding each enterance to it,
     * and adding this room to a list of cards.
     *
     * @return a list of all rooms (minus the murder room)
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

        //Randomly pick a room for the murder and remove it from the cards to be dealt
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

        //Conservatory
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

    /**
     * @return the minimum number of players needed to play Cluedo.
     */
    public static int minimumNumberOfPlayers() {
        return 3;
    }

    /**
     * @return the maximum number of players needed to play Cluedo.
     */
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
     * Runs the main game loop
     * The game continues while players are still in the game (have not made a false accusation)
     * And the murder has not been solved.
     *
     * @param input buffered reader getting input from the players
     */
    private void playGame(BufferedReader input) {
        int currentPlayer = 0; // Default player to start

        // Rule "Miss Scarlet always goes first", so check if Miss Red is playing
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getCharacter().getCharacter().equalsIgnoreCase("Miss Red")) {
                currentPlayer = i;
                break;
            }
        }

        while (players.stream().filter(p -> p.getIsStillPlaying() == true).count() > 1) { // Still players left to play
            //Clear the screen and print the current board.
            System.out.println(CLEAR_SCREEN);
            board.printBoard();

            System.out.println(players.get(currentPlayer).getCharacter() + "'s turn.");

            //If they are in a room, tell them
            Room room = players.get(currentPlayer).getPosition().getIsPartOf();
            if (room != null) {
                System.out.println("You are currently in the " + room);
            }

            //Check to see if they wish to see their hand
            seeHand(input, players.get(currentPlayer));

            //Move the player
            processMove(input, players.get(currentPlayer));

            //Check to see if they want to make a suggestion
            processSuggestion(input, players.get(currentPlayer));

            //Check to see if they want to make an accusation
            //If it returns true it means they correctly guessed, hence they won.
            if (processAccusation(input, players.get(currentPlayer))) return;

            //Pick the next character to play
            System.out.println();
            currentPlayer = getNextCharacter(currentPlayer);
        }

        //In this case there is only one player remaining (else it would have returned above) so it must be game over, last player wins.
        System.out.printf("%s, you are the last player standing, you win! \n", players.get(currentPlayer).getCharacter());
        System.out.println("The murder occurred as follows:");
        System.out.println(murderScenario.get(0) + " committed the crime in the " + murderScenario.get(1) + " with the " + murderScenario.get(2));
    }

    /**
     * Moves the current player to a new valid location
     *@param input buffered reader getting input from the players
     *@param player the current player to be moved
     */
    private void processMove(BufferedReader input, Player player) {
        try {
            //Find out how many moves the player has
            int numMoves = rollDice();
            System.out.println("You have " + numMoves + " moves.");

            //finds out where to move to
            System.out.println("Where would you like to move to?");
            String move = input.readLine();

            Tile goal = board.getBoardTile(move);
            while (goal == null) { // ensures the tile is valid (as in 2 letters, one upper one lower)
                System.out.println("Invalid tile, please choose again");
                System.out.println("Where would you like to move to?");
                move = input.readLine();
                goal = board.getBoardTile(move);
            }

            //Finds a path and moves the player, returns true if successful
            boolean validInput = board.movePlayer(player, goal, numMoves);

            while (!validInput) { // ensures the move is valid and there is a path to it
                System.out.println("That move is not valid, please try a different move");
                move = input.readLine();
                goal = board.getBoardTile(move);
                validInput = board.movePlayer(player, goal, numMoves);
            }

            //Reprints the board with their new position.
            board.printBoard();

        } catch (IOException e) {
            System.out.println("Error moving player" + e);
        }

    }


    /**
     * Gets the next character to play
     * Makes sure there are still characters playing.
     *
     * Checks if the directly next character is still playing, if not, recursively calls itself until it finds one
     *
     * Loops around to the first player once it is at the last one.
     * @param current the character currently playing
     * @return the next player
     */
    private int getNextCharacter(int current) {
        // No more players
        if (players.stream().filter(p -> p.getIsStillPlaying() == true).count() == 0) return 0;

        //Get the next player
        if (current < players.size() - 1) {
            if (!players.get(current + 1).getIsStillPlaying())
                return getNextCharacter(current + 1); //Find the next one
            else {
                return current + 1;
            }
        } else {
            //At the end of the list, loop back to the start
            if (!players.get(0).getIsStillPlaying())
                return getNextCharacter(0);
            else {
                return 0;
            }
        }
    }

    /**
     * Offers to show the hand to the current player
     * Prints hand on screen.
     *
     * @param input buffered reader getting input from the players
     * @param p the current player
     */
    private void seeHand(BufferedReader input, Player p) {
        try {
            boolean validInput = false;
            while (!validInput) { // Makes sure input is clear yes or no answer
                System.out.println("Would you like to see your hand? (Y/N)");
                String hand = input.readLine();
                if (hand.equalsIgnoreCase("yes") || hand.equalsIgnoreCase("y")) {
                    System.out.println(p.returnHand());
                    validInput = true;
                } else if (hand.equalsIgnoreCase("no") || hand.equalsIgnoreCase("n")) {
                    validInput = true;
                }
                else {
                    System.out.println("Please answer with Y or N.");
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

        while (!cardsToBeDealt.isEmpty()) { // Make sure all the cards are dealt
            currentPlayer.addCardToHand(cardsToBeDealt.remove(0)); // hand the current player the first card

            //Find the next player
            if (currentPlayerIndex < players.size() - 1)
                currentPlayerIndex++;
            else
                currentPlayerIndex = 0;

            currentPlayer = players.get(currentPlayerIndex);
        }

    }

    /**
     * Checks the current player is able to make a suggestion,
     * and gets the room, character and weapon they are suggesting
     *
     *@param input  input stream
     * @param player the current input
     */
    private void processSuggestion(BufferedReader input, Player player) {
        try {
            Room room = player.getPosition().getIsPartOf();
            if (room == null) { // only allow them to make suggestions in a room
                return;
            }

            System.out.println("Would you like to make a suggestion? (Y/N)");
            boolean validInput = false;

            while (!validInput) { // ensure the input is read correctly
                String suggest = input.readLine();

                //Yes they want to suggest
                if (suggest.equalsIgnoreCase("yes") || suggest.equalsIgnoreCase("y")) {
                    validInput = true;

                    //Ask if they wish to see their hand
                    seeHand(input, player);

                    //Get the weapon and character they wish to suggest
                    WeaponCard weapon = checkWeapon(input);
                    CharacterCard character = checkCharacter(input);
                    //Clear the screen, move the weapon and character into that room
                    System.out.println(CLEAR_SCREEN);
                    board.teleportCharacter(character, room);
                    board.teleportWeapon(weapon, room);
                    board.printBoard();

                    //Check if the suggestion is disputed by any character
                    Card dispute = checkSuggestion(player, weapon, character, room.getRoomCard(), input);
                    if (dispute != null) { //A dispute has occured
                        System.out.printf("%s, your suggestion has been refuted with the following card: %s. \n", player.getCharacter().toString(), dispute.toString());
                    } else { // No dispute
                        System.out.printf("%s, your suggestion has not been refuted.", player.getCharacter().toString());
                    }
                }
                //They do not want to make a suggestion
                else if (suggest.equalsIgnoreCase("no") || suggest.equalsIgnoreCase("n")) {
                    return;
                }
                //Unclear answer, ask again.
                else {
                    System.out.println("Please answer with Y or N.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error Processing Suggestion" + e);
        }
    }

    /**
     * Checks if anyone can refute the suggestion that has just been made.
     *
     * Loops through all the characters, even those who have made a false accusation,
     * (Other than the player who made the suggestion)
     * And checks if they have any card matching the suggestion.
     *
     * If they have one, they have to play it
     * If they have more than one, they get to choose which they would like to play
     *
     * @param player the current player
     * @param weapon the suggested murder weapon
     * @param character the suggested murderer
     * @param room the suggested murder location
     * @param input the buffered reader to process input from the user
     * @return the card that is used to refute the suggestion (null if no refuting occurred)
     */
    private Card checkSuggestion(Player player, WeaponCard weapon, CharacterCard character, RoomCard room, BufferedReader input) {
        try {
            for (Player p : players) {
                if (p != player) { // skip the player who made the suggestion

                    //get the players hand
                    Set<Card> hand = p.getHand();
                    List<Card> suggestions = new ArrayList<>();

                    //Check if any of the items are in the hand
                    if (hand.contains(weapon)) suggestions.add(weapon);
                    if (hand.contains(character)) suggestions.add(character);
                    if (hand.contains(room)) suggestions.add(room);


                    System.out.println(p.getCharacter() + "'s turn to check the suggestion:");
                    System.out.println("Press any letter to continue"); // for privacy reasons
                    input.readLine();
                    System.out.println(CLEAR_SCREEN);


                    System.out.println(p.returnHand());
                    System.out.printf("You have %d cards matching the suggestion\n", suggestions.size());
                    System.out.println("Press any letter to continue"); // for privacy reasons
                    input.readLine();

                    if (suggestions.size() == 0) System.out.println(CLEAR_SCREEN); // next player
                    if (suggestions.size() == 1) { // must return this card
                        System.out.println("Since you only have one card, you must use the " + suggestions.get(0).toString() + " to disprove the suggestion");
                        System.out.println("Press any letter to continue");
                        input.readLine();
                        System.out.println(CLEAR_SCREEN);
                        return suggestions.get(0);
                    }
                    if (suggestions.size() > 1) { // give them the option to choose
                        System.out.println("What card would you like to use to disprove the suggestion? ");
                        for (int i = 1; i <= suggestions.size(); i++) {
                            System.out.printf("[%d] %s \n", i, suggestions.get(i - 1).toString());
                        }

                        int dispute = -1;
                        while (dispute == -1) { // repeat until a valid input
                            try {
                                dispute = Integer.parseInt(input.readLine());
                            } catch (IOException e) {
                                System.out.println("Error on input" + e);
                            } catch (NumberFormatException n) {
                                System.out.println("Please enter a whole number only");
                            }
                        }
                        System.out.println(CLEAR_SCREEN);
                        return suggestions.get(dispute - 1);
                    }
                }
            }
            return null; // no one could disprove the suggestion, so return null
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }


    /**
     * Asks the user what they think the murder weapon is
     * Used for suggestions and accusations
     *
     * @param input the buffered reader to process input from the user
     * @return the card the player would like to suggest/accuse
     */
    private WeaponCard checkWeapon(BufferedReader input) {
        System.out.println("What do you think is the murder weapon?");

        //Prints a list of all the weapons
        List <WeaponCard> weapons = getWeapons();
        for (int weaponCount = 1; weaponCount <= weapons.size(); weaponCount++) {
            System.out.println("[" + weaponCount + "] " + weapons.get(weaponCount - 1).toString());
        }

        while (true) { //since we return this should be fine
            try {
                //Get the input and return the corresponding weapon card
                int weapon = Integer.parseInt(input.readLine());
                WeaponCard suggestedWeapon = (WeaponCard) weapons.get(weapon - 1);
                System.out.println("You have selected the weapon: " + suggestedWeapon + "\n");
                return suggestedWeapon;
            } catch (IOException e) {
                System.out.println("Error on input, please try again" + e);
            } catch (NumberFormatException n) {
                System.out.println("Please enter the number of the weapon");
            } catch (IndexOutOfBoundsException f) {
                System.out.println("Please enter a number between 1 and 6");
            }
        }
    }

    /**
     * Asks the user who they think the murderer is
     * Used for suggestions and accusations
     *
     * @param input the buffered reader to process input from the user
     * @return the card the player would like to suggest/accuse
     */
    private CharacterCard checkCharacter(BufferedReader input) {
        //Prints each of the characters
        System.out.println("Who do you think is the murderer?");
        for (int characterCount = 1; characterCount <= characters.size(); characterCount++) {
            System.out.println("[" + characterCount + "] " + characters.get(characterCount - 1).toString());
        }

        while (true) {
            try {
                //Get the input and return the corresponding character card
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

    /**
     * Asks the user what they think the murder room is
     * Used for accusations only as suggestions use the room players are in.
     *
     * @param input the buffered reader to process input from the user
     * @return the card the player would like to suggest/accuse
     */
    private RoomCard checkRoom(BufferedReader input) {
        //Prints all the rooms
        System.out.println("What do you think is the murder room?");
        for (int roomCount = 1; roomCount <= rooms.size(); roomCount++) {
            System.out.println("[" + roomCount + "] " + rooms.get(roomCount - 1).toString());
        }

        while (true) {
            try {
                //Gets the input and returns the corresponding room
                int room = Integer.parseInt(input.readLine());
                RoomCard suggestedRoom = rooms.get(room - 1).getRoomCard();
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

    /**
     *
     * @return a new list of all the weapons
     */
    public List<WeaponCard> getWeapons() {
        return new ArrayList<>(this.weapons);
    }

    /**
     * Checks if the current player wishes to make an accusation,
     * if so they ask what they think occurred in the murder.
     *
     * If this is correct, they win the game
     * If false, they are removed from being able to move/ make further accusations or suggestions.
     *
     * If they do not wish to make an accusation it simply returns
     *
     * @param input the buffered reader to process input from the user
     * @param player the current player
     * @return if the accusation was true or false
     */
    private boolean processAccusation(BufferedReader input, Player player) {
        try {
            System.out.println("Would you like to make an accusation? (Y/N)");

            boolean validInput = false; //checks for a yes or no answer
            while (!validInput) {
                String accuse = input.readLine();
                if (accuse.equalsIgnoreCase("yes") || accuse.equalsIgnoreCase("y")) {

                   //Finds out what they think occured, and returns if this is true or false
                    boolean accusation = checkAccusation(input);

                    if (accusation) { // they won, prints it for everyone to see
                        System.out.println("Congratulations, " + player.getCharacter().toString() + " has solved the murder!");
                        System.out.println("The murder occurred as follows:");
                        System.out.println(murderScenario.get(0) + " committed the crime in the " + murderScenario.get(1) + " with the " + murderScenario.get(2));
                        return true;
                    } else { // false, can no longer play
                        System.out.println("The accusation is incorrect, " + player.getCharacter().toString());
                        System.out.println("You can no longer win the game");
                        player.setIsStillPlaying(false);
                        System.out.println("Press any letter to continue");
                        input.readLine();
                        System.out.println(CLEAR_SCREEN);
                    }
                    validInput = true; // to break out of the loop
                } else if (accuse.equalsIgnoreCase("no") || accuse.equalsIgnoreCase("n")) {
                    // do not wish to make an accusation
                    validInput = true;
                }
                else { // invalid input
                    System.out.println("Please answer with Y or N.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error processing Accusation" + e);
        }
        return false; // guessed false or didnt accuse
    }

    /**
     * Checks what the player thinks happened by getting the murderer, murder weapon and location
     * And checking this against the murder scenario
     *
     * @param input the buffered reader to process input from the user
     * @return if the accusation made was correct or not
     */
    private boolean checkAccusation(BufferedReader input) {
        CharacterCard character = checkCharacter(input);
        WeaponCard weapon = checkWeapon(input);
        RoomCard room = checkRoom(input);

        return (character.equals(murderScenario.get(0)) && room.equals(murderScenario.get(1)) && weapon.equals(murderScenario.get(2)));
    }

    /**
     * Return board object for testing
     * @return
     */
    public Board getBoard(){
        return board;
    }

    /**
     * Exception used to check the correct number of players are playing.
     */
    private class IncorrectNumberOfPlayersException extends Throwable {
    }

    /**
     * A simple main, making a new game.
     * @param args
     */
    public static void main(String args[]) {
        new Game();
    }

}


