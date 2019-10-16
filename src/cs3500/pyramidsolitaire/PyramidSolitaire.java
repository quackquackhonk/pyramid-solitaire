package cs3500.pyramidsolitaire;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.TriPeaksPyramidSolitaire;

/**
 * Class that holds the main method for the PyramidSolitaire Game.
 */
public class PyramidSolitaire {

  private static int DEFAULT_ROWS = 7;
  private static int DEFAULT_DRAW = 3;

  /**
   * Main method for the Pyramid Solitaire game.
   *
   * @param args command line inputs (ignored).
   */
  public static void main(String[] args) {

    PyramidSolitaireController controller =
            new PyramidSolitaireTextualController(new InputStreamReader(System.in), System.out);
    PyramidSolitaireModel<Card> model;
    switch (args[0]) {
      case "basic":
        model = PyramidSolitaireCreator.create(PyramidSolitaireCreator.GameType.BASIC);
        break;
      case "relaxed":
        model = PyramidSolitaireCreator.create(PyramidSolitaireCreator.GameType.RELAXED);
        break;
      case "tripeaks":
        model = PyramidSolitaireCreator.create(PyramidSolitaireCreator.GameType.TRIPEAKS);
        break;
      default:
        return;
    }

    if (args.length == 1) {
      controller.playGame(model, model.getDeck(), true, DEFAULT_ROWS, DEFAULT_DRAW);
    }
    else if (args.length >= 3) {
      {
        try {
          int rows = Integer.parseInt(args[1]);
          int draw = Integer.parseInt(args[2]);
          controller.playGame(model, model.getDeck(), true, rows, draw);
        } catch (NumberFormatException | IllegalStateException e) {
          // Does nothing because arguments are invalid.
        }
      }
    }
  }
}
