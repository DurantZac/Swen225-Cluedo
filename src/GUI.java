
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;


public abstract class GUI {
    JFrame frame;
    JMenuBar menuBar;
    JMenuItem quitMenuItem;
    Controls controls;
    int playerNum = 1;
    JPanel diceSection = new JPanel(new GridBagLayout());



    // this makes the program actually quit when the frame's close button is
    // pressed.

    /**
     * Create gui and add menu bar
     * Calls setup players
     */
    public GUI(){
        menuBar = new JMenuBar();

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
        screen.setBackground(Color.blue);
        screen.setSize(new Dimension(800,800));


        controls = new Controls(new GridBagLayout());
        controls.setBackground(Color.RED);
        controls.setVisible(true);

        JButton rollDice = new JButton("Roll Dice");
        rollDice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Roll dice
                int dice1 = rollDice();
                int dice2 = rollDice();

                // Pass information to game class
                setMoves(dice1 + dice2);

                // Update gui
                controls.setDice1(dice1);
                controls.setDice2(dice2);
                rollDice.setEnabled(false);
                frame.revalidate();
                frame.repaint();
            }
        });

        // Add dice button to panel
        controls.constraints.gridy = 2;
        controls.constraints.gridx = 0;
        diceSection.add(rollDice,controls.constraints);


        JSplitPane mainSplit = new JSplitPane();
        mainSplit.setDividerSize(0);
        mainSplit.setResizeWeight(0.99);
        mainSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
        mainSplit.setTopComponent(screen);
        mainSplit.setBottomComponent(controls);

        frame.setContentPane(mainSplit);
        frame.revalidate();
        frame.repaint();
    }

    public abstract void setMoves(int moves);
    /**
     * Class for main display screen
     */
    class Screen extends JPanel{
        HashMap<java.net.URL,Image> imageMap = new HashMap<>();
        //Divide up grid
        double colDis;
        double rowDis;

        public Screen(){
            super(); // Super constructor
            try{
                // Fill map with all possible images
                Image cor = ImageIO.read(getClass().getResource("Cor.jpg"));
                imageMap.put(getClass().getResource("Cor.jpg"),cor);
                Image room = ImageIO.read(getClass().getResource("Room.jpg"));
                imageMap.put(getClass().getResource("Room.jpg"),room);
                Image wt = ImageIO.read(getClass().getResource("WT.jpg"));
                imageMap.put(getClass().getResource("WT.jpg"),wt);
                Image theVoid = ImageIO.read(getClass().getResource("The_Void.jpg"));
                imageMap.put(getClass().getResource("The_Void.jpg"),theVoid);
                Image gCor = ImageIO.read(getClass().getResource("G_Cor.jpg"));
                imageMap.put(getClass().getResource("G_Cor.jpg"),gCor);
                Image gRoom = ImageIO.read(getClass().getResource("G_Room.jpg"));
                imageMap.put(getClass().getResource("G_Room.jpg"),gRoom);
                Image mCor = ImageIO.read(getClass().getResource("M_Cor.jpg"));
                imageMap.put(getClass().getResource("M_Cor.jpg"),mCor);
                Image mRoom = ImageIO.read(getClass().getResource("M_Room.jpg"));
                imageMap.put(getClass().getResource("M_Room.jpg"),mRoom);
                Image pCor = ImageIO.read(getClass().getResource("P_Cor.jpg"));
                imageMap.put(getClass().getResource("P_Cor.jpg"),pCor);
                Image pRoom = ImageIO.read(getClass().getResource("P_Room.jpg"));
                imageMap.put(getClass().getResource("P_Room.jpg"),pRoom);
                Image rCor = ImageIO.read(getClass().getResource("R_Cor.jpg"));
                imageMap.put(getClass().getResource("R_Cor.jpg"),rCor);
                Image rRoom = ImageIO.read(getClass().getResource("R_Room.jpg"));
                imageMap.put(getClass().getResource("R_Room.jpg"),rRoom);
                Image tCor = ImageIO.read(getClass().getResource("T_Cor.jpg"));
                imageMap.put(getClass().getResource("T_Cor.jpg"),tCor);
                Image tRoom = ImageIO.read(getClass().getResource("T_Room.jpg"));
                imageMap.put(getClass().getResource("T_Room.jpg"),tRoom);
                Image wCor = ImageIO.read(getClass().getResource("W_Cor.jpg"));
                imageMap.put(getClass().getResource("W_Cor.jpg"),wCor);
                Image wRoom = ImageIO.read(getClass().getResource("W_Room.jpg"));
                imageMap.put(getClass().getResource("W_Room.jpg"),wRoom);
                Image wb = ImageIO.read(getClass().getResource("WB.jpg"));
                imageMap.put(getClass().getResource("WB.jpg"),wb);
                Image wbl = ImageIO.read(getClass().getResource("WBL.jpg"));
                imageMap.put(getClass().getResource("WBL.jpg"),wbl);
                Image wbr = ImageIO.read(getClass().getResource("WBR.jpg"));
                imageMap.put(getClass().getResource("WBR.jpg"),wbr);
                Image wl = ImageIO.read(getClass().getResource("WL.jpg"));
                imageMap.put(getClass().getResource("WL.jpg"),wl);
                Image wr = ImageIO.read(getClass().getResource("WR.jpg"));
                imageMap.put(getClass().getResource("WR.jpg"),wr);
                Image wtl = ImageIO.read(getClass().getResource("WTL.jpg"));
                imageMap.put(getClass().getResource("WTL.jpg"),wtl);
                Image wtr = ImageIO.read(getClass().getResource("WTR.jpg"));
                imageMap.put(getClass().getResource("WTR.jpg"),wtr);

                this.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent e) {
                        int x = e.getX();
                        int y = e.getY();

                        int col = (int)(x/colDis);
                        int row = (int)(y/rowDis);

                        System.out.println("Col= "+ col);
                        System.out.println("Row= "+ row);
                        System.out.println();

                        processMove(getBoard().getBoardTile(row,col));
                        frame.revalidate();
                        frame.repaint();
                    }
                });
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

             colDis = this.getWidth() / 24;
             rowDis = this.getHeight() / 25;
            // Paint images
            for(int row = 0; row < getHeight()-rowDis-1; row+=rowDis) {
                for (int col =0; col < getWidth()-colDis-1; col += colDis) {

                    //Use boardTile url to find image
                    Tile boardTile = getBoard().getBoardTile(Math.min((int)(row/rowDis),24),Math.min((int)(col/colDis),23));
                    g.drawImage(imageMap.get(boardTile.getActiveImage()), col, row,getWidth()/24,getHeight()/25,null);
                }
            }
        }

    }

    class Controls extends JPanel{

        // Dice section, will be placed on left side

        // Values for each dice
        private int dice1 = 1;
        private int dice2 = 1;

        // Labels for each image
        JLabel diceLabel1 = new JLabel();
        JLabel diceLabel2 = new JLabel();

        // Placement data for elements
        GridBagConstraints constraints = new GridBagConstraints();


        public Controls (GridBagLayout g){
            super(g);



            constraints.ipadx = 0;
            constraints.ipady = 0;
            // Create dice section
            diceSection.setBackground(Color.black);
            constraints.gridx = 0;
            constraints.gridy = 0;
            add(diceSection,constraints);


            //Setup hand stuff
            JPanel handSection = new JPanel(new GridLayout(2,3));
            for(int i =0; i < 6; i++){
                try {
                    BufferedImage myPicture = ImageIO.read(getClass().getResource("card_study.jpg"));
                    JLabel test = new JLabel(new ImageIcon(myPicture));
                    test.setSize(getWidth() / 15, getHeight() / 3);
                    handSection.add(test);
                }
                catch(IOException e){

                }
            }
            handSection.setBackground(Color.CYAN);
            handSection.setVisible(true);
            constraints.gridx = 5;
            constraints.gridy=0;
            add(handSection,constraints);



            // Set initial values
            setDice1(1);
            setDice2(1);

            JLabel playerLabel = new JLabel("Player " + playerNum +"'s Turn");
            constraints.gridx = 1;
            constraints.gridy = 0;
            add(playerLabel,constraints);

            // Next turn button
            JButton nextTurn = new JButton("Next Turn");
            nextTurn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    nextTurn();
                }
            });
            constraints.gridx = 2;
            this.add(nextTurn,constraints);

            // Suggest button
            JButton suggest = new JButton("Suggest?");
            suggest.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    processSuggestion();
                }
            });
            constraints.gridx = 2;
            constraints.gridy = 1;
            this.add(suggest);

            // Accuse button
            JButton accuse = new JButton("Accuse?");
            accuse.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    processAccusation();
                }
            });
            constraints.gridy= 2;
            this.add(accuse);
        }

        /**
         * Update dice 1
         * @param d1
         */
        public void setDice1(int d1) {
            diceSection.remove(diceLabel1);
            this.dice1 = d1;
            try {
                BufferedImage myPicture = ImageIO.read(getClass().getResource("roll" + dice1 + ".jpg"));
                diceLabel1 = new JLabel(new ImageIcon(myPicture));
                diceLabel1.setSize(getWidth()/15, getHeight()/3);
                constraints.gridx = 0;
                constraints.gridy = 0;
                diceSection.add(diceLabel1,constraints);
            }catch (IOException e){

            }
        }

        /**
         * Update dice 2
         * @param d2
         */
        public void setDice2(int d2) {
            diceSection.remove(diceLabel2);
            this.dice2 = d2;
            try {
                BufferedImage myPicture = ImageIO.read(getClass().getResource("roll" + dice2 + ".jpg"));
                diceLabel2 = new JLabel(new ImageIcon(myPicture));
                diceLabel2.setSize(getWidth()/15, getHeight()/3);
                constraints.gridx = 0;
                constraints.gridy = 1;
                diceSection.add(diceLabel2,constraints);
            }catch (IOException e){

            }
        }



    }

    public abstract Board getBoard();

    public abstract int rollDice();

    public abstract boolean processMove(Tile t);

    public abstract void nextTurn();

    public abstract boolean processSuggestion();

    public abstract boolean processAccusation();

    /**
     * Enable controls for next player and reset dice to 1's
     */
    public void resetControls(){
        for(Component c : diceSection.getComponents()){
            c.setEnabled(true);
        }
        controls.setDice1(1);
        controls.setDice2(1);
        frame.revalidate();
        frame.repaint();
    }
}