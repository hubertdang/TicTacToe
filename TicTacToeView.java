import java.awt.*;
import javax.swing.*;
import java.util.Observer;
import java.util.Observable;

/**
 * The user interface for the TicTacToe application,
 * built from the Swing and AWT frameworks.
 *
 * @author Hubert Dang
 * @version March 30, 2023
 */

public class TicTacToeView extends JPanel implements Observer {

    private JLabel gameStateDisplay;
    private JPanel gameScore;
    private JLabel numWinsXDisplay;
    private JLabel numWinsODisplay;
    private JMenuItem quitItem;
    private static ImageIcon oIcon = new ImageIcon("oicon.jpg");
    private static ImageIcon xIcon = new ImageIcon("xicon.jpg");
    private TicTacToeController controller;

    public TicTacToeView() {
        this.setLayout(new BorderLayout());

        // displaying the game state
        gameStateDisplay = new JLabel();
        gameStateDisplay.setFont(new Font("serif", Font.PLAIN, 24));
        this.add(gameStateDisplay, BorderLayout.WEST);

        // create text fields to show number of wins for player X and player O
        numWinsXDisplay = new JLabel();
        numWinsODisplay = new JLabel();
        numWinsXDisplay.setFont(new Font("serif", Font.PLAIN, 24));
        numWinsODisplay.setFont(new Font("serif", Font.PLAIN, 24));

        // create panel for the score and add the text fields for number of wins
        gameScore = new JPanel();
        gameScore.setLayout(new GridLayout(2, 1));

        gameScore.add(numWinsODisplay);
        gameScore.add(numWinsXDisplay);
        this.add(gameScore, BorderLayout.EAST);
    }


    /**
     * Set the controller to controller.
     * <p>
     * Note: This method was created to be able to access the game's
     * controller from within the view.
     *
     * @param controller
     */
    public void setController(TicTacToeController controller) {
        this.controller = controller;
    }


    /**
     * See the documentation for java.util.Observer.
     */
    public void update(Observable o, Object arg) {
        TicTacToeModel model = (TicTacToeModel) o;

        // display current game state
        String gameState = model.getGameState();
        gameStateDisplay.setText(gameState);

        // display the score between player X and O
        int numWinsX = model.getNumWins(model.PLAYER_X);
        int numWinsO = model.getNumWins(model.PLAYER_O);
        numWinsXDisplay.setText("X number of wins: " + numWinsX + " ");
        numWinsODisplay.setText("O number of wins: " + numWinsO + " ");

        // display the correct marks on the buttons
        String[][] board = model.getBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // set button icons according to their mark
                if (board[i][j] == model.PLAYER_X) {
                    controller.getButton(i, j).setIcon(xIcon);
                } else if (board[i][j] == model.PLAYER_O) {
                    controller.getButton(i, j).setIcon(oIcon);
                } else {
                    controller.getButton(i, j).setIcon(null);
                }
            }
        }
    }
}

