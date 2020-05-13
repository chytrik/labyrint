package org.mafagafogigante.labyrint.schema.rules;

import org.mafagafogigante.labyrint.date.DungeonTimeParser;

import com.eclipsesource.json.JsonValue;

class PeriodJsonRule extends StringJsonRule {

  @Override
  public void validate(JsonValue value) {
    super.validate(value);
    try {
      DungeonTimeParser.parseDuration(value.asString());
    } catch (IllegalArgumentException invalidValue) {
      throw new IllegalArgumentException(value + " is not a valid Dungeon period.");
    }
  }

}
