package cs3500.pyramidsolitaire.controller;

/**
 * Represents an expected output after a series of inputs.
 */
public class PrintInteraction {

  /**
   * Appends expected output from a series of commands onto the out StringBuilder.
   * @param lines the expected output.
   * @return an Interaction having appended the expected output.
   */
  static Interaction prints(String... lines) {
    return (input, output) -> {
      for (String line : lines) {
        output.append(line).append('\n');
      }
    };
  }

}
