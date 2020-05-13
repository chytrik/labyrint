package org.mafagafogigante.labyrint.schema.rules;

import org.mafagafogigante.labyrint.schema.JsonRule;

import com.eclipsesource.json.JsonValue;

class ArrayJsonRule implements JsonRule {

  @Override
  public void validate(JsonValue value) {
    if (!value.isArray()) {
      throw new IllegalArgumentException(value + " is not an array.");
    }
  }

}
