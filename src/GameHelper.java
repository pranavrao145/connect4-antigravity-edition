/* Name: Pranav Rao
   Course Code : ICS3U0
   Program Title: Connect 4 [Antigravity Edition] - Game Helper
   Date Completed : April 22, 2021
   Program Description: Helper class that provides useful objects and methods for running a game of Connect 4 [Antigravity Edition].
*/

import java.io.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class GameHelper {
    public static HashMap<Character, Integer> columnIndexes = new HashMap<>(); // declares and initializes new hashmap which will be used to associate column letters with numbers for indexes
    public static Scanner scanner = new Scanner(System.in); // declaring and initializing a scanner object

    public static void initializeHashMap() { // method called at the beginning of the program which will initialize the columnIndexes hash with the necessary values
        columnIndexes.put('A', 1);
        columnIndexes.put('B', 2);
        columnIndexes.put('C', 3);
        columnIndexes.put('D', 4);
        columnIndexes.put('E', 5);
        columnIndexes.put('F', 6);
        columnIndexes.put('G', 7);
    }

    // method that takes a board, player, row, and column for an antigravity piece placement
    // returns true if the placement is valid, return false otherwise
    public static boolean isValidAntiGravityPlacement(Board board, Player player, int row, char column) {
        int columnNum = columnIndexes.getOrDefault(column, -1); // use the columnIndexes hashmap to find the column number from the character supplied. Return -1 if the character is not found

        if (row < 1 || columnNum < 1)
            return false; // if the row or column is out of lower bounds of the board return false
        if (row > Board.ROWS || columnNum > Board.COLUMNS)
            return false; // if the row or column is out of upper bounds of the board return false
        if (!(player.getHighestLevel() > 0 && row >= player.getHighestLevel() - 1))
            return false; // if the player is attempting to build in a place illegal by game rules, then return false

        return board.getCurrentBoard()[row - 1][columnNum - 1] == 'E'; // return true if the requested place is empty (b/c placement passes all other validity checks), else return false
    }

    // method that takes a board, row, and column for checking if an antigravity piece exists in that slot
    // returns true if the slot contains an antigravity piece, else return false
    public static boolean isValidAntiGravityPieceExistent(Board board, int row, char column) {
        int columnNum = columnIndexes.getOrDefault(column, -1); // use the columnIndexes hashmap to find the column number from the character supplied. Return -1 if the character is not found

        if (row < 1 || column < 1)
            return false; // if the row or column is out of lower bounds of the board return false
        if (row > Board.ROWS || columnNum > Board.COLUMNS)
            return false; // if the row or column is out of upper bounds of the board return false
        return board.getCurrentBoard()[row - 1][columnNum - 1] == 'A'; // returns true if slot contains antigravity piece (b/c position passes all other validity checks), else returns false
    }

    // method that takes a column and checks if it is valid for placement of a regular piece
    public static boolean isRegularPiecePlacementValid(Board board, char column) {
        int columnNum = columnIndexes.getOrDefault(column, -1); // use the columnIndexes hashmap to find the column number from the character supplied. Return -1 if the character is not found
        if (!(columnNum >= 0 && columnNum <= Board.COLUMNS))
            return false; // if the column number is out of bounds then return false

        return board.getCurrentBoard()[0][columnNum - 1] == 'E'; // return whether or not the top place in that column is free. If it is not, a regular piece cannot be placed. (all other validity checks have been passed, so if this is true, the piece placement is valid and the method must return true, else false)
    }

    //  method that takes a player and a board, and places an antigravity piece on the board according to the player input.
    //  Returns nothing
    public static void placeAntiGravityPiece(Player player, Board board) {
        boolean success = false; // declare and initialize to false a boolean flag which will indicate the current state of success of this method

        while (!success) { // while the function is not successful
            char column = 'Z'; // declare and initialize column variable that will be used later in the function
            int row = 0; // declare and initialize row variable that will be used later in the function

            boolean validInput = false; // declaring and initializing boolean flag to determine if valid input has been given or not

            if (player.getHighestLevel() == 7) { // if the player has not yet placed any pieces on the board
                System.out.printf("Player %d, you have not built on this board yet. You can only place antigravity piece in row 6.%n", player.getColorNum()); // inform user they can only place piece in row 6
            } else { // if the player has already built on the board
                System.out.printf("Player %d, the highest row you have built up to until now is row %d. You can place an antigravity piece anywhere in row %d or below.%n", player.getColorNum(), player.getHighestLevel(), player.getHighestLevel() - 1); // inform user of where they can place a piece
            }

            while (!validInput) { // while valid input has not been given
                System.out.printf("Player %d, please choose a column for an antigravity piece (letter):%n", player.getColorNum()); // prompt
                try { // user input is unreliable, so using a try catch statement to handle any exceptions
                    column = scanner.nextLine().toUpperCase().charAt(0); // get user input for a column letter
                    if (Character.isAlphabetic(column))
                        validInput = true; // if the character is alphabetic, then change validInput flag to true so loop will not run again
                    else System.out.println("Invalid input, please try again.\n"); // else inform user of invalid input
                } catch (Exception ignored) { // catching any exception that may arise (invalid input)
                    System.out.println("Invalid input, please try again.\n");
                    scanner.nextLine(); // clearing scanner buffer so as to not pick up any unwanted input
                }
            }

            validInput = false; // resetting validInput to false for the next round of input


            while (!validInput) { // while the valid input has not been given
                System.out.println("Please enter a row number for an antigravity piece:"); // prompt
                try { // user input is unreliable, so using a try catch statement to handle any exceptions
                    row = scanner.nextInt(); // get user input for a row number
                    validInput = true; // set validInput flag to true so loop will not run again
                    scanner.nextLine(); // clear scanner buffer
                } catch (Exception ignored) { // catching any exception that may arise (invalid input)
                    System.out.println("Invalid input, please try again.\n");
                    scanner.nextLine(); // clearing scanner buffer
                }
            }

            if (isValidAntiGravityPlacement(board, player, row, column)) { // if the given antigravity piece placement is valid
                if (row > player.getHighestLevel())
                    player.setHighestLevel(row); // if the new row is higher than the level the player has already built to, set the player's highest level to that row
                board.getCurrentBoard()[row - 1][columnIndexes.get(column) - 1] = 'A'; // place the antigravity piece at the slot given

                // print out current board
                System.out.println("\nCurrent board: ");
                board.print();
                success = true; // set success flag to true to indicate the success of the function
            } else {  // if the antigravity piece placement is not valid
                System.out.println("Invalid piece placement, try again.\n"); // informing user of invalid input
            }
        }
    }

    // method that take a player and a board, and removes an anti-gravity piece from the board according to player input.
    // Returns nothing
    public static void removeAntiGravityPiece(Player player, Board board) {
        boolean success = false; // declare and initialize to false a boolean flag which will indicate the current state of success of this method

        while (!success) { // while the method is not successful
            char column = 'Z'; // declare and initialize column variable that will be used later in the function
            int row = 0; // declare and initialize row variable that will be used later in the function

            boolean validInput = false; // declaring and initializing boolean flag to determine if valid input has been given or not

            while (!validInput) { // while valid input has not been given
                System.out.printf("Player %d, please choose a column for the antigravity piece you want to remove (letter):%n", player.getColorNum()); // prompt
                try { // user input is unreliable, so using a try catch statement to handle any exceptions
                    column = scanner.nextLine().toUpperCase().charAt(0); // get user input for a column letter
                    if (Character.isAlphabetic(column))
                        validInput = true; // if the character is alphabetic, then change validInput flag to true so loop will not run again
                    else
                        System.out.println("Invalid input, please try again.\n"); // else inform the user of invalid input
                } catch (Exception ignored) { // catching any exception that may be thrown
                    System.out.println("Invalid input, please try again.\n"); // informing user of invalid input
                    scanner.nextLine(); // clearing scanner buffer
                }
            }

            validInput = false; // resetting validInput to false for next round of input


            while (!validInput) { // while the valid input has not been given
                System.out.println("Please enter a row number for the antigravity piece you want to remove:"); // prompt
                try { // user input is unreliable, so using a try catch statement to handle any exceptions
                    row = scanner.nextInt(); // get user input for a row number
                    validInput = true; // set validInput flag to true so loop will not run again
                    scanner.nextLine(); // clear scanner buffer
                } catch (Exception ignored) { // catch any exception that may be thrown
                    System.out.println("Invalid input, please try again.\n"); // inform user of invalid input
                    scanner.nextLine(); // clear scanner buffer
                }
            }

            if (isValidAntiGravityPieceExistent(board, row, column)) { // if the user supplied a valid position that contains an antigravity piece
                int columnNum = columnIndexes.getOrDefault(column, -1); // get the column number of the column supplied
                board.getCurrentBoard()[row - 1][columnNum - 1] = 'E'; // remove the antigravity piece

                // loop to methodically remove all pieces the antigravity piece supports

                int i = row - 2; // declaring and initializing the variable i (index) to the index of the row just above the supplied row (row - 1) - 1
                while (i >= 0) { // the loop will start checking for pieces it needs to remove from one row above the removed antigravity piece. The loop will continue upwards and terminate upon reaching the end of the board.
                    if (board.getCurrentBoard()[i][columnNum - 1] == 'A') // if the loop encounters an antigravity piece
                        i = -1; // set i to -1 to stop the execution of the loop, because that antigravity piece is not supported by the one we are removing right now
                    else if (board.getCurrentBoard()[i][columnNum - 1] == 'E') // if the loop encounters an empty spot
                        i = -1; // set i to -1 to stop the execution of the loop  if we encounter an empty spot, stop because there is no point in continuing.
                    else
                        board.getCurrentBoard()[i][columnNum - 1] = 'E'; // else (if there is a non-antigravity piece in the slot), changes the slot to empty
                    i--; // decrement i by one to move up to the slot in the next row above the current one
                }

                player.setRemovedAntiGravityPiece(true); // set the player's removedAntiGravityPiece attribute to true, which will prevent them from removing another one later in the game
                success = true; // the function is successful, so set success flag to true so this loop does not run again
            } else { // if the loop is unsuccessful for some reason
                System.out.println("No antigravity piece at that position or invalid position, try again.\n"); // inform the user of error before looping again
            }
        }
    }


    // method that takes a player and a board and drops a regular piece of the player's colour according to their input
    // Returns nothing
    public static void dropRegularPiece(Player player, Board board) {
        boolean success = false; // declare and initialize to false a boolean flag which will indicate the current state of success of this method

        while (!success) { // while the method is not successful
            char column = 'Z'; // declare and initialize column variable that will be used later in the function

            boolean validInput = false; // declaring and initializing boolean flag to determine if valid input has been given or not

            while (!validInput) { // while valid input has not been given
                System.out.printf("Player %d, please choose a column to drop regular piece (letter):%n", player.getColorNum()); // prompt
                try { // user input is unreliable, so using a try catch statement to handle any exceptions
                    column = scanner.nextLine().toUpperCase().charAt(0); // get input for a column letter from the user
                    if (Character.isAlphabetic(column))
                        validInput = true; // if the input is alphabetic, set the validInput flag to true so the loop does not run again.
                    else
                        System.out.println("Invalid input, please try again.\n"); // else inform the user of invalid input
                } catch (Exception ignored) { // catching any exceptions that might have been thrown
                    System.out.println("Invalid input, please try again.\n"); // inform the user of invalid input
                    scanner.nextLine(); // clear scanner buffer
                }
            }

            if (isRegularPiecePlacementValid(board, column)) { // if the column the user has given is valid for dropping a regular piece
                int currentRowIndex = -1; // declare and initialize int variable currentRowIndex to -1. This will be used to keep track of the row we are on during iteration.
                int columnNum = columnIndexes.getOrDefault(column, -1); // get column number from columnIndexes hash

                // loop for dropping the regular piece
                while (currentRowIndex < Board.ROWS) { // the loop must terminate when the currentRow is off the board, regardless of column it's dropped in
                    if (currentRowIndex == 5) { // check special case when index = 5 (cannot check the next index or will throw error)
                        currentRowIndex++; // increment currentRowIndex to 6 to terminate the loop
                        success = true; // change success flag to true so the method does not run again
                    } else { // for all other cases
                        if (board.getCurrentBoard()[currentRowIndex + 1][columnNum - 1] == 'E')
                            currentRowIndex++; // if the NEXT index is empty, increment currentRowIndex to the next row down
                        else { // if the NEXT index is NOT empty, then place the piece at the current row and column specified (last free place)
                            board.getCurrentBoard()[currentRowIndex][columnNum - 1] = player.getColorName(); // place a piece of the player's colour in the current spot
                            if (currentRowIndex + 1 > player.getHighestLevel()) // if the currentRowIndex + 1 (the row number on the board) is greater than the highest level the player has built to
                                player.setHighestLevel(currentRowIndex + 1); // set the highest level of the player to the current row number (index + 1)
                            success = true; // change success flag to true so the method does not run again
                            currentRowIndex = Board.ROWS; // set the currentRowIndex to off the board so the loop looping through the board's rows gets terminated
                        }
                    }
                }
            } else { // if the column the user has given is invalid
                System.out.println("Invalid piece placement, try again.\n"); // inform user of invalid input
            }
        }
    }

    // method that takes a board object and checks if there is a win on that board.
    // if there is, return the character of the player that won, else return E for empty slot.
    public static char checkWin(Board board) {
        // basic algorithm: loop through the matrix and for each element check for 3 other instances of the character in that slots in the
        // following directions: up, right, up + right, up + left. Since we are looping through the entire board (all the way to the bottom)
        // there is no need to check the opposite directions (down, left, down + left, down + right), because those will be checked when we
        // check the first four conditions on the pieces at the bottom fo the board (the direction in which the program detects the win does
        // not matter).

        char[][] currentBoard = board.getCurrentBoard(); // get the current board
        final char EMPTY_SLOT = 'E'; // declaring and initializing a constant for purposes of easier reference to an empty slot

        for (int row = 0; row < Board.ROWS; row++) { // iterate rows, top to bottom (row 1-6)
            for (int col = 0; col < Board.COLUMNS; col++) { // iterate columns (left to right (columns A-G)
                char current = currentBoard[row][col]; // get the value in the current slot

                if (current == 'E' || current == 'A')
                    continue; // don't check the slot if it's empty or an antigravity piece


                if (col + 3 < Board.COLUMNS) { // if there are three columns at least to the right of the current column
                    // check to the right

                    // if there are four pieces of the same colour to the right of the current piece
                    if (current == currentBoard[row][col + 1] &&
                            current == currentBoard[row][col + 2] &&
                            current == currentBoard[row][col + 3])
                        return current; // return the current value of the slot (the color of the winning player)
                }

                if (row + 3 < Board.ROWS) { // check if there are at least 3 rows above the current row
                    // check upwards direction

                    // if there are four pieces of the same colour in the upwards direction from this piece
                    if (current == currentBoard[row + 1][col] &&
                            current == currentBoard[row + 2][col] &&
                            current == currentBoard[row + 3][col])
                        return current; // return the current value of the slot (the color of the winning player)

                    // check up and to the right

                    // if there are four pieces of the same colour in the diagonal up and right direction from this piece
                    if (col + 3 < Board.COLUMNS &&
                            current == currentBoard[row + 1][col + 1] &&
                            current == currentBoard[row + 2][col + 2] &&
                            current == currentBoard[row + 3][col + 3])
                        return current; // return the current value of the slot (the color of the winning player)

                    // check up and to the left

                    // if there are four pieces of the same colour in the diagonal up and left direction from this piece
                    if (col - 3 >= 0 &&
                            current == currentBoard[row + 1][col - 1] &&
                            current == currentBoard[row + 2][col - 2] &&
                            current == currentBoard[row + 3][col - 3])
                        return current; // return the current value of the slot (the color of the winning player)
                }
            }
        }
        return EMPTY_SLOT; // if all else fails and no row of four is found, return 'E' (empty slot)
    }

    // method that takes no parameters and prints the logs from the data/logs.txt file.
    // returns nothing
    public static void printLogs() {
        try { // try to read from the logs - I/O operations can cause an error, so we are using a try catch statement to catch the error in that case.
            File file = new File("data/logs.txt"); // declares and initializes a new file object using the pathname data/logs.txt (our desired file)
            if (file.exists()) { // if the file already exists
                BufferedReader reader = new BufferedReader(new FileReader("data/logs.txt")); // declare and initialize a new BufferedReader object with data/logs.txt as the filename

                System.out.println("\nHere are the logs: ");

                String line = reader.readLine(); // read the first line and store it in the String variable line

                while (line != null) { // while the line variable has some content
                    System.out.println(line); // print out the line
                    line = reader.readLine(); // read the next line
                }
            } else { // if the file does not exist yet
                System.out.println("\nThere are no logs yet. Play the game to view the logs."); // inform the user there are no logs
            }
        } catch (Exception ignored) { // if there is some exception, this catch will handle it
            System.out.println("There was an error reading from the logs."); // inform the user there was an error
        }
    }

    // method that takes the one character code of the winning player and writes to the log file accordingly
    // return nothing
    public static void writeLogs(char winningPlayer) {
        try { // try to write to the logs file - I/O operations can cause an error, so we are using a try catch statement to catch the error in that case.
            Timestamp currentTimestamp = new Timestamp(new Date().getTime()); // declaring and initializing timestamp object - essentially getting the current date and time in a timestamp form
            PrintWriter output = new PrintWriter(new FileWriter("data/logs.txt")); // declaring and initializing a new PrintWriter object with filename data/logs.txt. This will be written to later.

            String winningColor; // declare a string variable that will be initialized dynamically

            switch (winningPlayer) { // check the value of winningPlayer
                case 'R': // if the winner is red
                    winningColor = "Red"; // set winningColor to Red
                    break;
                case 'Y': // if the winner is yellow
                    winningColor = "Yellow"; // set winningColor to Yellow
                    break;
                default: // if there is no winning player
                    winningColor = "None"; // set the winning color to None
                    break;
            }

            output.println("Game at " + currentTimestamp + " - Winner: " + winningColor); // print the appropriate log to the output file with the timestamp and winner
            output.close(); // save and close and the file

            System.out.println("\nResults of game written to logs successfully."); // inform the user the logs have been updated successfully
        } catch (Exception ignored) { // if some exception was thrown, this will handle it
            System.out.println("There was an error writing to the logs."); // inform the user there was an error
        }
    }
}
