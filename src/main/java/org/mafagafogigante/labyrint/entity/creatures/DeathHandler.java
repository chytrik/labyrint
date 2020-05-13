package org.mafagafogigante.labyrint.entity.creatures;

import org.mafagafogigante.labyrint.entity.items.Item;
import org.mafagafogigante.labyrint.game.Location;
import org.mafagafogigante.labyrint.game.World;
import org.mafagafogigante.labyrint.logging.DungeonLogger;

import org.jetbrains.annotations.NotNull;

final class DeathHandler {

  private DeathHandler() {
    throw new AssertionError();
  }

  public static void handleDeath(@NotNull Creature creature) {
    if (creature.getHealth().isAlive()) {
      throw new IllegalStateException("tvor je naživu.");
    }
    Location defeatedLocation = creature.getLocation();
    defeatedLocation.removeCreature(creature);
    if (creature.hasTag(Creature.Tag.CORPSE)) {
      World world = creature.getLocation().getWorld();
      Item item = world.getItemFactory().makeCorpse(creature, defeatedLocation.getWorld().getWorldDate());
      defeatedLocation.addItem(item);
    }
    creature.getDropper().dropEverything();
    DungeonLogger.fine("Zlikvidován " + creature.getName() + " na " + creature.getLocation() + ".");
  }

}
