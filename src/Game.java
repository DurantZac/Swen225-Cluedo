
import java.util.*;

public class Game extends GUI {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Game Associations
    private Board board;

    private List<Card> murderScenario = new ArrayList<>();
    private int numberOfPlayers;

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public List<Card> getMurderScenario() {
        return murderScenario;
    }

    private List<Player> players = new ArrayList<>();

    private List<CharacterCard> characters = new ArrayList<>();

    private List <Card> allCards = new ArrayList<>();

    private List<CharacterCard> unusedCharacters;

    private List<Room> rooms = new ArrayList<>();
    private List<WeaponCard> weapons = new ArrayList<>();

    private static int playerToAssignCharacter = 1;

    // Store moves from rollDice event
    public int moves = 0;

    private int refutingPlayer=0;
    private CharacterCard suggestedCharacter=null;
    private RoomCard suggestedRoom=null;
    private WeaponCard suggestedWeapon=null;

    //------------------------
    // CONSTRUCTOR
    //------------------------
    /**
     * Create new game by initialising board, assigning each player a character, and starting the game.
     */
    private Game() {
        createBoard();
        setupPlayerSelect();
    }

    /**
     * Set the total number of players to input from user
     * Calls character choice gui setup
     * @param num the total numbers of players playing
     */
    @Override
    public void setPlayers(int num) {
        try {
            if (num < minimumNumberOfPlayers() || num > maximumNumberOfPlayers()) {
                throw new IncorrectNumberOfPlayersException();
            }
            numberOfPlayers = num;
        }catch (IncorrectNumberOfPlayersException i){
            System.out.println(num + " is not a valid number of players");
        }
        makeCharacterCards();
        chooseCharacters();
    }

    /**
     * Makes all the character cards,
     * adds these to a list to pick characters from,
     * followed by making the rest of the cards to be dealt
     */
    private void makeCharacterCards(){
        //Make all the characters
        characters.add(new CharacterCard("Col. Mustard", board.getBoardTile("Ar")));
        characters.add(new CharacterCard("Mrs White", board.getBoardTile("Ja")));
        characters.add(new CharacterCard("Rev. Green", board.getBoardTile("Oa")));
        characters.add(new CharacterCard("Prof. Plum", board.getBoardTile("Xt")));
        characters.add(new CharacterCard("Ms Turquoise", board.getBoardTile("Xg")));
        characters.add(new CharacterCard("Miss Red", board.getBoardTile("Hy")));
        unusedCharacters = new ArrayList<>(characters); //List for players picking characters
        allCards = createCards(characters);
    }

    /**
     * Creates the player from a string (name) that they have chosen
     * Keeps track of how many players have been made
     * once all characters are assigned to a player, it deals the cards,
     * sets up the board, and plays the game.
     * @param characterToPlay the character they want to play
     */
    public void setCharacter(String characterToPlay){
        if (playerToAssignCharacter <= numberOfPlayers){
                for (CharacterCard c : unusedCharacters) {
                    if (c.toString().equalsIgnoreCase(characterToPlay)) {
                        Player p = new Player(c);
                        players.add(p);
                        unusedCharacters.remove(c);
                        playerToAssignCharacter++;
                        break;
                    }
            }
        }
        if(playerToAssignCharacter == numberOfPlayers+1){
        //Deals hand
        dealCards(allCards);

        //Game play begins
         setupGameplay();

         //Find the player that starts
         findFirstPlayer();
        }
    }

