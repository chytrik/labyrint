package org.mafagafogigante.labyrint.util;

import org.mafagafogigante.labyrint.game.ColoredString;
import org.mafagafogigante.labyrint.game.DungeonString;
import org.mafagafogigante.labyrint.game.Writable;
import org.mafagafogigante.labyrint.io.DungeonResource;
import org.mafagafogigante.labyrint.io.JsonObjectFactory;
import org.mafagafogigante.labyrint.io.ResourceNameResolver;

import java.util.List;

/**
 * Tutorial class that contains the game tutorial.
 */
public class Tutorial extends Writable {

  private static final String FILENAME = ResourceNameResolver.resolveName(DungeonResource.TUTORIAL);
  private static final String text = JsonObjectFactory.makeJsonObject(FILENAME).get("tutorial").asString();

  @Override
  public List<ColoredString> toColoredStringList() {
    return new DungeonString(text).toColoredStringList();
  }

}
