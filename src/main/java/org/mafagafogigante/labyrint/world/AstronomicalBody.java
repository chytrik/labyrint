package org.mafagafogigante.labyrint.world;

import org.mafagafogigante.labyrint.entity.creatures.Observer;

/**
 * An astronomical body that may be seen from a world.
 */
interface AstronomicalBody {

  boolean isVisible(Observer observer);

  String describeYourself();

}
