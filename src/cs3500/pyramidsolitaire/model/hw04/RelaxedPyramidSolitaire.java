package cs3500.pyramidsolitaire.model.hw04;

import java.util.ArrayList;
import java.util.Random;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.RealCard;

/**
 * A class for playing a game of Pyramid Solitaire with a relaxed set of rules. You can combine
 * cards below the desired card so long as there is only one card below the desired card to
 * remove, and they still sum to be 13.
 */
public class RelaxedPyramidSolitaire extends AbstractPyramidSolitaire implements PyramidSolitaireModel<Card> {

  private final int SUM_TO_REMOVE = 13;

  /**
   * Constructs an RelaxedPyramidSolitaire with the given seeded Random value;
   *
   * @param rand the seeded random value
   */
  public RelaxedPyramidSolitaire(Random rand) {
    super(rand);
  }

  /**
   * Constructs a RelaxedPyramidSolitaire with an unseeded Random value;
   */
  public RelaxedPyramidSolitaire() {
    super(new Random());
  }

  // Allows for removal of cards if there is only one card beneath one of the cards to remove.
  @Override
  public void remove(int row1, int card1, int row2, int card2) throws IllegalStateException {

    checkValidity(row1, card1, true);
    checkValidity(row2, card2, true);

    Card c1 = this.getCardAt(row1, card1);
    Card c2 = this.getCardAt(row2, card2);

    boolean c1Visiblity = this.noCardsBeneath(row1, card1);
    boolean c2Visiblity = this.noCardsBeneath(row2, card2);

    // both cards need to be visible.
    if (!c1Visiblity && !c2Visiblity) {
      throw new IllegalArgumentException("Both cards are not visible");
    } else if (!this.canCombine(c1, c2)) { // need to be able to combine cards.
      throw new IllegalArgumentException("Sum of the cards is not " + SUM_TO_REMOVE);
    } else if (c1Visiblity && !c2Visiblity) { // if only c2 isn't visible

      if (!this.onlyCardBeneath(row2, card2, row1, card1)) { // is c1 not the only card beneath c2
        throw new IllegalArgumentException("Card 1 is not the only card below Card 2.");
      }
      discard(row1, card1);
      discard(row2, card2);

    } else if (c2Visiblity && !c1Visiblity) { // if only c1 isn't visible
      if (!this.onlyCardBeneath(row1, card1, row2, card2)) { // is c2 the only card beneath c1
        throw new IllegalArgumentException("Card 2 is not the only card below Card 1.");
      }
      discard(row2, card2);
      discard(row1, card1);
    } else if (c2Visiblity && c1Visiblity) { // both cards are visible.
      discard(row1, card1);
      discard(row2, card2);
    }
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {

    if (!isGameStarted()) {
      throw new IllegalStateException("Game not started");
    }

    // check if any cards have any combinable cards above them.
    // loop through rows (-1 b/c no need to check bottom row)
    for (int r = 0; r < this.getNumRows() - 1; r++) {

      for (int c = 0; c < this.getRowWidth(r); c++) { // loop through all cards

        if (oneCombinableCardBelow(r, c)) {
          return false;
        }

      }

    }


    return super.isGameOver();
  }

  /**
   * Checks if the only card below the top card is the bottom card.
   * @param topRow the row of the top card.
   * @param topCard the card position of the top card.
   * @param bottomRow the row of the bottom card.
   * @param bottomCard the card position of the bottom card.
   * @return if the top card only has one card beneath it and it is the bottom card.
   */
  boolean onlyCardBeneath(int topRow, int topCard, int bottomRow, int bottomCard) {
    // is the card directly below?
    boolean isCardBelow = bottomRow == topRow + 1;
    // is the card in one of the slots beneath the top card?
    boolean cardBeneath = bottomCard == topCard || bottomCard == topCard + 1;

    Card botCard1 = this.getCardAt(topRow + 1, topCard);
    Card botCard2 = this.getCardAt(topRow + 1, topCard + 1);
    // only one of these cards can be null
    boolean onlyOneNull = (botCard1 == null) ^ (botCard2 == null);

    return onlyOneNull && cardBeneath && isCardBelow;
  }

  private boolean oneCombinableCardBelow(int row, int card) {
    Card thisCard = this.getCardAt(row, card);
    if (thisCard == null) {
      return false;
    }

    Card botCard1 = this.getCardAt(row + 1, card);
    Card botCard2 = this.getCardAt(row + 1, card + 1);


    if (botCard1 == null) {
      return canCombine(thisCard, botCard2);
    } else if (botCard2 == null) {
      return canCombine(thisCard, botCard1);
    }

    return false;
  }


}
