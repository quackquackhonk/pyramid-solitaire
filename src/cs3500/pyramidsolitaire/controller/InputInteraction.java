package cs3500.pyramidsolitaire.controller;

/**
 * Represents a simulated input from the user.
 */
public class InputInteraction {

  /**
   * Appends the given input onto the simulated input StringBuilder.
   * @param in a command input.
   * @return an Interaction, having applied the given input.
   */
  static Interaction inputs(String in) {
    return (input, output) -> input.append(in);
  }

}
