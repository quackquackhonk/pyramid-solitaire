import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.CardValue;
import cs3500.pyramidsolitaire.model.hw02.EmptyCard;
import cs3500.pyramidsolitaire.model.hw02.RealCard;
import cs3500.pyramidsolitaire.model.hw02.Suit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

/**
 * Testing class for the implementation of the PyramidSolitaire Model.
 */
public class BasicPyramidSolitaireTest {

  private Random seededRandom = new Random(69); // nice.
  private BasicPyramidSolitaire model = new BasicPyramidSolitaire(seededRandom);


  @Test
  public void getDeckTest() {
    // constructs a deck for testing
    List<Card> testDeck = new ArrayList<Card>();
    for (Suit s : Suit.values()) {
      for (CardValue v : CardValue.values()) {
        testDeck.add(new RealCard(v, s));
      }
    }

    assertEquals(testDeck, this.model.getDeck());
  }

  @Test(expected = IllegalStateException.class)
  public void startGameGetDrawCardsErrorTest() {
    this.model.getDrawCards();
  }

  @Test(expected = IllegalStateException.class)
  public void startGameGetCardErrorTest() {
    this.model.getCardAt(1, 1);
  }

  @Test(expected = IllegalStateException.class)
  public void startGameRowWidthErrorTest() {
    this.model.getRowWidth(0);
  }

  @Test(expected = IllegalStateException.class)
  public void startGameGetScoreErrorTest() {
    this.model.getScore();
  }

  @Test(expected = IllegalStateException.class)
  public void startGameIsGameOverErrorTest() {
    this.model.isGameOver();
  }

