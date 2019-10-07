import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import cs3500.pyramidsolitaire.controller.InputInteraction;
import cs3500.pyramidsolitaire.controller.Interaction;
import cs3500.pyramidsolitaire.controller.PrintInteraction;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.ConfirmInputsModel;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

import static org.junit.Assert.assertEquals;

/**
 * Testing suite for the PyramidSolitaireTextualController.
 */
public class PyramidSolitaireTextualControllerTest {

  PyramidSolitaireModel model = new BasicPyramidSolitaire();

  String startingBoard = "    A♠\n"
          + "  2♠  3♠\n"
          + "4♠  5♠  6♠\n"
          + "Draw: 7♠, 8♠, 9♠";

  @Test
  public void confirmInputsTest() {
    Reader in = new StringReader("rm1 1 1 rm1 3 3 rm2 1 1 2 2 rm2 5 5 5 4 rmwd 3 3 3 rmwd 1"
            + " 1 1 dd 1 dd 2 dd 3 q");
    StringBuilder out = new StringBuilder();

    PyramidSolitaireController controller = new PyramidSolitaireTextualController(in, out);

    StringBuilder log = new StringBuilder();
    PyramidSolitaireModel model = new ConfirmInputsModel(log);
    // these inputs don't matter
    controller.playGame(model, model.getDeck(), false, 5, 5);

    StringBuilder expectedLog = new StringBuilder();
    expectedLog.append("row: 0, card: 0\n");
    expectedLog.append("row: 2, card: 2\n");
    expectedLog.append("row1: 0, card1: 0, row2: 1, card2: 1\n");
    expectedLog.append("row1: 4, card1: 4, row2: 4, card2: 3\n");
    expectedLog.append("drawIndex: 2, row: 2, card: 2\n");
    expectedLog.append("drawIndex: 0, row: 0, card: 0\n");
    expectedLog.append("drawIndex: 0\n");
    expectedLog.append("drawIndex: 1\n");
    expectedLog.append("drawIndex: 2\n");
    assertEquals(expectedLog.toString(), log.toString());
  }

