
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;


public abstract class GUI {
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenuItem quitMenuItem;
    private Controls controls;
    private int playerNum = 1;
    int currentPlayer = 0;
    private JPanel diceSection = new JPanel(new GridBagLayout());
    private boolean showCards = false;
    private JButton suggest;



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
     void setupPlayerSelect(){
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
     void chooseCharacters(){
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

     void setupGameplay(){
        playerNum = 1;
        frame.setSize(new Dimension(800,1000));
        Screen screen = new Screen();
        screen.setVisible(true);
        //screen.setBackground(Color.blue);
        screen.setSize(new Dimension(800,800));


        controls = new Controls(new GridBagLayout());
        //controls.setBackground(Color.RED);
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

         Screen(){
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

                Image knife = ImageIO.read(getClass().getResource("dagger_Room.jpg"));
                imageMap.put(getClass().getResource("dagger_Room.jpg"),knife);
                Image candle = ImageIO.read(getClass().getResource("candlestick_Room.jpg"));
                imageMap.put(getClass().getResource("candlestick_Room.jpg"),candle);
                Image gun = ImageIO.read(getClass().getResource("revolver_Room.jpg"));
                imageMap.put(getClass().getResource("revolver_Room.jpg"),gun);
                Image pipe = ImageIO.read(getClass().getResource("lead_Room.jpg"));
                imageMap.put(getClass().getResource("lead_Room.jpg"),pipe);
                Image rope = ImageIO.read(getClass().getResource("rope_Room.jpg"));
                imageMap.put(getClass().getResource("rope_Room.jpg"),rope);
                Image spanner = ImageIO.read(getClass().getResource("spanner_Room.jpg"));
                imageMap.put(getClass().getResource("spanner_Room.jpg"),spanner);

                Image kitchen = ImageIO.read(getClass().getResource("card_kitchen.jpg"));
                imageMap.put(getClass().getResource("card_kitchen.jpg"),kitchen);
                Image lounge = ImageIO.read(getClass().getResource("card_kitchen.jpg"));
                imageMap.put(getClass().getResource("card_kitchen.jpg"),lounge);
                Image dining = ImageIO.read(getClass().getResource("card_kitchen.jpg"));
                imageMap.put(getClass().getResource("card_kitchen.jpg"),dining);
                Image entertainment = ImageIO.read(getClass().getResource("card_kitchen.jpg"));
                imageMap.put(getClass().getResource("card_kitchen.jpg"),entertainment);
                Image hall = ImageIO.read(getClass().getResource("card_kitchen.jpg"));
                imageMap.put(getClass().getResource("card_kitchen.jpg"),hall);
                Image book = ImageIO.read(getClass().getResource("card_kitchen.jpg"));
                imageMap.put(getClass().getResource("card_kitchen.jpg"),book);
                Image audi = ImageIO.read(getClass().getResource("card_kitchen.jpg"));
                imageMap.put(getClass().getResource("card_kitchen.jpg"),audi);
                Image con = ImageIO.read(getClass().getResource("card_kitchen.jpg"));
                imageMap.put(getClass().getResource("card_kitchen.jpg"),con);
                Image study = ImageIO.read(getClass().getResource("card_kitchen.jpg"));
                imageMap.put(getClass().getResource("card_kitchen.jpg"),study);

                this.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent e) {
                        int x = e.getX();
                        int y = e.getY();

                        int col = (int)(x/colDis);
                        int row = (int)(y/rowDis);

                        if(processMove(getBoard().getBoardTile(row,col))){
                            suggest.setEnabled(true);
                        }
                        else{
                            showInvalidMoveScreen();
                        }
                        frame.revalidate();
                        frame.repaint();
                    }
                });
            }
            catch(Exception e){
                System.out.println(e);
            }
        }

        /**
         * Called when repainting
         * @param g the graphics of the game
         */
        @SuppressWarnings("IntegerDivisionInFloatingPointContext")
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
        JLabel playerLabel;

        // Placement data for elements
        GridBagConstraints constraints = new GridBagConstraints();

        JPanel handSection;


         Controls (GridBagLayout g){
            super(g);



            constraints.ipadx = 0;
            constraints.ipady = 0;
            // Create dice section
            //diceSection.setBackground(Color.black);
            constraints.gridx = 0;
            constraints.gridy = 0;
            add(diceSection,constraints);


            //Setup hand stuff
            handSection = new JPanel(new GridLayout(2,3));
            for(int i =0; i < getPlayers().get(0).getHand().size(); i++){
                try {
                    BufferedImage myPicture = ImageIO.read(getClass().getResource("card_back.jpg"));
                    JLabel card = new JLabel(new ImageIcon(myPicture));
                    card.setSize(getWidth() / 15, getHeight() / 3);
                    handSection.add(card);
                }
                catch(IOException e){

                }
            }
            for(int i =getPlayers().get(0).getHand().size(); i < 6; i++){
                try {
                    BufferedImage myPicture = ImageIO.read(getClass().getResource("card_blank.jpg"));
                    JLabel card = new JLabel(new ImageIcon(myPicture));
                    card.setSize(getWidth() / 15, getHeight() / 3);
                    handSection.add(card);
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

            playerLabel = new JLabel(getPlayers().get(currentPlayer).getCharacter().toString() + "'s Turn");
            constraints.gridx = 1;
            constraints.gridy = 0;
            add(playerLabel,constraints);

            // Next turn button
            JButton nextTurn = new JButton("Next Turn");
            nextTurn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showCards = false;
                    nextTurn();
                    playerNum = currentPlayer +1;
                }
            });
            constraints.gridx = 2;
            this.add(nextTurn,constraints);

            // Suggest button
            suggest = new JButton("Suggest?");
            suggest.setEnabled(false);
            suggest.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    suggest.setEnabled(false);
                    if(processSuggestion()){
                        showSuggestionWindow();
                    }
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
                    //Pop up window to choose the 3 cards then call check accusation, returns if it was right
                    //boolean correct=checkAccusation(String charcter, String weapon, String room);

                    showAccusationWindow();
                }
            });
            constraints.gridy= 2;
            this.add(accuse);

            //Show/hide button
            JButton showHide = new JButton("Show/Hide");
            showHide.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showCards = !showCards;
                    if(showCards){
                        controls.showCards();
                    }
                    else{
                        controls.hideCards();
                    }
                }
            });
            constraints.gridx = 5;
            constraints.gridy = 2;
            this.add(showHide,constraints);

        }

        /**
         * Update dice 1
         * @param d1 dice roll 1
         */
         void setDice1(int d1) {
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
                System.out.println(e);
            }
        }

        /**
         * Update dice 2
         * @param d2 dice roll 2
         */
         void setDice2(int d2) {
            diceSection.remove(diceLabel2);
            this.dice2 = d2;
            try {
                BufferedImage image = ImageIO.read(getClass().getResource("roll" + dice2 + ".jpg"));
                diceLabel2 = new JLabel(new ImageIcon(image));
                diceLabel2.setSize(getWidth()/15, getHeight()/3);
                constraints.gridx = 0;
                constraints.gridy = 1;
                diceSection.add(diceLabel2,constraints);
            }catch (IOException e){

            }
        }

         void showCards(){
            handSection.removeAll();
            Player p = getPlayers().get(currentPlayer);
            try {
                Set<Card> hand = p.getHand();
                for(Card c : hand){
                    BufferedImage image = ImageIO.read(c.getImage());
                    JLabel card = new JLabel(new ImageIcon(image));
                    card.setSize(getWidth() / 15, getHeight() / 3);
                    handSection.add(card);
                }
                for(int i = hand.size(); i < 6; i++){
                    BufferedImage nullImage = ImageIO.read(getClass().getResource("card_blank.jpg"));
                    JLabel card = new JLabel(new ImageIcon(nullImage));
                    card.setSize(getWidth() / 15, getHeight() / 3);
                    handSection.add(card);
                }
                frame.revalidate();
                frame.repaint();
            }catch (IOException e){

            }
            catch (IllegalArgumentException i){
                System.out.println(i);
            }
        }

         void hideCards(){
            handSection.removeAll();
            try{
                for(int i = 0; i < getPlayers().get(currentPlayer).getHand().size(); i++){
                    BufferedImage nullImage = ImageIO.read(getClass().getResource("card_back.jpg"));
                    JLabel card = new JLabel(new ImageIcon(nullImage));
                    card.setSize(getWidth() / 15, getHeight() / 3);
                    handSection.add(card);
                }
                for(int i = getPlayers().get(currentPlayer).getHand().size(); i < 6;i++){
                    BufferedImage nullImage = ImageIO.read(getClass().getResource("card_blank.jpg"));
                    JLabel card = new JLabel(new ImageIcon(nullImage));
                    card.setSize(getWidth() / 15, getHeight() / 3);
                    handSection.add(card);
                }
                frame.revalidate();
                frame.repaint();
                frame.repaint();
            }
            catch (IOException e){

            }
        }


        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            playerLabel.setText(getPlayers().get(currentPlayer).getCharacter().toString() +"'s turn");
            repaint();
        }

    }

    public abstract Board getBoard();

    public abstract int rollDice();

    public abstract boolean processMove(Tile t);

    public abstract void nextTurn();

    public abstract boolean processSuggestion();

    public abstract List<Card> checkSuggestion(String character, String weapon);

    public abstract boolean checkAccusation(String character, String weapon, String room);

    public abstract List<Player> getPlayers();

    /**
     * Enable controls for next player and reset dice to 1's
     */
     void resetControls(){
        for(Component c : diceSection.getComponents()){
            c.setEnabled(true);
        }
        controls.setDice1(1);
        controls.setDice2(1);
        controls.hideCards();
        frame.revalidate();
        frame.repaint();
    }

    private void showSuggestionWindow(){
        JFrame popup = new JFrame();
        popup.setLayout(new GridLayout(3,2));
        popup.setVisible(true);
        popup.setSize(500,500);
        popup.setMinimumSize(new Dimension(200,200));
        JLabel character = new JLabel("Character: ");
        JLabel weapon = new JLabel("Weapon");
        String[] characters = {"Col. Mustard","Mrs White", "Rev. Green","Ms Turquoise","Prof. Plum","Miss Red"};
        String[] weapons = {"Dagger","Rope","Lead Pipe","Revolver","Candlestick","Spanner"};
        JComboBox charPick = new JComboBox(characters);
        JComboBox weapPick = new JComboBox(weapons);
        charPick.setSelectedIndex(-1);
        weapPick.setSelectedIndex(-1);

        //Submit button
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(charPick.getSelectedIndex() != -1 && weapPick.getSelectedIndex() != -1){
                    String characterString = charPick.getSelectedItem().toString();
                    String weaponString = weapPick.getSelectedItem().toString();
                    popup.dispose();
                    showRefuteWindow(checkSuggestion(characterString,weaponString));
                }
            }
        });

        popup.add(character);
        popup.add(charPick);
        popup.add(weapon);
        popup.add(weapPick);
        popup.add(submit);

        popup.pack();
        popup.revalidate();
        popup.repaint();
    }

    public void showAccusationWindow(){
        System.out.println(getMurderScenario());
        JFrame popup = new JFrame();
        popup.setLayout(new FlowLayout());
        popup.setVisible(true);
        popup.setSize(500,500);
        popup.setMinimumSize(new Dimension(200,200));
        JLabel character = new JLabel("Character: ");
        JLabel weapon = new JLabel("Weapon: ");
        JLabel room = new JLabel("Room: ");
        String[] characters = {"Col. Mustard","Mrs White", "Rev. Green","Ms Turquoise","Prof. Plum","Miss Red"};
        String[] weapons = {"Dagger","Rope","Lead Pipe","Revolver","Candlestick","Spanner"};
        String[] rooms = {"Kitchen","Lounge","Conservatory","Hall","Book Room","Study","Auditorium","Dining Room","Entertainment Room"};
        JComboBox charPick = new JComboBox(characters);
        JComboBox weapPick = new JComboBox(weapons);
        JComboBox roomPick = new JComboBox(rooms);
        charPick.setSelectedIndex(-1);
        weapPick.setSelectedIndex(-1);
        roomPick.setSelectedIndex(-1);

        //Submit button
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(charPick.getSelectedIndex() != -1 && weapPick.getSelectedIndex() != -1){
                    String characterString = charPick.getSelectedItem().toString();
                    String weaponString = weapPick.getSelectedItem().toString();
                    String roomString = roomPick.getSelectedItem().toString();
                    popup.dispose();
                    if(checkAccusation(characterString,weaponString,roomString)){
                        showWinScreen();
                    }
                    else{
                        showLoseScreen();
                    }
                }
            }
        });

        popup.add(character);
        popup.add(charPick);
        popup.add(weapon);
        popup.add(weapPick);
        popup.add(room);
        popup.add(roomPick);
        popup.add(submit);

        popup.pack();
        popup.revalidate();
        popup.repaint();
    }

    private void showLoseScreen(){
        JFrame loseScreen = new JFrame();
        loseScreen.setVisible(true);
        loseScreen.setLayout(new FlowLayout());
        JLabel message = new JLabel("That accusation was incorrect! You are out of the game!");
        loseScreen.add(message);

        JButton close = new JButton("Close");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loseScreen.dispose();
                frame.revalidate();
                frame.repaint();
            }
        });
        loseScreen.add(close);
        loseScreen.pack();
        loseScreen.revalidate();
        loseScreen.repaint();
    }


    private void showRefuteWindow(List<Card> cards){
        frame.revalidate();
        frame.repaint();
        JFrame popup = new JFrame();
        popup.setVisible(true);
        popup.setLayout(new FlowLayout());
        if(cards == null || cards.size() < 2){
            // Nobody can refute
            JLabel message = new JLabel("Nobody can refute your suggestion");
            popup.add(message);
            JButton close = new JButton("Close");
            close.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    popup.dispose();
                }
            });
            popup.add(close);

        }
        else{
            CharacterCard character = (CharacterCard)cards.get(0);
            cards.remove(0);
            JLabel charName = new JLabel(character.toString() + " please chose which card you wish to refute with.");
            popup.add(charName);
            String[] options = new String[cards.size()];
            for(int i = 0; i < cards.size(); i++){
                options[i] = cards.get(i).toString();
            }
            JComboBox optionBox = new JComboBox(options);
            optionBox.setSelectedIndex(-1);
            popup.add(optionBox);
            JButton submit = new JButton("Submit");
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String value = optionBox.getSelectedItem().toString();
                    popup.dispose();
                    JFrame showRefute = new JFrame();
                    showRefute.setVisible(true);
                    showRefute.setLayout(new FlowLayout());
                    JLabel message = new JLabel("Your suggestion has been refuted with: " + value);
                    showRefute.add(message);
                    JButton close = new JButton("Close");
                    close.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            showRefute.dispose();
                        }
                    });
                    showRefute.add(close);
                    showRefute.pack();
                    showRefute.revalidate();
                    showRefute.repaint();
                }
            });
            popup.add(submit);
        }
        popup.pack();
        popup.revalidate();
        popup.repaint();
    }

    private void showWinScreen(){
        frame.dispose();
        JFrame winScreen = new JFrame();
        winScreen.setVisible(true);
        winScreen.setLayout(new FlowLayout());

        JLabel message = new JLabel(getPlayers().get(currentPlayer).getCharacter().toString() + " wins!");
        JLabel message2 = new JLabel(getMurderScenario().get(0) + " committed the crime in the " + getMurderScenario().get(1) + " with the " + getMurderScenario().get(2));
        winScreen.add(message);
        winScreen.add(message2);

        JButton endGame = new JButton("Close Game");
        endGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        winScreen.add(endGame);

        winScreen.pack();
        winScreen.revalidate();
        winScreen.repaint();
    }

    private void showInvalidMoveScreen(){
        JFrame invalidMove = new JFrame();
        invalidMove.setVisible(true);
        invalidMove.setLayout(new FlowLayout());
        JLabel message = new JLabel("That move is not valid.");
        invalidMove.add(message);

        JButton close = new JButton("Close");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                invalidMove.dispose();
            }
        });
        invalidMove.add(close);
        invalidMove.pack();
        invalidMove.revalidate();
        invalidMove.repaint();
    }
    public abstract List<Card> getMurderScenario();
}