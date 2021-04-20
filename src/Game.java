/* Name: Pranav Rao
   Course Code : ICS3U0
   Program Title: Connect 4 [Antigravity Edition] - Game
   Date Completed : April 22, 2021
   Program Description: Game class - an instance of this class represents the active game of Connect 4 [Antigravity Edition].
*/

public class Game {
    private Board board; // declaring board object
    private final Player player1, player2; // declaring the two player objects for this instance of the Game object
    private int gameState; // declaring int var for this game's state. (0 when game is in setup, 1 when it is player 1's turn, 2 for player 2's turn, -1 for game over
    private int turnNumber; // declaring int var for the current turn number
    private int winner; // declaring int var to store the winner's player number

    public Game() { // constructor
        board = new Board(); // initializing new board object
        player1 = new Player('R', 1); // creating new player object with color R (red) and identifier number 1
        player2 = new Player('Y', 2); // creating new player object with color Y (yellow) and identifier number 2
        gameState = 0; // set gameState to 0, indicating setup
        turnNumber = 1; // setting the turn number to 1 for beginning of game
    }

    public void setUpGame() { // method called to set up the game (placing initial antigravity pieces)
        GameHelper.placeAntiGravityPiece(player1, board); // place player 1's first antigravity piece
        GameHelper.placeAntiGravityPiece(player2, board); // place player 2's first antigravity piece
        GameHelper.placeAntiGravityPiece(player1, board); // place player 1's second antigravity piece
        GameHelper.placeAntiGravityPiece(player2, board); // place player 2's second antigravity piece

        gameState = 1; // set gameState to 1, indicating player 1's turn
    }

