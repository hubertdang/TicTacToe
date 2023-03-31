/**
 * A TicTacToe game with a GUI.
 *
 * @author Hubert Dang
 * @version March 30, 2023
 */

import javax.swing.*;

public class TicTacToe {
    public static void main(String[] args) {
        TicTacToeModel model = new TicTacToeModel();
        TicTacToeView view = new TicTacToeView();
        TicTacToeController controller = new TicTacToeController(view, model);

        // enable the view to access the game's controllers in order to access button icons
        view.setController(controller);

        // register the view as an observer of the model
        model.addObserver(view);

        controller.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        controller.pack();
        controller.setResizable(false);
        controller.setVisible(true);

        // Update the view to reflect the initial state of the model
        view.update(model, null);
    }
}