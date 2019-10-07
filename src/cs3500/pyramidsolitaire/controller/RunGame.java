package cs3500.pyramidsolitaire.controller;

import java.io.InputStreamReader;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Class that holds the main method for the PyramidSolitaire Game.
 */
public class RunGame {

  /**
   * Main method for the Pyramid Solitaire game.
   * @param args command line inputs (ignored).
   */
  public static void main(String[] args) {

    PyramidSolitaireController controller =
            new PyramidSolitaireTextualController(new InputStreamReader(System.in), System.out);
    PyramidSolitaireModel model = new BasicPyramidSolitaire();

    controller.playGame(model, model.getDeck(), false, 7, 3);

  }

}
