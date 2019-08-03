
import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class Test{
    private final PrintStream originalOut = System.out;

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

        assertEquals(b.toString().substring(0,b.toString().indexOf("Weapon")),s);
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
        Game g = new Game();
        assertTrue(out.toString().contains("Please enter a number between 3-6 only\n") && out.toString().contains("Col. Mustard's turn"));
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
        Game g = new Game();
        assertTrue(out.toString().contains("Please enter a number between 3-6 only\n") && out.toString().contains("Col. Mustard's turn"));
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
        Game g = new Game();
        assertTrue(out.toString().contains("Please enter an available number between 1 and 6\n") && out.toString().contains("Col. Mustard's turn"));
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
        Game g = new Game();
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
        assertTrue(murder.size() == 3);
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
        assertEquals(b.toString().substring(0,b.toString().indexOf("Weapon")),s);
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
            assertTrue(hand.size() == 3);
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
            if(i < 2) assertTrue(hand.size() == 5);
            else assertTrue(hand.size() == 4);
        }
    }







    public static void main(String args[]){
    }
}
