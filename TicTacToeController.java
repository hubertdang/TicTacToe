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
    private JMenuItem newItem;
    private JMenuItem quitItem;

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
        // place menu above buttons
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar); // add menu bar to the frame

        JMenu fileMenu = new JMenu("Options"); // create a menu
        menuBar.add(fileMenu); // and add to the menu bar

        newItem = new JMenuItem("New"); // create a menu item called "New"
        fileMenu.add(newItem); // and add to the menu

        quitItem = new JMenuItem("Quit"); // create a menu item called "Quit"
        fileMenu.add(quitItem); // and add to the menu

        final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(); // to save typing
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, SHORTCUT_MASK));
        quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
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
        // listen for menu selections
        newItem.addActionListener(this);
        quitItem.addActionListener(new ActionListener() // create an anonymous inner class
                                   { // start of anonymous subclass of ActionListener
                                       // this allows us to put the code for this action here
                                       public void actionPerformed(ActionEvent event) {
                                           System.exit(0); // quit
                                       }
                                   } // end of anonymous subclass
        ); // end of addActionListener parameter list and statement
    }


    /**
     * This action listener is called when the user clicks on any
     * of the GUI's buttons.
     *
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource(); // get the action

        if (o instanceof JButton) {
            JButton button = (JButton) o;
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
        } else { // it's a JMenu item
            JMenuItem item = (JMenuItem) o;

            if (item == newItem) {
                // enable all buttons in case user is starting new game after a win
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        buttonBoard[i][j].setEnabled(true);
                    }
                }
                model.newGame();
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