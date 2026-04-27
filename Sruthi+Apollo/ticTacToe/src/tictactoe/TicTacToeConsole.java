package tictactoe;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToeConsole {

    private final Board board;
    private final GameLogic logic;
    private final Scanner scanner;

    public TicTacToeConsole() {
        this.board = new Board("board.csv");
        this.logic = new GameLogic();
        this.scanner = new Scanner(System.in);

        char[][] grid = board.getGrid();
        if (grid == null || grid[0][0] == '\u0000') {
            board.clearBoard();
        }
    }

    public void run() {
        System.out.println();
        System.out.println("=========================");
        System.out.println("   TIC TAC TOE - CONSOLE ");
        System.out.println("=========================");
        System.out.println("Rows and columns are numbered 1 - 3.");
        System.out.println("Type 'q' at any prompt to quit.");
        System.out.println("Type 'n' at any prompt to start a new game.");
        System.out.println();

        board.clearBoard();
        printBoard();

        boolean running = true;
        while (running) {
            if (logic.isGameOver(board)) {
                announceGameOver();
                if (!askPlayAgain()) {
                    running = false;
                    break;
                }
                board.clearBoard();
                printBoard();
                continue;
            }

            char player = logic.getCurrentPlayer(board);
            System.out.println("Player " + player + "'s turn.");

            int row = readCoordinate("Enter row (1-3): ");
            if (row == -1) { running = false; break; }
            if (row == -2) { board.clearBoard(); printBoard(); continue; }

            int col = readCoordinate("Enter column (1-3): ");
            if (col == -1) { running = false; break; }
            if (col == -2) { board.clearBoard(); printBoard(); continue; }

            boolean placed = logic.makeMove(board, row - 1, col - 1);
            if (!placed) {
                System.out.println("That spot is taken or invalid. Try again.");
                System.out.println();
                continue;
            }

            printBoard();
        }

        System.out.println("Thanks for playing!");
        scanner.close();
    }

    private int readCoordinate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.next().trim();
            if (input.equalsIgnoreCase("q")) {
                return -1;
            }
            if (input.equalsIgnoreCase("n")) {
                return -2;
            }
            try {
                int value = Integer.parseInt(input);
                if (value < 1 || value > 3) {
                    System.out.println("Please enter a number between 1 and 3.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("That is not a valid number.");
            } catch (InputMismatchException e) {
                System.out.println("That is not a valid number.");
            }
        }
    }

    private boolean askPlayAgain() {
        while (true) {
            System.out.print("Play again? (y/n): ");
            String input = scanner.next().trim();
            if (input.equalsIgnoreCase("y")) {
                return true;
            }
            if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("q")) {
                return false;
            }
            System.out.println("Please enter 'y' or 'n'.");
        }
    }

    private void announceGameOver() {
        System.out.println();
        if (logic.checkWin(board, 'X')) {
            System.out.println(">> X wins! <<");
        } else if (logic.checkWin(board, 'O')) {
            System.out.println(">> O wins! <<");
        } else if (logic.isDraw(board)) {
            System.out.println(">> It's a draw. <<");
        }
        System.out.println();
    }

    private void printBoard() {
        char[][] grid = board.getGrid();
        System.out.println();
        System.out.println("    1   2   3");
        System.out.println("  +---+---+---+");
        for (int row = 0; row < 3; row++) {
            System.out.print((row + 1) + " |");
            for (int col = 0; col < 3; col++) {
                char c = grid[row][col];
                String display = (c == 'E') ? " " : String.valueOf(c);
                System.out.print(" " + display + " |");
            }
            System.out.println();
            System.out.println("  +---+---+---+");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        new TicTacToeConsole().run();
    }
}
