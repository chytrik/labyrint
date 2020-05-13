package org.mafagafogigante.labyrint.world;

import org.mafagafogigante.labyrint.entity.creatures.Observer;
import org.mafagafogigante.labyrint.io.Version;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * A collection of visibility criterion.
 */
public class VisibilityCriteria implements Serializable {

  private static final long serialVersionUID = Version.MAJOR;
  private final List<VisibilityCriterion> visibilityCriteria;

  public VisibilityCriteria(VisibilityCriterion... visibilityCriteria) {
    this.visibilityCriteria = Arrays.asList(visibilityCriteria);
  }

  /**
   * Evaluates whether or not these visibility criteria is met by an observer.
   */
  public boolean isMetBy(Observer observer) {
    for (VisibilityCriterion criterion : visibilityCriteria) {
      if (!criterion.isMetBy(observer)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String toString() {
    return "visibilityCriteria=VisibilityCriteria{" + visibilityCriteria + '}';
  }

}
