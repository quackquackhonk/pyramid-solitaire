package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

public class PyramidSolitaireCreator {

  public enum GameType { BASIC, RELAXED, TRIPEAKS }

  public static PyramidSolitaireModel create(GameType type) {
    switch (type) {
      case BASIC:
        return new BasicPyramidSolitaire();
      case RELAXED:
        return new RelaxedPyramidSolitaire();
      case TRIPEAKS:
        return new TriPeaksPyramidSolitaire();
      default:
        throw new IllegalArgumentException("Invalid game type!");
    }
  }

}
