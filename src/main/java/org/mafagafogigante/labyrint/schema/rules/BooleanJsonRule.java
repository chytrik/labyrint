package org.mafagafogigante.labyrint.schema.rules;

import org.mafagafogigante.labyrint.schema.JsonRule;

import com.eclipsesource.json.JsonValue;

class BooleanJsonRule implements JsonRule {

  @Override
  public void validate(JsonValue value) {
    if (!value.isBoolean()) {
      throw new IllegalArgumentException(value + " is not a boolean.");
    }
  }

}
