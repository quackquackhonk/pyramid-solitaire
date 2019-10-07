package cs3500.pyramidsolitaire.controller;

/**
 * Represents an expected output after a series of inputs.
 */
public class PrintInteraction implements Interaction {

  String[] lines;
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
