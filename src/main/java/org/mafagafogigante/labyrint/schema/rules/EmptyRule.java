package org.mafagafogigante.labyrint.schema.rules;

import org.mafagafogigante.labyrint.schema.JsonRule;

import com.eclipsesource.json.JsonValue;

final class EmptyRule implements JsonRule {

  @Override
  public void validate(JsonValue value) {
  }

}
