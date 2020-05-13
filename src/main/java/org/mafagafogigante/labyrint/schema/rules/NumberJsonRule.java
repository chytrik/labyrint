package org.mafagafogigante.labyrint.schema.rules;

import org.mafagafogigante.labyrint.schema.JsonRule;

import com.eclipsesource.json.JsonValue;

class NumberJsonRule implements JsonRule {

  @Override
  public void validate(JsonValue value) {
    if (!value.isNumber()) {
      throw new IllegalArgumentException(value + " is not a number.");
    }
  }

}
