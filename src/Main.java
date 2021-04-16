public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Connect 4 - Gravity Edition!");

        String[] options = {
                "New game",
                "How to play",
                "View logs of previous matches",
                "Exit"
        };

        boolean appRunning = true;
        GameHelper.initializeHashMap();

        while (appRunning) {
            System.out.println("\nMain Menu - please select one of the options below: ");
            for (int i = 0; i < options.length; i++) {
                System.out.printf("%d. %s%n", i + 1, options[i]);
            }

            System.out.print("Enter an option from the list (1-4): ");

            try {
                int optionChosen = GameHelper.scanner.nextInt();
                GameHelper.scanner.nextLine();
                switch (optionChosen) {
                    case 1:
                        Game game = new Game();
                        System.out.println("\nNew game started! Board legend: ");
                        System.out.println("E - Empty slot");
                        System.out.println("R - Slot occupied by red");
                        System.out.println("Y - Slot occupied by yellow");
                        System.out.println("A - Slot occupied by antigravity piece");

                        System.out.println("\nCurrent board: ");
                        game.getBoard().print();
                        game.setUpGame();
                        while (game.getGameState() != -1) {
                            if (game.getGameState() == 1) {
                                if (!game.executeTurn(game.getPlayer1())) game.setGameState(-1);
                            } else if (game.getGameState() == 2) {
                                if (!game.executeTurn(game.getPlayer2())) game.setGameState(-1);
                            }
                        }
                        char winnerColor = game.getWinner() == 1 ? 'R' : 'Y';
                        if (game.getWinner() != 0) {
                            System.out.printf("Player %d (%s) won the game!%n", game.getWinner(), game.getWinner() == 1 ? "red" : "yellow");
                            GameHelper.writeLogs(winnerColor);
                        }
                        else {
                            System.out.println("No one won the game.");
                            GameHelper.writeLogs('N');
                        }
                        System.out.println("Game exited successfully.\n");
                        break;
                    case 2:
                        System.out.println("To learn how to play this game, please visit this link: https://docs.google.com/document/d/1itUWjn1K5enw4uMxQpyCaDoXEoYSXHGMX0TPXAARk1Q/edit?usp=sharing");
                        break;
                    case 3:
                        GameHelper.printLogs();
                        break;
                    case 4:
                        appRunning = false;
                        break;
                    default:
                        System.out.println("That is not a valid input. Please try again.");
                        break;
                }
            } catch (Exception ignored) {
                System.out.println("That is not a valid input. Please try again.");
                GameHelper.scanner.nextLine();
            }


        }

    }
}
