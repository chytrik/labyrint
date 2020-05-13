package org.mafagafogigante.labyrint.entity;

public interface Enchantment {
  String getName();

  String getDescription();

  void modifyAttackDamage(Damage damage);
}
