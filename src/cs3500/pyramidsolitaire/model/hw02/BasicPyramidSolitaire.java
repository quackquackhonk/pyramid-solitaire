package cs3500.pyramidsolitaire.model.hw02;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The model for the current state of the Pyramid Solitaire game.
 */
public class BasicPyramidSolitaire implements PyramidSolitaireModel<Card> {

  private List<Card> deck;
  private int numRows;
  private int numDraw;
  private List<Card> drawPile;
  private boolean gameStarted;
  private ArrayList<ArrayList<Card>> board;
  private Random rand;
  private int cardToDealNext;
  private ArrayList<Integer> indiciesDealt;
  private boolean ignoreOrder;


  /**
   * Constructs a new Pyramid Solitaire Game. Only a deck of cards will be created.
   */
  public BasicPyramidSolitaire() {
    this(new Random());
  }

  /**
   * Constructs a new Pyramid Solitaire Model with a Random passed in for testing.
   *
   * @param seedRand is the seeded Random object that is provided.
   */
  public BasicPyramidSolitaire(Random seedRand) {

    this.gameStarted = false;
    this.rand = seedRand;
  }

  @Override
  public List<Card> getDeck() {
    List<Card> d = new ArrayList<Card>();
    for (int s = 0; s < 4; s++) {
      for (int i = 1; i <= 13; i++) {
        if (s == 0) {
          d.add(new Card(i, Suit.Spades));
        } else if (s == 1) {
          d.add(new Card(i, Suit.Hearts));
        } else if (s == 2) {
          d.add(new Card(i, Suit.Diamonds));
        } else if (s == 3) {
          d.add(new Card(i, Suit.Clubs));
        }
      }
    }

    return d;
  }

  @Override
  public void startGame(List<Card> deck, boolean shouldShuffle, int numRows, int numDraw) {

    if (numRows < 1 || numDraw < 0) {
      throw new IllegalArgumentException("Invalid arguments");
    }

    if (!this.isDeckValid(deck)) {
      this.gameStarted = false;
      throw new IllegalArgumentException("Invalid Deck");
    } 

    this.gameStarted = true;
    this.ignoreOrder = shouldShuffle;
    this.cardToDealNext = 0;
    this.indiciesDealt = new ArrayList<Integer>();

    this.board = new ArrayList<ArrayList<Card>>();

    this.deck = deck;
    this.numRows = numRows;

    // initializes the draw and discard piles;
    this.drawPile = new ArrayList<Card>();

    // add rows to the board
    for (int i = 0; i < this.getNumRows(); i++) {
      this.board.add(new ArrayList<Card>());
    }

    this.numDraw = numDraw;


    // populate the board with cards
    for (int i = 0; i < this.getNumRows(); i++) {
      for (int j = 0; j < this.getRowWidth(i); j++) {

        this.board.get(i).add(this.deal());

      }
    }

    // populate the draw pile with cards
    for (int i = 0; i < this.numDraw; i++) {
      this.drawPile.add(this.deal());
    }

    // updates visibility of all cards
    this.updateVisibility();
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2) throws IllegalStateException {

    checkValidity(row1, card1, true);
    checkValidity(row2, card2, true);

    Card c1 = this.getCardAt(row1, card1);
    Card c2 = this.getCardAt(row2, card2);

    if (!c1.getVisibility() || !c2.getVisibility()) {
      throw new IllegalArgumentException("Both cards are not visible");
    }

    if (!c1.canCombine(c2)) {
      throw new IllegalArgumentException("Sum of the cards is not 13");
    }

    discard(row1, card1);
    discard(row2, card2);

  }

