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

    public TicTacToeView() {
        gameStateDisplay = new JTextField();
        gameStateDisplay.setEditable(false);
        gameStateDisplay.setFont(new Font(null, Font.PLAIN, 18));
        gameStateDisplay.setHorizontalAlignment(JTextField.LEFT);
        this.setLayout(new GridLayout(1, 1));
        this.add(gameStateDisplay);
    }


    /**
     * See the documentation for java.util.Observer.
     */
    public void update(Observable o, Object arg) {
        TicTacToeModel model = (TicTacToeModel) o;
        // display current game state
        String gameState = model.getGameState();
        gameStateDisplay.setText(gameState);
    }
}

