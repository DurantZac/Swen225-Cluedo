
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


public abstract class GUI {
    JFrame frame;
    JMenuBar menuBar;
    JMenuItem restartMenuItem;
    JMenuItem resignMenuItem;
    JMenuItem quitMenuItem;
    GridBagConstraints constraints;

    // this makes the program actually quit when the frame's close button is
    // pressed.

    public GUI(){
        menuBar = new JMenuBar();

        restartMenuItem = new JMenuItem("Restart");
        restartMenuItem.addActionListener(ev -> {
        });

        resignMenuItem = new JMenuItem("Resign");
        resignMenuItem.addActionListener(ev -> {
        });

        quitMenuItem = new JMenuItem("Quit");
        quitMenuItem.addActionListener(ev -> System.exit(0));

        JFrame.setDefaultLookAndFeelDecorated(true);

        menuBar.add(restartMenuItem);
        menuBar.add(resignMenuItem);
        menuBar.add(quitMenuItem);

        menuBar.setLayout(new GridBagLayout());


        frame = new JFrame("Cluedo");
        frame.setLayout(new GridLayout(1,2));
        frame.setSize(new Dimension(500,500));
        frame.setMinimumSize(new Dimension(500,500));
        frame.setMaximumSize(new Dimension(1920,1080));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(menuBar);

        frame.pack();
        frame.setVisible(true);

    }

    public void setupPlayerSelect(){
        JLabel label = new JLabel("How many players are playing?");
        String[] playerNum = {"3","4","5","6"};
        JComboBox players = new JComboBox(playerNum);
        players.setSelectedIndex(-1);
        players.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(players);
                frame.remove(label);
                frame.revalidate();
                frame.repaint();
                setPlayers(Integer.parseInt(players.getSelectedItem().toString()));
            }
        });
        frame.add(label);
        frame.add(players);
        frame.revalidate();
        frame.repaint();
    }

    public abstract void setPlayers(int num);

    public void chooseCharacters(){

    }
}