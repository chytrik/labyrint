package org.mafagafogigante.labyrint.schema.rules;

import org.mafagafogigante.labyrint.schema.JsonRule;

import com.eclipsesource.json.JsonValue;

class StringJsonRule implements JsonRule {

  @Override
  public void validate(JsonValue value) {
    if (!value.isString()) {
      throw new IllegalArgumentException(value + " is not a string.");
    }
  }

}
