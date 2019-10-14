package cs3500.pyramidsolitaire;

import java.io.InputStreamReader;
import java.util.Random;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Class that holds the main method for the PyramidSolitaire Game.
 */
public class PyramidSolitaire {

  /**
   * Main method for the Pyramid Solitaire game.
   * @param args command line inputs (ignored).
   */
  public static void main(String[] args) {

    PyramidSolitaireController controller =
            new PyramidSolitaireTextualController(new InputStreamReader(System.in), System.out);
    PyramidSolitaireModel model = new BasicPyramidSolitaire(new Random(1));

    controller.playGame(model, model.getDeck(), true, 7, 3);

  }

}
