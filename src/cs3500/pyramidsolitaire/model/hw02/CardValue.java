package cs3500.pyramidsolitaire.model.hw02;

/**
 * Enumeration of all possible values for a {@Code Card}.
 */
public enum CardValue {

  Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King;

  @Override
  public String toString() {
    switch(this) {
      case Ace:
        return "A";
      case Two:
        return "2";
      case Three:
        return "3";
      case Four:
        return "4";
      case Five:
        return "5";
      case Six:
        return "6";
      case Seven:
        return "7";
      case Eight:
        return "8";
      case Nine:
        return "9";
      case Ten:
        return "10";
      case Jack:
        return "J";
      case Queen:
        return "Q";
      case King:
        return "K";
      default:
        return "";
    }
  }

  /**
   * Returns a number value corresponding to the numeric value of this CardValue
   *
   * @return the numeric value of this CardValue
   */
  public int getValue() {
    switch(this) {
      case Ace:
        return 1;
      case Two:
        return 2;
      case Three:
        return 3;
      case Four:
        return 4;
      case Five:
        return 5;
      case Six:
        return 6;
      case Seven:
        return 7;
      case Eight:
        return 8;
      case Nine:
        return 9;
      case Ten:
        return 10;
      case Jack:
        return 11;
      case Queen:
        return 12;
      case King:
        return 13;
      default:
        return 0;
    }
  }



}