  @Override
  public void remove(int row, int card) throws IllegalStateException {

    checkValidity(row, card, true);

    Card c = this.getCardAt(row, card);

    if (!c.getVisibility()) {
      throw new IllegalArgumentException("Card is not visible.");
    }

    if (c.getValue() != 13) {
      throw new IllegalArgumentException("Card value is not 13");
    }

    discard(row, card);

  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card) throws IllegalStateException {

    checkValidity(row, card, true);
    if (drawIndex >= this.numDraw || drawIndex < 0) {
      throw new IllegalArgumentException("Draw index out of range");
    }

    if (this.drawPile.get(drawIndex).isEmpty()) {
      throw new IllegalArgumentException("No Card there!");
    }

    Card c1 = this.getCardAt(row, card);
    Card c2 = this.drawPile.get(drawIndex);

    if (!c1.getVisibility()) {
      throw new IllegalArgumentException("Card not visible");
    }

    if (!c1.canCombine(c2)) {
      throw new IllegalArgumentException("Sum of the cards is not 13");
    }

    discard(row, card);
    discardDraw(drawIndex);

  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalStateException {

    if (!gameStarted) {
      throw new IllegalStateException("Game not started");
    }

    if (drawIndex >= this.drawPile.size() || drawIndex < 0) {
      throw new IllegalArgumentException("Draw index out of range");
    }

    if (this.drawPile.get(drawIndex).isEmpty()) {
      throw new IllegalArgumentException("No card there!");
    }

    if (cardToDealNext > 51) {
      this.drawPile.set(drawIndex, new Card());
    } else {
      this.drawPile.set(drawIndex, this.deal());
    }

  }

  @Override
  public int getNumRows() {

    if (!gameStarted) {
      return -1;
    }

    return this.numRows;
  }

  @Override
  public int getNumDraw() {

    if (!gameStarted) {
      return -1;
    }

    int numCards = 0;

    for (int i = 0; i < this.drawPile.size(); i++) {
      Card c = this.drawPile.get(i);
      if (!(c.isEmpty())) {
        numCards++;
      }
    }

    return numCards;
  }

  @Override
  public int getRowWidth(int row) {

    if (!gameStarted) {
      throw new IllegalStateException("Game not started");
    }

    if (row < 0 || row >= this.getNumRows()) {
      throw new IllegalArgumentException("Invalid row");
    }

    return row + 1;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {

    if (!this.gameStarted) {
      throw new IllegalStateException("Game not started");
    }

    // list of all cards playable by the player
    ArrayList<Card> playableCards = new ArrayList<Card>();
    for (int r = 0; r < this.getNumRows(); r++) { // row loop
      for (int i = 0; i < this.getRowWidth(r); i++) { // card loop
        Card c = this.getCardAt(r, i);

        if (c != null && c.getVisibility()) {
          playableCards.add(c);
        }

      }
    }

    playableCards.addAll(this.getDrawCards());

    for (int x = 0; x < playableCards.size(); x++) { // loop through cards
      Card cx = playableCards.get(x);
      if (cx.getValue() == 13) {
        return false;
      }

      for (int y = x; y < playableCards.size(); y++) {
        Card cy = playableCards.get(y);

        if (cx.canCombine(cy)) {
          return false;
        }

      }
    }


    // if the draw pile is empty, the game is finished, because there are no moves left to be made.
    // also returns true if numDraw is 0, since you cannot draw anymore cards to play, even if
    // the deck is full
    return this.deck.size() == 0 || this.getNumDraw() == 0;
  }

  @Override
  public int getScore() throws IllegalStateException {

    if (!gameStarted) {
      throw new IllegalStateException("Game not started");
    }

    int score = 0;

    for (int r = 0; r < this.getNumRows(); r++) {
      for (int i = 0; i < this.getRowWidth(r); i++) {

        Card c = this.getCardAt(r, i);
        if (c != null) {
          score += c.getValue();
        }

      }
    }
    return score;
  }

  @Override
  public Card getCardAt(int row, int card) throws IllegalStateException {

    checkValidity(row, card, false);

    Card c = this.board.get(row).get(card);

    if (c.isEmpty()) {
      return null;
    }

    return c;
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {

    if (!gameStarted) {
      throw new IllegalStateException("Game not started!");
    }

    ArrayList<Card> returnList = new ArrayList<Card>();

    for (Card c : this.drawPile) {
      if (!(c.isEmpty())) {
        returnList.add(c.copy());
      }
    }

    return returnList;
  }

  /**
   * Discards the card in the given row at the specific card index.
   *
   * @param row  the row of the card to discard
   * @param card the position in the row of the card to discard
   * @throws IllegalArgumentException if the card position is invalid
   * @throws IllegalStateException    if the game hasn't started
   */
  void discard(int row, int card) throws IllegalStateException {

    checkValidity(row, card, true);

    // card must be visible
    if (!this.getCardAt(row, card).getVisibility()) {
      throw new IllegalArgumentException("Card not visible");
    }

    // sets the card to an empty card
    this.board.get(row).set(card, new Card());

    // updates the visibility of all the cards on the board
    this.updateVisibility();

  }

  /**
   * Updates all of the visibility flags for all the Cards in the board.
   *
   * @throws IllegalStateException if the game hasn't started
   */
  void updateVisibility() throws IllegalStateException {

    if (!this.gameStarted) {
      throw new IllegalStateException("Game not started");
    }

    // if there are no rows in the pyramid, no need to update visibility
    if (this.getNumRows() == 0) {
      return;
    }

    // r = row, i = card index
    for (int r = 0; r < this.getNumRows(); r++) {
      for (int i = 0; i < this.getRowWidth(r); i++) {
        if (this.noCardsBeneath(r, i)) {
          Card c = this.getCardAt(r, i);
          Card visibleC = c.copy();
          visibleC.makeVisible();
          this.board.get(r).set(i, visibleC);
        }
      }
    }

  }

  /**
   * Determines if there are cards below the Card at the given position.
   *
   * @param row  The row of the card to check.
   * @param card the position in the row of the Card to check.
   * @return is the Card available to be played?
   * @throws IllegalStateException    if the game hasn't started.
   * @throws IllegalArgumentException if the position provided is invalid.
   */
  boolean noCardsBeneath(int row, int card) throws IllegalStateException {

    checkValidity(row, card, false);

    // if the card is empty, return false
    if (this.getCardAt(row, card) == null) {
      return false;
    }

    // returns true if the card is the bottom row of the pyramid
    if (row == this.getNumRows() - 1) {
      return true;
    }

    return this.getCardAt(row + 1, card) == null
            && this.getCardAt(row + 1, card + 1) == null;
  }

  /**
   * Checks the validity of any moves involving the card at the given position.
   *
   * @param row            the row of the card.
   * @param card           the position of the card in the row
   * @param checkEmptyCard should the function test if the card at the given position is empty?
   * @throws IllegalArgumentException if the move is invalid
   * @throws IllegalStateException    if the game hasn't started
   */
  void checkValidity(int row, int card, boolean checkEmptyCard) throws IllegalStateException {

    if (!gameStarted) {
      throw new IllegalStateException("Game not started");
    } else if (row >= this.getNumRows() || row < 0) {
      throw new IllegalArgumentException("Invalid row @ row: " + row);
    } else if (card >= this.getRowWidth(row) || card < 0) {
      throw new IllegalArgumentException("Invalid card @ row: " + row + ", card: " + card);
    } else if (checkEmptyCard && this.getCardAt(row, card) == null) {
      throw new IllegalArgumentException("No card @ row: " + row + ", card: " + card);
    }

  }

  /**
   * Determines if a given deck is a valid (complete 52 card) deck.
   *
   * @param deck the deck to be tested
   * @return if the deck is complete and valid.
   */
  boolean isDeckValid(List<Card> deck) {

    List<Card> validDeck = this.getDeck();

    for (Card c : validDeck) {
      if (!deck.contains(c)) {
        return false;
      }
    }

    return true;
  }

  /**
   * Deals the next card from the deck. If game started and the shouldShuffle flag was true, does
   * not deal the cards in order.
   *
   * @return the next card to deal
   * @throws IllegalStateException if the game hasn't started
   */
  Card deal() throws IllegalStateException {

    Card nextCard;

    if (!this.gameStarted) {
      throw new IllegalStateException("Game not started");
    }

    if (cardToDealNext > 51) {
      return new Card();
    }

    if (ignoreOrder) {

      int cardIndex = rand.nextInt(51);
      nextCard = this.deck.get(cardIndex);

      while (indiciesDealt.contains(cardIndex)) {
        cardIndex = rand.nextInt(51);
        nextCard = this.deck.get(cardIndex);
      }

      indiciesDealt.add(cardIndex);

    } else {
      nextCard = this.deck.get(cardToDealNext);
    }

    cardToDealNext++;
    return nextCard.copy();
  }


}
