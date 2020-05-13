package org.mafagafogigante.labyrint.world;

import org.mafagafogigante.labyrint.entity.creatures.Observer;

/**
 * A criterion that describes whether or not something is visible.
 */
public interface VisibilityCriterion {

  /**
   * Evaluates whether or not this criterion is met by the specified observer.
   */
  boolean isMetBy(Observer observer);

}
