
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;

public class Game extends GUI {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Game Associations
    private Board board;

    public List<Card> getMurderScenario() {
        return murderScenario;
    }
    private List<Card> murderScenario = new ArrayList<>();
    private int numberOfPlayers;

    @Override
    public List<Player> getPlayers() {
        return players;
    }
    private List<Player> players = new ArrayList<>();
    public List<CharacterCard> getCharacters() {
        return characters;
    }

    private List<CharacterCard> characters = new ArrayList<>();

    private List <Card> allCards = new ArrayList<>();

    public List<Room> getRooms() {
        return rooms;
    }

    List<CharacterCard> unusedCharacters;

    private List<Room> rooms = new ArrayList<>();
    private List<WeaponCard> weapons = new ArrayList<>();

    private static final String CLEAR_SCREEN = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
            "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";

    private static int playerToAssignCharacter = 1;

    // Store moves from rollDice event
    private int moves = 0;

    int refutingPlayer=-1;
    CharacterCard suggestedCharacter=null;
    RoomCard suggestedRoom=null;
    WeaponCard suggestedWeapon=null;

    //------------------------
    // CONSTRUCTOR
    //------------------------
    /**
     * Create new game by initialising board, assigning each player a character, and starting the game.
     */
    public Game() {
        createBoard();
        setupPlayerSelect();
    }