    /**
     * Find the player that should start the game, this is Miss Red unless she isn't playing
     */
    private void findFirstPlayer(){
        //Default to 0
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getCharacter().getCharacter().equalsIgnoreCase("Miss Red")) {
                currentPlayer = i;
                break;
            }
        }
    }

    /**
     * Generate board from string
     *
     */
    private void createBoard() {

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
                "|#|_|_|_|_|_|_|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|X|" + "\n" +
                "|L|_|_|_|_|_|_|_|_|#|_|_|_|_|>|_|_|^|#|#|#|#|#|#|" + "\n" +
                "|#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|" + "\n" +
                "|#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|" + "\n" +
                "|#|#|#|#|#|#|#|R|X|#|#|H|#|#|#|X|_|#|#|#|#|#|#|S|" + "\n";

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
        board = new Board(this, tiles);
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
    private List<Card> createCards(List<CharacterCard> characters) {
        List<Card> allCards = new ArrayList<>(characters);
        // Characters
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
        List<Room> roomsShuffled = new ArrayList<>(rooms);
        Collections.shuffle(roomsShuffled);
        for (int i = 0; i < weapons.size(); i++) {
            WeaponCard w = (WeaponCard) (weapons.get(i));
            w.setLocation(roomsShuffled.get(i).getEmptySpace());
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
     * Each of the rooms is created by making the room, adding each entrance to it,
     * and adding this room to a list of cards.
     *
     * @return a list of all rooms (minus the murder room)
     */
    private List<RoomCard> createRooms() {
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
    private void markRoom(List<RoomCard> roomCards) {
        //Kitchen
        Room k = roomCards.get(0).getRoom();
        for (char r = 'b'; r < 'h'; r++) {
            for (char c = 'a'; c < 'g'; c++) {
                if(r != 'g' && c != 'a') {
                    Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
                    t.setIsPartOf(k);
                    if (!k.getEntrances().contains(t) && t.getSymbol() == '_') k.addEmptySpace(t);
                }
            }
        }

        //Dining
        Room d = roomCards.get(1).getRoom();
        for (char r = 'k'; r < 'q'; r++) {
            for (char c = 'a'; c < 'i'; c++) {
                Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
                t.setIsPartOf(d);
                if (!d.getEntrances().contains(t)&& t.getSymbol() == '_') d.addEmptySpace(t);
            }
        }
        board.getBoardTile("Aj").setIsPartOf(roomCards.get(1).getRoom());
        board.getBoardTile("Bj").setIsPartOf(roomCards.get(1).getRoom());
        board.getBoardTile("Cj").setIsPartOf(roomCards.get(1).getRoom());
        board.getBoardTile("Dj").setIsPartOf(roomCards.get(1).getRoom());
        board.getBoardTile("Ej").setIsPartOf(roomCards.get(1).getRoom());


        //Lounge
        Room l = roomCards.get(2).getRoom();
        for (char r = 't'; r < 'z'; r++) {
            for (char c = 'a'; c < 'h'; c++) {
                Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
                t.setIsPartOf(l);
                if (!l.getEntrances().contains(t)&& t.getSymbol() == '_') l.addEmptySpace(t);
            }
        }

        //Hall
        Room h = roomCards.get(3).getRoom();
        for (char r = 's'; r < 'z'; r++) {
            for (char c = 'j'; c < 'p'; c++) {
                Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
                t.setIsPartOf(h);
                if (!h.getEntrances().contains(t)&& t.getSymbol() == '_') h.addEmptySpace(t);
            }
        }

        //Study
        Room s = roomCards.get(4).getRoom();
        for (char r = 'v'; r < 'z'; r++) {
            for (char c = 'r'; c < 'y'; c++) {
                Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
                t.setIsPartOf(s);
                if (!s.getEntrances().contains(t)&& t.getSymbol() == '_') s.addEmptySpace(t);
            }
        }

        //BookRoom
        Room b = roomCards.get(5).getRoom();
        for (char r = 'o'; r < 't'; r++) {
            for (char c = 's'; c < 'y'; c++) {
                if(r == 's' && c == 'x') continue;
                if(r == 'o'&& c == 'x') continue;
                Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
                t.setIsPartOf(b);
                if (!b.getEntrances().contains(t)&& t.getSymbol() == '_') b.addEmptySpace(t);
            }
        }
        board.getBoardTile("Rp").setIsPartOf(roomCards.get(5).getRoom());
        board.getBoardTile("Rq").setIsPartOf(roomCards.get(5).getRoom());
        board.getBoardTile("Rr").setIsPartOf(roomCards.get(5).getRoom());


        //EntertainmentRoom
        Room e = roomCards.get(6).getRoom();
        for (char r = 'i'; r < 'n'; r++) {
            for (char c = 's'; c < 'y'; c++) {
                Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
                t.setIsPartOf(e);
                if (!e.getEntrances().contains(t)&& t.getSymbol() == '_') e.addEmptySpace(t);
            }
        }

        //Conservatory
        Room con = roomCards.get(7).getRoom();
        for (char r = 'b'; r < 'g'; r++) {
            for (char c = 's'; c < 'y'; c++) {
                if(r != 'f' && c != 's') {
                    Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
                    t.setIsPartOf(con);
                    if (!con.getEntrances().contains(t)&& t.getSymbol() == '_') con.addEmptySpace(t);
                }
            }
        }



        //Auditorium
        Room a = roomCards.get(8).getRoom();
        for (char r = 'c'; r < 'i'; r++) {
            for (char c = 'i'; c < 'q'; c++) {
                Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
                t.setIsPartOf(a);
                if (!a.getEntrances().contains(t)&& t.getSymbol() == '_') a.addEmptySpace(t);
            }
        }

        for(char r = 'a'; r < 'c'; r++){
            for(char c = 'k'; c < 'o'; c++){
                Tile t = board.getBoardTile((Character.toUpperCase(c)) + String.valueOf(r));
                t.setIsPartOf(a);
                if (!a.getEntrances().contains(t) && t.getSymbol() == '_') a.addEmptySpace(t);
            }
        }

        positionWalls();

    }

    /**
     * Marks the walls of the room as the correct wall shape
     * Purely stylistic
     */
    private void positionWalls(){
        Class c = getClass();

        //Library
        board.getBoardTile("Gt").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Gu").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Gv").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Gw").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Gx").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Gy").setDefaultImage(c.getResource("WBR.jpg"));

        board.getBoardTile("By").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Cy").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Dy").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Ey").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Fy").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Ay").setDefaultImage(c.getResource("WBL.jpg"));

        board.getBoardTile("Au").setDefaultImage(c.getResource("WL.jpg"));
//        board.getBoardTile("Av").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("Aw").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("Ax").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("At").setDefaultImage(c.getResource("WTL.jpg"));


        //Dining
        board.getBoardTile("Hp").setDefaultImage(c.getResource("WBR.jpg"));
        board.getBoardTile("Gp").setDefaultImage(c.getResource("Room.jpg"));
        board.getBoardTile("Fp").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Ep").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Dp").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Cp").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Bp").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Ap").setDefaultImage(c.getResource("WBL.jpg"));

        board.getBoardTile("Ak").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("Al").setDefaultImage(c.getResource("WL.jpg"));
