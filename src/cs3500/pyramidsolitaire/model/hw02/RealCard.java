package cs3500.pyramidsolitaire.model.hw02;

/**
 * Represents a standard 52 Card playing Card.
 */
public class RealCard implements Card{

  private CardValue value;
  private Suit suit;
  private boolean isVisible;

  /**
   * Creates a new Card with a specific suit and value. Defaults the visibility to false.
   * @param value the value of a card.
   * @param suit the suit of the card.
   */
  public RealCard(CardValue value, Suit suit) {
    this(value, suit, false);
  }

  /**
   * Creates a new Card with the specific suit, value, and visibility.
   * @param value the value of the card
   * @param suit the suit of the card
   * @param isVisible the visiblity of the card
   */
  private RealCard(CardValue value, Suit suit, boolean isVisible) {
    this.value = value;
    this.suit = suit;
    this.isVisible = isVisible;
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
  public boolean getVisibility() {
    return this.isVisible;
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


  /**
   * Determines if combining this card with the other card will allow a removal.
   * @param other the other card
   * @return true if the sums of the card values == 13, else false.
   */
  boolean canCombine(RealCard other) {
    return this.value.getValue() + other.value.getValue() == 13;
  }


  /**
   * Makes a card visible.
   */
  void makeVisible() {
    this.isVisible = true;
  }

  /**
   * Returns a deep copy of this card.
   * @return a copy of this card
   */
  Card copy() {
    return new RealCard(this.value, this.suit, this.isVisible);
  }

}
