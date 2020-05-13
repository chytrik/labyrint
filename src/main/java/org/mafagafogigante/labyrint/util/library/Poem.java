package org.mafagafogigante.labyrint.util.library;

import org.mafagafogigante.labyrint.game.ColoredString;
import org.mafagafogigante.labyrint.game.DungeonString;
import org.mafagafogigante.labyrint.game.Writable;

import java.util.List;

/**
 * Poem class that defines a poem storage data structure.
 */
public final class Poem extends Writable {

  private final String title;
  private final String author;
  private final String content;

  Poem(String title, String author, String content) {
    this.title = title;
    this.author = author;
    this.content = content;
  }

  public List<ColoredString> toColoredStringList() {
    DungeonString builder = new DungeonString(toString());
    return builder.toColoredStringList();
  }

  @Override
  public String toString() {
    return title + "\n\n" + content + "\n\n" + author;
  }

}
