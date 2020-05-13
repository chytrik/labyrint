package org.mafagafogigante.labyrint.io;

import org.mafagafogigante.labyrint.schema.JsonRule;
import org.mafagafogigante.labyrint.schema.rules.JsonRuleFactory;

import com.eclipsesource.json.JsonObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TutorialJsonFileTest extends ResourcesTypeTest {

  private static final String TUTORIAL_FIELD = "tutorial";

  @Test
  public void testIsFileHasValidStructure() {
    Map<String, JsonRule> tutorialFileRules = new HashMap<>();
    tutorialFileRules.put(TUTORIAL_FIELD, JsonRuleFactory.makeStringRule());
    JsonRule tutorialJsonRule = JsonRuleFactory.makeObjectRule(tutorialFileRules);
    String filename = ResourceNameResolver.resolveName(DungeonResource.TUTORIAL);
    JsonObject tutorialFileJsonObject = getJsonObjectByJsonFilename(filename);
    tutorialJsonRule.validate(tutorialFileJsonObject);
  }

}
