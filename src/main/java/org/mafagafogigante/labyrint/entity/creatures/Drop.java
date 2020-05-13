package org.mafagafogigante.labyrint.entity.creatures;

import org.mafagafogigante.labyrint.game.Id;
import org.mafagafogigante.labyrint.game.Random;
import org.mafagafogigante.labyrint.io.Version;
import org.mafagafogigante.labyrint.util.Percentage;

import java.io.Serializable;

/**
 * This class represents an item drop law.
 */
class Drop implements Serializable {

  private static final long serialVersionUID = Version.MAJOR;
  private final Id itemId;
  private final Percentage probability;

  public Drop(Id itemId, Percentage probability) {
    this.itemId = itemId;
    this.probability = probability;
  }

  public Id getItemId() {
    return itemId;
  }

  public boolean rollForDrop() {
    return Random.roll(probability);
  }

  @Override
  public String toString() {
    return "Drop{" +
        "itemId=" + itemId +
        ", pravděpodobnost=" + probability +
        '}';
  }

}
