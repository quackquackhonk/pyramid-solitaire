import org.junit.Test;

import java.io.StringReader;
import java.util.List;

import cs3500.pyramidsolitaire.controller.InputInteraction;
import cs3500.pyramidsolitaire.controller.Interaction;
import cs3500.pyramidsolitaire.controller.PrintInteraction;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

import static org.junit.Assert.*;

public class PyramidSolitaireTextualControllerTest {

  /*
   * TODO: test playGame (harness and interactions).
   */
  PyramidSolitaireModel model = new BasicPyramidSolitaire();

  String startingBoard = "    A♠\n"
          + "  2♠  3♠\n"
          + "4♠  5♠  6♠\n"
          + "Draw: 7♠, 8♠, 9♠";

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

    assertEquals(simulatedOutput.toString(), actualOutput.toString());
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

  @Test
  public void testPlayGameImmediateQuit() {
    testHarnessPSTController(model,
            model.getDeck(),
            false,
            3,
            3,
            new InputInteraction("q\n"),
            new PrintInteraction(startingBoard),
            new PrintInteraction("Score: 21"),
            new PrintInteraction("Game quit!"),
            new PrintInteraction("State of the game when quit:"),
            new PrintInteraction(startingBoard),
            new PrintInteraction("Score: 21"));
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


}