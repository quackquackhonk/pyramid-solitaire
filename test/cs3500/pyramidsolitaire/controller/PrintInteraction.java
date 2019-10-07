package cs3500.pyramidsolitaire.controller;

/**
 * Represents an expected output after a series of inputs.
 */
public class PrintInteraction implements Interaction {

  String[] lines;

  /**
   * Constructs a simulated output from the view.
   * @param lines the lines to be rendered.
   */
  public PrintInteraction(String... lines) {

    this.lines = lines;

  }


  @Override
  public void apply(StringBuilder in, StringBuilder out) {
    for (String line : lines) {
      out.append(line).append("\n");
    }
  }
}
