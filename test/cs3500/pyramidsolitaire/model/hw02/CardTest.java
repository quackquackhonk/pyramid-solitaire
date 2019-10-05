package cs3500.pyramidsolitaire.model.hw02;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Tests the methods in the Card class.
 */
public class CardTest {

  private Card empty = new EmptyCard();
  private Card aceSpade = new RealCard(CardValue.Ace, Suit.Spades);
  private Card fourHeart = new RealCard(CardValue.Two, Suit.Hearts);
  private Card tenHeart = new RealCard(CardValue.Ten, Suit.Hearts);
  private Card jackDiamond = new RealCard(CardValue.Jack, Suit.Diamonds);
  private Card queenClub = new RealCard(CardValue.Queen, Suit.Clubs);
  private Card kingDiamond = new RealCard(CardValue.King, Suit.Diamonds);

  @Test
  public void toStringTest() {
    assertEquals("  ", empty.toString());
    assertEquals("A♠", aceSpade.toString());
    assertEquals("2♥", fourHeart.toString());
    assertEquals("10♥", tenHeart.toString());
    assertEquals("J♦", jackDiamond.toString());
    assertEquals("Q♣", queenClub.toString());
    assertEquals("K♦", kingDiamond.toString());
  }

  @Test
  public void getValueTest() {
    assertEquals(CardValue.Ace, this.aceSpade.getValue());
    assertEquals(CardValue.Queen, this.queenClub.getValue());
    assertNull(this.empty.getValue());
  }

  @Test
  public void getIntegerValueTest() {
    assertEquals(1, this.aceSpade.getIntegerValue());
    assertEquals(12, this.queenClub.getIntegerValue());
    assertEquals(0, this.empty.getIntegerValue());
  }

  @Test
  public void testIsEquals() {
    Card emptyEqual = new EmptyCard();
    assertTrue(empty.equals(empty));
    assertTrue(empty.equals(emptyEqual));
    assertTrue(emptyEqual.equals(empty));

    Card aceEquals = new RealCard(CardValue.Ace, Suit.Spades);
    assertTrue(aceSpade.equals(aceSpade));
    assertTrue(aceSpade.equals(aceEquals));
    assertTrue(aceEquals.equals(aceSpade));

    assertFalse(aceSpade.equals(empty));
    assertFalse(aceSpade.equals(queenClub));
  }

}