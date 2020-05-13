package org.mafagafogigante.labyrint.schema.rules;

import org.mafagafogigante.labyrint.util.Percentage;

import com.eclipsesource.json.JsonValue;

class PercentageJsonRule extends StringJsonRule {

  @Override
  public void validate(JsonValue value) {
    super.validate(value);
    try {
      Percentage.fromString(value.asString());
    } catch (IllegalArgumentException invalidValue) {
      throw new IllegalArgumentException(value + " is not a valid Dungeon percentage.");
    }
  }

}
