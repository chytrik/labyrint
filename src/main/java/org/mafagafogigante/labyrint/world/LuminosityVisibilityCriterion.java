package org.mafagafogigante.labyrint.world;

import org.mafagafogigante.labyrint.entity.Luminosity;
import org.mafagafogigante.labyrint.entity.creatures.Observer;
import org.mafagafogigante.labyrint.io.Version;

import java.io.Serializable;

/**
 * A visibility criterion based on luminosity.
 */
public class LuminosityVisibilityCriterion implements Serializable, VisibilityCriterion {

  private static final long serialVersionUID = Version.MAJOR;
  private final Luminosity minimumLuminosity;

  public LuminosityVisibilityCriterion(Luminosity minimumLuminosity) {
    this.minimumLuminosity = minimumLuminosity;
  }

  @Override
  public boolean isMetBy(Observer observer) {
    double observerLuminosity = observer.getObserverLocation().getLuminosity().toPercentage().toDouble();
    return Double.compare(observerLuminosity, minimumLuminosity.toPercentage().toDouble()) >= 0;
  }

}