  /**
   * A test harness for testing functionality of the playGame function.
   * @param model the model that runs the game.
   * @param deck a valid deck of cards.
   * @param shuffle Whether to shuffle the deck or not.
   * @param numRows the number of rows in the pyramid.
   * @param numDraw the number of draw cards available.
   * @param interactions the interactions to be tested.
   */
  public void testHarnessPSTController(PyramidSolitaireModel<Card> model,
                                       List<Card> deck,
                                       boolean shuffle,
                                       int numRows,
                                       int numDraw,
                                       Interaction... interactions) {

    StringBuilder simulatedInput = new StringBuilder();
    StringBuilder simulatedOutput = new StringBuilder();

    for (Interaction interaction : interactions) {
      interaction.apply(simulatedInput, simulatedOutput);
    }

    StringReader input = new StringReader(simulatedInput.toString());
    StringBuilder actualOutput = new StringBuilder();

    PyramidSolitaireController controller =
            new PyramidSolitaireTextualController(input, actualOutput);
    controller.playGame(model, deck, shuffle, numRows, numDraw);

    String simOut = simulatedOutput.toString();
    simOut = simOut.substring(0, simOut.length() - 1);

    assertEquals(simOut, actualOutput.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testNoMoreValidCommandsToRead() {
    testHarnessPSTController(model,
            model.getDeck(),
            false,
            3,
            3,
            new InputInteraction("pee pee poo poo!"), // it doesn't matter what is here, because
            // the controller will run out of valid terms to interpret, causing the reader to fail
            new PrintInteraction(startingBoard),
            new PrintInteraction("Score: 21"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidModel() {
    testHarnessPSTController(null,
            model.getDeck(),
            false,
            3,
            3,
            new InputInteraction("q"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDeck() {
    List<Card> deck = model.getDeck();
    deck.remove(0);

    testHarnessPSTController(null,
            model.getDeck(),
            false,
            3,
            3,
            new InputInteraction("q"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDeck2() {
    testHarnessPSTController(null,
            null,
            false,
            3,
            3,
            new InputInteraction("q"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRows() {
    testHarnessPSTController(null,
            model.getDeck(),
            false,
            -1,
            3,
            new InputInteraction("q"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRows2() {
    testHarnessPSTController(null,
            model.getDeck(),
            false,
            10,
            3,
            new InputInteraction("q"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDraw() {
    testHarnessPSTController(null,
            model.getDeck(),
            false,
            3,
            -1,
            new InputInteraction("q"));
  }

  @Test
  public void testPlayGameImmediateQuit() {
    testHarnessPSTController(model,
            model.getDeck(),
            false,
            3,
            3,
            new InputInteraction("q\n"),
            new PrintInteraction(startingBoard,
                    "Score: 21",
                    "Game quit!",
                    "State of the game when quit:",
                    startingBoard,
                    "Score: 21"));
  }

  @Test
  public void testPlayGameImmediateLoss() {
    testHarnessPSTController(model,
            model.getDeck(),
            false,
            3,
            0,
            new PrintInteraction("Game over. Score: 21"));
  }

  @Test
  public void testPlayGameIntoWin() {
    String start = startingBoard + "\nScore: 21";
    String remove1 = "    A♠\n"
            + "  2♠  3♠\n"
            + "4♠  5♠     \n"
            + "Draw: 10♠, 8♠, 9♠\n"
            + "Score: 15";

    String remove2 = "    A♠\n"
            + "  2♠  3♠\n"
            + "4♠         \n"
            + "Draw: 10♠, J♠, 9♠\n"
            + "Score: 10";

    String remove3 = "    A♠\n"
            + "  2♠  3♠\n"
            + "           \n"
            + "Draw: 10♠, J♠, Q♠\n"
            + "Score: 6";

    String remove4 = "    A♠\n"
            + "  2♠     \n"
            + "           \n"
            + "Draw: K♠, J♠, Q♠\n"
            + "Score: 3";

    String remove5 = "    A♠\n"
            + "         \n"
            + "           \n"
            + "Draw: K♠, A♥, Q♠\n"
            + "Score: 1";

    testHarnessPSTController(model,
            model.getDeck(),
            false,
            3,
            3,
            new PrintInteraction(start),
            new InputInteraction("rmwd 1 3 3\n"),
            new PrintInteraction(remove1),
            new InputInteraction("rmwd 2 3 2\n"),
            new PrintInteraction(remove2),
            new InputInteraction("rmwd 3 3 1\n"),
            new PrintInteraction(remove3),
            new InputInteraction("rmwd 1 2 2\n"),
            new PrintInteraction(remove4),
            new InputInteraction("rmwd 2 2 1\n"),
            new PrintInteraction(remove5),
            new InputInteraction("rmwd 3 1 1\n"),
            new PrintInteraction("You win!"));
  }

  @Test
  public void testRunGameInvalidCommandArguments() {
    String start = startingBoard + "\nScore: 21";

    String reEnter = "Invalid value. Please enter a number, \'Q\', or \'q\'.";

    String remove1 = "    A♠\n"
            + "  2♠  3♠\n"
            + "4♠  5♠     \n"
            + "Draw: 10♠, 8♠, 9♠\n"
            + "Score: 15";

    testHarnessPSTController(model,
            model.getDeck(),
            false,
            3,
            3,
            new PrintInteraction(start),
            new InputInteraction("rmwd 1 a 3\n"),
            new PrintInteraction(reEnter),
            new InputInteraction("3\n"),
            new PrintInteraction(remove1),
            new InputInteraction("q"),
            new PrintInteraction("Game quit!", "State of the game when quit:", remove1));
  }

  @Test
  public void test2ArgRemove() {

    String largeStartingBoard =  "            A♠\n"
            + "          2♠  3♠\n"
            + "        4♠  5♠  6♠\n"
            + "      7♠  8♠  9♠  10♠\n"
            + "    J♠  Q♠  K♠  A♥  2♥\n"
            + "  3♥  4♥  5♥  6♥  7♥  8♥\n"
            + "9♥  10♥ J♥  Q♥  K♥  A♦  2♦\n"
            + "Draw: 3♦, 4♦, 5♦\n"
            + "Score: 185";

    String removeKingView =  "            A♠\n"
            + "          2♠  3♠\n"
            + "        4♠  5♠  6♠\n"
            + "      7♠  8♠  9♠  10♠\n"
            + "    J♠  Q♠  K♠  A♥  2♥\n"
            + "  3♥  4♥  5♥  6♥  7♥  8♥\n"
            + "9♥  10♥ J♥  Q♥      A♦  2♦\n"
            + "Draw: 3♦, 4♦, 5♦\n"
            + "Score: 172";

    testHarnessPSTController(model,
            model.getDeck(),
            false,
            7,
            3,
            new PrintInteraction(largeStartingBoard),
            new InputInteraction("rm1 7 1\n"),
            new PrintInteraction("Invalid move. Play again. Card value is not 13",
                    largeStartingBoard),
            new InputInteraction("rm1 1 1\n"),
            new PrintInteraction("Invalid move. Play again. Card is not visible.",
                    largeStartingBoard),
            new InputInteraction("rm1 7 5\n"),
            new PrintInteraction(removeKingView),
            new InputInteraction("q\n"),
            new PrintInteraction("Game quit!", "State of the game when quit:"),
            new PrintInteraction(removeKingView));
  }

  @Test
  public void test4ArgRemove() {
    String largeStartingBoard =  "            A♠\n"
            + "          2♠  3♠\n"
            + "        4♠  5♠  6♠\n"
            + "      7♠  8♠  9♠  10♠\n"
            + "    J♠  Q♠  K♠  A♥  2♥\n"
            + "  3♥  4♥  5♥  6♥  7♥  8♥\n"
            + "9♥  10♥ J♥  Q♥  K♥  A♦  2♦\n"
            + "Draw: 3♦, 4♦, 5♦\n"
            + "Score: 185";

    String removeQAView =  "            A♠\n"
            + "          2♠  3♠\n"
            + "        4♠  5♠  6♠\n"
            + "      7♠  8♠  9♠  10♠\n"
            + "    J♠  Q♠  K♠  A♥  2♥\n"
            + "  3♥  4♥  5♥  6♥  7♥  8♥\n"
            + "9♥  10♥ J♥      K♥      2♦\n"
            + "Draw: 3♦, 4♦, 5♦\n"
            + "Score: 172";

    testHarnessPSTController(model,
            model.getDeck(),
            false,
            7,
            3,
            new PrintInteraction(largeStartingBoard),
            new InputInteraction("rm2 7 1 7 2\n"),
            new PrintInteraction("Invalid move. Play again. Sum of the cards is not 13",
                    largeStartingBoard),
            new InputInteraction("rm2 7 4 7 6\n"),
            new PrintInteraction(removeQAView),
            new InputInteraction("q\n"),
            new PrintInteraction("Game quit!", "State of the game when quit:", removeQAView));
  }

  @Test
  public void testDiscardDraw() {
    String largeStartingBoard =  "            A♠\n"
            + "          2♠  3♠\n"
            + "        4♠  5♠  6♠\n"
            + "      7♠  8♠  9♠  10♠\n"
            + "    J♠  Q♠  K♠  A♥  2♥\n"
            + "  3♥  4♥  5♥  6♥  7♥  8♥\n"
            + "9♥  10♥ J♥  Q♥  K♥  A♦  2♦\n"
            + "Draw: 3♦, 4♦, 5♦\n"
            + "Score: 185";
    String discard1 = "            A♠\n"
            + "          2♠  3♠\n"
            + "        4♠  5♠  6♠\n"
            + "      7♠  8♠  9♠  10♠\n"
            + "    J♠  Q♠  K♠  A♥  2♥\n"
            + "  3♥  4♥  5♥  6♥  7♥  8♥\n"
            + "9♥  10♥ J♥  Q♥  K♥  A♦  2♦\n"
            + "Draw: 6♦, 4♦, 5♦\n"
            + "Score: 185";

    String discard2 = "            A♠\n"
            + "          2♠  3♠\n"
            + "        4♠  5♠  6♠\n"
            + "      7♠  8♠  9♠  10♠\n"
            + "    J♠  Q♠  K♠  A♥  2♥\n"
            + "  3♥  4♥  5♥  6♥  7♥  8♥\n"
            + "9♥  10♥ J♥  Q♥  K♥  A♦  2♦\n"
            + "Draw: 6♦, 7♦, 5♦\n"
            + "Score: 185";


    testHarnessPSTController(model,
            model.getDeck(),
            false,
            7,
            3,
            new PrintInteraction(largeStartingBoard),
            new InputInteraction("dd 1\n"),
            new PrintInteraction(discard1),
            new InputInteraction("dd 2\n"),
            new PrintInteraction(discard2),
            new InputInteraction("q\n"),
            new PrintInteraction("Game quit!", "State of the game when quit:", discard2));
  }


}