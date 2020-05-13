package org.mafagafogigante.labyrint.entity.items;

import org.mafagafogigante.labyrint.io.Version;

import java.io.Serializable;

public class ItemUsageEffect implements Serializable {

  private static final long serialVersionUID = Version.MAJOR;
  private final int healing;

  public ItemUsageEffect(int healing) {
    this.healing = healing;
  }

  public int getHealing() {
    return healing;
  }

}
