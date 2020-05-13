package org.mafagafogigante.labyrint.schema;

import com.eclipsesource.json.JsonValue;

/**
 * Interface for JSON value validation rules.
 */
public interface JsonRule {

  /**
   * Validates the provided JSON value.
   */
  void validate(JsonValue value);

}
