package org.mafagafogigante.labyrint.achievements.comparators;

import org.mafagafogigante.labyrint.achievements.UnlockedAchievement;
import org.mafagafogigante.labyrint.io.Version;

import java.io.Serializable;
import java.util.Comparator;

class NameUnlockedAchievementComparator implements Comparator<UnlockedAchievement>, Serializable {

  private static final long serialVersionUID = Version.MAJOR;

  @Override
  public int compare(UnlockedAchievement a, UnlockedAchievement b) {
    return a.getName().compareTo(b.getName());
  }

}
