package org.mafagafogigante.labyrint.game;

import org.mafagafogigante.labyrint.logging.DungeonLogger;

/**
 * Numeral enumerated type.
 */
public enum Numeral {

  ONE("jednoho"), TWO("dva"), THREE("tři"), FOUR("čtyři"), FIVE("pět"), MORE_THAN_FIVE("několik");

  final String stringRepresentation;

  Numeral(String stringRepresentation) {
    this.stringRepresentation = stringRepresentation;
  }

  /**
   * Returns a corresponding Numeral of an integer or null if there is not such Numeral.
   */
  public static Numeral getCorrespondingNumeral(int integer) {
    if (integer < 1) {
      DungeonLogger.warning("Tried to get nonpositive numeral.");
      return null;
    } else if (integer >= values().length) {
      return values()[values().length - 1];
    } else {
      return values()[integer - 1];
    }
  }

  @Override
  public String toString() {
    return stringRepresentation;
  }

}