  @Test(expected = IllegalStateException.class)
  public void startGameRemove2ArgErrorTest() {
    this.model.remove(0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void startGameRemove4ArgErrorTest() {
    this.model.remove(0, 0, 1, 1);
  }

  @Test(expected = IllegalStateException.class)
  public void startGameRemoveUsingDrawErrorTest() {
    this.model.removeUsingDraw(0, 0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void startGameDiscardDrawErrorTest() {
    this.model.discardDraw(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidRowTestMin() {
    this.model.startGame(this.model.getDeck(), false, 0, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidRowTestMax() {
    this.model.startGame(this.model.getDeck(), false, 10, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidDrawTest() {
    this.model.startGame(this.model.getDeck(), false, 0, -1);
  }

  @Test
  public void startGameFunctionalityTest() {

    // tests for before the game starts
    assertEquals(-1, this.model.getNumDraw());
    assertEquals(-1, this.model.getNumRows());

    // tests for starting
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    assertEquals(3, this.model.getNumRows());
    assertEquals(3, this.model.getNumDraw());
    assertEquals(new RealCard(CardValue.Ace, Suit.Spades), this.model.getCardAt(0, 0));
    assertEquals(21, this.model.getScore());
    assertFalse(this.model.isGameOver());
    assertEquals(1, this.model.getRowWidth(0));
    assertEquals(2, this.model.getRowWidth(1));
    assertEquals(3, this.model.getRowWidth(2));
    Card sevenSpade = new RealCard(CardValue.Seven, Suit.Spades);
    Card eightSpade = new RealCard(CardValue.Eight, Suit.Spades);
    Card nineSpade  = new RealCard(CardValue.Nine, Suit.Spades);
    List<Card> drawPileTest = new ArrayList<Card>();
    drawPileTest.add(sevenSpade);
    drawPileTest.add(eightSpade);
    drawPileTest.add(nineSpade);
    assertEquals(drawPileTest, this.model.getDrawCards());

    // testing restart capability
    this.model.startGame(this.model.getDeck(), false, 1, 1);
    assertEquals(1, this.model.getNumRows());
    assertEquals(1, this.model.getNumDraw());
    assertEquals(new RealCard(CardValue.Ace, Suit.Spades), this.model.getCardAt(0, 0));
    assertEquals(1, this.model.getScore());
    assertFalse(this.model.isGameOver());
    assertEquals(1, this.model.getRowWidth(0));
    Card twoSpade = new RealCard(CardValue.Two, Suit.Spades);
    drawPileTest = new ArrayList<Card>();
    drawPileTest.add(twoSpade);
    assertEquals(drawPileTest, this.model.getDrawCards());

    // getting a Card from the pyramid will produce an error;
    assertFalse(this.model.isGameOver()); // false because you can still discard draw pile cards
    // getting row width will throw an error


    // testing board with maximum spots in the pyramid
    this.model.startGame(this.model.getDeck(), false, 9, 10);
    // since the pyramid has 55 slots, the last three should be empty
    assertEquals(9, this.model.getNumRows());
    assertEquals(7, this.model.getNumDraw());
    assertEquals(new RealCard(CardValue.Ace, Suit.Spades), this.model.getCardAt(0, 0));
    assertEquals(294, this.model.getScore());
    assertFalse(this.model.isGameOver());
    assertEquals(9, this.model.getRowWidth(8));

    // shuffled testing
    this.model.startGame(this.model.getDeck(), true, 3, 3);
    assertEquals(3, this.model.getNumRows());
    assertEquals(3, this.model.getNumDraw());
    assertEquals(new RealCard(CardValue.King, Suit.Diamonds), this.model.getCardAt(0, 0));
    drawPileTest = new ArrayList<Card>();
    drawPileTest.add(new RealCard(CardValue.Ten, Suit.Diamonds));
    drawPileTest.add(new RealCard(CardValue.Ace, Suit.Clubs));
    drawPileTest.add(new RealCard(CardValue.Five, Suit.Hearts));
    assertEquals(drawPileTest, this.model.getDrawCards());
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameNegativeRowErrorTest() {
    this.model.startGame(this.model.getDeck(), false, -10, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameNegativeDrawErrorTest() {
    this.model.startGame(this.model.getDeck(), false, 10, -10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidDeckErrorTest() {
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

    this.model.startGame(testDeck, false, 3, 3);
  }

  // 4 Arg remove tests
  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveInvalidRowTest1() {
    this.model.startGame(this.model.getDeck(), false, 5, 3);
    this.model.remove(-1, 1, 4, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveInvalidRowTest2() {
    this.model.startGame(this.model.getDeck(), false, 5, 3);
    this.model.remove(6, 1, 4, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveInvalidRowTest3() {
    this.model.startGame(this.model.getDeck(), false, 5, 3);
    this.model.remove(4, 1, -1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveInvalidRowTest4() {
    this.model.startGame(this.model.getDeck(), false, 5, 3);
    this.model.remove(4, 1, 5, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveInvalidCardTest1() {
    this.model.startGame(this.model.getDeck(), false, 5, 3);
    this.model.remove(4, -1, 4, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveInvalidCardTest2() {
    this.model.startGame(this.model.getDeck(), false, 5, 3);
    this.model.remove(4, 5, 4, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveInvalidCardTest3() {
    this.model.startGame(this.model.getDeck(), false, 5, 3);
    this.model.remove(4, 1, 4, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveInvalidCardTest4() {
    this.model.startGame(this.model.getDeck(), false, 5, 3);
    this.model.remove(4, 1, 4, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveEmptyCardTest1() {
    // last spots will be empty
    this.model.startGame(this.model.getDeck(), false, 10, 3);

    this.model.remove(0, 0, 9, 9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveEmptyCardTest2() {
    // last spots will be empty
    this.model.startGame(this.model.getDeck(), false, 10, 3);

    this.model.remove(9, 9, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveVisibilityErrorTest1() {
    // top will not be visible
    this.model.startGame(this.model.getDeck(), false, 3, 3);

    this.model.remove(0, 0, 2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveVisibilityErrorTest2() {
    // top will not be visible
    this.model.startGame(this.model.getDeck(), false, 3, 3);

    this.model.remove(2, 2, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fourArgRemoveNotCombinableErrorTest() {
    // no cards will be combinable in the bottom row
    this.model.startGame(this.model.getDeck(), false, 3, 3);

    this.model.remove(2, 0, 2, 2);
  }

  @Test
  public void fourArgRemoveTest() {
    this.model.startGame(this.model.getDeck(), false, 5, 3);

    // cards are there
    assertEquals(new RealCard(CardValue.Queen, Suit.Spades), this.model.getCardAt(4, 1));
    assertEquals(new RealCard(CardValue.Ace, Suit.Hearts), this.model.getCardAt(4, 3));
    assertEquals(new RealCard(CardValue.Jack, Suit.Spades), this.model.getCardAt(4, 0));
    assertEquals(new RealCard(CardValue.Two, Suit.Hearts), this.model.getCardAt(4, 4));
    // the cards above the cards to remove are not visible
    assertFalse(this.model.getCardAt(3, 0).getVisibility());
    assertFalse(this.model.getCardAt(3, 3).getVisibility());

    //remove the cards
    this.model.remove(4, 1, 4, 3);
    this.model.remove(4, 0, 4, 4);

    // poof! the cards are gone!
    assertNull(this.model.getCardAt(4, 1));
    assertNull(this.model.getCardAt(4, 3));
    assertNull(this.model.getCardAt(4, 0));
    assertNull(this.model.getCardAt(4, 4));
    // the cards above the removed cards are now visible
    assertEquals(true, this.model.getCardAt(3, 0).getVisibility());
    assertEquals(true, this.model.getCardAt(3, 3).getVisibility());

  }

  // 2 arg remove tests
  @Test(expected = IllegalArgumentException.class)
  public void twoArgRemoveInvalidRowErrorTest1() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);

    this.model.remove(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void twoArgRemoveInvalidRowErrorTest2() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);

    this.model.remove(5, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void twoArgRemoveInvalidCardErrorTest1() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);

    this.model.remove(2, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void twoArgRemoveInvalidCardErrorTest2() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);

    this.model.remove(2, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void twoArgRemoveEmptyCardErrorTest() {
    this.model.startGame(this.model.getDeck(), false, 10, 3);

    this.model.remove(9, 9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void twoArgRemoveNonVisibleCardErrorTest() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);

    this.model.remove(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void twoArgRemoveNon13CardErrorTest() {
    this.model.startGame(this.model.getDeck(), false, 5, 5);

    this.model.remove(4, 1);
  }

  @Test
  public void twoArgRemoveTest() {
    this.model.startGame(this.model.getDeck(), false, 5, 3);

    // check cards are there
    assertEquals(new RealCard(CardValue.King, Suit.Spades), this.model.getCardAt(4, 2));
    // cards above are not visible
    assertFalse(this.model.getCardAt(3, 1).getVisibility());
    assertFalse(this.model.getCardAt(3, 2).getVisibility());

    // remove the card
    this.model.remove(4, 2);

    // poof! the card is gone
    assertEquals(null, this.model.getCardAt(4, 2));

    // cards above are still not visible
    assertEquals(false, this.model.getCardAt(3, 1).getVisibility());
    assertEquals(false, this.model.getCardAt(3, 2).getVisibility());
  }

  // removeUsingDraw tests
  @Test(expected = IllegalArgumentException.class)
  public void removeUsingDrawInvalidRowTest1() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.removeUsingDraw(0, -1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeUsingDrawInvalidRowTest2() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.removeUsingDraw(0, 4, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeUsingDrawInvalidCardTest1() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.removeUsingDraw(0, 2, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeUsingDrawInvalidCardTest2() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.removeUsingDraw(0, 2, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeUsingDrawEmptyCardTest1() {
    this.model.startGame(this.model.getDeck(), false, 10, 3);
    this.model.removeUsingDraw(0, 9, 9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeUsingDrawEmptyCardTest2() {
    this.model.startGame(this.model.getDeck(), false, 10, 3);
    this.model.removeUsingDraw(0, 2, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeUsingDrawInvalidDrawIndexErrorTest1() {
    this.model.startGame(this.model.getDeck(), false, 5, 3);
    this.model.removeUsingDraw(-1, 4, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeUsingDrawInvalidDrawIndexErrorTest2() {
    this.model.startGame(this.model.getDeck(), false, 5, 3);
    this.model.removeUsingDraw(5, 4, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeUsingDrawInvisibleCardErrorTest() {
    this.model.startGame(this.model.getDeck(), false, 5, 3);
    this.model.removeUsingDraw(0, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeUsingDrawNonCombinableErrorTest() {
    this.model.startGame(this.model.getDeck(), false, 5, 3);
    this.model.removeUsingDraw(0, 0, 0);
  }

  @Test
  public void removeUsingDrawTest() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);

    // cards are there
    assertEquals(new RealCard(CardValue.Five, Suit.Spades), this.model.getCardAt(2, 1));
    assertEquals(new RealCard(CardValue.Six, Suit.Spades), this.model.getCardAt(2, 2));
    assertEquals(new RealCard(CardValue.Seven, Suit.Spades), this.model.getDrawCards().get(0));
    assertEquals(new RealCard(CardValue.Eight, Suit.Spades), this.model.getDrawCards().get(1));

    // card above is invisible
    assertFalse(this.model.getCardAt(1, 1).getVisibility());

    // remove cards
    this.model.removeUsingDraw(1, 2, 1);
    this.model.removeUsingDraw(0, 2, 2);

    // poof cards are gone!
    assertNull(this.model.getCardAt(2, 1));
    assertNull(this.model.getCardAt(2, 2));
    // new cards in draw pile
    assertEquals(new RealCard(CardValue.Ten, Suit.Spades), this.model.getDrawCards().get(1));
    assertEquals(new RealCard(CardValue.Jack, Suit.Spades), this.model.getDrawCards().get(0));
  }

  // discardDraw tests
  @Test(expected = IllegalArgumentException.class)
  public void discardDrawInvalidIndexErrorTest1() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.discardDraw(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void discardDrawInvalidIndexErrorTest2() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.discardDraw(5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void discardDrawEmptyCardErrorTest() {
    this.model.startGame(this.model.getDeck(), false, 10, 3);
    this.model.discardDraw(0);
  }

  @Test
  public void discardDrawTest() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);

    // cards are there
    assertEquals(new RealCard(CardValue.Seven, Suit.Spades), this.model.getDrawCards().get(0));
    assertEquals(new RealCard(CardValue.Nine, Suit.Spades), this.model.getDrawCards().get(2));

    // discardsd the cards
    this.model.discardDraw(0);
    this.model.discardDraw(2);

    // check for the new cards
    assertEquals(new RealCard(CardValue.Ten, Suit.Spades), this.model.getDrawCards().get(0));
    assertEquals(new RealCard(CardValue.Jack, Suit.Spades), this.model.getDrawCards().get(2));
  }

  // tests for getNumRows
  @Test
  public void getNumRowsTest() {
    this.model.startGame(this.model.getDeck(), false, 1, 3);
    assertEquals(1, this.model.getNumRows());

    this.model.startGame(this.model.getDeck(), false, 3, 3);
    assertEquals(3, this.model.getNumRows());
  }

  // test for getNumDraw
  @Test
  public void getNumDrawTest() {
    // 0 draw slots
    this.model.startGame(this.model.getDeck(), false, 4, 0);
    assertEquals(0, this.model.getNumDraw());

    // all slots are filled
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    assertEquals(3, this.model.getNumDraw());

    // no slots are filled
    this.model.startGame(this.model.getDeck(), false, 9, 10);
    this.model.discardDraw(0);
    this.model.discardDraw(1);
    this.model.discardDraw(2);
    this.model.discardDraw(3);
    this.model.discardDraw(4);
    this.model.discardDraw(5);
    this.model.discardDraw(6);
    assertEquals(0, this.model.getNumDraw());

    // partially filled, partially empty
    this.model.startGame(this.model.getDeck(), false, 9, 10);
    assertEquals(7, this.model.getNumDraw());

  }

  // tests for getRowWidth
  @Test(expected = IllegalArgumentException.class)
  public void getRowWidthInvalidRowErrorTest1() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.getRowWidth(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getRowWidthInvalidRowErrorTest2() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.getRowWidth(3);
  }

  @Test
  public void getRowWidthTest() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    assertEquals(1, this.model.getRowWidth(0));
    assertEquals(2, this.model.getRowWidth(1));
    assertEquals(3, this.model.getRowWidth(2));
  }

  // tests for isGameOver
  @Test
  public void isGameOverTest() {

    List<Card> testDeck = this.model.getDeck();
    // swapping a king with the first card
    Card king = testDeck.get(12);
    Card ace = testDeck.set(0, king);
    testDeck.set(12, ace);
    this.model.startGame(testDeck, false, 1, 0);
    // remove the first card
    this.model.remove(0, 0);
    assertTrue(this.model.isGameOver());
    this.model.startGame(this.model.getDeck(), false, 1, 10);
    assertFalse(this.model.isGameOver());
    this.model.startGame(this.model.getDeck(), false, 9, 5);
    assertFalse(this.model.isGameOver());

    this.model.startGame(this.model.getDeck(), false, 3, 3);
    assertFalse(this.model.isGameOver());
    this.model.removeUsingDraw(0, 2, 2);
    this.model.removeUsingDraw(1, 2, 1);
    this.model.removeUsingDraw(2, 2, 0);
    this.model.removeUsingDraw(0, 1, 1);
    this.model.removeUsingDraw(1, 1, 0);
    this.model.removeUsingDraw(2, 0, 0);
    assertTrue(this.model.isGameOver());

  }

  // getScore tests
  @Test
  public void getScoreTest() {
    this.model.startGame(this.model.getDeck(), false, 1, 0);
    assertEquals(1, this.model.getScore());

    List<Card> testDeck = this.model.getDeck();
    Card king = testDeck.get(12);
    Card ace = testDeck.set(0, king);
    testDeck.set(12, ace);
    this.model.startGame(testDeck, false, 1, 0);
    // remove the first card
    this.model.remove(0, 0);
    assertEquals(0, this.model.getScore());

    this.model.startGame(this.model.getDeck(), false, 9, 0);
    assertEquals(294, this.model.getScore());

    this.model.startGame(this.model.getDeck(), false, 3, 3);
    assertEquals(21, this.model.getScore());
  }

  // getCardAt tests
  @Test(expected = IllegalArgumentException.class)
  public void getCardInvalidRowErrorTest1() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.getCardAt(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCardInvalidRowErrorTest2() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.getCardAt(4, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCardInvalidCardError1() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.getCardAt(2, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCardInvalidCardError2() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.getCardAt(2, 5);
  }

  @Test
  public void getCartAtTest() {
    this.model.startGame(this.model.getDeck(), false, 7, 3);
    assertEquals(new RealCard(CardValue.Ace, Suit.Spades), this.model.getCardAt(0, 0));
    assertEquals(new RealCard(CardValue.King, Suit.Hearts), this.model.getCardAt(6, 4));
    this.model.remove(6, 4);
    assertNull(this.model.getCardAt(6, 4));
  }

  // getDrawCards tests
  @Test
  public void getDrawCardsTest() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    Card sevenSpades = new RealCard(CardValue.Seven, Suit.Spades);
    Card eightSpades = new RealCard(CardValue.Eight, Suit.Spades);
    Card nineSpades = new RealCard(CardValue.Nine, Suit.Spades);
    ArrayList<Card> drawPileTest = new ArrayList<Card>();
    drawPileTest.add(sevenSpades);
    drawPileTest.add(eightSpades);
    drawPileTest.add(nineSpades);
    assertEquals(drawPileTest, this.model.getDrawCards());

    this.model.startGame(this.model.getDeck(), false, 3, 0);
    assertEquals(new ArrayList<Card>(), this.model.getDrawCards());

    this.model.startGame(this.model.getDeck(), false, 9, 10);
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
    assertEquals(drawPileTest, this.model.getDrawCards());


  }
}