package cs3500.pyramidsolitaire.model.hw02;

/**
 * Represents a Playing Card that has been played and discarded in a game of Pyramid Solitaire.
 * This is a placeholder value to prevent unwanted NullPointerExceptions when accessing the deck
 * and draw pile.
 */
public class EmptyCard implements Card {


  @Override
  public CardValue getValue() {
    return null;
  }

  @Override
  public int getIntegerValue() {
    return 0;
  }

  @Override
  public Suit getSuit() {
    return null;
  }

  @Override
  public String toString() {
    return "  ";
  }

  @Override
  public boolean equals(Object o) {

    if (o == this) {
      return true;
    }

    return o instanceof EmptyCard;
  }

  @Override
  public int hashCode() {
    return 0;
  }
}
