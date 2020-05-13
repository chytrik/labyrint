package org.mafagafogigante.labyrint.entity.items;

import org.mafagafogigante.labyrint.entity.Weight;

interface LimitedInventory {

  int getItemLimit();

  Weight getWeightLimit();

}