//        board.getBoardTile("Am").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("An").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("Ao").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("Aj").setDefaultImage(c.getResource("WTL.jpg"));

        board.getBoardTile("Ej").setDefaultImage(c.getResource("WTR.jpg"));
        board.getBoardTile("Ek").setDefaultImage(c.getResource("Room.jpg"));

        board.getBoardTile("Hk").setDefaultImage(c.getResource("WTR.jpg"));
        board.getBoardTile("Fp").setDefaultImage(c.getResource("WB.jpg"));

        board.getBoardTile("Hl").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Hn").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Ho").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Hm").setDefaultImage(c.getResource("Room.jpg"));

        //Kitchen
        board.getBoardTile("Bg").setDefaultImage(c.getResource("WBL.jpg"));
        board.getBoardTile("Cg").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Dg").setDefaultImage(c.getResource("WB.jpg"));

        board.getBoardTile("Af").setDefaultImage(c.getResource("WBL.jpg"));
//        board.getBoardTile("Ac").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("Ad").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("Ae").setDefaultImage(c.getResource("WL.jpg"));

        board.getBoardTile("Ab").setDefaultImage(c.getResource("WTL.jpg"));
        board.getBoardTile("Fb").setDefaultImage(c.getResource("WTR.jpg"));

        board.getBoardTile("Fc").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Fd").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Fe").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Ff").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Fg").setDefaultImage(c.getResource("WBR.jpg"));

        //Auditorium
        board.getBoardTile("Id").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("Ie").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("If").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("Ig").setDefaultImage(c.getResource("WL.jpg"));

        board.getBoardTile("Ic").setDefaultImage(c.getResource("WTL.jpg"));
        board.getBoardTile("Ih").setDefaultImage(c.getResource("WBL.jpg"));

        board.getBoardTile("Kb").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("Ka").setDefaultImage(c.getResource("WTL.jpg"));

        board.getBoardTile("Na").setDefaultImage(c.getResource("WTR.jpg"));
        board.getBoardTile("Nb").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Nc").setDefaultImage(c.getResource("Room.jpg"));

        board.getBoardTile("Pc").setDefaultImage(c.getResource("WTR.jpg"));
        board.getBoardTile("Pd").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Pe").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Pf").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Pg").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Ph").setDefaultImage(c.getResource("WBR.jpg"));

        board.getBoardTile("Kh").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Lh").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Mh").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Nh").setDefaultImage(c.getResource("WB.jpg"));