    public boolean executeTurn(Player player) { // method to execute a turn, given the player whose turn it is
        System.out.printf("It's player %d's (%s) turn. Player is currently on turn number %d.%n", player.getColorNum(), player.getColorName() == 'R' ? "red's" : "yellow's", turnNumber);
        System.out.println("\nCurrent board:");
        board.print(); // printing the current board for the player's information

        if (turnNumber % 3 == 0) { // if the current turn is a multiple of 3 (special operations required if so)
            String[] options = !(player.isRemovedAntiGravityPiece()) ? new String[]{ // if the player already removed an antigravity piece, the options array does not contain this option, else it does
                    "Drop regular piece",
                    "Place antigravity piece",
                    "Remove antigravity piece (one use only)",
                    "End game"
            } : new String[]{
                    "Drop regular piece",
                    "Place antigravity piece",
                    "End game"
            };

            int optionsVersion = options.length == 4 ? 1 : 2; // determining the version of the options array that was initialized
            boolean validInput = false; // declaring and initializing boolean flag to determine if valid input has been given or not


            System.out.printf("Player %d, here are your options for this turn:%n", player.getColorNum()); // displaying options

            for (int i = 0; i < options.length; i++) {
                System.out.printf("%d. %s%n", i + 1, options[i]);
            }

            if (optionsVersion == 1) { // if the version of the options array is 1 (4 options)
                while (!validInput) { // while valid input has not been given
                    try { // user input may generate exceptions, so a try-catch block is required
                        System.out.printf("Enter an option from the list (1-%d):%n", options.length); // prompt
                        int optionChosen = GameHelper.scanner.nextInt(); // getting the user's input
                        GameHelper.scanner.nextLine(); // clearing scanner buffer to not pick up unwanted lines

                        switch (optionChosen) { // checking value of the option chosen
                            case 1: // if it is 1
                                GameHelper.dropRegularPiece(player, board); // start process for a given player to drop a regular piece on the board
                                validInput = true; // change validInput to true so this loop does not run again
                                break;
                            case 2: // if it is 2
                                GameHelper.placeAntiGravityPiece(player, board); // start process for a given player to place an antigravity piece on the board
                                validInput = true; // change validInput to true so this loop does not run again
                                break;
                            case 3: // if it is 3
                                GameHelper.removeAntiGravityPiece(player, board); // start process for a given player to remove an antigravity piece on the board
                                validInput = true; // change validInput to true so this loop does not run again
                                break;
                            case 4: // if it is 4 (exit)
                                System.out.println("\nCurrent board: ");
                                board.print(); // print current board
                                return false; // return false to signal the driver method to end the game
                            default: // if option is invalid
                                System.out.println("Invalid input, please try again.\n");
                                break;
                        }
                    } catch (Exception ignored) { // if the user gives a wrong input and an exception is generated, this catch block will handle it
                        System.out.println("Invalid input, please try again.\n");
                        GameHelper.scanner.nextLine(); // clear scanner buffer
                    }
                }
            } else { // if the version of options is 2 (player has already removed antigravity piece so only 3 options)
                while (!validInput) { // while valid input has not been given
                    try {
                        System.out.printf("Enter an option from the list (1-%d):%n", options.length); // prompt
                        int optionChosen = GameHelper.scanner.nextInt(); // getting the user's input
                        GameHelper.scanner.nextLine(); // clearing scanner buffer to not pick up unwanted lines

                        switch (optionChosen) { // checking value of the option chosen
                            case 1: // if it is 1
                                GameHelper.dropRegularPiece(player, board); // start process for a given player to drop a regular piece on the board
                                validInput = true; // change validInput to true so this loop does not run again
                                break;
                            case 2:
                                GameHelper.placeAntiGravityPiece(player, board); // start process for a given player to place an antigravity piece on the board
                                validInput = true; // change validInput to true so this loop does not run again
                                break;
                            case 3: // if it is 4 (exit)
                                System.out.println("\nCurrent board: ");
                                board.print(); // print current board
                                return false; // return false to signal the driver method to end the game
                            default: // if option is invalid
                                System.out.println("Invalid input, please try again.\n");
                                break;
                        }
                    } catch (Exception ignored) { // if the user gives a wrong input and an exception is generated, this catch block will handle it
                        System.out.println("Invalid input, please try again.\n");
                        GameHelper.scanner.nextLine(); // clear scanner buffer
                    }
                }

            }

        } else { // if the turn number is NOT a multiple of 3 (player will not have option to place antigravity piece)
            String[] options = !(player.isRemovedAntiGravityPiece()) ? new String[]{ // if the player already removed an antigravity piece, the options array does not contain this option, else it does
                    "Drop regular piece",
                    "Remove antigravity piece (one use only)",
                    "End game"
            } : new String[]{
                    "Drop regular piece",
                    "End game"
            };

            int optionsVersion = options.length == 3 ? 1 : 2; // determining the version of the options array that was initialized
            boolean validInput = false; // declaring and initializing boolean flag to determine if valid input has been given or not

            System.out.printf("Player %d, here are your options for this turn:%n", player.getColorNum()); // displaying options

            for (int i = 0; i < options.length; i++) {
                System.out.printf("%d. %s%n", i + 1, options[i]);
            }

            if (optionsVersion == 1) { // if the version of the options array is 1 (3 options)
                while (!validInput) { // while valid input has not been given
                    try { // user input may generate exceptions, so a try-catch block is required
                        System.out.printf("Enter an option from the list (1-%d):%n", options.length); // prompt
                        int optionChosen = GameHelper.scanner.nextInt(); // getting the user's input
                        GameHelper.scanner.nextLine(); // clearing scanner buffer to not pick up unwanted lines

                        switch (optionChosen) { // checking value of the option chosen
                            case 1: // if it is 1
                                GameHelper.dropRegularPiece(player, board); // start process for a given player to drop a regular piece on the board
                                validInput = true; // change validInput to true so this loop does not run again
                                break;
                            case 2: // if it is 2
                                GameHelper.removeAntiGravityPiece(player, board); // start process for a given player to remove an antigravity piece on the board
                                validInput = true; // change validInput to true so this loop does not run again
                                break;
                            case 3: // if it is 3 (exit)
                                System.out.println("\nCurrent board: ");
                                board.print(); // print current board
                                return false; // return false to signal the driver method to end the game
                            default: // if option is invalid
                                System.out.println("Invalid input, please try again.\n");
                        }
                    } catch (Exception ignored) { // if the user gives a wrong input and an exception is generated, this catch block will handle it
                        System.out.println("Invalid input, please try again.\n");
                        GameHelper.scanner.nextLine(); // clear scanner buffer
                    }
                }
            } else {
                while (!validInput) { // if the version of options is 2 (player has already removed antigravity piece so only 2 options)
                    try {
                        System.out.printf("Enter an option from the list (1-%d):%n", options.length); // prompt
                        int optionChosen = GameHelper.scanner.nextInt(); // getting the user's input
                        GameHelper.scanner.nextLine(); // clearing scanner buffer to not pick up unwanted lines

                        switch (optionChosen) {
                            case 1: // if it is 1
                                GameHelper.dropRegularPiece(player, board); // start process for a given player to drop a regular piece on the board
                                validInput = true; // change validInput to true so this loop does not run again
                                break;
                            case 2: // if it is 2 (exit)
                                System.out.println("\nCurrent board: ");
                                board.print(); // print current board
                                return false; // return false to signal the driver method to end the game
                            default: // if option is invalid
                                System.out.println("Invalid input, please try again.\n");
                                break;
                        }
                    } catch (Exception ignored) { // if the user gives a wrong input and an exception is generated, this catch block will handle it
                        System.out.println("Invalid input, please try again.\n");
                        GameHelper.scanner.nextLine(); // clear scanner buffer
                    }
                }
            }
        }

        // if all input was given correctly, print the current board
        System.out.println("\nCurrent board: ");
        board.print();

        if (gameState == 2) turnNumber++; // if the current player playing is player 2, increment the turn number by 1
        gameState = gameState == 1 ? 2 : 1; // change the game state to 1 if it is 2 and 2 if it is 1 (signifies other player is playing)

        // check for a win at the end of each turn completed successfully
        char isWin = GameHelper.checkWin(board);
        if (isWin != 'E') { // checkWin returns 'E' if there is no winner, so if it is not 'E', then there was a winner
            winner = isWin == 'R' ? 1 : 2; // determines the number of the player that won (1 for red, 2 for yellow)
            gameState = -1; // the game has been won and completed, so sets gameState to -1 to signify game over
        }

        return true; // if the method executed successfully, return true to the driver method
    }


    // getters and setters

    public Board getBoard() {
        return board;
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getWinner() {
        return winner;
    }
}
