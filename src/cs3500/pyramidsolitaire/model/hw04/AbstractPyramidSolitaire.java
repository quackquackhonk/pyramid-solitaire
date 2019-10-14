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
 * Abstract class for providing general functionality for versions of the PyramidSolitaire game.
 * The default behavior is BasicPyramidSolitaire.
 */
public abstract class AbstractPyramidSolitaire implements PyramidSolitaireModel<Card> {

  private List<Card> deck;
  private int numRows;
  private int numDraw;
  private List<Card> drawPile;
  private boolean gameStarted;
  private ArrayList<ArrayList<Card>> board;
  private final Random rand;
  private int cardToDealNext;
  private ArrayList<Integer> indiciesDealt;
  private boolean ignoreOrder;

  private final int SUM_TO_REMOVE = 13;
  private final int NUM_CARDS_IN_DECK = 52;
  private final int MIN_ROWS = 1;
  private final int MAX_ROWS = 9;
  private final int MIN_DRAW = 0;

  /**
   * Constructs an AbstractPyramidSolitaire with the given seeded Random value;
   * @param rand the seeded random value
   */
  public AbstractPyramidSolitaire(Random rand) {
    this.rand = rand;
    this.gameStarted = false;
  }

  @Override
  public List<Card> getDeck() {
    List<Card> d = new ArrayList<Card>();
    for (Suit s : Suit.values()) {
      for (CardValue v : CardValue.values()) {
        d.add(new RealCard(v, s));
      }
    }

    return d;
  }

  @Override
  public void startGame(List<Card> deck, boolean shouldShuffle, int numRows, int numDraw) {

    if (numRows < MIN_ROWS || numRows > MAX_ROWS || numDraw < MIN_DRAW) {
      this.gameStarted = false;
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
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2) throws IllegalStateException {

    checkValidity(row1, card1, true);
    checkValidity(row2, card2, true);

    Card c1 = this.getCardAt(row1, card1);
    Card c2 = this.getCardAt(row2, card2);
    boolean c1Visiblity = this.noCardsBeneath(row1, card1);
    boolean c2Visiblity = this.noCardsBeneath(row2, card2);

    // both cards need to be empty
    if (!c1Visiblity || !c2Visiblity) {
      throw new IllegalArgumentException("Both cards are not visible");
    }

    if (!this.canCombine(c1, c2)) {
      throw new IllegalArgumentException("Sum of the cards is not " + SUM_TO_REMOVE);
    }

    discard(row1, card1);
    discard(row2, card2);

  }

  @Override
  public void remove(int row, int card) throws IllegalStateException {

    checkValidity(row, card, true);

    Card c = this.getCardAt(row, card);

    if (!this.noCardsBeneath(row, card)) {
      throw new IllegalArgumentException("Card is not visible.");
    }

    if (c.getIntegerValue() != SUM_TO_REMOVE) {
      throw new IllegalArgumentException("Card value is not " + SUM_TO_REMOVE);
    }

    discard(row, card);

  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card) throws IllegalStateException {

    checkValidity(row, card, true);
    if (drawIndex >= this.numDraw || drawIndex < 0) {
      throw new IllegalArgumentException("Draw index out of range");
    }

    if (this.isEmpty(this.drawPile.get(drawIndex))) {
      throw new IllegalArgumentException("No Card there!");
    }

    Card c1 = this.getCardAt(row, card);
    Card c2 = this.drawPile.get(drawIndex);

    // card must be empty
    if (!this.noCardsBeneath(row, card)) {
      throw new IllegalArgumentException("Card not visible");
    }

    if (!this.canCombine(c1, c2)) {
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

    if (this.isEmpty(this.drawPile.get(drawIndex))) {
      throw new IllegalArgumentException("No card there!");
    }

    if (cardToDealNext >= NUM_CARDS_IN_DECK) {
      this.drawPile.set(drawIndex, new EmptyCard());
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

    return this.numDraw;
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

    // if your score is 0, you win!
    if (this.getScore() == 0) {
      return true;
    }

    // list of all cards playable by the player
    ArrayList<RealCard> playableCards = new ArrayList<RealCard>();
    for (int r = 0; r < this.getNumRows(); r++) { // row loop
      for (int i = 0; i < this.getRowWidth(r); i++) { // card loop
        Card c = this.getCardAt(r, i);

        if (c != null && this.noCardsBeneath(r, i)) {
          playableCards.add((RealCard) c);
        }

      }
    }

    // add all the playable cards from the draw pile into the list
    for (Card c : this.getDrawCards()) {
      if (!this.isEmpty(c)) {
        playableCards.add((RealCard) c);
      }
    }

    for (int x = 0; x < playableCards.size(); x++) { // loop through cards
      RealCard cx = playableCards.get(x);
      if (cx.getIntegerValue() == SUM_TO_REMOVE) {
        return false;
      }

      for (int y = x; y < playableCards.size(); y++) {
        RealCard cy = playableCards.get(y);

        if (this.canCombine(cx, cy)) {
          return false;
        }

      }
    }

    // how many cards are available in the draw pile
    int numCards = 0;

    for (int i = 0; i < this.drawPile.size(); i++) {
      Card c = this.drawPile.get(i);
      if (!this.isEmpty(c)) {
        numCards++;
      }
    }


    // if the draw pile is empty, the game is finished, because there are no moves left to be made.
    // also returns true if numDraw is 0, since you cannot draw anymore cards to play, even if
    // the deck is full
    return this.deck.size() == 0 || numCards == 0;
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
          score += c.getIntegerValue();
        }

      }
    }
    return score;
  }

