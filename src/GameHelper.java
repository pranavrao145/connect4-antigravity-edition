import java.io.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class GameHelper { // GameHelper class - helper class that provides useful objects and methods for running a game
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

        if (row < 1 || columnNum < 1) return false; // if the row or column is out of lower bounds of the board return false
        if (row > Board.ROWS || columnNum > Board.COLUMNS) return false; // if the row or column is out of upper bounds of the board return false
        if (!(player.getHighestLevel() > 0 && row >= player.getHighestLevel() - 1)) return false; // if the player is attempting to build in a place illegal by game rules, then return false

        return board.getCurrentBoard()[row - 1][columnNum - 1] == 'E'; // return true if the requested place is empty (b/c placement passes all other validity checks), else return false
    }

    // method that takes a board, row, and column for checking if an antigravity piece exists in that slot
    // returns true if the slot contains an antigravity piece, else return false
    public static boolean isValidAntiGravityPieceExistent(Board board, int row, char column) {
        int columnNum = columnIndexes.getOrDefault(column, -1); // use the columnIndexes hashmap to find the column number from the character supplied. Return -1 if the character is not found

        if (row < 1 || column < 1) return false; // if the row or column is out of lower bounds of the board return false
        if (row > Board.ROWS || columnNum > Board.COLUMNS) return false; // if the row or column is out of upper bounds of the board return false
        return board.getCurrentBoard()[row - 1][columnNum - 1] == 'A'; // returns true if slot contains antigravity piece (b/c position passes all other validity checks), else returns false
    }

    // method that takes a column and checks if it is valid for placement of a regular piece
    public static boolean isRegularPiecePlacementValid(Board board, char column) {
        int columnNum = columnIndexes.getOrDefault(column, -1); // use the columnIndexes hashmap to find the column number from the character supplied. Return -1 if the character is not found
        if (!(columnNum >= 0 && columnNum <= Board.COLUMNS)) return false; // if the column number is out of bounds then return false

        return board.getCurrentBoard()[0][columnNum - 1] == 'E'; // return whether or not the top place in that column is free. If it is not, a regular piece cannot be placed. (all other validity checks have been passed, so if this is true, the piece placement is valid and the method must return true, else false)
    }

    public static void placeAntiGravityPiece(Player player, Board board) { //  method to place an antigravity piece given the current player and the board
        boolean success = false; // declare and initialize boolean flag which will indicate the current state of success of this method

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
                    if (Character.isAlphabetic(column)) validInput = true; // if the character is indeed a character, then change validInput flag to true so loop will not run again
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
                if (row > player.getHighestLevel()) player.setHighestLevel(row); // if the new row is higher than the level the player has already built to, set the player's highest level to that row
                board.getCurrentBoard()[row - 1][columnIndexes.get(column) - 1] = 'A'; // place the antigravity piece at the slot given

                // print out current board
                System.out.println("\nCurrent board: ");
                board.print();
                success = true; // set success flag to true to indicate the success of the function
            } else {  // if the antigravity piece placement is not valid
                System.out.println("Invalid piece placement, try again.\n"); // let the user know of invalid input
            }
        }
    }

    public static void removeAntiGravityPiece(Player player, Board board) {
        boolean success = false;

        while (!success) {
            char column = 'Z';
            int row = 0;

            boolean validInput = false;

            while (!validInput) {
                System.out.printf("Player %d, please choose a column for the antigravity piece you want to remove (letter):%n", player.getColorNum());
                try {
                    column = scanner.nextLine().toUpperCase().charAt(0);
                    if (Character.isAlphabetic(column)) validInput = true;
                    else System.out.println("Invalid input, please try again.\n");
                } catch (Exception ignored) {
                    System.out.println("Invalid input, please try again.\n");
                    scanner.nextLine();
                }
            }

            validInput = false; // resetting validInput


            while (!validInput) {
                System.out.println("Please enter a row number for the antigravity piece you want to remove:");
                try {
                    row = scanner.nextInt();
                    validInput = true;
                    scanner.nextLine();
                } catch (Exception ignored) {
                    System.out.println("Invalid input, please try again.\n");
                    scanner.nextLine();
                }
            }

            if (isValidAntiGravityPieceExistent(board, row, column)) {
                int columnNum = columnIndexes.getOrDefault(column, -1);
                board.getCurrentBoard()[row - 1][columnNum - 1] = 'E';

                // maybe convert to while loop (don't really know how many times looping)
                for (int i = row - 2; i >= 0; i--) {
                    if (board.getCurrentBoard()[i][columnNum - 1] == 'A')
                        break; // check if antigravity piece and break if so
                    else if (board.getCurrentBoard()[i][columnNum - 1] == 'E') break;
                    else board.getCurrentBoard()[i][columnNum - 1] = 'E'; // else changes the slot to empty
                }

                player.setRemovedAntiGravityPiece(true);
                success = true;
            } else {
                System.out.println("No antigravity piece at that position or invalid position, try again.\n");
            }
        }


    }

    public static void dropRegularPiece(Player player, Board board) {
        boolean success = false;

        while (!success) {
            char column = 'Z';

            boolean validInput = false;

            while (!validInput) {
                System.out.printf("Player %d, please choose a column to drop regular piece (letter):%n", player.getColorNum());
                try {
                    column = scanner.nextLine().toUpperCase().charAt(0);
                    if (Character.isAlphabetic(column)) validInput = true;
                    else System.out.println("Invalid input, please try again.\n");
                } catch (Exception ignored) {
                    System.out.println("Invalid input, please try again.\n");
                    scanner.nextLine();
                }
            }

            if (isRegularPiecePlacementValid(board, column)) {
                int currentRowIndex = -1, columnNum = columnIndexes.getOrDefault(column, -1);

                while (currentRowIndex < Board.ROWS) {
                    if (currentRowIndex == 5) { // check special case when index = 5 (cannot check the next index or will throw error)
                        currentRowIndex++;
                        success = true;
                    } else {
                        if (board.getCurrentBoard()[currentRowIndex + 1][columnNum - 1] == 'E') currentRowIndex++;
                        else {
                            board.getCurrentBoard()[currentRowIndex][columnNum - 1] = player.getColorName();
                            if (currentRowIndex + 1 > player.getHighestLevel()) player.setHighestLevel(currentRowIndex + 1);
                            success = true;
                            break;
                        }
                    }
                }
            }
            else {
                System.out.println("Invalid piece placement, try again.\n");
            }
        }
    }

    public static char checkWin(Board board) {
        // checks if there is a win on the board. If there is, return the character of the player that won.
        // basic algorithm: loop through the matrix and for each element check for 3 other instances of the character in that slots in the
        // following directions: up, right, up + right, up + left. Since we are looping through the entire board (all the way to the bottom)
        // there is no need to check the opposite directions (down, left, down + left, down + right), because those will be checked when we
        // check the first four conditions on the pieces at the bottom fo the board (the direction in which the program detects the win does
        // not matter.

        char[][] boardMatrix = board.getCurrentBoard();
        final char EMPTY_SLOT = 'E';


        for (int row = 0; row < Board.ROWS; row++) { // iterate rows, top to bottom (row 1-6)
            for (int col = 0; col < Board.COLUMNS; col++) { // iterate columns (left to right (columns A-G)
                char current = boardMatrix[row][col];

                if (current == 'E' || current == 'A') continue; // don't check the slot if it's empty


                // check to the right
                if (col + 3 < Board.COLUMNS)
                    if (current == boardMatrix[row][col + 1] &&
                            current == boardMatrix[row][col + 2] &&
                            current == boardMatrix[row][col + 3])
                        return current;
                if (row + 3 < Board.ROWS) {
                    // check up
                    if (current == boardMatrix[row + 1][col] &&
                            current == boardMatrix[row + 2][col] &&
                            current == boardMatrix[row + 3][col])
                        return current;

                    // check up and to the right
                    if (col + 3 < Board.COLUMNS &&
                            current == boardMatrix[row + 1][col + 1] &&
                            current == boardMatrix[row + 2][col + 2] &&
                            current == boardMatrix[row + 3][col + 3])
                        return current;

                    // check up and to the left
                    if (col - 3 >= 0 &&
                            current == boardMatrix[row + 1][col - 1] &&
                            current == boardMatrix[row + 2][col - 2] &&
                            current == boardMatrix[row + 3][col - 3])
                        return current;
                }
            }
        }
        return EMPTY_SLOT; // if all else fails and no row of four is found, return 'E' (empty slot)
    }

    public static void printLogs() {
        try {
            File file = new File("data/data.txt");
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader("data/logs.txt"));

                System.out.println("\nHere are the logs: ");

                String line = reader.readLine();

                while (line != null) {
                    System.out.println(line);
                    line = reader.readLine();
                }
            } else {
                System.out.println("\nThere are no logs yet. Play the game to view the logs.");
            }
        } catch (Exception e) {
            System.out.println("There was an error reading from the logs.");
            e.printStackTrace();
        }
    }

    public static void writeLogs(char winningPlayer) {
        try {
            Timestamp currentTimestamp = new Timestamp(new Date().getTime());
            PrintWriter output = new PrintWriter(new FileWriter("data/logs.txt"));

            String winningColor;

            if (winningPlayer == 'R') winningColor = "Red";
            else if (winningPlayer == 'Y') winningColor = "Yellow";
            else winningColor = "None";

            output.println("Game at " + currentTimestamp + " - Winner: " + winningColor);
            output.close();

            System.out.println("\nResults of game written to logs successfully.");
        } catch (Exception e) {
            System.out.println("There was an error writing to the logs.");
            e.printStackTrace();
        }
    }
}
