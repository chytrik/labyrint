package org.mafagafogigante.labyrint.entity.creatures;

import org.mafagafogigante.labyrint.io.Version;

import java.io.Serializable;
import java.util.List;

abstract class EffectTemplate implements Serializable {

  private static final long serialVersionUID = Version.MAJOR;

  abstract Effect instantiate(List<String> parameters);

}
