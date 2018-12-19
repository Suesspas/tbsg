package test;
import model.Fighter;
import model.Game;
import model.Player;
import model.PlayerType;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.io.IOException;

/**
 * This class enables user interaction with the Game via input through a
 * console.
 *
 * Created by Pascal on 13.12.2018.
 */
public final class Shell {

    private final static String PROMPT = "tbsg> ";
    private static Game game = null;
    private static int level;
    private static boolean gameOver = false;

    private Shell() {
    }

    /**
     * Communication with the Game trough a buffered reader. The input is
     * checked by a switch-case query so the first char of the commands is
     * sufficient for the input to work.
     *
     * @param args
     *            unused
     * @throws IOException
     *             if an IOException at {@code readLine()} occurs
     */
    public static void main(String[] args) throws IOException {
        BufferedReader stdin =
                new BufferedReader(new InputStreamReader(System.in));
        boolean quit = false;
        while (!quit) {
            System.out.print(PROMPT);
            String input = stdin.readLine(); // read one line
            if (input == null) {
                break;
            } // no more input?
            if (input.trim().equals("")) {
                printErrorMessage("No input found");
                continue;
            }
            // split input on white spaces
            String[] tokens = input.trim().split("\\s+");
            switch (tokens[0].toUpperCase().charAt(0)) {
                case 'N':
                    initialize(tokens);
                    break;
                case 'M':
                    move(tokens);
                    break;
                case 'P':
                    print(tokens);
                    break;
                case 'A':
                    add(tokens);
                    break;
                case 'H':
                    help(tokens);
                    break;
                case 'Q':
                    quit = quit(tokens);
                    break;
                default:
                    nocommand(tokens);
            }
        }
    }

    private static void add(String[] arguments) {
        if (checkNumberOfArguments(arguments.length, 1)) {
            //TODO nicht in shell fighter + player anlegen
            Fighter stdFighter2 = new Fighter("testeroni", 11, 6, 5, 2, 2, PlayerType.Human);
            game.addFighterToPlayer(stdFighter2, PlayerType.Human);
        } else {
            printErrorMessage("No arguments needed");
        }
    }

    private static void move(String[] arguments) {
        if (checkNumberOfArguments(arguments.length, 3)) {
            //TODO variable angriffe
            int atkPos = -1;
            int defPos = -1;
            try {
                atkPos = Integer.parseInt(arguments[1]);
                defPos = Integer.parseInt(arguments[2]);
            } catch (NumberFormatException e) {
                printErrorMessage("both arguments have to be numbers.");
            }
            game.humanAttack(atkPos, defPos);
        } else {
            printErrorMessage("2 arguments needed, postion of attacker 0-2, and position of defender 0-2");
        }
    }

    private static void initialize(String[] arguments) {
        if (checkNumberOfArguments(arguments.length, 1)) {
            game = new Game();
            System.out.println("New game initialized.");
        } else {
            printErrorMessage("No arguments needed");
        }
    }

    private static void print(String[] arguments) {
        if (checkNumberOfArguments(arguments.length, 1)) {
            if (game == null) {
                printErrorMessage("You need to initialize a Field first. "
                        + "Use the NEW command");
            } else {
                System.out.println(game.toString());
            }
        } else {
            printErrorMessage("No arguments needed");
        }
    }

    private static void help(String[] arguments) {
        if (!checkNumberOfArguments(arguments.length, 1)) {
            printErrorMessage("No arguments needed");
            return;
        }
        //TODO
        String tab = "             ";
        System.out.println("Commands: ");
        System.out.println("");
        System.out.println("NEW        : "
                + "Initializes a new game");
        System.out.println("PRINT      : "
                + "Gives back the string representation "
                + "of the current Game\n");
        System.out.println("QUIT       : Quits the program.");
        System.out.println("");
        System.out.println("The parameters for all of the commands"
                + " are not optional!\n"
                + "Also the commands with no parameters given"
                + " must not contain any parameters!");
        System.out.println("");
        System.out.println("The first char of the commands is sufficient"
                + " for the input to work.\n"
                + "Also it doesn't matter if upper or"
                + " lower case is used.\n");


    }

    private static boolean quit(String[] arguments) {
        if (checkNumberOfArguments(arguments.length, 1)) {
            return true;
        } else {
            printErrorMessage("No arguments needed");
            return false;
        }
    }

    private static void nocommand(String[] arguments) {
        printErrorMessage("Unknown command\ntype HELP to see"
                + " all available commands and useful information");
    }

    private static boolean checkNumberOfArguments(int arguments,
                                                  int neededArguments) {
        return (arguments == neededArguments);
    }

    private static void printErrorMessage(String error) {
        System.out.println("Error! " + error);
    }

}
