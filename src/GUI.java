
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
    int playerNum = 1;

    // this makes the program actually quit when the frame's close button is
    // pressed.

    /**
     * Create gui and add menu bar
     * Calls setup players
     */
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
        frame.setLayout(new GridLayout(10,2));
        frame.setSize(new Dimension(500,500));
        frame.setMinimumSize(new Dimension(500,500));
        frame.setMaximumSize(new Dimension(1920,1080));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(menuBar);

        frame.pack();
        frame.setVisible(true);

    }

    /**
     * Adds gui options for selecting number of players
     */
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

    public abstract void setCharacter(String character);

    public abstract String getCharacterStrings();

    /**
     * Add gui items from selecting characters
     */
    public void chooseCharacters(){
        JLabel label = new JLabel("Player "+playerNum+ ", choose your character:");
        JPanel controls = new JPanel();
        JRadioButton mustard = new JRadioButton("Col. Mustard");
        mustard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mustard.setEnabled(false);
                frame.repaint();
                setCharacter(mustard.getText());
            }
        });
        JRadioButton white = new JRadioButton("Mrs White");
        mustard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                white.setEnabled(false);
                frame.repaint();
                setCharacter(white.getText());
            }
        });
        JRadioButton green = new JRadioButton("Rev. Green");
        mustard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                green.setEnabled(false);
                frame.repaint();
                setCharacter(green.getText());
            }
        });
        JRadioButton turquoise = new JRadioButton("Ms Turquoise");
        mustard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turquoise.setEnabled(false);
                frame.repaint();
                setCharacter(turquoise.getText());
            }
        });
        JRadioButton plum = new JRadioButton("Prof. Plum");
        mustard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plum.setEnabled(false);
                frame.repaint();
                setCharacter(plum.getText());
            }
        });
        JRadioButton red = new JRadioButton("Miss Red");
        mustard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                red.setEnabled(false);
                frame.repaint();
                setCharacter(red.getText());
            }
        });
        frame.add(label);

        controls.add(mustard);
        controls.add(white);
        controls.add(green);
        controls.add(turquoise);
        controls.add(plum);
        controls.add(red);

        for(Component c : controls.getComponents()){
            if(c instanceof JRadioButton){
                JRadioButton j = (JRadioButton)c;
                if(getCharacterStrings().contains(j.getText())){
                    c.setEnabled(false);
                }
            }
        }

        frame.getContentPane().add(controls, BorderLayout.PAGE_START);
        frame.pack();
        frame.revalidate();
        frame.repaint();
    }
}