/* Name: Pranav Rao
   Course Code : ICS3U0
   Program Title: Connect 4 [Antigravity Edition] - Main
   Date Completed : April 22, 2021
   Program Description: Main class and driver code for Connect 4 [Antigravity Edition].
*/

public class Main {
    public static void main(String[] args) { // main method
        System.out.println("Welcome to Connect 4 - Gravity Edition!");

        String[] options = { // declaring and initializing options array for dynamic display later on
                "New game",
                "How to play",
                "View logs of previous matches",
                "Exit"
        };

        boolean appRunning = true; // declare a boolean flag to signify if the app is running, and initialize it to true.
        GameHelper.initializeHashMap(); // calls method to set up the GameHelper class as necessary. This only needs to be done once, so this method will not be called again.

        while (appRunning) { // while the app is running (the boolean flag above is true)
            System.out.println("\nMain Menu - please select one of the options below: ");
            for (int i = 0; i < options.length; i++) { // dynamically displaying options
                System.out.printf("%d. %s%n", i + 1, options[i]);
            }

            System.out.print("Enter an option from the list (1-4): "); // prompt

            try { // user input can be unreliable, so using a try catch statement to catch any potential exceptions
                int optionChosen = GameHelper.scanner.nextInt(); // take user input
                GameHelper.scanner.nextLine(); // clear scanner buffer so as to not take in any unwanted input
                switch (optionChosen) { // check the option chosen
                    case 1: // if it is 1 (play a new game)
                        Game game = new Game(); // initialize new instance of the game class

                        // printing out the legend for the user to read
                        System.out.println("\nNew game started! Board legend: ");
                        System.out.println("E - Empty slot");
                        System.out.println("R - Slot occupied by red");
                        System.out.println("Y - Slot occupied by yellow");
                        System.out.println("A - Slot occupied by antigravity piece");

                        // printing out current board
                        System.out.println("\nCurrent board: ");
                        game.getBoard().print();
                        game.setUpGame(); // call set up game method, which will set up the instance of the Game class it is called on
                        while (game.getGameState() != -1) { // while the state of the game is not -1 (i.e. while it is running) - check Game class for more information
                            if (game.getGameState() == 1) { // if the gameState is 1 (i.e. player 1 is playing)
                                if (!game.executeTurn(game.getPlayer1())) game.setGameState(-1); // execute the turn with player 1 of the game instance. If the executeTurn() method returns false, set the gameState to -1 (ended) so the loop does not run again
                            } else if (game.getGameState() == 2) { // if the gameState is 1 (i.e. player 1 is playing)
                                if (!game.executeTurn(game.getPlayer2())) game.setGameState(-1); // execute the turn with player 1 of the game instance. If the executeTurn() method returns false, set the gameState to -1 (ended) so the loop does not run again
                            }
                        }
                        char winnerColor = game.getWinner() == 1 ? 'R' : 'Y'; // check the winner of the game, and figure out their colour. Winner is determined in the Game class. If there is no winner (gameWinner == 0), this char will default to yellow, but never be used
                        if (game.getWinner() != 0) { // if the winner of the game is NOT player 0 (i.e. no one). If this is the case, gameWinner will be 1 or 2.
                            System.out.printf("Player %d (%s) won the game!%n", game.getWinner(), game.getWinner() == 1 ? "red" : "yellow"); // let the players know who won the game
                            GameHelper.writeLogs(winnerColor); // write this result to the logs
                        }
                        else { // if no one won the game
                            System.out.println("No one won the game."); // inform players of this
                            GameHelper.writeLogs('N'); // write this result to the logs (draw)
                        }
                        System.out.println("Game exited successfully."); // let the users know the game exited successfully. After this, the current game instance is destroyed.
                        break;
                    case 2: // if it is 2 (how to play)
                        System.out.println("\nTo learn how to play this game, please visit this link: https://docs.google.com/document/d/1itUWjn1K5enw4uMxQpyCaDoXEoYSXHGMX0TPXAARk1Q/edit?usp=sharing");
                        break;
                    case 3: // if it is 3 (view the logs)
                        GameHelper.printLogs(); // print the logs
                        break;
                    case 4: // if it is 4 (exit)
                        System.out.println("\n" +
                                "Exiting game, thanks for playing!");
                        appRunning = false; // set the appRunning flag to false so the loop does not run again
                        break;
                    default: // if the input is invalid
                        System.out.println("Invalid input, please try again.");
                        break;
                }
            } catch (Exception ignored) { // if there is an exception
                System.out.println("Invalid input, please try again."); // inform the user of invalid input
                GameHelper.scanner.nextLine(); // clear scanner buffer
            }


        }

    }
}
