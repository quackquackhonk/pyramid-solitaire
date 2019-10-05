import org.junit.Test;

import java.util.List;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the textual rendering of a given PyramidSolitaire model game state.
 */
public class PyramidSolitaireTextualViewTest {

  private PyramidSolitaireTextualView gameNotStarted =
          new PyramidSolitaireTextualView(new BasicPyramidSolitaire());


  @Test
  public void testToString() {
    assertEquals("", gameNotStarted.toString());

    BasicPyramidSolitaire model = new BasicPyramidSolitaire();
    model.startGame(model.getDeck(), false, 7, 3);
    PyramidSolitaireTextualView view = new PyramidSolitaireTextualView(model);

    String startingView =  "            A♠\n"
                         + "          2♠  3♠\n"
                         + "        4♠  5♠  6♠\n"
                         + "      7♠  8♠  9♠  10♠\n"
                         + "    J♠  Q♠  K♠  A♥  2♥\n"
                         + "  3♥  4♥  5♥  6♥  7♥  8♥\n"
                         + "9♥  10♥ J♥  Q♥  K♥  A♦  2♦\n"
                         + "Draw: 3♦, 4♦, 5♦";
    String removeKingView =  "            A♠\n"
                           + "          2♠  3♠\n"
                           + "        4♠  5♠  6♠\n"
                           + "      7♠  8♠  9♠  10♠\n"
                           + "    J♠  Q♠  K♠  A♥  2♥\n"
                           + "  3♥  4♥  5♥  6♥  7♥  8♥\n"
                           + "9♥  10♥ J♥  Q♥      A♦  2♦\n"
                           + "Draw: 3♦, 4♦, 5♦";
    assertEquals(startingView, view.toString());
    model.remove(6, 4);
    assertEquals(removeKingView, view.toString());
    model.startGame(model.getDeck(), false, 1, 0);
    assertEquals("Game over. Score: 1", view.toString());

    List<Card> testDeck = model.getDeck();
    // swapping a king with the first card
    testDeck.set(0, testDeck.set(12, testDeck.get(0)));
    model.startGame(testDeck, false, 1, 0);
    // remove the first card
    model.remove(0, 0);
    assertEquals("You win!", view.toString());


  }


}