package cs3500.pyramidsolitaire.controller;

/**
 * Represents a simulated input from the user.
 */
public class InputInteraction implements Interaction {

  String input;
  public InputInteraction(String input) {
    this.input = input;
  }

  @Override
  public void apply(StringBuilder in, StringBuilder out) {
    in.append(input);
  }
}
