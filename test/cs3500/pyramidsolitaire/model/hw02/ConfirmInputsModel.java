package cs3500.pyramidsolitaire.model.hw02;

import java.util.List;

/**
 * Mock model used for testing if the controller is passing the correct inputs to the model.
 */
public class ConfirmInputsModel implements PyramidSolitaireModel<Card> {

  private final StringBuilder log;

  public ConfirmInputsModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public List<Card> getDeck() {
    return null;
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numRows, int numDraw) {
    return;
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2) throws IllegalStateException {
    log.append(String.format("row1: %d, card1: %d, row2: %d, card2: %d\n",
            row1, card1, row2, card2));
  }

  @Override
  public void remove(int row, int card) throws IllegalStateException {
    log.append(String.format("row: %d, card: %d\n", row, card));
  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card) throws IllegalStateException {
    log.append(String.format("drawIndex: %d, row: %d, card: %d\n", drawIndex, row, card));
  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalStateException {
    log.append(String.format("drawIndex: %d\n", drawIndex));
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
