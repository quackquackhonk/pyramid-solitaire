package cs3500.pyramidsolitaire.model.hw04;

import java.util.List;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * A class for playing a game of Pyramid Solitaire with a relaxed set of rules. You can combine
 * cards below the desired card so long as there is only one card below the desired card to
 * remove, and they still sum to be 13.
 */
public class RelaxedPyramidSolitaire implements PyramidSolitaireModel<Card> {



  @Override
  public List<Card> getDeck() {
    return null;
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numRows, int numDraw) {

  }

  @Override
  public void remove(int row1, int card1, int row2, int card2) throws IllegalStateException {

  }

  @Override
  public void remove(int row, int card) throws IllegalStateException {

  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card) throws IllegalStateException {

  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalStateException {

  }

  @Override
  public int getNumRows() {
    return 0;
  }

  @Override
  public int getNumDraw() {
    return 0;
  }

  @Override
  public int getRowWidth(int row) {
    return 0;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    return false;
  }

  @Override
  public int getScore() throws IllegalStateException {
    return 0;
  }

  @Override
  public Card getCardAt(int row, int card) throws IllegalStateException {
    return null;
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    return null;
  }
}
