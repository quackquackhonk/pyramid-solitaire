package cs3500.pyramidsolitaire.model.hw02;

/**
 * Represents a standard 52 Card playing Card.
 */
public class RealCard implements Card {

  private final CardValue value;
  private final Suit suit;

  /**
   * Creates a new Card with a specific suit and value. Defaults the visibility to false.
   * @param value the value of a card.
   * @param suit the suit of the card.
   */
  public RealCard(CardValue value, Suit suit) {
    this.value = value;
    this.suit = suit;
  }


  @Override
  public String toString() {
    return this.value.toString() + this.suit.toString();
  }

  @Override
  public boolean equals(Object o) {

    if (o == this) {
      return true;
    }

    if (!(o instanceof RealCard)) {
      return false;
    }

    RealCard c = (RealCard) o;

    return (this.suit == c.suit && this.value == c.getValue());

  }

  @Override
  public int hashCode() {
    return this.value.hashCode() + this.suit.hashCode();
  }

  @Override
  public Suit getSuit() {
    return this.suit;
  }

  @Override
  public CardValue getValue() {
    return this.value;
  }

  @Override
  public int getIntegerValue() {
    return this.value.getValue();
  }

}
