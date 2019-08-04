import java.io.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class Test{
    /**
     * Load initial board
     */
    @org.junit.Test
    public void test_1() {
        ByteArrayInputStream in = new ByteArrayInputStream(("3" + System.lineSeparator() + "1" + System.lineSeparator() +
                "1" + System.lineSeparator() + "1" + System.lineSeparator() + "EXIT" + System.lineSeparator()).getBytes());
        System.setIn(in);
        Game g = new Game();
        Board b = g.getBoard();
        String s = "\n" +
                "   ____ _                _       \n" +
                "  / ___| |_   _  ___  __| | ___  \n" +
                " | |   | | | | |/ _ \\/ _` |/ _ \\ \n" +
                " | |___| | |_| |  __/ (_| | (_) |\n" +
                "  \\____|_|\\__,_|\\___|\\__,_|\\___/ \n" +
                "                                 \n" +
                "a |X|X|X|X|X|X|X|X|X|\u001B[34mW\u001B[0m|#|#|#|#|\u001B[32mG\u001B[0m|X|X|X|X|X|X|X|X|X|\n" +
                "b |#|#|#|#|#|#|X|_|_|_|#|_|_|#|_|_|_|X|#|#|#|#|#|#|\n" +
                "c |K|_|_|_|_|#|_|_|#|#|A|_|_|#|#|#|_|_|#|_|_|_|_|C|\n" +
                "d |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|#|_|_|_|_|#|\n" +
                "e |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|v|_|_|_|_|#|\n" +
                "f |#|_|_|_|_|#|_|_|<|_|_|_|_|_|_|>|_|_|_|#|#|#|#|X|\n" +
                "g |X|#|#|#|v|#|_|_|#|_|_|_|_|_|_|#|_|_|_|_|_|_|_|\u001B[36mT\u001B[0m|\n" +
                "h |_|_|_|_|_|_|_|_|#|v|#|#|#|#|v|#|_|_|_|_|_|_|_|X|\n" +
                "i |X|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|\n" +
                "j |#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|<|_|_|_|_|E|\n" +
                "k |#|_|_|_|#|#|#|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "l |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "m |D|_|_|_|_|_|_|>|_|_|X|X|X|X|X|_|_|_|#|#|#|#|^|#|\n" +
                "n |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|_|_|_|_|_|X|\n" +
                "o |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|#|^|#|#|X|\n" +
                "p |#|#|#|#|#|#|v|#|_|_|X|X|X|X|X|_|_|#|#|_|_|_|_|B|\n" +
                "q |X|_|_|_|_|_|_|_|_|_|X|X|X|X|X|_|_|<|_|_|_|_|_|#|\n" +
                "r |\u001B[33mM\u001B[0m|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|_|_|_|_|#|\n" +
                "s |X|_|_|_|_|_|_|_|_|#|#|^|^|#|#|_|_|_|#|#|#|#|#|X|\n" +
                "t |#|#|#|#|#|#|^|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|\u001B[35mP\u001B[0m|\n" +
                "u |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|X|\n" +
                "v |L|_|_|_|_|_|#|_|_|#|_|_|_|_|>|_|_|^|S|#|#|#|#|#|\n" +
                "w |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "x |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "y |#|#|#|#|#|#|#|\u001B[31mR\u001B[0m|X|#|#|H|#|#|#|X|_|#|#|#|#|#|#|#|\n" +
                "   A B C D E F G H I J K L M N O P Q R S T U V W X\n";
        String sNoCol = "\n" +
                "   ____ _                _       \n" +
                "  / ___| |_   _  ___  __| | ___  \n" +
                " | |   | | | | |/ _ \\/ _` |/ _ \\ \n" +
                " | |___| | |_| |  __/ (_| | (_) |\n" +
                "  \\____|_|\\__,_|\\___|\\__,_|\\___/ \n" +
                "                                 \n" +
                "a |X|X|X|X|X|X|X|X|X|W|#|#|#|#|G|X|X|X|X|X|X|X|X|X|\n" +
                "b |#|#|#|#|#|#|X|_|_|_|#|_|_|#|_|_|_|X|#|#|#|#|#|#|\n" +
                "c |K|_|_|_|_|#|_|_|#|#|A|_|_|#|#|#|_|_|#|_|_|_|_|C|\n" +
                "d |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|#|_|_|_|_|#|\n" +
                "e |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|v|_|_|_|_|#|\n" +
                "f |#|_|_|_|_|#|_|_|<|_|_|_|_|_|_|>|_|_|_|#|#|#|#|X|\n" +
                "g |X|#|#|#|v|#|_|_|#|_|_|_|_|_|_|#|_|_|_|_|_|_|_|T|\n" +
                "h |_|_|_|_|_|_|_|_|#|v|#|#|#|#|v|#|_|_|_|_|_|_|_|X|\n" +
                "i |X|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|\n" +
                "j |#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|<|_|_|_|_|E|\n" +
                "k |#|_|_|_|#|#|#|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "l |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "m |D|_|_|_|_|_|_|>|_|_|X|X|X|X|X|_|_|_|#|#|#|#|^|#|\n" +
                "n |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|_|_|_|_|_|X|\n" +
                "o |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|#|^|#|#|X|\n" +
                "p |#|#|#|#|#|#|v|#|_|_|X|X|X|X|X|_|_|#|#|_|_|_|_|B|\n" +
                "q |X|_|_|_|_|_|_|_|_|_|X|X|X|X|X|_|_|<|_|_|_|_|_|#|\n" +
                "r |M|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|_|_|_|_|#|\n" +
                "s |X|_|_|_|_|_|_|_|_|#|#|^|^|#|#|_|_|_|#|#|#|#|#|X|\n" +
                "t |#|#|#|#|#|#|^|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|P|\n" +
                "u |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|X|\n" +
                "v |L|_|_|_|_|_|#|_|_|#|_|_|_|_|>|_|_|^|S|#|#|#|#|#|\n" +
                "w |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "x |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "y |#|#|#|#|#|#|#|R|X|#|#|H|#|#|#|X|_|#|#|#|#|#|#|#|\n" +
                "   A B C D E F G H I J K L M N O P Q R S T U V W X\n";
        if(!b.toString().substring(0,b.toString().indexOf("Weapon")).equals(s)){
            assertEquals(b.toString().substring(0,b.toString().indexOf("Weapon")),sNoCol);
        }
    }

    /**
     * Try play with 2 players then 3
     */
    @org.junit.Test
    public void test_2(){
        ByteArrayInputStream in = new ByteArrayInputStream(("2" + System.lineSeparator() +"3" + System.lineSeparator() + "1" + System.lineSeparator()
        +"1" + System.lineSeparator() + "1" + System.lineSeparator() + "EXIT" + System.lineSeparator()).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        new Game();
        assertTrue(out.toString().contains("Please enter a number between 3-6 only"));
    }

    /**
     * Try play with 7 players then 5
     */
    @org.junit.Test
    public void test_3(){
        ByteArrayInputStream in = new ByteArrayInputStream(("7" + System.lineSeparator() +"5" + System.lineSeparator() + "1" + System.lineSeparator()
                +"1" + System.lineSeparator() + "1" + System.lineSeparator()+ "1" + System.lineSeparator()+ "1" + System.lineSeparator() + "EXIT" + System.lineSeparator()).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        new Game();
        assertTrue(out.toString().contains("Please enter a number between 3-6 only") && out.toString().contains("Col. Mustard's turn"));
    }

    /**
     * Try select player out of range
     */
    @org.junit.Test
    public void test_4(){
        ByteArrayInputStream in = new ByteArrayInputStream(("3" + System.lineSeparator() +"0" + System.lineSeparator() + "10" + System.lineSeparator()
                +"1" + System.lineSeparator() + "6" + System.lineSeparator()+ "1" + System.lineSeparator() +"1" + System.lineSeparator()+ "EXIT" + System.lineSeparator()).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        new Game();
        assertTrue(out.toString().contains("Please enter an available number between 1 and 6") && out.toString().contains("Col. Mustard's turn"));
    }

    /**
     * Test miss scarlett first
     */
    @org.junit.Test
    public void test_5(){
        ByteArrayInputStream in = new ByteArrayInputStream(("3" + System.lineSeparator() + "1" + System.lineSeparator()
                +"1" + System.lineSeparator() + "4" +System.lineSeparator() + "EXIT" + System.lineSeparator()).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        new Game();
        assertTrue(out.toString().contains("Miss Red's turn"));
    }

    /**
     * Murder chosen and not in any hand
     */
    @org.junit.Test
    public void test_6(){
        ByteArrayInputStream in = new ByteArrayInputStream(("3" + System.lineSeparator() + "1" + System.lineSeparator()
                +"1" + System.lineSeparator() + "1" +System.lineSeparator() + "EXIT" + System.lineSeparator()).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        Game g = new Game();
        List<Card> murder = g.getMurderScenario();
        assertEquals(murder.size() ,3);
        List<Player> players = g.getPlayers();
        for(Player p : players){
            Set<Card> hand = p.getHand();
            assertTrue(Collections.disjoint(hand,murder));
        }
    }

    /**
     * Test simple moves
     */
    @org.junit.Test
    public void test_7(){
        ByteArrayInputStream in = new ByteArrayInputStream(("3" + System.lineSeparator() + "1" + System.lineSeparator()
                +"1" + System.lineSeparator() + "1" +System.lineSeparator() + "EXIT" + System.lineSeparator()).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        Game g = new Game();
        Board b = g.getBoard();
        b.movePlayer(g.getPlayers().get(0),b.getBoardTile("Gr"),6);
        b.movePlayer(g.getPlayers().get(1),b.getBoardTile("Hh"),9);
        b.movePlayer(g.getPlayers().get(2),b.getBoardTile("Qb"),3);
        String s = "\n" +
                "   ____ _                _       \n" +
                "  / ___| |_   _  ___  __| | ___  \n" +
                " | |   | | | | |/ _ \\/ _` |/ _ \\ \n" +
                " | |___| | |_| |  __/ (_| | (_) |\n" +
                "  \\____|_|\\__,_|\\___|\\__,_|\\___/ \n" +
                "                                 \n" +
                "a |X|X|X|X|X|X|X|X|X|_|#|#|#|#|_|X|X|X|X|X|X|X|X|X|\n" +
                "b |#|#|#|#|#|#|X|_|_|_|#|_|_|#|_|_|\u001B[32mG\u001B[0m|X|#|#|#|#|#|#|\n" +
                "c |K|_|_|_|_|#|_|_|#|#|A|_|_|#|#|#|_|_|#|_|_|_|_|C|\n" +
                "d |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|#|_|_|_|_|#|\n" +
                "e |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|v|_|_|_|_|#|\n" +
                "f |#|_|_|_|_|#|_|_|<|_|_|_|_|_|_|>|_|_|_|#|#|#|#|X|\n" +
                "g |X|#|#|#|v|#|_|_|#|_|_|_|_|_|_|#|_|_|_|_|_|_|_|\u001B[36mT\u001B[0m|\n" +
                "h |_|_|_|_|_|_|_|\u001B[34mW\u001B[0m|#|v|#|#|#|#|v|#|_|_|_|_|_|_|_|X|\n" +
                "i |X|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|\n" +
                "j |#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|<|_|_|_|_|E|\n" +
                "k |#|_|_|_|#|#|#|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "l |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "m |D|_|_|_|_|_|_|>|_|_|X|X|X|X|X|_|_|_|#|#|#|#|^|#|\n" +
                "n |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|_|_|_|_|_|X|\n" +
                "o |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|#|^|#|#|X|\n" +
                "p |#|#|#|#|#|#|v|#|_|_|X|X|X|X|X|_|_|#|#|_|_|_|_|B|\n" +
                "q |X|_|_|_|_|_|_|_|_|_|X|X|X|X|X|_|_|<|_|_|_|_|_|#|\n" +
                "r |_|_|_|_|_|_|\u001B[33mM\u001B[0m|_|_|_|_|_|_|_|_|_|_|#|#|_|_|_|_|#|\n" +
                "s |X|_|_|_|_|_|_|_|_|#|#|^|^|#|#|_|_|_|#|#|#|#|#|X|\n" +
                "t |#|#|#|#|#|#|^|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|\u001B[35mP\u001B[0m|\n" +
                "u |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|X|\n" +
                "v |L|_|_|_|_|_|#|_|_|#|_|_|_|_|>|_|_|^|S|#|#|#|#|#|\n" +
                "w |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "x |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "y |#|#|#|#|#|#|#|\u001B[31mR\u001B[0m|X|#|#|H|#|#|#|X|_|#|#|#|#|#|#|#|\n" +
                "   A B C D E F G H I J K L M N O P Q R S T U V W X\n";
        String sNoCol = "\n" +
                "   ____ _                _       \n" +
                "  / ___| |_   _  ___  __| | ___  \n" +
                " | |   | | | | |/ _ \\/ _` |/ _ \\ \n" +
                " | |___| | |_| |  __/ (_| | (_) |\n" +
                "  \\____|_|\\__,_|\\___|\\__,_|\\___/ \n" +
                "                                 \n" +
                "a |X|X|X|X|X|X|X|X|X|_|#|#|#|#|_|X|X|X|X|X|X|X|X|X|\n" +
                "b |#|#|#|#|#|#|X|_|_|_|#|_|_|#|_|_|G|X|#|#|#|#|#|#|\n" +
                "c |K|_|_|_|_|#|_|_|#|#|A|_|_|#|#|#|_|_|#|_|_|_|_|C|\n" +
                "d |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|#|_|_|_|_|#|\n" +
                "e |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|v|_|_|_|_|#|\n" +
                "f |#|_|_|_|_|#|_|_|<|_|_|_|_|_|_|>|_|_|_|#|#|#|#|X|\n" +
                "g |X|#|#|#|v|#|_|_|#|_|_|_|_|_|_|#|_|_|_|_|_|_|_|T|\n" +
                "h |_|_|_|_|_|_|_|W|#|v|#|#|#|#|v|#|_|_|_|_|_|_|_|X|\n" +
                "i |X|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|\n" +
                "j |#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|<|_|_|_|_|E|\n" +
                "k |#|_|_|_|#|#|#|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "l |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "m |D|_|_|_|_|_|_|>|_|_|X|X|X|X|X|_|_|_|#|#|#|#|^|#|\n" +
                "n |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|_|_|_|_|_|X|\n" +
                "o |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|#|^|#|#|X|\n" +
                "p |#|#|#|#|#|#|v|#|_|_|X|X|X|X|X|_|_|#|#|_|_|_|_|B|\n" +
                "q |X|_|_|_|_|_|_|_|_|_|X|X|X|X|X|_|_|<|_|_|_|_|_|#|\n" +
                "r |_|_|_|_|_|_|M|_|_|_|_|_|_|_|_|_|_|#|#|_|_|_|_|#|\n" +
                "s |X|_|_|_|_|_|_|_|_|#|#|^|^|#|#|_|_|_|#|#|#|#|#|X|\n" +
                "t |#|#|#|#|#|#|^|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|P|\n" +
                "u |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|X|\n" +
                "v |L|_|_|_|_|_|#|_|_|#|_|_|_|_|>|_|_|^|S|#|#|#|#|#|\n" +
                "w |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "x |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "y |#|#|#|#|#|#|#|R|X|#|#|H|#|#|#|X|_|#|#|#|#|#|#|#|\n" +
                "   A B C D E F G H I J K L M N O P Q R S T U V W X\n";
        if(!b.toString().substring(0,b.toString().indexOf("Weapon")).equals(s)){
            assertEquals(b.toString().substring(0,b.toString().indexOf("Weapon")),sNoCol);
        }
    }

    /**
     * Cards dealt evenly with 6 players receiving 3 each
     */
    @org.junit.Test
    public void test_8(){
        ByteArrayInputStream in = new ByteArrayInputStream(("6" + System.lineSeparator() + "1" + System.lineSeparator()
                +"1" + System.lineSeparator() + "1" +System.lineSeparator()+ "1" +System.lineSeparator()+ "1" +System.lineSeparator()+ "1"
                +System.lineSeparator() + "EXIT" + System.lineSeparator()).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        Game g = new Game();
        List<Player> players = g.getPlayers();
        for(Player p : players){
            Set<Card> hand = p.getHand();
            assertEquals(hand.size(),3);
        }
    }

    /**
     * Cards dealt evenly with 4 players, first 2 players receiving 5, other 2 players receiving 4
     */
    @org.junit.Test
    public void test_9(){
        ByteArrayInputStream in = new ByteArrayInputStream(("4" + System.lineSeparator() + "1" + System.lineSeparator()
                +"1" + System.lineSeparator() + "1" +System.lineSeparator()+ "1"
                +System.lineSeparator() + "EXIT" + System.lineSeparator()).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        Game g = new Game();
        List<Player> players = g.getPlayers();
        for(int i = 0; i < players.size(); i++){
            Set<Card> hand = players.get(i).getHand();
            if(i < 2) assertEquals(hand.size(),5);
            else assertEquals(hand.size(),4);
        }
    }

    /**
     * Suggestion allowed when in room
     */
    @org.junit.Test
    public void test_10(){
        ByteArrayInputStream in = new ByteArrayInputStream(("3" + System.lineSeparator() + "1" + System.lineSeparator()
                +"1" + System.lineSeparator() + "1" +System.lineSeparator() + "EXIT" + System.lineSeparator()).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        Game g = new Game();
        Board b = g.getBoard();
        b.movePlayer(g.getPlayers().get(0),b.getBoardTile("Gt"),8);
        in =  new ByteArrayInputStream(("Y" + System.lineSeparator() + "EXIT" + System.lineSeparator()).getBytes());
        g.processSuggestion(new BufferedReader(new InputStreamReader(in)),g.getPlayers().get(0));
        assertTrue(out.toString().contains("Would you like to make a suggestion? (Y/N)"));
    }

    /**
     * Suggestion not allowed when not in room
     */
    @org.junit.Test
    public void test_11(){
        ByteArrayInputStream in = new ByteArrayInputStream(("3" + System.lineSeparator() + "1" + System.lineSeparator()
                +"1" + System.lineSeparator() + "1" +System.lineSeparator() + "EXIT" + System.lineSeparator()).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        Game g = new Game();
        Board b = g.getBoard();
        b.movePlayer(g.getPlayers().get(0),b.getBoardTile("Gs"),7);
        in =  new ByteArrayInputStream(("Y" + System.lineSeparator() + "EXIT" + System.lineSeparator()).getBytes());
        g.processSuggestion(new BufferedReader(new InputStreamReader(in)),g.getPlayers().get(0));
        assertFalse(out.toString().contains("Would you like to make a suggestion? (Y/N)"));
    }

    /**
     * Suggestion refuted
     */
    @org.junit.Test
    public void test_12(){
        ByteArrayInputStream in = new ByteArrayInputStream(("3" + System.lineSeparator() + "1" + System.lineSeparator()
                +"1" + System.lineSeparator() + "1"  +System.lineSeparator()+ "EXIT" + System.lineSeparator()).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        Game g = new Game();
        Board b = g.getBoard();
        b.movePlayer(g.getPlayers().get(0),b.getBoardTile("Gt"),8);
        Set<Card> hand = g.getPlayers().get(1).getHand();
        int weaponIndex;
        int charIndex;
        if(hand.contains(g.getPlayers().get(0).getPosition().getIsPartOf().getRoomCard())){
            charIndex = 1;
            weaponIndex = 1;
            in =  new ByteArrayInputStream(("Y" + System.lineSeparator() + "N" + System.lineSeparator() + weaponIndex + System.lineSeparator() + charIndex + System.lineSeparator() +"1"+ System.lineSeparator()
                    + "1" +System.lineSeparator() + "1" + System.lineSeparator() + "1"  + System.lineSeparator()).getBytes());
        }
        else{
            g.getCharacters().removeAll(g.getPlayers().get(0).getHand());
            g.getCharacters().removeAll(g.getMurderScenario());
            charIndex = g.getCharacters().get(0).getIndex();
            weaponIndex = 1;
            in =  new ByteArrayInputStream(("Y" + System.lineSeparator() + "N" + System.lineSeparator() + weaponIndex + System.lineSeparator() + charIndex + System.lineSeparator() + "1" + System.lineSeparator()
                    + "1" + System.lineSeparator() + "N" + System.lineSeparator() + "EXIT"  + System.lineSeparator()+"1" + System.lineSeparator()+"1" + System.lineSeparator()+"1" + System.lineSeparator()+"1" + System.lineSeparator()).getBytes());
        }
        g.processSuggestion(new BufferedReader(new InputStreamReader(in)),g.getPlayers().get(0));
        assertTrue(out.toString().contains("your suggestion has been refuted with the following card:"));
    }

    /**
     * Suggestion not refuted
     */
    @org.junit.Test
    public void test_13(){
        ByteArrayInputStream in = new ByteArrayInputStream(("5" + System.lineSeparator() + "1" + System.lineSeparator()
                +"1" + System.lineSeparator() + "1" +System.lineSeparator() + "1" +System.lineSeparator()+ "1" +System.lineSeparator()+ "EXIT" + System.lineSeparator()).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        Game g = new Game();
        Board b = g.getBoard();
        b.movePlayer(g.getPlayers().get(0),b.getBoardTile("Gt"),8);
        int weaponIndex = g.getMurderScenario().get(2).getIndex();
        int charIndex = g.getMurderScenario().get(0).getIndex();
        g.getPlayers().get(1).getHand().remove(g.getRooms().get(2).getRoomCard());
        in =  new ByteArrayInputStream(("Y" + System.lineSeparator() + "N" + System.lineSeparator() + weaponIndex + System.lineSeparator() + charIndex + System.lineSeparator() + System.lineSeparator()
                     + System.lineSeparator() + "1"+ System.lineSeparator() + "1" + System.lineSeparator() + "1" + System.lineSeparator()+ "1" + System.lineSeparator()+ "1" + System.lineSeparator()+ "1"  + System.lineSeparator()+ "1"  + System.lineSeparator()+ "1"  + System.lineSeparator()).getBytes());
        g.processSuggestion(new BufferedReader(new InputStreamReader(in)),g.getPlayers().get(0));

        assertTrue(out.toString().contains("You cannot refute the suggestion\n"));
    }

    /**
     * Incorrect murder guess
     */
    @org.junit.Test
    public void test_14(){
        ByteArrayInputStream in = new ByteArrayInputStream(("3" + System.lineSeparator() + "1" + System.lineSeparator()
                +"1" + System.lineSeparator() + "1" +System.lineSeparator() + "EXIT" + System.lineSeparator()).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        Game g = new Game();
        Board b = g.getBoard();
        b.movePlayer(g.getPlayers().get(0),b.getBoardTile("Gs"),7);
        List<Card> murder = g.getMurderScenario();
        Set<Card> murderSet = new HashSet<>(murder);
        int charIndex = getCharCardNotInSet(murderSet,g);
        int weaponIndex = getWeaponCardNotInSet(murderSet,g);
        int roomIndex = getRoomCardNotInSet (murderSet,g);
        in =  new ByteArrayInputStream(("Y" + System.lineSeparator() + charIndex + System.lineSeparator() + weaponIndex + System.lineSeparator() + roomIndex + System.lineSeparator() + System.lineSeparator()).getBytes());
        assertFalse(g.processAccusation(new BufferedReader(new InputStreamReader(in)),g.getPlayers().get(0)));
    }

    /**
     * Correct murder guess
     */
    @org.junit.Test
    public void test_15(){
        ByteArrayInputStream in = new ByteArrayInputStream(("3" + System.lineSeparator() + "1" + System.lineSeparator()
                +"1" + System.lineSeparator() + "1" +System.lineSeparator() + "EXIT" + System.lineSeparator()).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        Game g = new Game();
        Board b = g.getBoard();
        b.movePlayer(g.getPlayers().get(0),b.getBoardTile("Gs"),7);
        List<Card> murder = g.getMurderScenario();
        int charIndex = murder.get(0).getIndex();
        int weaponIndex = murder.get(2).getIndex();
        int roomIndex = murder.get(1).getIndex();
        in =  new ByteArrayInputStream(("Y" + System.lineSeparator() + charIndex + System.lineSeparator() + weaponIndex + System.lineSeparator() + roomIndex + System.lineSeparator() + System.lineSeparator()).getBytes());
        assertTrue(g.processAccusation(new BufferedReader(new InputStreamReader(in)),g.getPlayers().get(0)));
    }

    /**
     * Trapped player cannot walk through others
     */
    @org.junit.Test
    public void test_16(){
        ByteArrayInputStream in = new ByteArrayInputStream(("3" + System.lineSeparator() + "1" + System.lineSeparator()
                +"1" + System.lineSeparator() + "1" +System.lineSeparator() + "EXIT" + System.lineSeparator()).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        Game g = new Game();
        Board b = g.getBoard();
        b.movePlayer(g.getPlayers().get(0),b.getBoardTile("Hv"),11);
        b.movePlayer(g.getPlayers().get(1),b.getBoardTile("Iv"),24);
        assertFalse(b.movePlayer(g.getPlayers().get(2),b.getBoardTile("Ht"),5));
        String s = "\n" +
                "   ____ _                _       \n" +
                "  / ___| |_   _  ___  __| | ___  \n" +
                " | |   | | | | |/ _ \\/ _` |/ _ \\ \n" +
                " | |___| | |_| |  __/ (_| | (_) |\n" +
                "  \\____|_|\\__,_|\\___|\\__,_|\\___/ \n" +
                "                                 \n" +
                "a |X|X|X|X|X|X|X|X|X|_|#|#|#|#|\u001B[32mG\u001B[0m|X|X|X|X|X|X|X|X|X|\n" +
                "b |#|#|#|#|#|#|X|_|_|_|#|_|_|#|_|_|_|X|#|#|#|#|#|#|\n" +
                "c |K|_|_|_|_|#|_|_|#|#|A|_|_|#|#|#|_|_|#|_|_|_|_|C|\n" +
                "d |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|#|_|_|_|_|#|\n" +
                "e |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|v|_|_|_|_|#|\n" +
                "f |#|_|_|_|_|#|_|_|<|_|_|_|_|_|_|>|_|_|_|#|#|#|#|X|\n" +
                "g |X|#|#|#|v|#|_|_|#|_|_|_|_|_|_|#|_|_|_|_|_|_|_|\u001B[36mT\u001B[0m|\n" +
                "h |_|_|_|_|_|_|_|_|#|v|#|#|#|#|v|#|_|_|_|_|_|_|_|X|\n" +
                "i |X|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|\n" +
                "j |#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|<|_|_|_|_|E|\n" +
                "k |#|_|_|_|#|#|#|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "l |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "m |D|_|_|_|_|_|_|>|_|_|X|X|X|X|X|_|_|_|#|#|#|#|^|#|\n" +
                "n |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|_|_|_|_|_|X|\n" +
                "o |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|#|^|#|#|X|\n" +
                "p |#|#|#|#|#|#|v|#|_|_|X|X|X|X|X|_|_|#|#|_|_|_|_|B|\n" +
                "q |X|_|_|_|_|_|_|_|_|_|X|X|X|X|X|_|_|<|_|_|_|_|_|#|\n" +
                "r |_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|_|_|_|_|#|\n" +
                "s |X|_|_|_|_|_|_|_|_|#|#|^|^|#|#|_|_|_|#|#|#|#|#|X|\n" +
                "t |#|#|#|#|#|#|^|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|\u001B[35mP\u001B[0m|\n" +
                "u |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|X|\n" +
                "v |L|_|_|_|_|_|#|\u001B[33mM\u001B[0m|\u001B[34mW\u001B[0m|#|_|_|_|_|>|_|_|^|S|#|#|#|#|#|\n" +
                "w |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "x |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "y |#|#|#|#|#|#|#|\u001B[31mR\u001B[0m|X|#|#|H|#|#|#|X|_|#|#|#|#|#|#|#|\n" +
                "   A B C D E F G H I J K L M N O P Q R S T U V W X\n";
        String sNoCol = "\n" +
                "   ____ _                _       \n" +
                "  / ___| |_   _  ___  __| | ___  \n" +
                " | |   | | | | |/ _ \\/ _` |/ _ \\ \n" +
                " | |___| | |_| |  __/ (_| | (_) |\n" +
                "  \\____|_|\\__,_|\\___|\\__,_|\\___/ \n" +
                "                                 \n" +
                "a |X|X|X|X|X|X|X|X|X|_|#|#|#|#|G|X|X|X|X|X|X|X|X|X|\n" +
                "b |#|#|#|#|#|#|X|_|_|_|#|_|_|#|_|_|_|X|#|#|#|#|#|#|\n" +
                "c |K|_|_|_|_|#|_|_|#|#|A|_|_|#|#|#|_|_|#|_|_|_|_|C|\n" +
                "d |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|#|_|_|_|_|#|\n" +
                "e |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|v|_|_|_|_|#|\n" +
                "f |#|_|_|_|_|#|_|_|<|_|_|_|_|_|_|>|_|_|_|#|#|#|#|X|\n" +
                "g |X|#|#|#|v|#|_|_|#|_|_|_|_|_|_|#|_|_|_|_|_|_|_|T|\n" +
                "h |_|_|_|_|_|_|_|_|#|v|#|#|#|#|v|#|_|_|_|_|_|_|_|X|\n" +
                "i |X|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|\n" +
                "j |#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|<|_|_|_|_|E|\n" +
                "k |#|_|_|_|#|#|#|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "l |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "m |D|_|_|_|_|_|_|>|_|_|X|X|X|X|X|_|_|_|#|#|#|#|^|#|\n" +
                "n |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|_|_|_|_|_|X|\n" +
                "o |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|#|^|#|#|X|\n" +
                "p |#|#|#|#|#|#|v|#|_|_|X|X|X|X|X|_|_|#|#|_|_|_|_|B|\n" +
                "q |X|_|_|_|_|_|_|_|_|_|X|X|X|X|X|_|_|<|_|_|_|_|_|#|\n" +
                "r |_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|_|_|_|_|#|\n" +
                "s |X|_|_|_|_|_|_|_|_|#|#|^|^|#|#|_|_|_|#|#|#|#|#|X|\n" +
                "t |#|#|#|#|#|#|^|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|P|\n" +
                "u |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|X|\n" +
                "v |L|_|_|_|_|_|#|M|W|#|_|_|_|_|>|_|_|^|S|#|#|#|#|#|\n" +
                "w |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "x |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "y |#|#|#|#|#|#|#|R|X|#|#|H|#|#|#|X|_|#|#|#|#|#|#|#|\n" +
                "   A B C D E F G H I J K L M N O P Q R S T U V W X\n";
        if(!b.toString().substring(0,b.toString().indexOf("Weapon")).equals(s)){
            assertEquals(b.toString().substring(0,b.toString().indexOf("Weapon")),sNoCol);
        }
    }

    /**
     * Trapped player cannot leave room
     */
    @org.junit.Test
    public void test_17(){
        ByteArrayInputStream in = new ByteArrayInputStream(("3" + System.lineSeparator() + "1" + System.lineSeparator()
                +"5" + System.lineSeparator() + "1" +System.lineSeparator() + "EXIT" + System.lineSeparator()).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        Game g = new Game();
        Board b = g.getBoard();
        b.movePlayer(g.getPlayers().get(0),b.getBoardTile("Gt"),8);
        b.movePlayer(g.getPlayers().get(1),b.getBoardTile("Gs"),7);
        assertFalse(b.movePlayer(g.getPlayers().get(0),b.getBoardTile("Gr"),2));
    }

    /**
     * Player can skip turn
     */
    @org.junit.Test
    public void test_18(){
        ByteArrayInputStream in = new ByteArrayInputStream(("3" + System.lineSeparator() + "1" + System.lineSeparator()
                +"5" + System.lineSeparator() + "1" +System.lineSeparator() + "N" + System.lineSeparator()+ "SKIP" + System.lineSeparator()+ "N" + System.lineSeparator()+ "EXIT" + System.lineSeparator()).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        Game g = new Game();
        Board b = g.getBoard();
        String s = "\n" +
                "   ____ _                _       \n" +
                "  / ___| |_   _  ___  __| | ___  \n" +
                " | |   | | | | |/ _ \\/ _` |/ _ \\ \n" +
                " | |___| | |_| |  __/ (_| | (_) |\n" +
                "  \\____|_|\\__,_|\\___|\\__,_|\\___/ \n" +
                "                                 \n" +
                "a |X|X|X|X|X|X|X|X|X|\u001B[34mW\u001B[0m|#|#|#|#|\u001B[32mG\u001B[0m|X|X|X|X|X|X|X|X|X|\n" +
                "b |#|#|#|#|#|#|X|_|_|_|#|_|_|#|_|_|_|X|#|#|#|#|#|#|\n" +
                "c |K|_|_|_|_|#|_|_|#|#|A|_|_|#|#|#|_|_|#|_|_|_|_|C|\n" +
                "d |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|#|_|_|_|_|#|\n" +
                "e |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|v|_|_|_|_|#|\n" +
                "f |#|_|_|_|_|#|_|_|<|_|_|_|_|_|_|>|_|_|_|#|#|#|#|X|\n" +
                "g |X|#|#|#|v|#|_|_|#|_|_|_|_|_|_|#|_|_|_|_|_|_|_|\u001B[36mT\u001B[0m|\n" +
                "h |_|_|_|_|_|_|_|_|#|v|#|#|#|#|v|#|_|_|_|_|_|_|_|X|\n" +
                "i |X|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|\n" +
                "j |#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|<|_|_|_|_|E|\n" +
                "k |#|_|_|_|#|#|#|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "l |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "m |D|_|_|_|_|_|_|>|_|_|X|X|X|X|X|_|_|_|#|#|#|#|^|#|\n" +
                "n |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|_|_|_|_|_|X|\n" +
                "o |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|#|^|#|#|X|\n" +
                "p |#|#|#|#|#|#|v|#|_|_|X|X|X|X|X|_|_|#|#|_|_|_|_|B|\n" +
                "q |X|_|_|_|_|_|_|_|_|_|X|X|X|X|X|_|_|<|_|_|_|_|_|#|\n" +
                "r |\u001B[33mM\u001B[0m|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|_|_|_|_|#|\n" +
                "s |X|_|_|_|_|_|_|_|_|#|#|^|^|#|#|_|_|_|#|#|#|#|#|X|\n" +
                "t |#|#|#|#|#|#|^|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|\u001B[35mP\u001B[0m|\n" +
                "u |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|X|\n" +
                "v |L|_|_|_|_|_|#|_|_|#|_|_|_|_|>|_|_|^|S|#|#|#|#|#|\n" +
                "w |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "x |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "y |#|#|#|#|#|#|#|\u001B[31mR\u001B[0m|X|#|#|H|#|#|#|X|_|#|#|#|#|#|#|#|\n" +
                "   A B C D E F G H I J K L M N O P Q R S T U V W X\n";
        String sNoCol = "\n" +
                "   ____ _                _       \n" +
                "  / ___| |_   _  ___  __| | ___  \n" +
                " | |   | | | | |/ _ \\/ _` |/ _ \\ \n" +
                " | |___| | |_| |  __/ (_| | (_) |\n" +
                "  \\____|_|\\__,_|\\___|\\__,_|\\___/ \n" +
                "                                 \n" +
                "a |X|X|X|X|X|X|X|X|X|W|#|#|#|#|G|X|X|X|X|X|X|X|X|X|\n" +
                "b |#|#|#|#|#|#|X|_|_|_|#|_|_|#|_|_|_|X|#|#|#|#|#|#|\n" +
                "c |K|_|_|_|_|#|_|_|#|#|A|_|_|#|#|#|_|_|#|_|_|_|_|C|\n" +
                "d |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|#|_|_|_|_|#|\n" +
                "e |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|v|_|_|_|_|#|\n" +
                "f |#|_|_|_|_|#|_|_|<|_|_|_|_|_|_|>|_|_|_|#|#|#|#|X|\n" +
                "g |X|#|#|#|v|#|_|_|#|_|_|_|_|_|_|#|_|_|_|_|_|_|_|T|\n" +
                "h |_|_|_|_|_|_|_|_|#|v|#|#|#|#|v|#|_|_|_|_|_|_|_|X|\n" +
                "i |X|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|\n" +
                "j |#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|<|_|_|_|_|E|\n" +
                "k |#|_|_|_|#|#|#|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "l |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "m |D|_|_|_|_|_|_|>|_|_|X|X|X|X|X|_|_|_|#|#|#|#|^|#|\n" +
                "n |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|_|_|_|_|_|X|\n" +
                "o |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|#|^|#|#|X|\n" +
                "p |#|#|#|#|#|#|v|#|_|_|X|X|X|X|X|_|_|#|#|_|_|_|_|B|\n" +
                "q |X|_|_|_|_|_|_|_|_|_|X|X|X|X|X|_|_|<|_|_|_|_|_|#|\n" +
                "r |M|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|_|_|_|_|#|\n" +
                "s |X|_|_|_|_|_|_|_|_|#|#|^|^|#|#|_|_|_|#|#|#|#|#|X|\n" +
                "t |#|#|#|#|#|#|^|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|P|\n" +
                "u |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|X|\n" +
                "v |L|_|_|_|_|_|#|_|_|#|_|_|_|_|>|_|_|^|S|#|#|#|#|#|\n" +
                "w |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "x |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "y |#|#|#|#|#|#|#|R|X|#|#|H|#|#|#|X|_|#|#|#|#|#|#|#|\n" +
                "   A B C D E F G H I J K L M N O P Q R S T U V W X\n";
        if(!b.toString().substring(0,b.toString().indexOf("Weapon")).equals(s)){
            assertEquals(b.toString().substring(0,b.toString().indexOf("Weapon")),sNoCol);
        }
    }

    /**
     * Player can select a tile out of range
     */
    @org.junit.Test
    public void test_19(){
        ByteArrayInputStream in = new ByteArrayInputStream(("3" + System.lineSeparator() + "1" + System.lineSeparator()
                +"1" + System.lineSeparator() + "1" +System.lineSeparator() + "EXIT" + System.lineSeparator()).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        Game g = new Game();
        Board b = g.getBoard();
        assertFalse(b.movePlayer(g.getPlayers().get(0),b.getBoardTile("Gr"),5));
        assertTrue(b.movePlayer(g.getPlayers().get(0),b.getBoardTile("Fr"),5));
        String s = "\n" +
                "   ____ _                _       \n" +
                "  / ___| |_   _  ___  __| | ___  \n" +
                " | |   | | | | |/ _ \\/ _` |/ _ \\ \n" +
                " | |___| | |_| |  __/ (_| | (_) |\n" +
                "  \\____|_|\\__,_|\\___|\\__,_|\\___/ \n" +
                "                                 \n" +
                "a |X|X|X|X|X|X|X|X|X|\u001B[34mW\u001B[0m|#|#|#|#|\u001B[32mG\u001B[0m|X|X|X|X|X|X|X|X|X|\n" +
                "b |#|#|#|#|#|#|X|_|_|_|#|_|_|#|_|_|_|X|#|#|#|#|#|#|\n" +
                "c |K|_|_|_|_|#|_|_|#|#|A|_|_|#|#|#|_|_|#|_|_|_|_|C|\n" +
                "d |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|#|_|_|_|_|#|\n" +
                "e |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|v|_|_|_|_|#|\n" +
                "f |#|_|_|_|_|#|_|_|<|_|_|_|_|_|_|>|_|_|_|#|#|#|#|X|\n" +
                "g |X|#|#|#|v|#|_|_|#|_|_|_|_|_|_|#|_|_|_|_|_|_|_|\u001B[36mT\u001B[0m|\n" +
                "h |_|_|_|_|_|_|_|_|#|v|#|#|#|#|v|#|_|_|_|_|_|_|_|X|\n" +
                "i |X|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|\n" +
                "j |#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|<|_|_|_|_|E|\n" +
                "k |#|_|_|_|#|#|#|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "l |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "m |D|_|_|_|_|_|_|>|_|_|X|X|X|X|X|_|_|_|#|#|#|#|^|#|\n" +
                "n |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|_|_|_|_|_|X|\n" +
                "o |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|#|^|#|#|X|\n" +
                "p |#|#|#|#|#|#|v|#|_|_|X|X|X|X|X|_|_|#|#|_|_|_|_|B|\n" +
                "q |X|_|_|_|_|_|_|_|_|_|X|X|X|X|X|_|_|<|_|_|_|_|_|#|\n" +
                "r |_|_|_|_|_|\u001B[33mM\u001B[0m|_|_|_|_|_|_|_|_|_|_|_|#|#|_|_|_|_|#|\n" +
                "s |X|_|_|_|_|_|_|_|_|#|#|^|^|#|#|_|_|_|#|#|#|#|#|X|\n" +
                "t |#|#|#|#|#|#|^|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|\u001B[35mP\u001B[0m|\n" +
                "u |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|X|\n" +
                "v |L|_|_|_|_|_|#|_|_|#|_|_|_|_|>|_|_|^|S|#|#|#|#|#|\n" +
                "w |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "x |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "y |#|#|#|#|#|#|#|\u001B[31mR\u001B[0m|X|#|#|H|#|#|#|X|_|#|#|#|#|#|#|#|\n" +
                "   A B C D E F G H I J K L M N O P Q R S T U V W X\n";
        String sNoCol = "\n" +
                "   ____ _                _       \n" +
                "  / ___| |_   _  ___  __| | ___  \n" +
                " | |   | | | | |/ _ \\/ _` |/ _ \\ \n" +
                " | |___| | |_| |  __/ (_| | (_) |\n" +
                "  \\____|_|\\__,_|\\___|\\__,_|\\___/ \n" +
                "                                 \n" +
                "a |X|X|X|X|X|X|X|X|X|W|#|#|#|#|G|X|X|X|X|X|X|X|X|X|\n" +
                "b |#|#|#|#|#|#|X|_|_|_|#|_|_|#|_|_|_|X|#|#|#|#|#|#|\n" +
                "c |K|_|_|_|_|#|_|_|#|#|A|_|_|#|#|#|_|_|#|_|_|_|_|C|\n" +
                "d |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|#|_|_|_|_|#|\n" +
                "e |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|v|_|_|_|_|#|\n" +
                "f |#|_|_|_|_|#|_|_|<|_|_|_|_|_|_|>|_|_|_|#|#|#|#|X|\n" +
                "g |X|#|#|#|v|#|_|_|#|_|_|_|_|_|_|#|_|_|_|_|_|_|_|T|\n" +
                "h |_|_|_|_|_|_|_|_|#|v|#|#|#|#|v|#|_|_|_|_|_|_|_|X|\n" +
                "i |X|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|\n" +
                "j |#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|<|_|_|_|_|E|\n" +
                "k |#|_|_|_|#|#|#|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "l |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "m |D|_|_|_|_|_|_|>|_|_|X|X|X|X|X|_|_|_|#|#|#|#|^|#|\n" +
                "n |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|_|_|_|_|_|X|\n" +
                "o |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|#|^|#|#|X|\n" +
                "p |#|#|#|#|#|#|v|#|_|_|X|X|X|X|X|_|_|#|#|_|_|_|_|B|\n" +
                "q |X|_|_|_|_|_|_|_|_|_|X|X|X|X|X|_|_|<|_|_|_|_|_|#|\n" +
                "r |_|_|_|_|_|M|_|_|_|_|_|_|_|_|_|_|_|#|#|_|_|_|_|#|\n" +
                "s |X|_|_|_|_|_|_|_|_|#|#|^|^|#|#|_|_|_|#|#|#|#|#|X|\n" +
                "t |#|#|#|#|#|#|^|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|P|\n" +
                "u |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|X|\n" +
                "v |L|_|_|_|_|_|#|_|_|#|_|_|_|_|>|_|_|^|S|#|#|#|#|#|\n" +
                "w |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "x |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "y |#|#|#|#|#|#|#|R|X|#|#|H|#|#|#|X|_|#|#|#|#|#|#|#|\n" +
                "   A B C D E F G H I J K L M N O P Q R S T U V W X\n";
        if(!b.toString().substring(0,b.toString().indexOf("Weapon")).equals(s)){
            assertEquals(b.toString().substring(0,b.toString().indexOf("Weapon")),sNoCol);
        }    }


    /**
     * Player can type invalid tile
     */
    @org.junit.Test
    public void test_20(){
        ByteArrayInputStream in = new ByteArrayInputStream(("3" + System.lineSeparator() + "1" + System.lineSeparator()
                +"1" + System.lineSeparator() + "1" +System.lineSeparator() + "EXIT" + System.lineSeparator()).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        Game g = new Game();
        Board b = g.getBoard();
        assertFalse(b.movePlayer(g.getPlayers().get(0),b.getBoardTile("abc"),10));
        assertTrue(b.movePlayer(g.getPlayers().get(0),b.getBoardTile("Kr"),10));
        String s = "\n" +
                "   ____ _                _       \n" +
                "  / ___| |_   _  ___  __| | ___  \n" +
                " | |   | | | | |/ _ \\/ _` |/ _ \\ \n" +
                " | |___| | |_| |  __/ (_| | (_) |\n" +
                "  \\____|_|\\__,_|\\___|\\__,_|\\___/ \n" +
                "                                 \n" +
                "a |X|X|X|X|X|X|X|X|X|\u001B[34mW\u001B[0m|#|#|#|#|\u001B[32mG\u001B[0m|X|X|X|X|X|X|X|X|X|\n" +
                "b |#|#|#|#|#|#|X|_|_|_|#|_|_|#|_|_|_|X|#|#|#|#|#|#|\n" +
                "c |K|_|_|_|_|#|_|_|#|#|A|_|_|#|#|#|_|_|#|_|_|_|_|C|\n" +
                "d |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|#|_|_|_|_|#|\n" +
                "e |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|v|_|_|_|_|#|\n" +
                "f |#|_|_|_|_|#|_|_|<|_|_|_|_|_|_|>|_|_|_|#|#|#|#|X|\n" +
                "g |X|#|#|#|v|#|_|_|#|_|_|_|_|_|_|#|_|_|_|_|_|_|_|\u001B[36mT\u001B[0m|\n" +
                "h |_|_|_|_|_|_|_|_|#|v|#|#|#|#|v|#|_|_|_|_|_|_|_|X|\n" +
                "i |X|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|\n" +
                "j |#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|<|_|_|_|_|E|\n" +
                "k |#|_|_|_|#|#|#|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "l |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "m |D|_|_|_|_|_|_|>|_|_|X|X|X|X|X|_|_|_|#|#|#|#|^|#|\n" +
                "n |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|_|_|_|_|_|X|\n" +
                "o |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|#|^|#|#|X|\n" +
                "p |#|#|#|#|#|#|v|#|_|_|X|X|X|X|X|_|_|#|#|_|_|_|_|B|\n" +
                "q |X|_|_|_|_|_|_|_|_|_|X|X|X|X|X|_|_|<|_|_|_|_|_|#|\n" +
                "r |_|_|_|_|_|_|_|_|_|_|\u001B[33mM\u001B[0m|_|_|_|_|_|_|#|#|_|_|_|_|#|\n" +
                "s |X|_|_|_|_|_|_|_|_|#|#|^|^|#|#|_|_|_|#|#|#|#|#|X|\n" +
                "t |#|#|#|#|#|#|^|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|\u001B[35mP\u001B[0m|\n" +
                "u |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|X|\n" +
                "v |L|_|_|_|_|_|#|_|_|#|_|_|_|_|>|_|_|^|S|#|#|#|#|#|\n" +
                "w |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "x |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "y |#|#|#|#|#|#|#|\u001B[31mR\u001B[0m|X|#|#|H|#|#|#|X|_|#|#|#|#|#|#|#|\n" +
                "   A B C D E F G H I J K L M N O P Q R S T U V W X\n";
        String sNoCol = "\n" +
                "   ____ _                _       \n" +
                "  / ___| |_   _  ___  __| | ___  \n" +
                " | |   | | | | |/ _ \\/ _` |/ _ \\ \n" +
                " | |___| | |_| |  __/ (_| | (_) |\n" +
                "  \\____|_|\\__,_|\\___|\\__,_|\\___/ \n" +
                "                                 \n" +
                "a |X|X|X|X|X|X|X|X|X|W|#|#|#|#|G|X|X|X|X|X|X|X|X|X|\n" +
                "b |#|#|#|#|#|#|X|_|_|_|#|_|_|#|_|_|_|X|#|#|#|#|#|#|\n" +
                "c |K|_|_|_|_|#|_|_|#|#|A|_|_|#|#|#|_|_|#|_|_|_|_|C|\n" +
                "d |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|#|_|_|_|_|#|\n" +
                "e |#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|#|_|_|v|_|_|_|_|#|\n" +
                "f |#|_|_|_|_|#|_|_|<|_|_|_|_|_|_|>|_|_|_|#|#|#|#|X|\n" +
                "g |X|#|#|#|v|#|_|_|#|_|_|_|_|_|_|#|_|_|_|_|_|_|_|T|\n" +
                "h |_|_|_|_|_|_|_|_|#|v|#|#|#|#|v|#|_|_|_|_|_|_|_|X|\n" +
                "i |X|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|\n" +
                "j |#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|<|_|_|_|_|E|\n" +
                "k |#|_|_|_|#|#|#|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "l |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|_|_|_|_|#|\n" +
                "m |D|_|_|_|_|_|_|>|_|_|X|X|X|X|X|_|_|_|#|#|#|#|^|#|\n" +
                "n |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|_|_|_|_|_|X|\n" +
                "o |#|_|_|_|_|_|_|#|_|_|X|X|X|X|X|_|_|_|#|#|^|#|#|X|\n" +
                "p |#|#|#|#|#|#|v|#|_|_|X|X|X|X|X|_|_|#|#|_|_|_|_|B|\n" +
                "q |X|_|_|_|_|_|_|_|_|_|X|X|X|X|X|_|_|<|_|_|_|_|_|#|\n" +
                "r |_|_|_|_|_|_|_|_|_|_|M|_|_|_|_|_|_|#|#|_|_|_|_|#|\n" +
                "s |X|_|_|_|_|_|_|_|_|#|#|^|^|#|#|_|_|_|#|#|#|#|#|X|\n" +
                "t |#|#|#|#|#|#|^|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|P|\n" +
                "u |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|X|\n" +
                "v |L|_|_|_|_|_|#|_|_|#|_|_|_|_|>|_|_|^|S|#|#|#|#|#|\n" +
                "w |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "x |#|_|_|_|_|_|#|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|_|_|\n" +
                "y |#|#|#|#|#|#|#|R|X|#|#|H|#|#|#|X|_|#|#|#|#|#|#|#|\n" +
                "   A B C D E F G H I J K L M N O P Q R S T U V W X\n";
        if(!b.toString().substring(0,b.toString().indexOf("Weapon")).equals(s)){
            assertEquals(b.toString().substring(0,b.toString().indexOf("Weapon")),sNoCol);
        }
}

    /**
     * Player can enter room by typing door tile
     */
    @org.junit.Test
    public void test_21(){
        ByteArrayInputStream in = new ByteArrayInputStream(("3" + System.lineSeparator() + "1" + System.lineSeparator()
                +"1" + System.lineSeparator() + "1" +System.lineSeparator() + "EXIT" + System.lineSeparator()).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        Game g = new Game();
        Board b = g.getBoard();
        assertTrue(b.movePlayer(g.getPlayers().get(0),b.getBoardTile("Gt"),8));
        assertNotNull(g.getPlayers().get(0).getPosition().getIsPartOf());
    }

    /**
     * Player can enter room by typing random tile in room
     */
    @org.junit.Test
    public void test_22(){
        ByteArrayInputStream in = new ByteArrayInputStream(("3" + System.lineSeparator() + "1" + System.lineSeparator()
                +"1" + System.lineSeparator() + "1" +System.lineSeparator() + "EXIT" + System.lineSeparator()).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        Game g = new Game();
        Board b = g.getBoard();
        assertTrue(b.movePlayer(g.getPlayers().get(0),b.getBoardTile("Dw"),8));
        assertNotNull(g.getPlayers().get(0).getPosition().getIsPartOf());
    }



    // UTIL METHODS

    private int getCharCardNotInSet(Set<Card> hand,Game g){
        for(CharacterCard c : g.getCharacters()){
            if(!hand.contains(c)){
                return c.getIndex();
            }
        }
        return 0;
    }

    private int getWeaponCardNotInSet(Set<Card> hand, Game g){
        for(WeaponCard w : g.getWeapons()){
            if(!hand.contains(w)){
                return w.getIndex();
            }
        }
        return 0;
    }

    private int getRoomCardNotInSet(Set<Card> hand,Game g){
        for(Room r : g.getRooms()){
            if(!hand.contains(r.getRoomCard())){
                return r.getRoomCard().getIndex();
            }
        }
        return 0;
    }




    public static void main(String[] args){
    }
}
