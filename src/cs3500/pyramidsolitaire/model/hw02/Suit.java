package cs3500.pyramidsolitaire.model.hw02;

/**
 * Enumeration of all the Suits a {@Code Card} can have.
 */
public enum Suit {

  Spades, Hearts, Diamonds, Clubs;

  String clubChar = "♣";
  String diamondChar = "♦";
  String heartChar = "♥";
  String spadeChar = "♠";

  @Override
  public String toString() {
    switch (this) {
      case Clubs:
        return "♣";
      case Hearts:
        return "♥";
      case Spades:
        return "♠";
      case Diamonds:
        return "♦";
      default:
        return " ";
    }
  }

}