  @Override
  public Card getCardAt(int row, int card) throws IllegalStateException {

    checkValidity(row, card, false);

    Card c = this.board.get(row).get(card);

    if (this.isEmpty(c)) {
      return null;
    }

    return c;
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {

    if (!gameStarted) {
      throw new IllegalStateException("Game not started!");
    }

    /*
    ArrayList<Card> returnList = new ArrayList<Card>();

    for (Card c : this.drawPile) {
      if (!(this.isEmpty(c))) {
        returnList.add(this.copy(c));
      }
    }

    return returnList;
     */
    return this.drawPile;

  }

  /**
   * Discards the card in the given row at the specific card index.
   *
   * @param row  the row of the card to discard
   * @param card the position in the row of the card to discard
   * @throws IllegalArgumentException if the card position is invalid
   * @throws IllegalStateException    if the game hasn't started
   */
  protected void discard(int row, int card) throws IllegalStateException {

    checkValidity(row, card, true);

    // card must be visible
    if (!this.noCardsBeneath(row, card)) {
      throw new IllegalArgumentException("Card not visible");
    }

    // sets the card to an empty card
    this.board.get(row).set(card, new EmptyCard());

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
  protected boolean noCardsBeneath(int row, int card) throws IllegalStateException {

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
  protected void checkValidity(int row, int card, boolean checkEmptyCard) throws IllegalStateException {

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
  protected boolean isDeckValid(List<Card> deck) {

    if (deck == null) {
      return false;
    }

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
  protected Card deal() throws IllegalStateException {

    Card nextCard;

    if (!this.gameStarted) {
      throw new IllegalStateException("Game not started");
    }

    if (cardToDealNext >= NUM_CARDS_IN_DECK) {
      return new EmptyCard();
    }

    if (ignoreOrder) {

      int cardIndex = rand.nextInt(NUM_CARDS_IN_DECK);
      nextCard = this.deck.get(cardIndex);

      while (indiciesDealt.contains(cardIndex)) {
        cardIndex = rand.nextInt(NUM_CARDS_IN_DECK);
        nextCard = this.deck.get(cardIndex);
      }

      indiciesDealt.add(cardIndex);

    } else {
      nextCard = this.deck.get(cardToDealNext);
    }

    cardToDealNext++;
    return this.copy(nextCard);
  }

  /**
   * Determines if the card is an empty Card.
   *
   * @param c the Card to check.
   * @return if the suit is Empty and the value is -1, return true, else return false.
   * @throws IllegalStateException if the game hasn't started yet.
   */
  protected boolean isEmpty(Card c) throws IllegalStateException {

    if (!gameStarted) {
      throw new IllegalStateException("Game not started!");
    }

    return c.getValue() == null || c.getSuit() == null;
  }

  /**
   * Copies all the data of the given Card into a new Card.
   *
   * @param c the Card to copy
   * @return a copy of the Card.
   * @throws IllegalStateException if the game hasn't started.
   */
  protected Card copy(Card c) throws IllegalStateException  {

    if (!gameStarted) {
      throw new IllegalStateException("Game not started!");
    }

    if (isEmpty(c)) {
      return new EmptyCard();
    }

    return new RealCard(c.getValue(), c.getSuit());

  }

  /**
   * Determines if the given cards have values that can be summed to equal the required removal
   * value.
   *
   * @param c1 The first card.
   * @param c2 The second card.
   * @return can these two cards be combined?
   * @throws IllegalStateException if the game hasn't started.
   */
  protected boolean canCombine(Card c1, Card c2) throws IllegalStateException {

    if (!gameStarted) {
      throw new IllegalStateException("Game not started!");
    }

    // cannot combine empty cards with anything
    if (this.isEmpty(c1) || this.isEmpty(c2)) {
      return false;
    }

    return c1.getIntegerValue() + c2.getIntegerValue() == SUM_TO_REMOVE;
  }


}
