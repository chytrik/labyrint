package org.mafagafogigante.labyrint.achievements.comparators;

import org.mafagafogigante.labyrint.achievements.UnlockedAchievement;
import org.mafagafogigante.labyrint.io.Version;

import java.io.Serializable;
import java.util.Comparator;

class DateUnlockedAchievementComparator implements Comparator<UnlockedAchievement>, Serializable {

  private static final long serialVersionUID = Version.MAJOR;

  @Override
  public int compare(UnlockedAchievement left, UnlockedAchievement right) {
    return left.getDate().compareTo(right.getDate());
  }

}
