package cs3500.pyramidsolitaire.model.hw04;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.CardValue;
import cs3500.pyramidsolitaire.model.hw02.EmptyCard;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.RealCard;
import cs3500.pyramidsolitaire.model.hw02.Suit;

/**
 * Creates an instance of Pyramid Solitaire with the same rules as the Basic Pyramid Solitaire,
 * but having a board with 3 pyramids. The 3 pyramids overlap on the ceil(r/2)th row, where r is
 * the number of rows.
 */
public class TriPeaksPyramidSolitaire
        extends AbstractPyramidSolitaire implements PyramidSolitaireModel<Card> {

  private final int SUM_TO_REMOVE;
  private final int MAX_CARDS_IN_DECK;
  private final int MIN_ROWS;
  private final int MAX_ROWS;
  private final int MIN_DRAW;

  /**
   * Constructs a TriPeaksPyramidSolitaire with the given seeded Random value.
   *
   * @param rand the seeded random value
   */
  public TriPeaksPyramidSolitaire(Random rand) {
    super(rand, 13, 104, 3, 9, 0);
    this.SUM_TO_REMOVE = 13;
    this.MAX_CARDS_IN_DECK = 104;
    this.MIN_ROWS = 3;
    this.MAX_ROWS = 9;
    this.MIN_DRAW = 0;
  }

  /**
   * Constructs a TriPeaksPyramidSolitaire with the given unseeded Random value.
   */
  public TriPeaksPyramidSolitaire() {
    super(new Random(), 13, 104, 3, 9, 0);
    this.SUM_TO_REMOVE = 13;
    this.MAX_CARDS_IN_DECK = 104;
    this.MIN_ROWS = 3;
    this.MAX_ROWS = 9;
    this.MIN_DRAW = 0;
  }

  // must return a double deck instead.
  @Override
  public List<Card> getDeck() {
    List<Card> d = new ArrayList<Card>();
    for (Suit s : Suit.values()) {
      for (CardValue v : CardValue.values()) {
        d.add(new RealCard(v, s));
      }
    }
    // doubles the deck
    d.addAll(d);
    return d;
  }

  @Override
  protected ArrayList<ArrayList<Card>> populate() {

    ArrayList<ArrayList<Card>> retBoard = new ArrayList<ArrayList<Card>>();

    // add rows to the board
    for (int i = 0; i < this.getNumRows(); i++) {
      retBoard.add(new ArrayList<Card>());
    }

    // populate the board with cards
    for (int i = 0; i < this.getNumRows(); i++) {
      for (int j = 0; j < this.getRowWidthInternal(i); j++) {

        if (rowHasSpace(i, j)) {
          retBoard.get(i).add(new EmptyCard());
        } else {
          retBoard.get(i).add(this.deal());
        }

      }
    }

    return retBoard;

  }

  @Override
  public int getRowWidth(int row) {

    if (!super.isGameStarted()) {
      throw new IllegalStateException("Game not started");
    }

    if (row < 0 || row >= this.getNumRows()) {
      throw new IllegalArgumentException("Invalid row");
    }

    return getRowWidthInternal(row);
  }

  /**
   * Internal method for getting the width of a row.
   *
   * @param row the row to get the width for
   * @return the width of the row
   */
  private int getRowWidthInternal(int row) {

    if (this.getNumRows() % 2 == 0) {
      return super.getNumRows() + 1 + row;
    } else {
      return super.getNumRows() + row;
    }
  }

  /**
   * Determines if the given position on the board should have a space when dealing cards.
   * @param row the row of the position
   * @param card the position in the row
   * @return does this position have a card when dealing cards onto the board?
   */
  boolean rowHasSpace(int row, int card) {

    int halfRows;

    if (this.getNumRows() % 2 == 0) {
      halfRows = this.getNumRows() / 2;

    } else {
      halfRows = this.getNumRows() / 2 + 1;
    }

    // positions for the peaks
    int peak0Pos = 0;
    int peak1Pos = this.getRowWidthInternal(0) / 2;
    int peak2Pos = this.getRowWidthInternal(0) - 1;

    if (row > halfRows) {
      return false;
    }

    boolean betweenPeak01 = card > peak0Pos + row && card < peak1Pos;
    boolean betweenPeak12 = card > peak1Pos + row && card < peak2Pos;

    return betweenPeak01 || betweenPeak12;
  }

  @Override
  protected boolean isDeckValid(List<Card> deck) {
    List<Card> checkDeck = new ArrayList<Card>();
    checkDeck.addAll(deck);
    checkDeck.addAll(deck);
    if (deck.size() != this.MAX_CARDS_IN_DECK) {
      return false;
    }

    List<Card> allCards = new ArrayList<Card>();
    for (Suit s : Suit.values()) {
      for (CardValue v : CardValue.values()) {
        allCards.add(new RealCard(v, s));
      }
    }

    for (Card c : allCards) {
      if (!containsTwo(checkDeck, c)) {
        return false;
      }
    }


    return true;
  }

  <K> boolean containsTwo(List<K> list, K element) {
    if (!list.contains(element)) {
      return false;
    } else {
      list.remove(element);
      return list.contains(element);
    }
  }

}
