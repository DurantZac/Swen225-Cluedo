
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Board
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Board Associations
  private Tile[][] boardTiles = new Tile[25][24];
  private Game game;


  //------------------------
  // CONSTRUCTOR
  //------------------------

  /**
   * Constructor of Board,
   * Makes a new game
   * @param aGame Game object
   * @param allBoardTiles List of tiles from game
   */
  public Board(Game aGame, List<Tile>allBoardTiles)
  {
    int i = 0;
    for(int row = 0; row < 25; row++){
      for(int col = 0; col < 24; col++){
        boardTiles[row][col] = allBoardTiles.get(i); // converts list of tiles to 2D array
        i++;
        boardTiles[row][col].setRow(row);
        boardTiles[row][col].setCol(col);
      }
    }

    game = aGame;

    // Add adjacent tiles
    for(char row = 'a'; row < 'z'; row++){
      for(char col = 'A'; col < 'Y'; col++){
        Tile t  = getBoardTile(col + String.valueOf(row));
        if(row != 'a'){t.addAdjacent(getBoardTile(col + String.valueOf((char)(row-1))));}
        if(row != 'y'){t.addAdjacent(getBoardTile(col + String.valueOf((char)(row+1))));}
        if(col != 'A'){t.addAdjacent(getBoardTile((char)(col-1) + String.valueOf(row)));}
        if(col != 'X'){t.addAdjacent(getBoardTile((char)(col+1) + String.valueOf(row)));}
      }
    }
  }


    /**
     * Marks the walls of the room as the correct wall shape
     * Purely stylistic
     */
    public void positionWalls(){
        Class c = getClass();

        //Library
        getBoardTile("Gt").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Gu").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Gv").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Gw").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Gx").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Gy").setDefaultImage(c.getResource("WBR.jpg"));

        getBoardTile("By").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Cy").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Dy").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Ey").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Fy").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Ay").setDefaultImage(c.getResource("WBL.jpg"));

        getBoardTile("Au").setDefaultImage(c.getResource("WL.jpg"));
        getBoardTile("Aw").setDefaultImage(c.getResource("WL.jpg"));
        getBoardTile("Ax").setDefaultImage(c.getResource("WL.jpg"));
        getBoardTile("At").setDefaultImage(c.getResource("WTL.jpg"));


        //Dining
        getBoardTile("Hp").setDefaultImage(c.getResource("WBR.jpg"));
        getBoardTile("Gp").setDefaultImage(c.getResource("Room.jpg"));
        getBoardTile("Fp").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Ep").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Dp").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Cp").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Bp").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Ap").setDefaultImage(c.getResource("WBL.jpg"));

        getBoardTile("Ak").setDefaultImage(c.getResource("WL.jpg"));
        getBoardTile("Al").setDefaultImage(c.getResource("WL.jpg"));
        getBoardTile("An").setDefaultImage(c.getResource("WL.jpg"));
        getBoardTile("Ao").setDefaultImage(c.getResource("WL.jpg"));
        getBoardTile("Aj").setDefaultImage(c.getResource("WTL.jpg"));

        getBoardTile("Ej").setDefaultImage(c.getResource("WTR.jpg"));
        getBoardTile("Ek").setDefaultImage(c.getResource("Room.jpg"));

        getBoardTile("Hk").setDefaultImage(c.getResource("WTR.jpg"));
        getBoardTile("Fp").setDefaultImage(c.getResource("WB.jpg"));

        getBoardTile("Hl").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Hn").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Ho").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Hm").setDefaultImage(c.getResource("Room.jpg"));

        //Kitchen
        getBoardTile("Bg").setDefaultImage(c.getResource("WBL.jpg"));
        getBoardTile("Cg").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Dg").setDefaultImage(c.getResource("WB.jpg"));

        getBoardTile("Af").setDefaultImage(c.getResource("WBL.jpg"));
        getBoardTile("Ad").setDefaultImage(c.getResource("WL.jpg"));
        getBoardTile("Ae").setDefaultImage(c.getResource("WL.jpg"));

        getBoardTile("Ab").setDefaultImage(c.getResource("WTL.jpg"));
        getBoardTile("Fb").setDefaultImage(c.getResource("WTR.jpg"));

        getBoardTile("Fc").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Fd").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Fe").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Ff").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Fg").setDefaultImage(c.getResource("WBR.jpg"));

        //Auditorium
        getBoardTile("Id").setDefaultImage(c.getResource("WL.jpg"));
        getBoardTile("Ie").setDefaultImage(c.getResource("WL.jpg"));
        getBoardTile("If").setDefaultImage(c.getResource("WL.jpg"));
        getBoardTile("Ig").setDefaultImage(c.getResource("WL.jpg"));

        getBoardTile("Ic").setDefaultImage(c.getResource("WTL.jpg"));
        getBoardTile("Ih").setDefaultImage(c.getResource("WBL.jpg"));

        getBoardTile("Kb").setDefaultImage(c.getResource("WL.jpg"));
        getBoardTile("Ka").setDefaultImage(c.getResource("WTL.jpg"));

        getBoardTile("Na").setDefaultImage(c.getResource("WTR.jpg"));
        getBoardTile("Nb").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Nc").setDefaultImage(c.getResource("Room.jpg"));

        getBoardTile("Pc").setDefaultImage(c.getResource("WTR.jpg"));
        getBoardTile("Pd").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Pe").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Pf").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Pg").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Ph").setDefaultImage(c.getResource("WBR.jpg"));

        getBoardTile("Kh").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Lh").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Mh").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Nh").setDefaultImage(c.getResource("WB.jpg"));


        //Cons
        getBoardTile("Sb").setDefaultImage(c.getResource("WTL.jpg"));
        getBoardTile("Sc").setDefaultImage(c.getResource("WL.jpg"));
        getBoardTile("Sd").setDefaultImage(c.getResource("WL.jpg"));
        getBoardTile("Se").setDefaultImage(c.getResource("WL.jpg"));

        getBoardTile("Tf").setDefaultImage(c.getResource("WBL.jpg"));
        getBoardTile("Uf").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Vf").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Wf").setDefaultImage(c.getResource("WBR.jpg"));

        getBoardTile("Xe").setDefaultImage(c.getResource("WBR.jpg"));
        getBoardTile("Xd").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Xb").setDefaultImage(c.getResource("WTR.jpg"));


        //Entertainment Room
        getBoardTile("Si").setDefaultImage(c.getResource("WTL.jpg"));
        getBoardTile("Sk").setDefaultImage(c.getResource("WL.jpg"));
        getBoardTile("Sl").setDefaultImage(c.getResource("WL.jpg"));
        getBoardTile("Sm").setDefaultImage(c.getResource("WBL.jpg"));

        getBoardTile("Tm").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Um").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Vm").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Xm").setDefaultImage(c.getResource("WBR.jpg"));

        getBoardTile("Xk").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Xl").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Xi").setDefaultImage(c.getResource("WTR.jpg"));


        //Book Room
        getBoardTile("Sr").setDefaultImage(c.getResource("Room.jpg"));
        getBoardTile("Sp").setDefaultImage(c.getResource("Room.jpg"));

        getBoardTile("Rp").setDefaultImage(c.getResource("WTL.jpg"));
        getBoardTile("Rr").setDefaultImage(c.getResource("WBL.jpg"));

        getBoardTile("Ss").setDefaultImage(c.getResource("WBL.jpg"));
        getBoardTile("Ts").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Us").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Vs").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Ws").setDefaultImage(c.getResource("WBR.jpg"));

        getBoardTile("Xr").setDefaultImage(c.getResource("WBR.jpg"));
        getBoardTile("Xq").setDefaultImage(c.getResource("WR.jpg"));

        getBoardTile("Wo").setDefaultImage(c.getResource("WTR.jpg"));
        getBoardTile("So").setDefaultImage(c.getResource("WTL.jpg"));


        //Study
        getBoardTile("Rv").setDefaultImage(c.getResource("WL.jpg"));
        getBoardTile("Rw").setDefaultImage(c.getResource("WL.jpg"));
        getBoardTile("Rx").setDefaultImage(c.getResource("WL.jpg"));

        getBoardTile("Ry").setDefaultImage(c.getResource("WBL.jpg"));
        getBoardTile("Sy").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Ty").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Uy").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Vy").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Wy").setDefaultImage(c.getResource("WB.jpg"));

        //Hall
        getBoardTile("Ky").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("My").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Ny").setDefaultImage(c.getResource("WB.jpg"));
        getBoardTile("Jy").setDefaultImage(c.getResource("WBL.jpg"));
        getBoardTile("Oy").setDefaultImage(c.getResource("WBR.jpg"));

        getBoardTile("Ox").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Ow").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Ou").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Ot").setDefaultImage(c.getResource("WR.jpg"));
        getBoardTile("Os").setDefaultImage(c.getResource("WTR.jpg"));
        getBoardTile("Js").setDefaultImage(c.getResource("WTL.jpg"));

        getBoardTile("Jt").setDefaultImage(c.getResource("WL.jpg"));
        getBoardTile("Ju").setDefaultImage(c.getResource("WL.jpg"));
        getBoardTile("Jv").setDefaultImage(c.getResource("WL.jpg"));
        getBoardTile("Jw").setDefaultImage(c.getResource("WL.jpg"));
        getBoardTile("Jx").setDefaultImage(c.getResource("WL.jpg"));

        getBoardTile("If").setDefaultImage(c.getResource("Room.jpg"));
        getBoardTile("Pf").setDefaultImage(c.getResource("Room.jpg"));
    }
  
  /**
   *  Gets the tile corresponding to XY coordinates.
   *  String must be 2 letters, with one being upper case and one being lower
   * @param tile the tile to get according to its coordinates
   * @return the tile.
   */
  public Tile getBoardTile(String tile)
  {
    try {
      if(tile == null) throw new moveInvalidException("Null move");
      if (tile.length() != 2) { // needs to be exactly 2 letters long
        throw new moveInvalidException ("Move invalid.");
      }

      char letter1 = tile.charAt(0);
      char letter2 = tile.charAt(1);

      //Needs to be one upper case and one lower case

      //Case X followed by Y
      if (Character.isUpperCase(letter1)){
        if (Character.isLowerCase(letter2)){
          int x = letter1 - 'A';
          int y = letter2 - 'a';
          return boardTiles[y][x];
        }
        throw new moveInvalidException ("Move invalid.");
      }

      //Case Y followed by X
      else if (Character.isUpperCase(letter2)) {
        if (Character.isLowerCase(letter1)) {
          int y = letter1 - 'a';
          int x = letter2 - 'A';
          return boardTiles[y][x];
        }
        throw new moveInvalidException("Move invalid.");
      }

      //Neither letters upper case
      throw new moveInvalidException ("Move invalid.");
    }
    catch (moveInvalidException e){ //Invalid input.
      return null;
    }
  }

  /**
   * Return tile at row,col
   * @param row the row of the tile
   * @param col the column of the tile
   * @return the tile corresponding to the row and column
   */
  public Tile getBoardTile(int row, int col){
    if(row >= 25 || col >= 24) { // check its a valid tile
      return null;
    }
    return boardTiles[row][col];
  }

  /**
   * Checks if move is valid, if so, return path found
   * If not return false
   * @param p player
   * @param goal node target
   * @param moves number of moves to use
   * @return Tile path
   */
  public List<Tile> movePlayer(Player p, Tile goal, int moves) {
      if(goal == null) return null;
      Tile startTile = p.getPosition();
      if(goal.getIsPartOf() == null && !goal.getIsAccessible()) return null;

      // Find if path available
      List<Tile> path = new ArrayList<>();
      if(startTile.getIsPartOf() == null){
          path = pathFinding(startTile,goal,moves,0,new ArrayList<>());
      }
      else{ // If in room, try all exits
          if(startTile.getIsPartOf() == goal.getIsPartOf()) return null;
          for(Tile t : p.getPosition().getIsPartOf().getEntrances()){
              List<Tile> option = pathFinding(t,goal,moves,0,new ArrayList<>());
              if(option != null) path = option;
          }
      }
      return path;
  }

    /**
     * Move player p to tile t.
     * Called to move player step by step along a path
     * @param p the player moving
     * @param t the tile to move to
     */
  public void takeStep(Player p, Tile t){
      Tile oldPos = p.getPosition();
      if(oldPos.getIsPartOf() != null) oldPos.getIsPartOf().addEmptySpace(oldPos); //the player is in a room
      oldPos.removeCharacter();
      if(t.getIsPartOf() != null){
          t = t.getIsPartOf().getEmptySpace();
          p.getCharacter().setPosition(t); // move them out of the room
      }
      else{
          p.getCharacter().setPosition(t); // just move the player
      }
  }




    /**
   * Recursive Depth first search to find path from start to end using required number of moves
   * @param node Current Node
   * @param goal End Node
   * @param moveGoal Moves to use
   * @param moveCount Moves used so far
   * @param visited List of nodes that have been visited
   * @return Path if path found, null otherwise
   */
    private List<Tile> pathFinding(Tile node, Tile goal, int moveGoal, int moveCount, List<Tile> visited){
        List<Tile> path = null;
        if(node == goal && moveCount == moveGoal){ //Whole path found
            visited.add(node);
            return visited;
        }
        else if(node.getIsPartOf() != null && node.getIsPartOf() == goal.getIsPartOf() && moveCount <= moveGoal){ // in a room
            visited.add(node);
            return visited;
        }
        else if(moveCount >= moveGoal) return null; // out of moves
        else{visited.add(node);}
        for(Tile neigh : node.getAdjacent()){ //add all the neighbours
            if(!visited.contains(neigh)) {
                if(neigh.getSymbol() != '_' && !neigh.getIsAccessibleFull(node)) continue;
                List<Tile> option = pathFinding(neigh, goal, moveGoal, moveCount + 1, new ArrayList<>(visited)); //search at this neighbour
                path = option != null ? option : path;
            }
        }
        return path;
    }



    /**
   * Teleports the player to a room they have been suggested in
   * @param c character card object
   * @param goal room to move to
   */
  public void teleportCharacter(CharacterCard c, Room goal){
    Tile oldPos = c.getPosition();
    if(oldPos.getIsPartOf() != null) oldPos.getIsPartOf().addEmptySpace(oldPos); //remove player from old room
    oldPos.removeCharacter(); // remove player from the old tile
    Tile t = goal.getEmptySpace(); //move player to a free space in the room
    c.setPosition(t);
  }

  /**
   * Teleports weapon to given room after suggestion
   * @param w Weapon object
   * @param goal Room goal
   */
  public void teleportWeapon(WeaponCard w, Room goal){
    w.setLocation(goal.getEmptySpace());
  }

  /**
   * ToString printing out the board and weapon locations
   * @return the final string
   */
  public String toString(){
    String s="";
    s+= "M - Col Mustard. W - Mrs White. G - Rev. Green. T - Ms Turquoise. P - Prof. Plum. R - Miss Red.\nL - Library. D - Dining Room. K - Kitchen. A - Auditorium. C - Conservatory. E - Entertainment Room. B - Book Room. S - Study. H - Hall\n" +
            "# - Wall. X - Out of bounds. _ - Open tile. ^>v< - Doorway (Direction).\n";
    char c = 'a';
    for(int row = 0; row < 25; row++){
      for(int col = 0; col < 24; col++){
        String str = "";
        if(col == 0) s+=(c++ + " ");
        str += boardTiles[row][col];
        s+=(str);
      }
      s+=("|\n");
    }
    s+= ("   A B C D E F G H I J K L M N O P Q R S T U V W X\n");
    s+=("Weapon locations: \n");
    for(WeaponCard w : game.getWeapons()){
      s+=("The " + w.getWeapon() + " is in the " + w.getLocation()+"\n");
    }
    s+= "\n";
    return s;
  }

  private class moveInvalidException extends Throwable {
    public moveInvalidException(String s) {
    }
  }
}