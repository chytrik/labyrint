package org.mafagafogigante.labyrint.entity.creatures;

import org.mafagafogigante.labyrint.entity.items.Item;
import org.mafagafogigante.labyrint.game.DungeonString;
import org.mafagafogigante.labyrint.game.Game;
import org.mafagafogigante.labyrint.io.Writer;

import java.awt.Color;
import java.util.Locale;

/**
 * This class is uninstantiable and provides utility IO methods for AttackAlgorithm implementations.
 */
final class AttackAlgorithmWriter {

  private AttackAlgorithmWriter() { // Ensure that this class cannot be instantiated.
    throw new AssertionError();
  }

  /**
   * Writes a message about the inflicted damage based on the parameters.
   *
   * @param attacker the Creature that performed the attack
   * @param hitDamage the damage inflicted by the attacker
   * @param defender the target of the attack
   * @param criticalHit a boolean indicating if the attack was a critical hit or not
   */
  static void writeInflictedDamage(Creature attacker, int hitDamage, Creature defender, boolean criticalHit) {
    DungeonString string = new DungeonString();
    string.setColor(attacker.getId().equals(Game.getGameState().getHero().getId()) ? Color.GREEN : Color.RED);
    string.append(attacker.getName().getSingular());
    string.append(" způsobil ");
    string.append(String.valueOf(hitDamage));
    string.append(" body poškození ");
    string.append(defender.getName().getSingular());
    if (criticalHit) {
      string.append(" s kritickým zásahem");
    }
    string.append(".");
    string.append(" To vypadá ");
    string.append(defender.getHealth().getHealthState().toString().toLowerCase(Locale.ENGLISH));
    string.append(".\n");
    Writer.writeAndWait(string);
  }

  /**
   * Writes a miss message.
   *
   * @param attacker the attacker creature
   */
  static void writeMiss(Creature attacker) {
    Writer.writeAndWait(new DungeonString(attacker.getName() + " minul.\n", Color.YELLOW));
  }

  /**
   * Writes a weapon breakage message.
   *
   * @param weapon the weapon that broke, should be broken
   */
  static void writeWeaponBreak(Item weapon) {
    if (!weapon.isBroken()) {
      throw new IllegalArgumentException("zbraň není rozbitá.");
    }
    Writer.write(new DungeonString(weapon.getName() + " rozbil!\n", Color.RED));
  }

}
