import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.CardValue;
import cs3500.pyramidsolitaire.model.hw02.EmptyCard;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.RealCard;
import cs3500.pyramidsolitaire.model.hw02.Suit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

/**
 * Testing class for the implementation of all the PyramidSolitaire Model.
 */
public abstract class AbstractPyramidSolitaireTests {

  private Random seededRandom = new Random(69); // nice.

  protected abstract PyramidSolitaireModel<Card> factory();

  @Test
  public void getDeckTest() {
    PyramidSolitaireModel<Card> model = factory();
    // constructs a deck for testing
    List<Card> testDeck = new ArrayList<Card>();
    for (Suit s : Suit.values()) {
      for (CardValue v : CardValue.values()) {
        testDeck.add(new RealCard(v, s));
      }
    }

    assertEquals(testDeck, model.getDeck());
  }

  @Test(expected = IllegalStateException.class)
  public void startGameGetDrawCardsErrorTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.getDrawCards();
  }

  @Test(expected = IllegalStateException.class)
  public void startGameGetCardErrorTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.getCardAt(1, 1);
  }

  @Test(expected = IllegalStateException.class)
  public void startGameRowWidthErrorTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.getRowWidth(0);
  }

  @Test(expected = IllegalStateException.class)
  public void startGameGetScoreErrorTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.getScore();
  }

  @Test(expected = IllegalStateException.class)
  public void startGameIsGameOverErrorTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.isGameOver();
  }

  @Test(expected = IllegalStateException.class)
  public void startGameRemove2ArgErrorTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.remove(0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void startGameRemove4ArgErrorTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.remove(0, 0, 1, 1);
  }

  @Test(expected = IllegalStateException.class)
  public void startGameRemoveUsingDrawErrorTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.removeUsingDraw(0, 0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void startGameDiscardDrawErrorTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.discardDraw(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidRowTestMin() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 0, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidRowTestMax() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 10, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidDrawTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 0, -1);
  }

  @Test
  public void startGameFunctionalityTest() {

    PyramidSolitaireModel<Card> model = factory();

    // tests for before the game starts
    assertEquals(-1, model.getNumDraw());
    assertEquals(-1, model.getNumRows());

    // tests for starting
    model.startGame(model.getDeck(), false, 3, 3);
    assertEquals(3, model.getNumRows());
    assertEquals(3, model.getNumDraw());
    assertEquals(new RealCard(CardValue.Ace, Suit.Spades), model.getCardAt(0, 0));
    assertEquals(21, model.getScore());
    assertFalse(model.isGameOver());
    assertEquals(1, model.getRowWidth(0));
    assertEquals(2, model.getRowWidth(1));
    assertEquals(3, model.getRowWidth(2));
    Card sevenSpade = new RealCard(CardValue.Seven, Suit.Spades);
    Card eightSpade = new RealCard(CardValue.Eight, Suit.Spades);
    Card nineSpade  = new RealCard(CardValue.Nine, Suit.Spades);
    List<Card> drawPileTest = new ArrayList<Card>();
    drawPileTest.add(sevenSpade);
    drawPileTest.add(eightSpade);
    drawPileTest.add(nineSpade);
    assertEquals(drawPileTest, model.getDrawCards());

    // testing restart capability
    model.startGame(model.getDeck(), false, 1, 1);
    assertEquals(1, model.getNumRows());
    assertEquals(1, model.getNumDraw());
    assertEquals(new RealCard(CardValue.Ace, Suit.Spades), model.getCardAt(0, 0));
    assertEquals(1, model.getScore());
    assertFalse(model.isGameOver());
    assertEquals(1, model.getRowWidth(0));
    Card twoSpade = new RealCard(CardValue.Two, Suit.Spades);
    drawPileTest = new ArrayList<Card>();
    drawPileTest.add(twoSpade);
    assertEquals(drawPileTest, model.getDrawCards());

    // getting a Card from the pyramid will produce an error;
    assertFalse(model.isGameOver()); // false because you can still discard draw pile cards
    // getting row width will throw an error


    // testing board with maximum spots in the pyramid
    model.startGame(model.getDeck(), false, 9, 10);
    // since the pyramid has 55 slots, the last three should be empty
    assertEquals(9, model.getNumRows());
    assertEquals(10, model.getNumDraw());
    assertEquals(new RealCard(CardValue.Ace, Suit.Spades), model.getCardAt(0, 0));
    assertEquals(294, model.getScore());
    assertFalse(model.isGameOver());
    assertEquals(9, model.getRowWidth(8));

    // shuffled testing
    model.startGame(model.getDeck(), true, 3, 3);
    assertEquals(3, model.getNumRows());
    assertEquals(3, model.getNumDraw());
    assertEquals(new RealCard(CardValue.King, Suit.Diamonds), model.getCardAt(0, 0));
    drawPileTest = new ArrayList<Card>();
    drawPileTest.add(new RealCard(CardValue.Ten, Suit.Diamonds));
    drawPileTest.add(new RealCard(CardValue.Ace, Suit.Clubs));
    drawPileTest.add(new RealCard(CardValue.Five, Suit.Hearts));
    assertEquals(drawPileTest, model.getDrawCards());
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameNegativeRowErrorTest() {

    PyramidSolitaireModel<Card> model = factory();

    model.startGame(model.getDeck(), false, -10, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameNegativeDrawErrorTest() {

    PyramidSolitaireModel<Card> model = factory();

    model.startGame(model.getDeck(), false, 10, -10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidDeckErrorTest() {

    PyramidSolitaireModel<Card> model = factory();

    // constructs a deck for testing
    List<Card> testDeck = new ArrayList<Card>();
    for (Suit s : Suit.values()) {
      for (CardValue v : CardValue.values()) {
        testDeck.add(new RealCard(v, s));
      }
    }

    // remove some cards in the testDeck
    testDeck.remove(0);
    testDeck.remove(12);
    testDeck.remove(26);
    testDeck.remove(44);

    model.startGame(testDeck, false, 3, 3);
  }

  // 4 Arg remove tests
  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveInvalidRowTest1() {

    PyramidSolitaireModel<Card> model = factory();

    model.startGame(model.getDeck(), false, 5, 3);
    model.remove(-1, 1, 4, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveInvalidRowTest2() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 5, 3);
    model.remove(6, 1, 4, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveInvalidRowTest3() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 5, 3);
    model.remove(4, 1, -1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveInvalidRowTest4() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 5, 3);
    model.remove(4, 1, 5, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveInvalidCardTest1() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 5, 3);
    model.remove(4, -1, 4, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveInvalidCardTest2() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 5, 3);
    model.remove(4, 5, 4, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveInvalidCardTest3() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 5, 3);
    model.remove(4, 1, 4, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveInvalidCardTest4() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 5, 3);
    model.remove(4, 1, 4, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveEmptyCardTest1() {
    PyramidSolitaireModel<Card> model = factory();
    // last spots will be empty
    model.startGame(model.getDeck(), false, 10, 3);

    model.remove(0, 0, 9, 9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveEmptyCardTest2() {
    PyramidSolitaireModel<Card> model = factory();
    // last spots will be empty
    model.startGame(model.getDeck(), false, 10, 3);

    model.remove(9, 9, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveVisibilityErrorTest1() {
    PyramidSolitaireModel<Card> model = factory();
    // top will not be visible
    model.startGame(model.getDeck(), false, 3, 3);

    model.remove(0, 0, 2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveVisibilityErrorTest2() {
    PyramidSolitaireModel<Card> model = factory();
    // top will not be visible
    model.startGame(model.getDeck(), false, 3, 3);

    model.remove(2, 2, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveNotCombinableErrorTest() {
    PyramidSolitaireModel<Card> model = factory();
    // no cards will be combinable in the bottom row
    model.startGame(model.getDeck(), false, 3, 3);

    model.remove(2, 0, 2, 2);
  }

  @Test
  public void fourArgRemoveTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 5, 3);

    // cards are there
    assertEquals(new RealCard(CardValue.Queen, Suit.Spades), model.getCardAt(4, 1));
    assertEquals(new RealCard(CardValue.Ace, Suit.Hearts), model.getCardAt(4, 3));
    assertEquals(new RealCard(CardValue.Jack, Suit.Spades), model.getCardAt(4, 0));
    assertEquals(new RealCard(CardValue.Two, Suit.Hearts), model.getCardAt(4, 4));
    //remove the cards
    model.remove(4, 1, 4, 3);
    model.remove(4, 0, 4, 4);

    // poof! the cards are gone!
    assertNull(model.getCardAt(4, 1));
    assertNull(model.getCardAt(4, 3));
    assertNull(model.getCardAt(4, 0));
    assertNull(model.getCardAt(4, 4));
  }

  // 2 arg remove tests
  @Test(expected = IllegalArgumentException.class)
  public void twoArgRemoveInvalidRowErrorTest1() {
    PyramidSolitaireModel<Card> model = factory();

    model.startGame(model.getDeck(), false, 3, 3);

    model.remove(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void twoArgRemoveInvalidRowErrorTest2() {
    PyramidSolitaireModel<Card> model = factory();

    model.startGame(model.getDeck(), false, 3, 3);

    model.remove(5, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void twoArgRemoveInvalidCardErrorTest1() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 3, 3);

    model.remove(2, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void twoArgRemoveInvalidCardErrorTest2() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 3, 3);

    model.remove(2, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void twoArgRemoveEmptyCardErrorTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 10, 3);

    model.remove(9, 9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void twoArgRemoveNonVisibleCardErrorTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 3, 3);

    model.remove(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void twoArgRemoveNon13CardErrorTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 5, 5);

    model.remove(4, 1);
  }

  @Test
  public void twoArgRemoveTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 5, 3);

    // check cards are there
    assertEquals(new RealCard(CardValue.King, Suit.Spades), model.getCardAt(4, 2));

    // remove the card
    model.remove(4, 2);

    // poof! the card is gone
    assertEquals(null, model.getCardAt(4, 2));
  }

  // removeUsingDraw tests
  @Test(expected = IllegalArgumentException.class)
  public void removeUsingDrawInvalidRowTest1() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 3, 3);
    model.removeUsingDraw(0, -1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeUsingDrawInvalidRowTest2() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 3, 3);
    model.removeUsingDraw(0, 4, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeUsingDrawInvalidCardTest1() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 3, 3);
    model.removeUsingDraw(0, 2, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeUsingDrawInvalidCardTest2() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 3, 3);
    model.removeUsingDraw(0, 2, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeUsingDrawEmptyCardTest1() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 10, 3);
    model.removeUsingDraw(0, 9, 9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeUsingDrawEmptyCardTest2() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 10, 3);
    model.removeUsingDraw(0, 2, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeUsingDrawInvalidDrawIndexErrorTest1() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 5, 3);
    model.removeUsingDraw(-1, 4, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeUsingDrawInvalidDrawIndexErrorTest2() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 5, 3);
    model.removeUsingDraw(5, 4, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeUsingDrawInvisibleCardErrorTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 5, 3);
    model.removeUsingDraw(0, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeUsingDrawNonCombinableErrorTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 5, 3);
    model.removeUsingDraw(0, 0, 0);
  }

  @Test
  public void removeUsingDrawTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 3, 3);

    // cards are there
    assertEquals(new RealCard(CardValue.Five, Suit.Spades), model.getCardAt(2, 1));
    assertEquals(new RealCard(CardValue.Six, Suit.Spades), model.getCardAt(2, 2));
    assertEquals(new RealCard(CardValue.Seven, Suit.Spades), model.getDrawCards().get(0));
    assertEquals(new RealCard(CardValue.Eight, Suit.Spades), model.getDrawCards().get(1));

    // remove cards
    model.removeUsingDraw(1, 2, 1);
    model.removeUsingDraw(0, 2, 2);

    // poof cards are gone!
    assertNull(model.getCardAt(2, 1));
    assertNull(model.getCardAt(2, 2));
    // new cards in draw pile
    assertEquals(new RealCard(CardValue.Ten, Suit.Spades), model.getDrawCards().get(1));
    assertEquals(new RealCard(CardValue.Jack, Suit.Spades), model.getDrawCards().get(0));
  }

  // discardDraw tests
  @Test(expected = IllegalArgumentException.class)
  public void discardDrawInvalidIndexErrorTest1() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 3, 3);
    model.discardDraw(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void discardDrawInvalidIndexErrorTest2() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 3, 3);
    model.discardDraw(5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void discardDrawEmptyCardErrorTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 10, 3);
    model.discardDraw(0);
  }

  @Test
  public void discardDrawTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 3, 3);

    // cards are there
    assertEquals(new RealCard(CardValue.Seven, Suit.Spades), model.getDrawCards().get(0));
    assertEquals(new RealCard(CardValue.Nine, Suit.Spades), model.getDrawCards().get(2));

    // discardsd the cards
    model.discardDraw(0);
    model.discardDraw(2);

    // check for the new cards
    assertEquals(new RealCard(CardValue.Ten, Suit.Spades), model.getDrawCards().get(0));
    assertEquals(new RealCard(CardValue.Jack, Suit.Spades), model.getDrawCards().get(2));
  }

  // tests for getNumRows
  @Test
  public void getNumRowsTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 1, 3);
    assertEquals(1, model.getNumRows());

    model.startGame(model.getDeck(), false, 3, 3);
    assertEquals(3, model.getNumRows());
  }

  // test for getNumDraw
  @Test
  public void getNumDrawTest() {
    PyramidSolitaireModel<Card> model = factory();
    // 0 draw slots
    model.startGame(model.getDeck(), false, 4, 0);
    assertEquals(0, model.getNumDraw());

    // all slots are filled
    model.startGame(model.getDeck(), false, 3, 3);
    assertEquals(3, model.getNumDraw());

    // no slots are filled
    model.startGame(model.getDeck(), false, 9, 10);
    model.discardDraw(0);
    model.discardDraw(1);
    model.discardDraw(2);
    model.discardDraw(3);
    model.discardDraw(4);
    model.discardDraw(5);
    model.discardDraw(6);
    assertEquals(10, model.getNumDraw());

    // partially filled, partially empty
    model.startGame(model.getDeck(), false, 9, 10);
    assertEquals(10, model.getNumDraw());

  }

  // tests for getRowWidth
  @Test(expected = IllegalArgumentException.class)
  public void getRowWidthInvalidRowErrorTest1() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 3, 3);
    model.getRowWidth(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getRowWidthInvalidRowErrorTest2() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 3, 3);
    model.getRowWidth(3);
  }

  @Test
  public void getRowWidthTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 3, 3);
    assertEquals(1, model.getRowWidth(0));
    assertEquals(2, model.getRowWidth(1));
    assertEquals(3, model.getRowWidth(2));
  }

  // tests for isGameOver
  @Test
  public void isGameOverTest() {
    PyramidSolitaireModel<Card> model = factory();

    List<Card> testDeck = model.getDeck();
    // swapping a king with the first card
    Card king = testDeck.get(12);
    Card ace = testDeck.set(0, king);
    testDeck.set(12, ace);
    model.startGame(testDeck, false, 1, 0);
    // remove the first card
    model.remove(0, 0);
    assertTrue(model.isGameOver());
    model.startGame(model.getDeck(), false, 1, 10);
    assertFalse(model.isGameOver());
    model.startGame(model.getDeck(), false, 9, 5);
    assertFalse(model.isGameOver());

    model.startGame(model.getDeck(), false, 3, 3);
    assertFalse(model.isGameOver());
    model.removeUsingDraw(0, 2, 2);
    model.removeUsingDraw(1, 2, 1);
    model.removeUsingDraw(2, 2, 0);
    model.removeUsingDraw(0, 1, 1);
    model.removeUsingDraw(1, 1, 0);
    model.removeUsingDraw(2, 0, 0);
    assertTrue(model.isGameOver());

  }

  // getScore tests
  @Test
  public void getScoreTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 1, 0);
    assertEquals(1, model.getScore());

    List<Card> testDeck = model.getDeck();
    Card king = testDeck.get(12);
    Card ace = testDeck.set(0, king);
    testDeck.set(12, ace);
    model.startGame(testDeck, false, 1, 0);
    // remove the first card
    model.remove(0, 0);
    assertEquals(0, model.getScore());

    model.startGame(model.getDeck(), false, 9, 0);
    assertEquals(294, model.getScore());

    model.startGame(model.getDeck(), false, 3, 3);
    assertEquals(21, model.getScore());
  }

  // getCardAt tests
  @Test(expected = IllegalArgumentException.class)
  public void getCardInvalidRowErrorTest1() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 3, 3);
    model.getCardAt(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCardInvalidRowErrorTest2() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 3, 3);
    model.getCardAt(4, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCardInvalidCardError1() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 3, 3);
    model.getCardAt(2, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCardInvalidCardError2() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 3, 3);
    model.getCardAt(2, 5);
  }

  @Test
  public void getCartAtTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 7, 3);
    assertEquals(new RealCard(CardValue.Ace, Suit.Spades), model.getCardAt(0, 0));
    assertEquals(new RealCard(CardValue.King, Suit.Hearts), model.getCardAt(6, 4));
    model.remove(6, 4);
    assertNull(model.getCardAt(6, 4));
  }

  // getDrawCards tests
  @Test
  public void getDrawCardsTest() {
    PyramidSolitaireModel<Card> model = factory();
    model.startGame(model.getDeck(), false, 3, 3);
    Card sevenSpades = new RealCard(CardValue.Seven, Suit.Spades);
    Card eightSpades = new RealCard(CardValue.Eight, Suit.Spades);
    Card nineSpades = new RealCard(CardValue.Nine, Suit.Spades);
    ArrayList<Card> drawPileTest = new ArrayList<Card>();
    drawPileTest.add(sevenSpades);
    drawPileTest.add(eightSpades);
    drawPileTest.add(nineSpades);
    assertEquals(drawPileTest, model.getDrawCards());

    model.startGame(model.getDeck(), false, 3, 0);
    assertEquals(new ArrayList<Card>(), model.getDrawCards());

    model.startGame(model.getDeck(), false, 9, 10);
    Card sevenClub = new RealCard(CardValue.Seven, Suit.Clubs);
    Card eightClub = new RealCard(CardValue.Eight, Suit.Clubs);
    Card nineClub = new RealCard(CardValue.Nine, Suit.Clubs);
    Card tenClub = new RealCard(CardValue.Ten, Suit.Clubs);
    Card jackClub = new RealCard(CardValue.Jack, Suit.Clubs);
    Card queenClub = new RealCard(CardValue.Queen, Suit.Clubs);
    Card kingClub = new RealCard(CardValue.King, Suit.Clubs);
    drawPileTest = new ArrayList<Card>();
    drawPileTest.add(sevenClub);
    drawPileTest.add(eightClub);
    drawPileTest.add(nineClub);
    drawPileTest.add(tenClub);
    drawPileTest.add(jackClub);
    drawPileTest.add(queenClub);
    drawPileTest.add(kingClub);
    drawPileTest.add(new EmptyCard());
    drawPileTest.add(new EmptyCard());
    drawPileTest.add(new EmptyCard());
    assertEquals(drawPileTest, model.getDrawCards());
  }
}