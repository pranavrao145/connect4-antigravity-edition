/* Name: Pranav Rao
   Course Code : ICS3U0
   Program Title: Connect 4 [Antigravity Edition] - Board
   Date Completed : April 22, 2021
   Program Description: Board class - an instance of this class represents the
   board in a game of Connect 4 [Antigravity Edition]. Any instance of this
   class will be an attribute of an instance of the Game class.
*/

import java.util.Arrays;

public class Board {      // board class - will be instantiated and belong to an
                          // instance of Game
  private char[][] board; // declaring character array for board
  public static final int ROWS =
      6; // declaring constant for the number of rows the board will have
  public static final int COLUMNS =
      7; // declaring constant for the number of columns the board will have

  public Board() { // constructor
    board =
        new char[ROWS]
                [COLUMNS]; // create a new character array with dimensions 6x7

    // fill the character array with E's for empty
    for (int i = 0; i < 6; i++) {
      Arrays.fill(board[i], 'E');
    }
  }

  public void print() { // method to print the board
    System.out.println(
        "    A B C D E F G"); // formatting (print column letters)
    System.out.println("    -------------"); // formatting
    for (int i = 0; i < board.length;
         i++) { // loop over the values 0-5 inclusive (used to access rows in
                // the char array board)
      System.out.print((i + 1) + " | "); // formatting
      for (int j = 0; j < board[i].length;
           j++) { // loop over values of 0-6 inclusive (used to access columns
                  // in char array board)
        System.out.print(board[i][j] +
                         " "); // for each position in the matrix, output the
                               // value at that position
      }
      System.out.println(); // formatting
    }
    System.out.println(); // formatting
  }

  // getters and setters

  public char[][] getCurrentBoard() { // getter method - returns the current
                                      // char array board of this Board object
    return board;
  }
}
