import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * The controller part of the user-interface for the TicTacToe application,
 * built from the Swing and AWT frameworks.
 *
 * @author Hubert Dang
 * @version March 30, 2023
 */

public class TicTacToeController extends JFrame implements ActionListener {
    private JButton buttonBoard[][]; // 3x3 array of buttons on the board
    private TicTacToeModel model;

    public TicTacToeController(TicTacToeView view, TicTacToeModel model) {
        super("TicTacToe");

        this.setPreferredSize(new Dimension(800, 800));
        this.model = model;
        buttonBoard = new JButton[3][3];
        populateFrame(view);
        registerListener();
    }


    /**
     * Populate the frame with the view.
     *
     * @param view The view to populate the frame with.
     */
    private void populateFrame(TicTacToeView view) {
        Container contentPane = this.getContentPane();
        contentPane.add(view, BorderLayout.SOUTH);
        // create the 3x3 grid for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3));
        // initialize the buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttonBoard[i][j] = new JButton("");
                // add buttons to the button panel
                buttonPanel.add(buttonBoard[i][j]);
            }
        }
        // place buttons on top of the game state
        contentPane.add(buttonPanel, BorderLayout.CENTER);
    }


    /**
     * Register this frame as the listener for the buttons.
     */
    private void registerListener() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttonBoard[i][j].addActionListener(this);
            }
        }
    }


    /**
     * This action listener is called when the user clicks on any
     * of the GUI's buttons.
     *
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (button == buttonBoard[i][j]) {
                    model.setSquare(i, j);
                    break;
                }
            }
        }
        // Disable all buttons when someone has won the game
        if (model.haveWinner()) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttonBoard[i][j].setEnabled(false);
                }
            }
        }
    }


    /**
     * Returns a button in the buttonBoard.
     *
     * @param row The row of the button.
     * @param col The column of the button.
     * @return The button at the specified row and column in the buttonBoard.
     */
    public JButton getButton(int row, int col) {
        return buttonBoard[row][col];
    }
}