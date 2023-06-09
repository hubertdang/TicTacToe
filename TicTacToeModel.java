import java.awt.image.WritableRenderedImage;
import java.util.*;
import java.util.Observable;

/**
 * A class modelling a tic-tac-toe (noughts and crosses, Xs and Os) game.
 *
 * Note: The 3x3 array of Strings was kept because the game's buttons are in
 * the controller class, and the logic for marking squares is in this (model)
 * class.
 *
 * @author Lynn Marshall
 * @author Hubert Dang
 * @version April 4, 2023
 */

public class TicTacToeModel extends Observable {
    public static final String PLAYER_X = "X"; // player using "X"
    public static final String PLAYER_O = "O"; // player using "O"
    public static final String EMPTY = " ";  // empty cell
    public static final String TIE = "T"; // game ended in a tie

    private String player;   // current player (PLAYER_X or PLAYER_O)
    private String startingPlayer;  // the player that will start the game

    private String winner;   // winner: PLAYER_X, PLAYER_O, TIE, EMPTY = in progress

    private int numWinsX;  // number of times player using "X" won
    private int numWinsO;  // number of times player using "O" won

    private int numFreeSquares; // number of squares still free

    private String board[][]; // 3x3 array representing the board

    /**
     * Constructs a new Tic-Tac-Toe board.
     */
    public TicTacToeModel() {
        board = new String[3][3];
        startingPlayer = PLAYER_X;  // player X starts by default
        clearBoard();
        resetScore();
    }

    /**
     * Sets everything up for a new game.  Marks all squares in the Tic Tac Toe board as empty,
     * and indicates no winner yet, 9 free squares and the current player is the starting player.
     */
    private void clearBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
            }
        }
        winner = EMPTY;
        numFreeSquares = 9;
        player = startingPlayer;
    }


    /**
     * Changes the starting player to the other player.
     */
    public void changeStartingPlayer() {
        if (startingPlayer == PLAYER_X) {
            startingPlayer = PLAYER_O;
        } else {
            startingPlayer = PLAYER_X;
        }
    }


    /**
     * Resets the number of wins for player X and O.
     */
    public void resetScore() {
        numWinsX = 0;
        numWinsO = 0;
        setChanged();
        notifyObservers();
    }


    /**
     * Resets the game to its initial state.
     *
     * Note: This method was created because clearBoard could not be used to notify the observer.
     */
    public void newGame() {
        this.clearBoard();
        setChanged();
        notifyObservers();
    }


    /**
     * Fills a square in the 3x3 board with either an X or an O. If the square is already
     * marked, do nothing.
     *
     * @param row The row of the square.
     * @param col The column of the square.
     */
    public void setSquare(int row, int col) {
        String potentialWinner = player;  // store the player in case they win on this turn
        // only fill square if it is a valid and empty square
        if (row >= 0 && row <= 2 && col >= 0 && col <= 2 && board[row][col] == EMPTY) {
            board[row][col] = player;
            numFreeSquares--;
            // change to other player
            if (player == PLAYER_X) {
                player = PLAYER_O;
            } else {
                player = PLAYER_X;
            }
        }
        // see if the game is over
        if (haveWinner(row, col)) {
            winner = potentialWinner; // must be the player who just went
            incrementNumWins(winner);
        } else if (numFreeSquares == 0) {
            winner = TIE; // board is full so it's a tie
        }
        setChanged();
        notifyObservers();
    }


    /**
     * Returns the board.
     *
     * Note: This method was created for the view to access the values of the board.
     *
     * @return The board.
     */
    public String[][] getBoard() {
        return board;
    }

    /**
     * Returns true if filling the given square gives us a winner, and false
     * otherwise.
     *
     * @param row of square just set
     * @param col of square just set
     * @return true if we have a winner, false otherwise
     */
    private boolean haveWinner(int row, int col) {
        // unless at least 5 squares have been filled, we don't need to go any further
        // (the earliest we can have a winner is after player X's 3rd move).

        if (numFreeSquares > 4) return false;

        // Note: We don't need to check all rows, columns, and diagonals, only those
        // that contain the latest filled square.  We know that we have a winner
        // if all 3 squares are the same, as they can't all be blank (as the latest
        // filled square is one of them).

        // check row "row"
        if (board[row][0].equals(board[row][1]) &&
                board[row][0].equals(board[row][2])) return true;

        // check column "col"
        if (board[0][col].equals(board[1][col]) &&
                board[0][col].equals(board[2][col])) return true;

        // if row=col check one diagonal
        if (row == col)
            if (board[0][0].equals(board[1][1]) &&
                    board[0][0].equals(board[2][2])) return true;

        // if row=2-col check other diagonal
        if (row == 2 - col)
            if (board[0][2].equals(board[1][1]) &&
                    board[0][2].equals(board[2][0])) return true;

        // no winner yet
        return false;
    }


    /**
     * Returns the winner.
     *
     * Note: This method was created to be used by the controller to identify if the
     * game was won by a particular player, or if it was tied.
     *
     * @return A String of the winner.
     */
    public String getWinner() {
        return winner;
    }


    /**
     * Return the game's state as a String.
     *
     * Note: This method takes logic from the toString method in lab 10.
     *
     * @return A String of the game's current state.
     */
    public String getGameState() {
        String gameState;
        if (winner == EMPTY) {
            gameState = "Game in progress: " + player + "'s turn";
        } else if (winner == TIE) {
            gameState = "Tie";
        } else {
            gameState = winner + " wins";
        }
        return gameState;
    }


    /**
     * Returns the number of wins that a player has.
     *
     * @param player The player whose number of wins is being returned.
     * @return The number of times a player has won.
     */
    public int getNumWins(String player) {
        if (player == PLAYER_X) {
            return numWinsX;
        } else {
            return numWinsO;
        }
    }


    /**
     * Increments a player's number of wins by 1.
     *
     * @param player The player whose number of wins is to be incremented.
     */
    private void incrementNumWins(String player) {
        if (player == PLAYER_X) {
            numWinsX++;
        } else {
            numWinsO++;
        }
    }


    /**
     * Returns the String of the current player.
     *
     * @return A String of the current player
     */
    public String getCurrentPlayer() {
        return player;
    }


    /**
     * Returns the mark at a specified square on the board.
     *
     * @param row The row of the tile.
     * @param col The column of the tile.
     * @return The String mark at the specified square on the board.
     *
     * Note: This method was created for the controller to know the mark of a square
     * in method actionPerformed.
     */
    public String getMark(int row, int col) {
        return board[row][col];
    }
}

