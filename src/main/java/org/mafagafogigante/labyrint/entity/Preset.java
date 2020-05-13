package org.mafagafogigante.labyrint.entity;

import org.mafagafogigante.labyrint.game.Id;
import org.mafagafogigante.labyrint.game.Name;
import org.mafagafogigante.labyrint.util.Percentage;

/**
 * An interface that simplifies Entity instantiation.
 */
public interface Preset {

  Id getId();

  String getType();

  Name getName();

  Weight getWeight();

  Percentage getVisibility();

}
