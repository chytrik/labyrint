package org.mafagafogigante.labyrint.spells;

import org.mafagafogigante.labyrint.game.Id;
import org.mafagafogigante.labyrint.game.Name;
import org.mafagafogigante.labyrint.game.NameFactory;
import org.mafagafogigante.labyrint.io.Version;

import java.io.Serializable;

/**
 * SpellDefinition class that contains immutable data that may be shared by multiple Spell objects.
 *
 * <p>Equality is tested based on the Id field.
 */
final class SpellDefinition implements Serializable {

  private static final long serialVersionUID = Version.MAJOR;
  public final Id id;
  // Use a name because in the future we may want to write stuff like "you casted 10 fireballs so far."
  public final Name name;

  SpellDefinition(String id, String name) {
    this.id = new Id(id);
    this.name = NameFactory.newInstance(name);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    SpellDefinition that = (SpellDefinition) object;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return "SpellDefinition{" + "id=" + id + ", name=" + name + '}';
  }

}
