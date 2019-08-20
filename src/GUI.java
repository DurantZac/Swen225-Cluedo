
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Gui class for displaying items and setting up events
 */
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
    JScrollPane scroller;
    JLabel title;
    JButton nextTurn = new JButton();


    public int dice1;
    public int dice2;


    // ABSTRACT METHODS
    public abstract List<Tile> getFoundPath();
    public abstract void removePathStep();
    public abstract void setPlayers(int num);
    public abstract void setCharacter(String character);
    public abstract void setMoves(int moves);
    public abstract Board getBoard();
    public abstract int rollDice();
    public abstract boolean processMove(Tile t);
    public abstract void nextTurn();
    public abstract List<Card> checkSuggestion(String character, String weapon);
    public abstract boolean checkAccusation(String character, String weapon, String room);
    public abstract List<Player> getPlayers();
    public abstract  List<CharacterCard> getCharacters();
    public abstract List<WeaponCard> getWeapons();
    public abstract List<Card> getMurderScenario();


    /**
     * Create gui and add menu bar
     * Calls setup players
     */
    public GUI(){

        //Setup title and menu bar
        title = new JLabel(new ImageIcon(getClass().getResource("title.png")));
        menuBar = new JMenuBar();

        quitMenuItem = new JMenuItem("Quit");
        quitMenuItem.addActionListener(e -> {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to quit?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION){
                System.exit(0);
            }
        });

        JFrame.setDefaultLookAndFeelDecorated(true);
        menuBar.add(quitMenuItem);
        menuBar.setLayout(new GridBagLayout());

        // Add notes button
        JMenuItem showNotes = new JMenuItem("Show Notes");
        showNotes.addActionListener(e -> showNotes());
        menuBar.add(showNotes);

        //Add key button
        JMenuItem showKey = new JMenuItem("Show Key");
        showKey.addActionListener(e -> showKey());
        menuBar.add(showKey);


        // Create main frame
        frame = new JFrame("Cluedo");
        frame.setLayout(new GridLayout(10,2));
        frame.setSize(new Dimension(600,600));
        frame.setMinimumSize(new Dimension(600,600));
        frame.setMaximumSize(new Dimension(1920,1080));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);


        // Setup action timer loop to move players.
        // Function is called every 400ms
        // If player is currently moving, this function will animate the movement.
        int delay = 400; //milliseconds
        ActionListener taskPerformer = evt -> {
            if(getFoundPath() == null || getFoundPath().size() == 0){ // no player currently moving
                nextTurn.setEnabled(true);
                return;
            }
            getBoard().takeStep(getPlayers().get(currentPlayer),getFoundPath().get(0));
            removePathStep();
        };

        Timer timer = new Timer(delay, taskPerformer);
        timer.start();
    }


    /**
     * Adds gui options for selecting number of players
     */
     void setupPlayerSelect() {
         frame.setLayout(new FlowLayout());
         title.setSize(frame.getWidth(), frame.getHeight() / 3);
         JLabel label = new JLabel("How many players are playing?");
         String[] playerNum = {"3", "4", "5", "6"};
         JComboBox players = new JComboBox(playerNum);
         players.setSelectedIndex(-1);
         players.addActionListener(e -> {
             frame.remove(players);
             frame.remove(label);
             frame.revalidate();
             frame.repaint();
             setPlayers(Integer.parseInt(players.getSelectedItem().toString()));
         });
         frame.add(title);
         frame.add(label);
         frame.add(players);
         frame.revalidate();
         frame.repaint();
     }


    /**
     * Add gui items for selecting characters
     */
     void chooseCharacters(){
        frame.remove(title);
        frame.setLayout(new GridLayout(10,2));
        JLabel label = new JLabel("Player "+playerNum+ ", choose your character:");
        JPanel controls = new JPanel();

        //Set up radio buttons
        JRadioButton mustard = new JRadioButton("Col. Mustard");
        mustard.addActionListener(e -> {
            mustard.setEnabled(false);
            playerNum++;
            label.setText("Player "+playerNum + ", choose your character:");
            frame.repaint();
            setCharacter(mustard.getText());
        });
        JRadioButton white = new JRadioButton("Mrs White");
        white.addActionListener(e -> {
            white.setEnabled(false);
            playerNum++;
            label.setText("Player "+playerNum + ", choose your character:");
            frame.repaint();
            setCharacter(white.getText());
        });
        JRadioButton green = new JRadioButton("Rev. Green");
        green.addActionListener(e -> {
            green.setEnabled(false);
            playerNum++;
            label.setText("Player "+playerNum + ", choose your character:");
            frame.repaint();
            setCharacter(green.getText());
        });
        JRadioButton turquoise = new JRadioButton("Ms Turquoise");
        turquoise.addActionListener(e -> {
            turquoise.setEnabled(false);
            playerNum++;
            label.setText("Player "+playerNum + ", choose your character:");
            frame.repaint();
            setCharacter(turquoise.getText());
        });
        JRadioButton plum = new JRadioButton("Prof. Plum");
        plum.addActionListener(e -> {
            plum.setEnabled(false);
            playerNum++;
            label.setText("Player "+playerNum + ", choose your character:");
            frame.repaint();
            setCharacter(plum.getText());
        });
        JRadioButton red = new JRadioButton("Miss Red");
        red.addActionListener(e -> {
            red.setEnabled(false);
            playerNum++;
            label.setText("Player "+playerNum + ", choose your character:");
            frame.repaint();
            setCharacter(red.getText());
        });

        // Add elements to frame
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

    /**
     * Setup gui elements for gameplay screen
     */
     void setupGameplay(){
        // Set up screen and counter
        playerNum = 1;
        frame.setSize(new Dimension(800,1000));
        Screen screen = new Screen();
        screen.setVisible(true);
        screen.setSize(new Dimension(800,800));


        //Create controls panel for dice and buttons
        controls = new Controls(new GridBagLayout());
        controls.setVisible(true);

        // Add roll dice button and event
        JButton rollDice = new JButton("Roll Dice");
        rollDice.addActionListener(e -> {

            // Roll dice
            dice1 = rollDice();
            dice2 = rollDice();

            // Pass information to game class
            setMoves(dice1 + dice2);

            // Update gui
            controls.setDice1(dice1);
            controls.setDice2(dice2);
            rollDice.setEnabled(false);
            frame.revalidate();
            frame.repaint();
        });

        // Add dice button to panel
        controls.constraints.gridy = 2;
        controls.constraints.gridx = 0;
        diceSection.add(rollDice,controls.constraints);

        //Split main frame into screen for displaying board, and controls for user actions
        JSplitPane mainSplit = new JSplitPane();
        mainSplit.setDividerSize(0);
        mainSplit.setResizeWeight(0.9);
        mainSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
        mainSplit.setTopComponent(screen);
        mainSplit.setBottomComponent(controls);
        mainSplit.setVisible(true);


        frame.setContentPane(mainSplit);
        frame.revalidate();
        frame.repaint();

    }

    /**
     * Class for main display screen
     */
    class Screen extends JPanel implements MouseMotionListener{
        HashMap<java.net.URL,Image> imageMap = new HashMap<>();

        //Divide up grid
        double colDis;
        double rowDis;

        List<JLabel> tiles = new ArrayList<JLabel>();

         Screen(){
            super(); // Super constructor
            try{
                // Fill map with all possible images that can be used on a tile

                //Corridors, Rooms and Players
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

                //Weapons
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

                //Room icons
                Image kitchen = ImageIO.read(getClass().getResource("Kitchen_Icon.jpg"));
                imageMap.put(getClass().getResource("Kitchen_Icon.jpg"),kitchen);
                Image lounge = ImageIO.read(getClass().getResource("Lounge_Icon.jpg"));
                imageMap.put(getClass().getResource("Lounge_Icon.jpg"),lounge);
                Image dining = ImageIO.read(getClass().getResource("Dining_Icon.jpg"));
                imageMap.put(getClass().getResource("Dining_Icon.jpg"),dining);
                Image entertainment = ImageIO.read(getClass().getResource("Entertainment_Icon.jpg"));
                imageMap.put(getClass().getResource("Entertainment_Icon.jpg"),entertainment);
                Image hall = ImageIO.read(getClass().getResource("Hall_Icon.jpg"));
                imageMap.put(getClass().getResource("Hall_Icon.jpg"),hall);
                Image book = ImageIO.read(getClass().getResource("Book_Icon.jpg"));
                imageMap.put(getClass().getResource("Book_Icon.jpg"),book);
                Image audi = ImageIO.read(getClass().getResource("Auditorium_Icon.jpg"));
                imageMap.put(getClass().getResource("Auditorium_Icon.jpg"),audi);
                Image con = ImageIO.read(getClass().getResource("Conservatory_Icon.jpg"));
                imageMap.put(getClass().getResource("Conservatory_Icon.jpg"),con);
                Image study = ImageIO.read(getClass().getResource("Study_Icon.jpg"));
                imageMap.put(getClass().getResource("Study_Icon.jpg"),study);

                // Add mouse listener for user to click on tiles
                this.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent e) {

                        //Get col and row
                        int x = e.getX();
                        int y = e.getY();

                        //Find corresponding tile on the grid
                        int col = (int)(x/colDis);
                        int row = (int)(y/rowDis);

                        // If move choice is valid, disable ending turn until animation is finished
                        if(processMove(getBoard().getBoardTile(row,col))){
                            nextTurn.setEnabled(false);
                            // If player has entered a room, allow suggestions
                            if(getBoard().getBoardTile(row,col).getIsPartOf() != null) {
                                suggest.setEnabled(true);
                            }
                        }
                        // If invalid, display invalid popup
                        else{
                            showInvalidMoveScreen();
                        }

                        frame.revalidate();
                        frame.repaint();
                    }
                });

                // Mouse motion listener for hover tool tips
                addMouseMotionListener(this);
                ToolTipManager.sharedInstance().setDismissDelay(500);

                for(int i = 0; i < 600; i++){
                    tiles.add(new JLabel(new ImageIcon()));
                    add(tiles.get(i));
                }

                this.setLayout(new GridLayout(25,24));

            }
            catch(Exception e){
                System.out.println(e);
            }
        }

        /**
         * Called when repainting to properly resize and repaint components
         */
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g); // Super constructor

            // Reset cursor to default
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            //Reset size of col and rows
            colDis = this.getWidth() / 24;
            rowDis = this.getHeight() / 25;

            //Re-render board
            int i =0;
            for(int row = 0; row < 25; row++){
                for(int col = 0; col < 24; col++) {
                    Image tileImage = imageMap.get(getBoard().getBoardTile(row,col).getActiveImage()).getScaledInstance(getWidth()/24,getHeight()/25, Image.SCALE_FAST);
                    tiles.get(i).setIcon(new ImageIcon(tileImage));
                    tiles.get(i).setSize(getWidth() / 24, getHeight() / 25);
                    tiles.get(i).setVisible(true);
                    i++;
                }
            }
            frame.revalidate();
            frame.repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        /**
         * Called when mouse is moved, checks if tile mouse is over
         * has special feature, if so, displays tooltip
         * @param e the mouse event
         */
        @Override
        public void mouseMoved(MouseEvent e) {
            // Reset tool tip
            setToolTipText("");

            // Get tile
            int x = e.getX();
            int y = e.getY();
            int col = (int)(x/colDis);
            int row = (int)(y/rowDis);
            Tile t = getBoard().getBoardTile(row,col);

            if(t == null) return;

            // Check if tile contains character
            for(CharacterCard c : getCharacters()){
                if(c.getPosition() == t){
                    setToolTipText(c.toString());
                }
            }
            // Check if tile contains weapon
            for(WeaponCard w : getWeapons()){
                if(w.location == t){
                    setToolTipText(w.toString());
                }
            }

            //Check if tile is an icon
            if(t.getActiveImage().toString().contains("Icon")){
                setToolTipText(t.getActiveImage().toString().substring(t.getActiveImage().toString().lastIndexOf("/") +1,t.getActiveImage().toString().lastIndexOf("_")));
            }

            frame.revalidate();
            frame.repaint();
        }
    }

    /**
     * Class for control panel
     */
    class Controls extends JPanel{

        // Values for each dice
        private int dice1 = 1;
        private int dice2 = 1;

        // Labels for each dice
        JLabel diceLabel1 = new JLabel();
        JLabel diceLabel2 = new JLabel();

        // Label for player name
        JLabel playerLabel;

        // Placement data for elements
        GridBagConstraints constraints = new GridBagConstraints();

        // Section for cards to be displayed
        JPanel handSection;


         Controls (GridBagLayout g){
            super(g);

            //Add dice section
            constraints.ipadx = 0;
            constraints.ipady = 0;
            constraints.gridx = 0;
            constraints.gridy = 0;
            add(diceSection,constraints);


            //Setup hand section
            handSection = new JPanel(new GridLayout(2,3));
            handSection.setBackground(Color.CYAN);
            handSection.setVisible(true);
            constraints.gridx = 5;
            constraints.gridy=0;
            add(handSection,constraints);


            //Setup player name label
            playerLabel = new JLabel(getPlayers().get(currentPlayer).getCharacter().toString() + "'s Turn");
            constraints.gridx = 1;
            constraints.gridy = 0;
            add(playerLabel,constraints);

            // Add next turn button
            nextTurn = new JButton("Next Turn");
            nextTurn.addActionListener(e -> {
                // Hide hand and disable suggestions
                showCards = false;
                suggest.setEnabled(false);

                //Next player
                nextTurn();
                playerNum = currentPlayer +1;
            });
            constraints.gridx = 2;
            this.add(nextTurn,constraints);

            // Add suggest button
            suggest = new JButton("Suggest?");
            suggest.setEnabled(false);
            suggest.addActionListener(e -> showSuggestionWindow());
            constraints.gridx = 2;
            constraints.gridy = 1;
            this.add(suggest);

            // Accuse button
            JButton accuse = new JButton("Accuse?");
            accuse.addActionListener(e -> {
                //Pop up window to choose the 3 cards then call check accusation, returns if it was right
                //boolean correct=checkAccusation(String character, String weapon, String room);
                showAccusationWindow();
            });
            constraints.gridy= 2;
            this.add(accuse);

            //Show/hide cards button
            JButton showHide = new JButton("Show/Hide");
            showHide.addActionListener(e -> {
                showCards = !showCards;
                if(showCards){
                    controls.showCards();
                }
                else{
                    controls.hideCards();
                }
            });
            constraints.gridx = 5;
            constraints.gridy = 2;
            this.add(showHide,constraints);

            //Create dice faces
            createDice();
        }

        /**
         * Create default dice faces
         */
        void createDice(){
            try {
                Image image = ImageIO.read(getClass().getResource("roll" + dice1 + ".jpg"));
                diceLabel1 = new JLabel(new ImageIcon(image));
                diceLabel1.setSize(getWidth()/15, getHeight()/3);
                constraints.gridx = 0;
                constraints.gridy = 0;
                diceSection.add(diceLabel1,constraints);

                image = ImageIO.read(getClass().getResource("roll" + dice2 + ".jpg"));
                diceLabel2 = new JLabel(new ImageIcon(image));
                diceLabel2.setSize(getWidth()/15, getHeight()/3);
                constraints.gridx = 0;
                constraints.gridy = 1;
                diceSection.add(diceLabel2,constraints);
            }catch (IOException e){
                System.out.println("Dice images could not be found: " + e);
            }
        }
        /**
         * Update dice 1
         * @param d1 dice roll 1
         */
         void setDice1(int d1) {
            diceSection.remove(diceLabel1);
            this.dice1 = d1;
            try {
                Image myPicture = ImageIO.read(getClass().getResource("roll" + dice1 + ".jpg")).getScaledInstance(getWidth()/12,getHeight()/3, Image.SCALE_SMOOTH);
                diceLabel1 = new JLabel(new ImageIcon(myPicture));
                diceLabel1.setSize(getWidth()/12, getHeight()/3);
                constraints.gridx = 0;
                constraints.gridy = 0;
                diceSection.add(diceLabel1,constraints);

            }catch (IOException e){
                System.out.println("Dice images could not be found: " + e);
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
                Image image = ImageIO.read(getClass().getResource("roll" + dice2 + ".jpg")).getScaledInstance(getWidth()/12,getHeight()/3, Image.SCALE_SMOOTH);
                diceLabel2 = new JLabel(new ImageIcon(image));
                diceLabel2.setSize(getWidth()/12, getHeight()/3);
                constraints.gridx = 0;
                constraints.gridy = 1;
                diceSection.add(diceLabel2,constraints);
            }catch (IOException e){
                System.out.println("Dice images could not be found: " + e);
            }
        }

        /**
         * Show players cards
         */
        void showCards(){
            handSection.removeAll();
            Player p = getPlayers().get(currentPlayer);
            try {
                Set<Card> hand = p.getHand();

                // Add cards in hand
                for(Card c : hand){
                    Image image = ImageIO.read(c.getImage()).getScaledInstance(getWidth()/10,getHeight()/3, Image.SCALE_SMOOTH);
                    JLabel card = new JLabel(new ImageIcon(image));
                    card.setSize(getWidth() / 10, getHeight() / 3);
                    handSection.add(card);
                }

                // Add blanks for remaining slots
                for(int i = hand.size(); i < 6; i++){
                    Image nullImage = ImageIO.read(getClass().getResource("card_blank.jpg")).getScaledInstance(getWidth()/10,getHeight()/3, Image.SCALE_SMOOTH);
                    JLabel card = new JLabel(new ImageIcon(nullImage));
                    card.setSize(getWidth() / 10, getHeight() / 3);
                    handSection.add(card);
                }
                frame.revalidate();
                frame.repaint();
            }catch (IOException e){
                System.out.println("Card images could not be found: " + e);
            }
        }

        /**
         * Hide players cards
         */
         void hideCards(){
            handSection.removeAll();
            try{
                // Set players cards to show card backs
                for(int i = 0; i < getPlayers().get(currentPlayer).getHand().size(); i++){
                    Image nullImage = ImageIO.read(getClass().getResource("card_back.jpg")).getScaledInstance(getWidth()/10,getHeight()/3, Image.SCALE_SMOOTH);
                    JLabel card = new JLabel(new ImageIcon(nullImage));
                    card.setSize(getWidth() / 10, getHeight() / 3);
                    handSection.add(card);
                }

                // Add blanks in remaining slots
                for(int i = getPlayers().get(currentPlayer).getHand().size(); i < 6;i++){
                    Image nullImage = ImageIO.read(getClass().getResource("card_blank.jpg")).getScaledInstance(getWidth()/10,getHeight()/3, Image.SCALE_SMOOTH);
                    JLabel card = new JLabel(new ImageIcon(nullImage));
                    card.setSize(getWidth() / 10, getHeight() / 3);
                    handSection.add(card);
                }

                frame.revalidate();
                frame.repaint();
                frame.repaint();
            }
            catch (IOException e){
                System.out.println("Card images could not be found: " + e);
            }
        }


        /**
         * Repaint and resize the controls section
         * @param g the graphics
         */
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            //Label which player's turn it is
            playerLabel.setText(getPlayers().get(currentPlayer).getCharacter().toString() +"'s turn");
            playerLabel.setSize(getWidth()/5,getHeight()/10);
            nextTurn.setSize(getWidth()/10, getHeight()/10);

            //Set the dice
            setDice1(dice1);
            setDice2(dice2);

            //show the cards
            if(showCards) showCards();
            else hideCards();

            repaint();
        }
    }


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

    /**
     * Display popup for allowing suggestions
     */
    private void showSuggestionWindow(){
        //Setup popup
        JFrame popup = new JFrame();
        popup.setLayout(new GridLayout(3,2));
        popup.setVisible(true);
        popup.setSize(500,500);
        popup.setMinimumSize(new Dimension(200,200));

        //Setup dropboxes
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
        submit.addActionListener(e -> {
            if(charPick.getSelectedIndex() != -1 && weapPick.getSelectedIndex() != -1){
                suggest.setEnabled(false);
                String characterString = charPick.getSelectedItem().toString();
                String weaponString = weapPick.getSelectedItem().toString();
                popup.dispose();
                showRefuteWindow(checkSuggestion(characterString,weaponString));
            }
        });

        // Add items
        popup.add(character);
        popup.add(charPick);
        popup.add(weapon);
        popup.add(weapPick);
        popup.add(submit);
        popup.pack();
        popup.revalidate();
        popup.repaint();
    }

    /**
     * Show accusation popup
     */
    public void showAccusationWindow(){
        // Setup popup
        JFrame popup = new JFrame();
        popup.setLayout(new FlowLayout());
        popup.setVisible(true);
        popup.setSize(500,500);
        popup.setMinimumSize(new Dimension(200,200));

        // Setup accusation options
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
        submit.addActionListener(e -> {
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
        });

        // Add items
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

    /**
     * Show incorrect accusation popup
     */
    private void showLoseScreen(){
        //Setup popup
        JFrame loseScreen = new JFrame();
        loseScreen.setVisible(true);
        loseScreen.setLayout(new FlowLayout());
        JLabel message = new JLabel("That accusation was incorrect! You are out of the game!");
        loseScreen.add(message);

        //Add close button
        JButton close = new JButton("Close");
        close.addActionListener(e -> {
            loseScreen.dispose();
            frame.revalidate();
            frame.repaint();
        });

        // Add items
        loseScreen.add(close);
        loseScreen.pack();
        loseScreen.revalidate();
        loseScreen.repaint();
    }

    /**
     * Show popup for suggestions to be refuted
     * @param cards Cards player can refute with, first card is refuting player
     */
    private void showRefuteWindow(List<Card> cards){
        frame.revalidate();
        frame.repaint();

        // Setup popup
        JFrame popup = new JFrame();
        popup.setVisible(true);
        popup.setLayout(new FlowLayout());

        // If null, not refuted
        if(cards == null || cards.size() < 2){
            // Nobody can refute
            JLabel message = new JLabel("Nobody can refute your suggestion");
            popup.add(message);
            JButton close = new JButton("Close");
            close.addActionListener(e -> popup.dispose());
            popup.add(close);

        }
        else{
            // Get refuting character
            CharacterCard character = (CharacterCard)cards.get(0);
            cards.remove(0);
            JLabel charName = new JLabel(character.toString() + " please chose which card you wish to refute with.");
            popup.add(charName);

            //Show options to refute with
            String[] options = new String[cards.size()];
            for(int i = 0; i < cards.size(); i++){
                options[i] = cards.get(i).toString();
            }
            JComboBox optionBox = new JComboBox(options);
            optionBox.setSelectedIndex(-1);
            popup.add(optionBox);

            // Add submit box and allow original player to close box
            JButton submit = new JButton("Submit");
            submit.addActionListener(e -> {
                String value = optionBox.getSelectedItem().toString();
                popup.dispose();
                JFrame showRefute = new JFrame();
                showRefute.setVisible(true);
                showRefute.setLayout(new FlowLayout());
                JLabel message = new JLabel("Your suggestion has been refuted with: " + value);
                showRefute.add(message);
                JButton close = new JButton("Close");
                close.addActionListener(e1 -> showRefute.dispose());
                showRefute.add(close);
                showRefute.pack();
                showRefute.revalidate();
                showRefute.repaint();
            });
            popup.add(submit);
        }

        popup.pack();
        popup.revalidate();
        popup.repaint();
    }

    /**
     * Show win screen from either correct accusation or last player standing
     */
    public void showWinScreen(){
        //Close main screen
        frame.dispose();

        //Set up win screen
        JFrame winScreen = new JFrame();
        winScreen.setVisible(true);
        winScreen.setLayout(new FlowLayout());

        JLabel message = new JLabel(getPlayers().get(currentPlayer).getCharacter().toString() + " wins!");
        JLabel message2 = new JLabel(getMurderScenario().get(0) + " committed the crime in the " + getMurderScenario().get(1) + " with the " + getMurderScenario().get(2));
        winScreen.add(message);
        winScreen.add(message2);

        // End game button
        JButton endGame = new JButton("Close Game");
        endGame.addActionListener(e -> System.exit(0));

        winScreen.add(endGame);
        winScreen.pack();
        winScreen.revalidate();
        winScreen.repaint();
    }

    /**
     * Show popup for invalid moves
     */
    private void showInvalidMoveScreen(){
        //Set up popup
        JFrame invalidMove = new JFrame();
        invalidMove.setVisible(true);
        invalidMove.setLayout(new FlowLayout());
        JLabel message = new JLabel("That move is not valid.");
        invalidMove.add(message);

        // Add close button
        JButton close = new JButton("Close");
        close.addActionListener(e -> invalidMove.dispose());
        invalidMove.add(close);
        invalidMove.pack();
        invalidMove.revalidate();
        invalidMove.repaint();
    }

    /**
     * Show notes screen, displaying current player's stored notes
     */
     public void showNotes(){
         if(getPlayers() == null || getPlayers().size() == 0) return;

         // Setup popup
         Player p = getPlayers().get(currentPlayer);
         JFrame notes = new JFrame();
         notes.setLayout(new GridBagLayout());
         notes.setVisible(true);
         notes.setSize(200,200);
         notes.setMinimumSize(new Dimension(400,400));

         // Build string
         StringBuilder sb = new StringBuilder();
         sb.append("<html>");
         sb.append(p.getNotes());
         sb.append("</html>");

         // Layout elements
         GridBagConstraints c = new GridBagConstraints();
         c.weightx = 1;
         c.fill = GridBagConstraints.HORIZONTAL;

         JLabel previousNotes = new JLabel(sb.toString(), SwingConstants.CENTER);

         //enable scrolling as text size increases
         scroller = new JScrollPane(previousNotes, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
         scroller.setMinimumSize(new Dimension(200,200));

         // Load text field from players notes
         JTextField textField = new JTextField();
         textField.addActionListener(e -> {
             p.setNotes(textField.getText());
             textField.setText("");
             notes.remove(scroller);

             StringBuilder builder = new StringBuilder();
             builder.append("<html>");
             builder.append(p.getNotes());
             builder.append("</html>");;
             previousNotes.setText(builder.toString());

             scroller = new JScrollPane(previousNotes, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
             scroller.setMinimumSize(new Dimension(200,200));

             c.gridy = 0;
             notes.add(scroller);

             notes.revalidate();
             notes.repaint();
         });

         // Add elements to popup
         c.gridy=0;
         notes.add(scroller,c);

         c.gridy = 1;
         notes.add(textField,c);

         //Add close button
         c.gridy = 2;
         JButton close = new JButton("Close");
         close.addActionListener(e -> notes.dispose());

         notes.add(close,c);
         notes.pack();
         notes.revalidate();
         notes.repaint();
     }

    /**
     * Show key popup
     */
    void showKey(){
        //Setup popup
        JFrame key = new JFrame();
        key.setVisible(true);
        key.setLayout(new GridLayout(16,2));

        // Make items
        JLabel m = new JLabel("M");
        JLabel w = new JLabel("W");
        JLabel g = new JLabel("G");
        JLabel t = new JLabel("T");
        JLabel p = new JLabel("P");
        JLabel r = new JLabel("R");

        JLabel l = new JLabel("L");
        JLabel d = new JLabel("D");
        JLabel k = new JLabel("K");
        JLabel a = new JLabel("A");
        JLabel c = new JLabel("C");
        JLabel e = new JLabel("E");
        JLabel b = new JLabel("B");
        JLabel s = new JLabel("S");
        JLabel h = new JLabel("H");

        JLabel mustard = new JLabel("Col. Mustard");
        JLabel white = new JLabel("Mrs White");
        JLabel green = new JLabel("Rev. Green");
        JLabel turq = new JLabel("Ms Turquoise");
        JLabel plum = new JLabel("Prof. Plum");
        JLabel red = new JLabel("Miss Red");

        JLabel lounge = new JLabel("Lounge");
        JLabel dining = new JLabel("Dining Room");
        JLabel kitchen = new JLabel("Kitchen");
        JLabel audi = new JLabel("Auditorium");
        JLabel con = new JLabel("Conservatory");
        JLabel enter = new JLabel("Entertainment Room");
        JLabel book = new JLabel("Book Room");
        JLabel study = new JLabel("Study");
        JLabel hall = new JLabel("Hall");

        //add them to the Key
        key.add(m);
        key.add(mustard);
        key.add(w);
        key.add(white);
        key.add(g);
        key.add(green);
        key.add(t);
        key.add(turq);
        key.add(p);
        key.add(plum);
        key.add(r);
        key.add(red);
        key.add(l);
        key.add(lounge);
        key.add(d);
        key.add(dining);
        key.add(k);
        key.add(kitchen);
        key.add(a);
        key.add(audi);
        key.add(c);
        key.add(con);
        key.add(e);
        key.add(enter);
        key.add(b);
        key.add(book);
        key.add(s);
        key.add(study);
        key.add(h);
        key.add(hall);

        // Add close button
        JButton close = new JButton("Close");
        close.addActionListener(e1 -> key.dispose());

        key.add(close);
        key.pack();
        key.revalidate();
        key.repaint();
     }
}