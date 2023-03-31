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

    private JTextField gameStateDisplay;
    private JMenuItem quitItem;
    private static ImageIcon oIcon = new ImageIcon("oicon.jpg");
    private static ImageIcon xIcon = new ImageIcon("xicon.jpg");
    private TicTacToeController controller;

    public TicTacToeView() {
        gameStateDisplay = new JTextField();
        gameStateDisplay.setEditable(false);
        gameStateDisplay.setFont(new Font(null, Font.PLAIN, 18));
        gameStateDisplay.setHorizontalAlignment(JTextField.LEFT);
        this.setLayout(new GridLayout(1, 1));
        this.add(gameStateDisplay);
    }


    /**
     * Set the controller to controller.
     *
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
        // display the correct marks on the buttons
        String[][] board = model.getBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // set button icons according to their mark
                if (board[i][j] == "X") {
                    controller.getButton(i, j).setIcon(xIcon);
                }
                if (board[i][j] == "O") {
                    controller.getButton(i, j).setIcon(oIcon);
                }
            }
        }
    }
}

