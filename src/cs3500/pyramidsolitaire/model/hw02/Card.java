package cs3500.pyramidsolitaire.model.hw02;

/**
 * Represents a Card to be played in the PyramidSolitaire model.
 */
public class Card {

  //  -1 = Empty 1 = Ace, 11 = Jack, 12 = Queen, 13 = King
  private int value;
  private Suit suit;
  private boolean isVisible;

  /**
   * Creates an empty card (value = -1, suit = Suit.Empty)
   */
  Card() {
    this(-1, Suit.Empty);
  }

  /**
   * Creates a new Card with a specific suit and value. Defaults the visibility to false.
   * @param value the value of a card.
   * @param suit the suit of the card.
   */
  public Card(int value, Suit suit) {
    this(value, suit, false);
  }

  /**
   * Creates a new Card with the specific suit, value, and visibility.
   * @param value the value of the card
   * @param suit the suit of the card
   * @param isVisible the visiblity of the card
   */
  private Card(int value, Suit suit, boolean isVisible) {
    this.value = value;
    this.suit = suit;
    this.isVisible = isVisible;
  }

  @Override
  public String toString() {
    String clubChar = "♣";
    String diamondChar = "♦";
    String heartChar = "♥";
    String spadeChar = "♠";

    String valueString = "";
    String suitString = "";
    String returnString = "";

    if (this.isEmpty()) {
      return "   ";
    }

    switch (this.suit) {
      case Clubs:
        suitString = clubChar;
        break;
      case Diamonds:
        suitString = diamondChar;
        break;
      case Hearts:
        suitString = heartChar;
        break;
      case Spades:
        suitString = spadeChar;
        break;
      default:
        suitString = " ";
        break;
    }

    switch (this.value) {
      case 11:
        valueString = "J";
        break;
      case 12:
        valueString = "Q";
        break;
      case 13:
        valueString = "K";
        break;
      case 1:
        valueString = "A";
        break;
      default:
        valueString = Integer.toString(this.value);
        break;
    }

    returnString = valueString + suitString;
    return returnString;
  }

  @Override
  public boolean equals(Object o) {

    if (o == this) {
      return true;
    }

    if (!(o instanceof Card)) {
      return false;
    }

    Card c = (Card) o;

    return (this.suit == c.suit && this.value == c.getValue());

  }

  @Override
  public int hashCode() {
    return 7 * this.value + this.suit.hashCode();
  }

  /**
   * Checks if the card is visible or not.
   *
   * @return the value of this.isExposed.
   */
  public boolean getVisibility() {
    return this.isVisible;
  }

  /**
   * Gets the value of the card as a number.
   *
   * @return the value of the card.
   */
  int getValue() {
    return this.value;
  }

  /**
   * Determines if combining this card with the other card will allow a removal.
   * @param other the other card
   * @return true if the sums of the card values == 13, else false.
   */
  boolean canCombine(Card other) {
    return this.getValue() + other.getValue() == 13;
  }

  /**
   * Determines if the card is an empty Card.
   *
   * @return if the suit is Empty and the value is -1, return true, else return false
   */
  boolean isEmpty() {
    return this.suit == Suit.Empty && this.value == -1;
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
    return new Card(this.value, this.suit, this.isVisible);
  }

}
