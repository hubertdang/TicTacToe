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

        // register the view and the controller (because of the button icons) as an observer of the model
        model.addObserver(view);
        model.addObserver(controller);

        controller.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        controller.pack();
        controller.setResizable(true);
        controller.setVisible(true);

        // Update controller buttons and the view to reflect the initial state of the model
        controller.update(model, null);
        view.update(model, null);
    }
}