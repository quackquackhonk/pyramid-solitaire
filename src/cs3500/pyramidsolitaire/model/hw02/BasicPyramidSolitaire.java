package cs3500.pyramidsolitaire.model.hw02;

import java.util.Random;

import cs3500.pyramidsolitaire.model.hw04.AbstractPyramidSolitaire;

/**
 * The model for the current state of the Basic Pyramid Solitaire game.
 */
public class BasicPyramidSolitaire extends AbstractPyramidSolitaire implements PyramidSolitaireModel<Card> {

  private boolean gameStarted;
  private Random rand;


  /**
   * Constructs a new Pyramid Solitaire Game. Only a deck of cards will be created.
   */
  public BasicPyramidSolitaire() {
    super(new Random());
  }

  /**
   * Constructs a new Pyramid Solitaire Model with a Random passed in for testing.
   *
   * @param seedRand is the seeded Random object that is provided.
   */
  public BasicPyramidSolitaire(Random seedRand) {
    super(seedRand);
  }

}
