package cs3500.pyramidsolitaire.model.hw02;

/**
 * The interface that outlines the basic functionality of a Playing Card.
 */
public interface Card {

  @Override
  String toString();

  @Override
  boolean equals(Object o);

  @Override
  int hashCode();

  /**
   * Gets the value of the card as a number.
   *
   * @return the value of the card.
   */
  CardValue getValue();

  /**
   * Gets the integer value of a card for use of arithmetic involving the value of the Card.
   *
   * @return the integer value associated with the value of the card.
   */
  int getIntegerValue();

  /**
   * Gets the Suit of the Card as a Suit.
   *
   * @return the suit of the card.
   */
  Suit getSuit();
}
