package cs3500.pyramidsolitaire.controller;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;

/**
 * Controller for the PyramidSolitaire Game. Operates in a text based manner.
 */
public class PyramidSolitaireTextualController implements PyramidSolitaireController {

  private final Readable in;
  private final Appendable out;
  private PyramidSolitaireView view;

  /**
   * Constructs a Pyramid Solitaire Textual Controller.
   * @param in the inputs from the user.
   * @param out the outputs from the model.
   * @throws IllegalArgumentException if either of the arguments are null.
   */
  public PyramidSolitaireTextualController(Readable in, Appendable out)
          throws IllegalArgumentException {
    if (in == null) {
      throw new IllegalArgumentException("Input cannot be null");
    }

    if (out == null) {
      throw new IllegalArgumentException("Output cannot be null");
    }

    this.in = in;
    this.out = out;
  }


  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck,
                           boolean shuffle, int numRows, int numDraw) {
    // we need a model
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }

    try {
      model.startGame(deck, shuffle, numRows, numDraw);
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException("Invalid game state.");
    }

    // initialize view
    this.view = new PyramidSolitaireTextualView(model, this.out);

    // values for the scanner to pass in
    String commandArg1;
    String commandArg2;
    String commandArg3;
    String commandArg4;

    // big try catch to catch all the IOExceptions.
    try {

      Scanner scan = new Scanner(this.in);

      while (true) {
        // show the player the current game state
        view.render();
        if (model.isGameOver()) {
          return;
        }
        this.out.append("\n");
        this.out.append(String.format("Score: %d", model.getScore())).append("\n");

        switch (scan.next()) {
          case "rm1":
            // row of card
            if (isQuitCharacter(commandArg1 = this.getQuitOrValue(scan))) {
              quitGame(model.getScore());
              return;
            }
            // position of card
            if (isQuitCharacter(commandArg2 = this.getQuitOrValue(scan))) {
              quitGame(model.getScore());
              return;
            }
            // parses all inputs into integers and subtracts 1 (for array access in the model)
            int row = Integer.parseInt(commandArg1) - 1;
            int card = Integer.parseInt(commandArg2) - 1;

            try {
              model.remove(row, card);
            } catch (IllegalArgumentException e) {
              printErrorMessage(e.getMessage());
            }

            break;
          case "rm2":
            // row of card1
            if (isQuitCharacter(commandArg1 = this.getQuitOrValue(scan))) {
              quitGame(model.getScore());
              return;
            }
            // position of card1
            if (isQuitCharacter(commandArg2 = this.getQuitOrValue(scan))) {
              quitGame(model.getScore());
              return;
            }
            // row of card 2
            if (isQuitCharacter(commandArg3 = this.getQuitOrValue(scan))) {
              quitGame(model.getScore());
              return;
            }
            // position of card2
            if (isQuitCharacter(commandArg4 = this.getQuitOrValue(scan))) {
              quitGame(model.getScore());
              return;
            }

            // parses all inputs into integers and subtracts 1 (for array access in the model)
            int row1 = Integer.parseInt(commandArg1) - 1;
            int card1 = Integer.parseInt(commandArg2) - 1;
            int row2 = Integer.parseInt(commandArg3) - 1;
            int card2 = Integer.parseInt(commandArg4) - 1;

            try {
              model.remove(row1, card1, row2, card2);
            } catch (IllegalArgumentException e) {
              printErrorMessage(e.getMessage());
            }

            break;
          case "rmwd":
            // draw pile index
            if (isQuitCharacter(commandArg1 = this.getQuitOrValue(scan))) {
              quitGame(model.getScore());
              return;
            }
            // row of card
            if (isQuitCharacter(commandArg2 = this.getQuitOrValue(scan))) {
              quitGame(model.getScore());
              return;
            }
            // position of the card
            if (isQuitCharacter(commandArg3 = this.getQuitOrValue(scan))) {
              quitGame(model.getScore());
              return;
            }
            // parses all inputs into integers and subtracts 1 (for array access in the model)
            int rmwdDrawIdx = Integer.parseInt(commandArg1) - 1;
            int rmwdRow = Integer.parseInt(commandArg2) - 1;
            int rmwdCard = Integer.parseInt(commandArg3) - 1;

            try {
              model.removeUsingDraw(rmwdDrawIdx, rmwdRow, rmwdCard);
            } catch (IllegalArgumentException e) {
              printErrorMessage(e.getMessage());
            }
            break;
          case "dd":
            // draw pile index
            if (isQuitCharacter(commandArg1 = this.getQuitOrValue(scan))) {
              quitGame(model.getScore());
              return;
            }
            // parses all inputs into integers and subtracts 1 (for array access in the model)
            int drawIndex = Integer.parseInt(commandArg1) - 1;

            try {
              model.discardDraw(drawIndex);
            } catch (IllegalArgumentException e) {
              printErrorMessage(e.getMessage());
            }

            break;
          case "q":
          case "Q":
            quitGame(model.getScore());
            return;
          default:
            printErrorMessage("Command not recognized.");
            break;
        }

      }
    } catch (IOException e) {
      throw new IllegalStateException("Input or Output error");
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Reached the end of input");
    }



  }

  /**
   * Keeps getting user input until a number or an uppercase or lowercase 'q' is input.
   * @param scan the scanner used to read user input.
   * @return a String containing the valid input.
   * @throws IOException if prompting for new user input fails for some reason.
   */
  String getQuitOrValue(Scanner scan) throws IOException {
    String qOrInt = scan.next();

    while (!this.isQOrInt(qOrInt)) {
      this.out.append("Invalid value. Please enter a number, \'Q\', or \'q\'.\n");
      qOrInt = scan.next();
    }

    return qOrInt;

  }

  /**
   * Checks if the given String is a number (can be parsed into an Integer) or if it is an upper
   * or lower case q.
   * @param check the String to check.
   * @return is the String valid?
   */
  boolean isQOrInt(String check) {

    boolean isNumber = true;
    try {
      int test = Integer.parseInt(check);
    } catch (NumberFormatException e) {
      isNumber = false;
    }


    return check.equalsIgnoreCase("Q") || isNumber;
  }

  /**
   * Renders the current state of the game when the quit command is input.
   *
   * @throws IOException if any rendering fails for some reason.
   */
  void quitGame(int score) throws IOException {

    this.out.append("Game quit!" + "\n");
    this.out.append("State of the game when quit:" + "\n");
    this.view.render();
    this.out.append("\n");
    this.out.append(String.format("Score: %d", score));

  }

  /**
   * Checks if any of the given Strings are valid quit characters.
   *
   * @param s the String to check
   * @return are any of the Strings quit characters?
   */
  boolean isQuitCharacter(String s) {
    return s.equalsIgnoreCase("Q");
  }

  /**
   * Prompts the user to enter a new command after a failed attempt. Appends the given message
   * onto the end.
   *
   * @param message the error message to send to the used
   */
  void printErrorMessage(String message) throws IOException {
    this.out.append("Invalid move. Play again. " + message + "\n");
  }


}
