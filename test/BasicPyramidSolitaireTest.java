import java.util.Random;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;


public class BasicPyramidSolitaireTest extends AbstractPyramidSolitaireTests {

  @Override
  protected PyramidSolitaireModel<Card> factory() {
    return new BasicPyramidSolitaire(new Random(69));
  }
}