//        board.getBoardTile("Kc").setDefaultImage(c.getResource("Room.jpg"));


        //Cons
        board.getBoardTile("Sb").setDefaultImage(c.getResource("WTL.jpg"));
        board.getBoardTile("Sc").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("Sd").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("Se").setDefaultImage(c.getResource("WL.jpg"));

        board.getBoardTile("Tf").setDefaultImage(c.getResource("WBL.jpg"));
        board.getBoardTile("Uf").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Vf").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Wf").setDefaultImage(c.getResource("WBR.jpg"));

        board.getBoardTile("Xe").setDefaultImage(c.getResource("WBR.jpg"));
        board.getBoardTile("Xd").setDefaultImage(c.getResource("WR.jpg"));
//        board.getBoardTile("Xc").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Xb").setDefaultImage(c.getResource("WTR.jpg"));


        //Entertainment Room
        board.getBoardTile("Si").setDefaultImage(c.getResource("WTL.jpg"));
        board.getBoardTile("Sk").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("Sl").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("Sm").setDefaultImage(c.getResource("WBL.jpg"));

        board.getBoardTile("Tm").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Um").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Vm").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Xm").setDefaultImage(c.getResource("WBR.jpg"));

//        board.getBoardTile("Xj").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Xk").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Xl").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Xi").setDefaultImage(c.getResource("WTR.jpg"));


        //Book Room
        board.getBoardTile("Sr").setDefaultImage(c.getResource("Room.jpg"));
        board.getBoardTile("Sp").setDefaultImage(c.getResource("Room.jpg"));

        board.getBoardTile("Rp").setDefaultImage(c.getResource("WTL.jpg"));
        board.getBoardTile("Rr").setDefaultImage(c.getResource("WBL.jpg"));

        board.getBoardTile("Ss").setDefaultImage(c.getResource("WBL.jpg"));
        board.getBoardTile("Ts").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Us").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Vs").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Ws").setDefaultImage(c.getResource("WBR.jpg"));

        board.getBoardTile("Xr").setDefaultImage(c.getResource("WBR.jpg"));
        board.getBoardTile("Xq").setDefaultImage(c.getResource("WR.jpg"));
//        board.getBoardTile("Xp").setDefaultImage(c.getResource("WTR.jpg"));

        board.getBoardTile("Wo").setDefaultImage(c.getResource("WTR.jpg"));
        board.getBoardTile("So").setDefaultImage(c.getResource("WTL.jpg"));


        //Study
        board.getBoardTile("Rv").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("Rw").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("Rx").setDefaultImage(c.getResource("WL.jpg"));

        board.getBoardTile("Ry").setDefaultImage(c.getResource("WBL.jpg"));
        board.getBoardTile("Sy").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Ty").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Uy").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Vy").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Wy").setDefaultImage(c.getResource("WB.jpg"));
