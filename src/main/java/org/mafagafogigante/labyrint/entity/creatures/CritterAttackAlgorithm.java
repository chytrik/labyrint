package org.mafagafogigante.labyrint.entity.creatures;

import org.mafagafogigante.labyrint.game.DungeonString;
import org.mafagafogigante.labyrint.game.Random;
import org.mafagafogigante.labyrint.io.Writer;

import org.jetbrains.annotations.NotNull;

import java.awt.Color;

/**
 * An implementation of AttackAlgorithm that just writes to the screen.
 */
public class CritterAttackAlgorithm implements AttackAlgorithm {

  @Override
  public void renderAttack(@NotNull Creature attacker, @NotNull Creature defender) {
    if (Random.nextBoolean()) {
      Writer.writeAndWait(new DungeonString(attacker.getName() + " nic nedělá.\n", Color.YELLOW));
    } else {
      Writer.writeAndWait(new DungeonString(attacker.getName() + " se snaží utéct.\n", Color.YELLOW));
    }
  }

}
