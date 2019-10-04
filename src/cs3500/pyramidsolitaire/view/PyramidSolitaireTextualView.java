package cs3500.pyramidsolitaire.view;

import java.io.IOException;
import java.util.List;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Represents a Textual view of the current state of the PyramidSolitaire game based on the model
 * given to the class.
 */
public class PyramidSolitaireTextualView  implements PyramidSolitaireView {
  private final PyramidSolitaireModel<?> model;
  private final Appendable out;

  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    this.model = model;
    this.out = new StringBuilder();
  }

  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable out) {
    this.model = model;
    this.out = out;
  }

  @Override
  public String toString() {

    String returnString = "";
    String newLine = "\n";

    try {
      this.model.isGameOver();
    } catch (IllegalStateException e) { // catch the game not started exception.
      return "";
    }

    if (this.model.isGameOver() && this.model.getScore() == 0) {
      return "You win!";
    }
    else if (this.model.isGameOver() && this.model.getScore() != 0) {
      return "Game over. Score: " + this.model.getScore();
    }
    else { // Game still going

      String paddingBlock = "  ";
      int finalIndex = this.model.getNumRows() - 1;
      for (int r = 0; r < this.model.getNumRows(); r++) { // go through rows
        // adds the spaces before each row to line each row up
        String spacesToAdd = "";
        for (int x = 0; x < finalIndex - r; x++) {
          spacesToAdd += paddingBlock;
        }
        // append the spaces onto return string
        returnString += spacesToAdd;

        // adds the cards to the row
        for (int i = 0; i < this.model.getRowWidth(r); i++) { // go through cards

          if (this.model.getCardAt(r, i) == null) { // render empty Cards

            returnString += "   ";

          } else {

            String cardToAddString = this.model.getCardAt(r, i).toString();
            int numCardsInRow = this.model.getRowWidth(r) - 1;
            if (i == numCardsInRow) {
              returnString += cardToAddString;
            } else {
              if (cardToAddString.length() != 3) {
                returnString += cardToAddString + " ";
              } else {
                returnString += cardToAddString;
              }
            }
          }
          returnString += " ";
        }
        returnString = returnString.substring(0, returnString.length() - 1);
        // moves to the next line
        returnString += newLine;
      }

      String draw = "Draw:";
      List<?> drawPile = this.model.getDrawCards();

      if (drawPile.size() != 0) {
        for (Object o : drawPile) {
          draw += " ";
          draw += o.toString();
          draw += ",";
        }
        draw = draw.substring(0, draw.length() - 1);
      }



      returnString += draw;
    }

    return returnString;
  }


  @Override
  public void render() throws IOException {

  }
}