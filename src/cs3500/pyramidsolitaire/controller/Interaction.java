package cs3500.pyramidsolitaire.controller;


/**
 * Represents an interaction between the user and the controller, and uses simulated inputs to
 * show an expected output.
 */
public interface Interaction {

  /**
   * Applies an interaction. Can apply an input to the input StringBuilder and can also append
   * lines to the expected output.
   * @param in the simulated inputs.
   * @param out the expected output.
   */
  void apply(StringBuilder in, StringBuilder out);

}
