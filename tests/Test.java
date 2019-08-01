
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

public class Test extends Game{

    private int rollDice(){
        return 1;
    }


    @Override
    public void playGame(BufferedReader input){
        int currentPlayer = 0; // Default player to start

        // Rule "Miss Scarlet always goes first", so check if Miss Red is playing
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getCharacter().getCharacter().equalsIgnoreCase("Miss Red")) {
                currentPlayer = i;
                break;
            }
        }

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
//            seeHand(input, players.get(currentPlayer));
//
//            //Move the player or exit loop
//            processMove(input, players.get(currentPlayer));
//
//            //Check to see if they want to make a suggestion
//            processSuggestion(input, players.get(currentPlayer));
//
//            //Check to see if they want to make an accusation
//            //If it returns true it means they correctly guessed, hence they won.
//            if (processAccusation(input, players.get(currentPlayer))) return;
//
//            //Pick the next character to play
//            System.out.println();
//            currentPlayer = getNextCharacter(currentPlayer);
//        }

        //In this case there is only one player remaining (else it would have returned above) so it must be game over, last player wins.
        System.out.printf("%s, you are the last player standing, you win! \n", players.get(currentPlayer).getCharacter());
        System.out.println("The murder occurred as follows:");
        System.out.println(murderScenario.get(0) + " committed the crime in the " + murderScenario.get(1) + " with the " + murderScenario.get(2));
    }

    @org.junit.Test
    public void test_1() {
        ByteArrayInputStream in = new ByteArrayInputStream(("3" + System.lineSeparator() + "1" + System.lineSeparator() +
                "1" + System.lineSeparator() + "1" + System.lineSeparator() + "N" + System.lineSeparator() + "EXIT" + System.lineSeparator()).getBytes());
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

    @org.junit.Test
    public void test_2(){
        ByteArrayInputStream in = new ByteArrayInputStream(("3" + System.lineSeparator() + "1" + System.lineSeparator() +
                "1" + System.lineSeparator() + "1" + System.lineSeparator() + "N" + System.lineSeparator() + "EXIT" + System.lineSeparator()).getBytes());
        System.setIn(in);
        Game g = new Game();
        Board b = g.getBoard();
    }
}
