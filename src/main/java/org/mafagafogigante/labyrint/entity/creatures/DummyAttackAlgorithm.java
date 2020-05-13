package org.mafagafogigante.labyrint.entity.creatures;

import org.mafagafogigante.labyrint.game.DungeonString;
import org.mafagafogigante.labyrint.io.Writer;

import org.jetbrains.annotations.NotNull;

import java.awt.Color;

/**
 * An implementation of AttackAlgorithm that just writes to the screen.
 */
public class DummyAttackAlgorithm implements AttackAlgorithm {

  @Override
  public void renderAttack(@NotNull Creature attacker, @NotNull Creature defender) {
    Writer.writeAndWait(new DungeonString(attacker.getName() + " stojí nehybně.\n", Color.YELLOW));
  }

}
