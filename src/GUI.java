
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


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
            frame.dispose();
            new Game();
        });

        quitMenuItem = new JMenuItem("Quit");
        quitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to quit?","Warning",dialogButton);
                if(dialogResult == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });

        JFrame.setDefaultLookAndFeelDecorated(true);

        menuBar.add(restartMenuItem);
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
                playerNum++;
                label.setText("Player "+playerNum + ", choose your character:");
                frame.repaint();
                setCharacter(mustard.getText());
            }
        });
        JRadioButton white = new JRadioButton("Mrs White");
        white.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                white.setEnabled(false);
                playerNum++;
                label.setText("Player "+playerNum + ", choose your character:");
                frame.repaint();
                setCharacter(white.getText());
            }
        });
        JRadioButton green = new JRadioButton("Rev. Green");
        green.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                green.setEnabled(false);
                playerNum++;
                label.setText("Player "+playerNum + ", choose your character:");
                frame.repaint();
                setCharacter(green.getText());
            }
        });
        JRadioButton turquoise = new JRadioButton("Ms Turquoise");
        turquoise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turquoise.setEnabled(false);
                playerNum++;
                label.setText("Player "+playerNum + ", choose your character:");
                frame.repaint();
                setCharacter(turquoise.getText());
            }
        });
        JRadioButton plum = new JRadioButton("Prof. Plum");
        plum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plum.setEnabled(false);
                playerNum++;
                label.setText("Player "+playerNum + ", choose your character:");
                frame.repaint();
                setCharacter(plum.getText());
            }
        });
        JRadioButton red = new JRadioButton("Miss Red");
        red.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                red.setEnabled(false);
                playerNum++;
                label.setText("Player "+playerNum + ", choose your character:");
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

        frame.getContentPane().add(controls, BorderLayout.PAGE_START);
        frame.pack();
        frame.revalidate();
        frame.repaint();
    }

    public void setupGameplay(){
        frame.setSize(new Dimension(800,1000));
        Screen screen = new Screen();
        screen.setVisible(true);
        screen.setSize(new Dimension(800,800));
        JPanel controls = new JPanel();
        controls.setBackground(Color.RED);
        controls.setVisible(true);
        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerSize(0);
        splitPane.setResizeWeight(0.8);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(screen);
        splitPane.setBottomComponent(controls);

        frame.setContentPane(splitPane);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Class for main display screen
     */
    class Screen extends JPanel{
        HashMap<java.net.URL,Image> imageMap = new HashMap<>();

        public Screen(){
            super(); // Super constructor
            try{
                // Fill map with all possible images
                Image Cor = ImageIO.read(getClass().getResource("Cor.jpg"));
                imageMap.put(getClass().getResource("Cor.jpg"),Cor);
            }
            catch(Exception e){

            }
        }

        /**
         * Called when repainting
         * @param g
         */
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);

            //Divide up grid
            int colDis = this.getWidth() / 24;
            int rowDis = this.getHeight() / 25;

            // Paint images
            for(int row = 0; row < getHeight()-rowDis; row+=rowDis) {
                for (int col =0; col < getWidth()-colDis; col += colDis) {

                    //Use boardTile url to find image
                    Tile boardTile = getBoard().getBoardTile(row/rowDis,col/colDis);
                    g.drawImage(imageMap.get(boardTile.getActiveImage()), col, row,getWidth()/24,getHeight()/25,null);
                }
            }
        }
    }

    public abstract Board getBoard();
}