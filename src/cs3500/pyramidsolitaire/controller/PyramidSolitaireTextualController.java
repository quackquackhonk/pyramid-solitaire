package cs3500.pyramidsolitaire.controller;

import java.util.List;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

public class PyramidSolitaireTextualController implements PyramidSolitaireController {

  private final Readable in;
  private final Appendable out;

  public PyramidSolitaireTextualController(Readable in, Appendable out) {
    this.in = in;
    this.out = out;
  }


  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck, boolean shuffle, int numRows, int numDraw) {
    model.startGame(deck, shuffle, numRows, numDraw);

    // TODO: finish this.

  }

}
