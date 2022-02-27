/* Name: Pranav Rao
   Course Code : ICS3U0
   Program Title: Connect 4 [Antigravity Edition] - Player
   Date Completed : April 22, 2021
   Program Description: Player class - an instance of this class represents a
   player in a game of Connect 4 [Antigravity Edition]. Any instance of this
   class will be an attribute of an instance of the Game class.
*/

public class Player {
  private int highestLevel; // declaring integer var storing the highest level
                            // the player has built up to at this point
  private boolean
      removedAntiGravityPiece; // declaring boolean var that storing whether or
                               // not the player has used their chance to remove
                               // an antigravity piece
  private final char colorName; // declaring char variable storing the name of
                                // the player's colour
  private final int colorNum;   // declaring int variable storing the number of
                                // the player (i.e. 1 or 2)

  public Player(char colorName, int colorNum) { // constructor
    this.highestLevel =
        7; // sets the highest level of the player to 7 (off the board)
    this.removedAntiGravityPiece =
        false; // sets the boolean flag removedAntiGravityPiece to false
    this.colorName = colorName; // sets the colour name to the value passed to
                                // the constructor method
    this.colorNum = colorNum;   // sets the colour number to the value passed to
                                // the constructor method
  }

  // Getters and setters

  public int getHighestLevel() { return highestLevel; }

  public void setHighestLevel(int highestLevel) {
    this.highestLevel = highestLevel;
  }

  public boolean isRemovedAntiGravityPiece() { return removedAntiGravityPiece; }

  public void setRemovedAntiGravityPiece(boolean removedAntiGravityPiece) {
    this.removedAntiGravityPiece = removedAntiGravityPiece;
  }

  public char getColorName() { return colorName; }

  public int getColorNum() { return colorNum; }
}
