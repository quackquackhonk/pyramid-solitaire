package cs3500.pyramidsolitaire.model.hw02;

import org.junit.Test;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Tests non public methods in the BasicPyramidSolitaire model implementation.
 */
public class BasicPyramidSolitaireOtherMethodTests {

  private Random seededRandom = new Random(69); // nice
  private BasicPyramidSolitaire model = new BasicPyramidSolitaire(seededRandom);

  @Test(expected = IllegalStateException.class)
  public void discardInvalidStateErrorTest() {
    this.model.discard(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void discardInvalidRowErrorTest1() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.discard(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void discardInvalidRowErrorTest2() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.discard(3, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void discardInvalidCardErrorTest1() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.discard(2, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void discardInvalidCardErrorTest2() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.discard(2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void discardInvisibleCardErrorTest() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.discard(0, 0);
  }

  @Test
  public void discardTest() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    assertEquals(new RealCard(CardValue.Four, Suit.Spades), this.model.getCardAt(2, 0));
    this.model.discard(2, 0);
    assertNull(this.model.getCardAt(2, 0));

    this.model.startGame(this.model.getDeck(), false, 6, 3);
    assertEquals(new RealCard(CardValue.Eight, Suit.Hearts), this.model.getCardAt(5, 5));
    assertEquals(new RealCard(CardValue.Seven, Suit.Hearts), this.model.getCardAt(5, 4));
    assertEquals(new RealCard(CardValue.Two, Suit.Hearts), this.model.getCardAt(4, 4));
    assertFalse(this.model.getCardAt(4, 4).getVisibility());
    this.model.discard(5, 5);
    this.model.discard(5, 4);
    assertNull(this.model.getCardAt(5, 5));
    assertNull(this.model.getCardAt(5, 4));
    assertTrue(this.model.getCardAt(4, 4).getVisibility());
    this.model.discard(4, 4);
    assertNull(this.model.getCardAt(4, 4));
  }

  @Test(expected = IllegalStateException.class)
  public void updateVisiblityInvalidStateErrorTest() {
    this.model.updateVisibility();
  }

  @Test
  public void updateVisibilityTest() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    // no removals
    assertTrue(this.model.getCardAt(2, 0).getVisibility());
    assertTrue(this.model.getCardAt(2, 1).getVisibility());
    assertTrue(this.model.getCardAt(2, 2).getVisibility());
    assertFalse(this.model.getCardAt(1, 0).getVisibility());
    assertFalse(this.model.getCardAt(1, 1).getVisibility());
    // remove bottom row
    this.model.discard(2, 0);
    this.model.discard(2, 1);
    this.model.discard(2, 2);

    assertTrue(this.model.getCardAt(1, 0).getVisibility());
    assertTrue(this.model.getCardAt(1, 1).getVisibility());
  }

  @Test(expected = IllegalStateException.class)
  public void noCardsBeneathInvalidStateErrorTest() {
    this.model.noCardsBeneath(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void noCardsBeneathInvalidRowErrorTest1() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.noCardsBeneath(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void noCardsBeneathInvalidRowErrorTest2() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.noCardsBeneath(3, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void noCardsBeneathInvalidCardErrorTest1() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.noCardsBeneath(2, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void noCardsBeneathInvalidCardErrorTest2() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.noCardsBeneath(2, 3);
  }

  @Test
  public void noCardsBeneath() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    assertTrue(this.model.noCardsBeneath(2, 0));
    assertTrue(this.model.noCardsBeneath(2, 1));
    assertTrue(this.model.noCardsBeneath(2, 2));
    assertFalse(this.model.noCardsBeneath(1, 0));
    this.model.discard(2, 0);
    this.model.discard(2, 1);
    assertTrue(this.model.noCardsBeneath(1, 0));
    assertFalse(this.model.noCardsBeneath(2, 0));
    assertFalse(this.model.noCardsBeneath(2, 1));
  }

  @Test(expected = IllegalStateException.class)
  public void checkValidityInvalidStateErrorTest() {
    this.model.checkValidity(0, 0, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkValidityInvalidRowErrorTest1() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.checkValidity(-1, 0, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkValidityInvalidRowErrorTest2() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.checkValidity(3, 0, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkValidityInvalidCardErrorTest1() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.checkValidity(2, -1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkValidityInvalidCardErrorTest2() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.checkValidity(2, 3, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkValidityEmptyCardErrorTest() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model.discard(2, 0);
    this.model.checkValidity(2, 0, true);
  }

  @Test
  public void isDeckValidTest() {

    List<Card> testDeck = this.model.getDeck();
    assertTrue(this.model.isDeckValid(testDeck));

    this.model.startGame(testDeck, false, 3, 3);
    assertTrue(this.model.isDeckValid(testDeck));

  }

  @Test(expected = IllegalStateException.class)
  public void dealInvalidStateErrorTest() {
    this.model.deal();
  }

  @Test
  public void dealTest() {
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    assertEquals(new RealCard(CardValue.Ten, Suit.Spades), this.model.deal());
    assertEquals(new RealCard(CardValue.Jack, Suit.Spades), this.model.deal());
    assertEquals(new RealCard(CardValue.Queen, Suit.Spades), this.model.deal());

    this.model.startGame(this.model.getDeck(), false, 1, 0);
    assertEquals(new RealCard(CardValue.Two, Suit.Spades), this.model.deal());
    assertEquals(new RealCard(CardValue.Three, Suit.Spades), this.model.deal());
    assertEquals(new RealCard(CardValue.Four, Suit.Spades), this.model.deal());

    this.model.startGame(this.model.getDeck(), false, 9, 7);
    assertEquals(new EmptyCard(), this.model.deal());
    assertEquals(new EmptyCard(), this.model.deal());
    assertEquals(new EmptyCard(), this.model.deal());
    assertEquals(new EmptyCard(), this.model.deal());

    this.model.startGame(this.model.getDeck(), true, 3, 3);
    assertEquals(new RealCard(CardValue.Eight, Suit.Spades), this.model.deal());
    assertEquals(new RealCard(CardValue.Eight, Suit.Diamonds), this.model.deal());
    assertEquals(new RealCard(CardValue.Eight, Suit.Clubs), this.model.deal());
  }

  @Test(expected = IllegalStateException.class)
  public void isEmptyTestInvalidStateTest() {
    this.model.isEmpty(new RealCard(CardValue.Ace, Suit.Clubs));
  }

  @Test
  public void isEmptyTest() {

    this.model.startGame(this.model.getDeck(), false, 3, 3);

    Card empty = new EmptyCard();
    Card aceSpade = new RealCard(CardValue.Ace, Suit.Spades);
    assertTrue(this.model.isEmpty(empty));
    assertFalse(this.model.isEmpty(aceSpade));

  }

  @Test(expected = IllegalStateException.class)
  public void copyInvalidStateTest() {
    this.model.copy(new EmptyCard());
  }

  @Test
  public void copyTest() {

    this.model.startGame(this.model.getDeck(), false, 3, 3);

    Card empty = new EmptyCard();
    Card aceSpade = new RealCard(CardValue.Ace, Suit.Spades);

    assertEquals(new EmptyCard(), this.model.copy(empty));
    assertEquals(new RealCard(CardValue.Ace, Suit.Spades), this.model.copy(aceSpade));

  }

  @Test(expected = IllegalStateException.class)
  public void canCombineInvalidStateTest() {
    this.model.canCombine(new EmptyCard(), new EmptyCard());
  }

  @Test
  public void canCombineTest() {

    this.model.startGame(this.model.getDeck(), false, 3, 3);

    Card empty = new EmptyCard();
    Card aceSpade = new RealCard(CardValue.Ace, Suit.Spades);
    Card queenDiamond = new RealCard(CardValue.Queen, Suit.Diamonds);
    Card kingHearts = new RealCard(CardValue.King, Suit.Hearts);

    assertTrue(this.model.canCombine(aceSpade, queenDiamond));
    assertFalse(this.model.canCombine(kingHearts, empty));
    assertFalse(this.model.canCombine(kingHearts, queenDiamond));
    assertFalse(this.model.canCombine(aceSpade, empty));



  }
}
