import org.junit.Test;

import java.util.List;
import java.util.Random;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.CardValue;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.RealCard;
import cs3500.pyramidsolitaire.model.hw02.Suit;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;

import static org.junit.Assert.*;

public class RelaxedPyramidSolitaireTest extends AbstractPyramidSolitaireTests {


  @Override
  protected PyramidSolitaireModel<Card> factory() {
    return new RelaxedPyramidSolitaire(new Random(69)); // nice.
  }

  @Test
  public void relaxedRemovalTesting() {
    PyramidSolitaireModel<Card> model = this.factory();

    model.startGame(model.getDeck(), false, 7, 3);

    // check the cards we're testing are all there.
    assertEquals(new RealCard(CardValue.Nine, Suit.Hearts), model.getCardAt(6, 0));
    assertEquals(new RealCard(CardValue.Ten, Suit.Hearts), model.getCardAt(6, 1));
    assertEquals(new RealCard(CardValue.Three, Suit.Hearts), model.getCardAt(5, 0));
    assertEquals(new RealCard(CardValue.Four, Suit.Diamonds), model.getDrawCards().get(1));

    // remove the 9 with the 4
    model.removeUsingDraw(1, 6, 0);

    // only the 9 is gone
    assertNull(model.getCardAt(6, 0));
    assertEquals(new RealCard(CardValue.Ten, Suit.Hearts), model.getCardAt(6, 1));
    assertEquals(new RealCard(CardValue.Three, Suit.Hearts), model.getCardAt(5, 0));

    // relaxed remove
    model.remove(6, 1, 5, 0);

    // all the cards are gone
    assertNull(model.getCardAt(6, 1));
    assertNull(model.getCardAt(5, 0));
  }

  @Test
  public void relaxedGameOverTesting() {

    PyramidSolitaireModel<Card> model = this.factory();

    List<Card> deck = model.getDeck();

    Card king = deck.remove(12);
    Card queen = deck.remove(11);
    Card ace = deck.remove(0);
    deck.add(0, king);
    deck.add(0, ace);
    deck.add(0, queen);


    model.startGame(deck, false, 2, 0);

    assertFalse(model.isGameOver());

    // remove king
    model.remove(1, 1);
    assertFalse(model.isGameOver());

    // remove ace and queen
    model.remove(0, 0, 1, 0);
    assertTrue(model.isGameOver());



  }




}