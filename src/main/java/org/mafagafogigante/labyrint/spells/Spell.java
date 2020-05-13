package org.mafagafogigante.labyrint.spells;

import org.mafagafogigante.labyrint.entity.creatures.Hero;
import org.mafagafogigante.labyrint.game.Id;
import org.mafagafogigante.labyrint.game.Name;
import org.mafagafogigante.labyrint.io.Version;
import org.mafagafogigante.labyrint.util.Selectable;

import java.io.Serializable;

/**
 * The class that represents a spell.
 */
public abstract class Spell implements Selectable, Serializable {

  private static final long serialVersionUID = Version.MAJOR;
  private final SpellDefinition definition;

  Spell(String id, String name) {
    this.definition = new SpellDefinition(id, name);
  }

  public abstract void operate(Hero hero, String[] targetMatcher);

  public Id getId() {
    return definition.id; // Delegate to SpellDefinition.
  }

  @Override
  public Name getName() {
    return definition.name; // Delegate to SpellDefinition.
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    Spell spell = (Spell) object;
    return definition.equals(spell.definition);
  }

  @Override
  public int hashCode() {
    return definition.hashCode();
  }

  @Override
  public String toString() {
    return getName().getSingular();
  }

}
