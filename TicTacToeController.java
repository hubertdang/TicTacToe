import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

/**
 * The controller part of the user-interface for the TicTacToe application,
 * built from the Swing and AWT frameworks.
 *
 * @author Hubert Dang
 * @version April 4, 2023
 */

public class TicTacToeController extends JFrame implements ActionListener, MouseListener {
    private JButton buttonBoard[][]; // 3x3 array of buttons on the board
    private TicTacToeModel model;
    private JMenuItem newItem;
    private JMenuItem quitItem;
    private JMenuItem resetItem;  // for resetting the players' scores
    private JMenuItem changeStartingPlayerItem;  // for changing the starting player
    AudioClip click;


    /**
     * Create new controller for the TicTacToe game.
     *
     * @param view The game's view.
     * @param model The game's model.
     */
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

        // create a menu and place it above buttons
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar); // add menu bar to the frame

        JMenu fileMenu = new JMenu("Game"); // create a menu
        fileMenu.addMouseListener(this);
        fileMenu.setFont(new Font("serif", Font.PLAIN, 24));
        menuBar.add(fileMenu); // and add to the menu bar

        newItem = new JMenuItem("New game"); // create a menu item called "New"
        fileMenu.add(newItem); // and add to the menu

        resetItem = new JMenuItem("Reset score");
        fileMenu.add(resetItem);

        changeStartingPlayerItem = new JMenuItem("Change starting player");
        fileMenu.add(changeStartingPlayerItem);

        quitItem = new JMenuItem("Quit"); // create a menu item called "Quit"
        fileMenu.add(quitItem); // and add to the menu

        final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(); // to save typing
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, SHORTCUT_MASK));
        quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
        resetItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, SHORTCUT_MASK));
        changeStartingPlayerItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, SHORTCUT_MASK));
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
        resetItem.addActionListener(this);
        changeStartingPlayerItem.addActionListener(this);
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
                        // only make sound if the button wasn't marked
                        if (model.getCurrentPlayer() == model.PLAYER_X && model.getMark(i,j) == model.EMPTY) {
                            URL urlClick = TicTacToeController.class.getResource("sword.wav");
                            click = Applet.newAudioClip(urlClick);
                            click.play(); // just plays clip once
                            // only make sound if the button wasn't marked
                        } else if (model.getCurrentPlayer() == model.PLAYER_O && model.getMark(i,j) == model.EMPTY) {
                            URL urlClick = TicTacToeController.class.getResource("shield.wav");
                            click = Applet.newAudioClip(urlClick);
                            click.play(); // just plays clip once
                        }
                        model.setSquare(i, j);
                        break;
                    }
                }
            }
            // Disable all buttons when someone has won the game or tied
            String winner = model.getWinner();
            if (winner != model.EMPTY) {
                if (winner != model.TIE) {  // only play the winning sound if someone won, not including ties
                    URL urlClick = TicTacToeController.class.getResource("won.wav");
                    click = Applet.newAudioClip(urlClick);
                    click.play(); // just plays clip once
                }
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
            } else if (item == resetItem) {
                model.resetScore();
            } else if (item == changeStartingPlayerItem) {
                model.changeStartingPlayer();
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


    /**
     * Detects when the mouse enters the component.  We are only "listening" to the
     * JMenu.  We highlight the menu name when the mouse goes into that component.
     *
     * @param e The mouse event triggered when the mouse was moved into the component
     */
    public void mouseEntered(MouseEvent e) {
        JMenu item = (JMenu) e.getSource();
        item.setSelected(true); // highlight the menu name
    }

    /**
     * Detects when the mouse exits the component.  We are only "listening" to the
     * JMenu.  We stop highlighting the menu name when the mouse exits  that component.
     *
     * @param e The mouse event triggered when the mouse was moved out of the component
     */
    public void mouseExited(MouseEvent e) {
        JMenu item = (JMenu) e.getSource();
        item.setSelected(false); // stop highlighting the menu name
    }


    // empty implementations of methods in MouseListener interface

    /**
     * Not used.
     */
    public void mouseClicked(MouseEvent e){}

    /**
     * Not used.
     */
    public void mousePressed(MouseEvent e){}

    /**
     * Not used.
     */
    public void mouseReleased(MouseEvent e){}
}