    /**
     * Set the total number of players to input from user
     * Calls character choice gui setup
     * @param num
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

         playGame();
        }
    }

    /**
     * Find the player that should start the game, this is Miss Red unless she isnt playing
     */
    private void findFirstPlayer(){
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
     * @return a new board matching the string
     */
    public void createBoard() {

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
                "|L|_|_|_|_|_|_|_|_|#|_|_|_|_|>|_|_|^|S|#|#|#|#|#|" + "\n" +
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

        positionWalls();

    }


    public void positionWalls(){
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
        board.getBoardTile("Av").setDefaultImage(c.getResource("WL.jpg"));
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
        board.getBoardTile("Am").setDefaultImage(c.getResource("WL.jpg"));
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
        board.getBoardTile("Ac").setDefaultImage(c.getResource("WL.jpg"));
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
        board.getBoardTile("Kc").setDefaultImage(c.getResource("Room.jpg"));


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
        board.getBoardTile("Xc").setDefaultImage(c.getResource("WR.jpg"));
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

        board.getBoardTile("Xj").setDefaultImage(c.getResource("WR.jpg"));
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
        board.getBoardTile("Xp").setDefaultImage(c.getResource("WTR.jpg"));

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
        board.getBoardTile("Xy").setDefaultImage(c.getResource("WB.jpg"));

        //Hall
        board.getBoardTile("Ky").setDefaultImage(c.getResource("WB.jpg"));
        board.getBoardTile("Ly").setDefaultImage(c.getResource("WB.jpg"));
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
     * Runs the main game loop
     * The game continues while players are still in the game (have not made a false accusation)
     * And the murder has not been solved.
     *
     */
    private void playGame() {
        int currentPlayer = 0; // Default player to start


//
//        while (players.stream().filter(p -> p.getIsStillPlaying() == true).count() > 1) { // Still players left to play
//            //Clear the screen and print the current board.
//            System.out.println(CLEAR_SCREEN);
//            board.printBoard();
//
//            System.out.println(players.get(currentPlayer).getCharacter() + "'s turn.");
//
//            //If they are in a room, tell them
//            Room room = players.get(currentPlayer).getPosition().getIsPartOf();
//            if (room != null) {
//                System.out.println("You are currently in the " + room);
//            }
//
//            //Check to see if they wish to see their hand
//            if(!seeHand(input, players.get(currentPlayer))) return;
//
//            //Move the player
//            boolean ableToSuggest = processMove(input, players.get(currentPlayer));
//
//            //Check to see if they want to make a suggestion
//            if (ableToSuggest) processSuggestion(input, players.get(currentPlayer));
//
//            //Check to see if they want to make an accusation
//            //If it returns true it means they correctly guessed, hence they won.
//            if (processAccusation(input, players.get(currentPlayer))) return;
//
//            //Pick the next character to play
//            System.out.println();
//            currentPlayer = getNextCharacter(currentPlayer);
//        }
//
//        //In this case there is only one player remaining (else it would have returned above) so it must be game over, last player wins.
//        System.out.printf("%s, you are the last player standing, you win! \n", players.get(currentPlayer).getCharacter());
//        System.out.println("The murder occurred as follows:");
//        System.out.println(murderScenario.get(0) + " committed the crime in the " + murderScenario.get(1) + " with the " + murderScenario.get(2));
    }

//    /**
//     * Moves the current player to a new valid location
//     *@param input buffered reader getting input from the players
//     *@param player the current player to be moved
//     * @return if they moved - hence if they can suggest while in a room
//     */
//    private boolean processMove(BufferedReader input, Player player) {
//        try {
//            //Find out how many moves the player has
//            int numMoves = rollDice();
//            System.out.println("You have " + numMoves + " moves.");
//
//            //finds out where to move to
//            System.out.println("Where would you like to move to? If you cannot move or do not wish to, type SKIP");
//            String move = input.readLine();
//            if (move.equalsIgnoreCase("skip"))
//                return false;
//
//            Tile goal = board.getBoardTile(move);
//            while (goal == null) { // ensures the tile is valid (as in 2 letters, one upper one lower)
//                System.out.println("Invalid tile, please choose again");
//                System.out.println("Where would you like to move to?");
//                move = input.readLine();
//                goal = board.getBoardTile(move);
//            }
//
//            //Finds a path and moves the player, returns true if successful
//            boolean validInput = board.movePlayer(player, goal, numMoves);
//
//            while (!validInput) { // ensures the move is valid and there is a path to it
//                System.out.println("That move is not valid, please try a different move");
//                move = input.readLine();
//                goal = board.getBoardTile(move);
//                validInput = board.movePlayer(player, goal, numMoves);
//            }
//
//            //Reprints the board with their new position.
//            board.printBoard();
//
//        } catch (IOException e) {
//            System.out.println("Error moving player" + e);
//        }
//        return true;
//    }


    /**
     * Take tile t and check if current player can reach this tile with currents moves.
     * If so, move the player, if not, return false for now.
     * Will need to use player field and moves fields that are updated.
     * Moves are updated on dice roll.
     * Player not currently updating
     * @param t Tile to move to
     * @return
     */
    public boolean processMove(Tile t){
        if(board.movePlayer(players.get(currentPlayer),t,moves)){
            moves = 0;
        }
        else{
            System.out.println("INVALID MOVE");
        }
        return false;
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
     * Offers to show the hand to the current player
     * Prints hand on screen.
     *
     * @param input buffered reader getting input from the players
     * @param p the current player
     */
    private boolean seeHand(BufferedReader input, Player p) {
        try {
            boolean validInput = false;
            while (!validInput) { // Makes sure input is clear yes or no answer
                System.out.println("Would you like to see your hand? (Y/N)");
                String hand = input.readLine();
                if(hand.equalsIgnoreCase("EXIT")) return false;
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
        return true;
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
//    public void processSuggestion(BufferedReader input, Player player) {
    //Get the weapon and character they wish to suggest
//    WeaponCard weapon = checkWeapon(input);
//    CharacterCard character = checkCharacter(input);
//    //Clear the screen, move the weapon and character into that room
//                    board.teleportCharacter(character, room);
//                    board.teleportWeapon(weapon, room);
//
//    //Check if the suggestion is disputed by any character
//    Card dispute = checkSuggestion(player, weapon, character, room.getRoomCard(), input);
//                    board.printBoard();
//                    if (dispute != null) { //A dispute has occured
//        System.out.printf("%s, your suggestion has been refuted with the following card: %s. \n", player.getCharacter().toString(), dispute.toString());
//    } else { // No dispute
//        System.out.printf("%s, your suggestion has not been refuted.", player.getCharacter().toString());
//    }

//    }

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

                    if (suggestions.size() == 0) System.out.println("You cannot refute the suggestion\n" +CLEAR_SCREEN); // next player
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
//    public boolean processAccusation(BufferedReader input, Player player) {
//        try {
//            System.out.println("Would you like to make an accusation? (Y/N)");
//
//            boolean validInput = false; //checks for a yes or no answer
//            while (!validInput) {
//                String accuse = input.readLine();
//                if (accuse.equalsIgnoreCase("yes") || accuse.equalsIgnoreCase("y")) {
//
//                   //Finds out what they think occured, and returns if this is true or false
//                    boolean accusation = checkAccusation(input);
//
//                    if (accusation) { // they won, prints it for everyone to see
//                        System.out.println("Congratulations, " + player.getCharacter().toString() + " has solved the murder!");
//                        System.out.println("The murder occurred as follows:");
//                        System.out.println(murderScenario.get(0) + " committed the crime in the " + murderScenario.get(1) + " with the " + murderScenario.get(2));
//                        return true;
//                    } else { // false, can no longer play
//                        System.out.println("The accusation is incorrect, " + player.getCharacter().toString());
//                        System.out.println("You can no longer win the game");
//                        player.setIsStillPlaying(false);
//                        System.out.println("Press any letter to continue");
//                        input.readLine();
//                        System.out.println(CLEAR_SCREEN);
//                    }
//                    validInput = true; // to break out of the loop
//                } else if (accuse.equalsIgnoreCase("no") || accuse.equalsIgnoreCase("n")) {
//                    // do not wish to make an accusation
//                    validInput = true;
//                }
//                else { // invalid input
//                    System.out.println("Please answer with Y or N.");
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("Error processing Accusation" + e);
//        }
//        return false; // guessed false or didnt accuse
//    }



    /**
     * Return board object for testing
     * @return
     */
    public Board getBoard(){
        return board;
    }

    public void setMoves(int m){
        moves = m;
    }

    public void nextTurn(){
        currentPlayer = getNextCharacter(currentPlayer);
        resetControls();
    }

    @Override
    public boolean processSuggestion(){
        Room room = players.get(currentPlayer).getPosition().getIsPartOf();
        if (room == null) { // only allow them to make suggestions in a room
            return false;
        }
        return true;
    }

    @Override
    public List<Card> checkSuggestion(String character, String weapon) {
        //Get the weapon and character they wish to suggest
        WeaponCard weaponCard = checkWeapon(weapon);
        CharacterCard characterCard = checkCharacter(character);
        board.teleportCharacter(characterCard, players.get(currentPlayer).getPosition().getIsPartOf());
        board.teleportWeapon(weaponCard, players.get(currentPlayer).getPosition().getIsPartOf());


        suggestedCharacter = characterCard;
        suggestedWeapon = weaponCard;
        suggestedRoom = players.get(currentPlayer).getPosition().getIsPartOf().getRoomCard();

       return refuteSuggestion();
    }

    public List<Card> refuteSuggestion() {
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
                    refutingPlayer = -1;
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

        return (character.equals(murderScenario.get(0)) && room.equals(murderScenario.get(1)) && weapon.equals(murderScenario.get(2)));
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