//        board.getBoardTile("Xy").setDefaultImage(c.getResource("WB.jpg"));

        //Hall
        board.getBoardTile("Ky").setDefaultImage(c.getResource("WB.jpg"));
//        board.getBoardTile("Ly").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("My").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Ny").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Jy").setDefaultImage(c.getResource("WBL.jpg"));
        board.getBoardTile("Oy").setDefaultImage(c.getResource("WBR.jpg"));

        board.getBoardTile("Ox").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Ow").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Ou").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Ot").setDefaultImage(c.getResource("WR.jpg"));
        board.getBoardTile("Os").setDefaultImage(c.getResource("WTR.jpg"));
        board.getBoardTile("Js").setDefaultImage(c.getResource("WTL.jpg"));

        board.getBoardTile("Jt").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("Ju").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("Jv").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("Jw").setDefaultImage(c.getResource("WL.jpg"));
        board.getBoardTile("Jx").setDefaultImage(c.getResource("WL.jpg"));

        board.getBoardTile("If").setDefaultImage(c.getResource("Room.jpg"));
        board.getBoardTile("Pf").setDefaultImage(c.getResource("Room.jpg"));

    }

    //------------------------
    // INTERFACE
    //------------------------

    /**
     * @return the minimum number of players needed to play Cluedo.
     */
    private static int minimumNumberOfPlayers() {
        return 3;
    }

    /**
     * @return the maximum number of players needed to play Cluedo.
     */
    private static int maximumNumberOfPlayers() {
        return 6;
    }

    /**
     * Creates a "dice" which pick a random number from 1-6
     *
     * @return the roll
     */
    public int rollDice() {
        Random diceRoll1 = new Random();

        int roll1 = diceRoll1.nextInt(6); //random int from 0-5

        roll1 += 1; // add 1 to make interval 1-6

        return roll1;
    }


    /**
     * Take tile t and check if current player can reach this tile with currents moves.
     * If so, move the player, if not, return false for now.
     * Will need to use player field and moves fields that are updated.
     * Moves are updated on dice roll.
     * Player not currently updating
     * @param t Tile to move to
     * @return if the move was valid
     */
    public boolean processMove(Tile t){
        if(t == null) return false;
        if(board.movePlayer(players.get(currentPlayer),t,moves)){
            moves = 0;
            return true;
        }
        else{
            return false;
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
        if (players.stream().noneMatch(p -> p.getIsStillPlaying())) return 0;

        //Get the next player
        if (current < players.size()-1) {
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
     * turn a string into the weapon card
     * Used for suggestions and accusations
     *
     * @param weapon the string of the weapon the user would like to suggest
     * @return the card the player would like to suggest/accuse
     */
    private WeaponCard checkWeapon(String weapon) {
        //Get the input and return the corresponding weapon card
        for (WeaponCard w: weapons){
            if (w.toString().equalsIgnoreCase(weapon)){
                return w;
            }
        }
        return null;
    }

    /**
     * Asks the user who they think the murderer is
     * Used for suggestions and accusations
     *
     * @param character the string of the character card the player chose
     * @return the card the player would like to suggest/accuse
     */
    private CharacterCard checkCharacter(String character) {
        for (CharacterCard c: characters){
            if (c.toString().equalsIgnoreCase(character))
                return c;
        }
        return null;
    }

    /**
     * Asks the user what they think the murder room is
     * Used for accusations only as suggestions use the room players are in.
     *
     * @param room the room the player chose (as a string)
     * @return the card the player would like to suggest/accuse
     */
    private RoomCard checkRoom(String room) {
        for (Room r: rooms){
            if (r.toString().equalsIgnoreCase(room))
                return r.getRoomCard();
        }
        return null;
    }

    /**
     *
     * @return a new list of all the weapons
     */
     List<WeaponCard> getWeapons() {
        return new ArrayList<>(this.weapons);
    }


    /**
     * Return board object for testing
     * @return the current board
     */
    public Board getBoard(){
        return board;
    }

    /**
     * Sets the moves the current player has to the input
     * @param m number of moves a player has
     */
    public void setMoves(int m){
        moves = m;
    }

    /**
     * Gets the next player still in the game
     * Resets the controls(dice, any grayed out buttons)
     */
    public void nextTurn(){
        if(players.size() > 0) {
            currentPlayer = getNextCharacter(currentPlayer);
            resetControls();
        }
    }

    /**
     * Checks that a suggestion is allowed
     * The player has to be inside a room in order to be allowed
     * @return if the player can make a suggestion
     */
    @Override
    public boolean processSuggestion(){
        return true;
//        Room room = players.get(currentPlayer).getPosition().getIsPartOf();
//        if (room == null) { // only allow them to make suggestions in a room
//            return false;
//        }
//        return true;
    }

    /**
     * Turns two strings into the corresponding cards, saves the suggested scenario, and checks if this can be refuted
     *
     * @param character the string of the character in this suggestion
     * @param weapon the string of the weapon in this suggestion
     * @return A list of cards that refute the suggestion, null if there are none, received from 'refuteSuggestions()'
     */
    @Override
    public List<Card> checkSuggestion(String character, String weapon) {
        //Get the weapon and character they wish to suggest
        WeaponCard weaponCard = checkWeapon(weapon);
        CharacterCard characterCard = checkCharacter(character);
        board.teleportCharacter(characterCard, players.get(currentPlayer).getPosition().getIsPartOf());
        board.teleportWeapon(weaponCard, players.get(currentPlayer).getPosition().getIsPartOf());

        //Save the scenario
        suggestedCharacter = characterCard;
        suggestedWeapon = weaponCard;
        suggestedRoom = players.get(currentPlayer).getPosition().getIsPartOf().getRoomCard();

       return refuteSuggestion();
    }

    /**
     * Loops through the players, and checks if they have a card (or cards) that match the suggestion
     * If they do, the player and the refuted card is returned (with the character the first item of the list)
     * @return a List of cards matching the suggestion or Null if no player could refute it.
     */
    private List<Card> refuteSuggestion() {
        while (true) { //must return so while true is okay
            if (refutingPlayer!=currentPlayer) {
                Player player = players.get(refutingPlayer);

                //get the players hand
                Set<Card> hand = player.getHand();
                List<Card> suggestions = new ArrayList<>();

                //Check if any of the items are in the hand
                suggestions.add(player.getCharacter());
                if (hand.contains(suggestedCharacter)) suggestions.add(suggestedCharacter);
                if (hand.contains(suggestedRoom)) suggestions.add(suggestedRoom);
                if (hand.contains(suggestedWeapon)) suggestions.add(suggestedWeapon);

                if (suggestions.size()>1){
                    return suggestions;
                }
            }
                //Get the next player
                if (refutingPlayer < players.size() - 1) {
                    refutingPlayer++;
                } else {
                    refutingPlayer = 0;
                    return null;
                }
        }
    }

    /**
     * Checks what the player thinks happened by getting the murderer, murder weapon and location
     * And checking this against the murder scenario
     *
     * @param character the character to accuse
     * @param weapon the weapon to accuse
     * @param room the room to accuse
     * @return if the accusation was correct/incorrect
     */
    public boolean checkAccusation(String character, String weapon, String room) {
        CharacterCard characterCard = checkCharacter(character);
        WeaponCard weaponCard = checkWeapon(weapon);
        RoomCard roomCard = checkRoom(room);


        if (!(characterCard.equals(murderScenario.get(0)) && roomCard.equals(murderScenario.get(1)) && weaponCard.equals(murderScenario.get(2)))) {
            players.get(currentPlayer).setIsStillPlaying(false);
            nextTurn();
            if(players.stream().filter(p -> p.getIsStillPlaying() == true).count() == 1){
                showWinScreen();
            }
            return false;
        }
        return true;
    }

    /**
     * Exception used to check the correct number of players are playing.
     */
    private class IncorrectNumberOfPlayersException extends Throwable {
    }

    /**
     * A simple main, making a new game.
     * @param args arguments of the game
     */
    public static void main(String args[]) {
        new Game();
    }

}


