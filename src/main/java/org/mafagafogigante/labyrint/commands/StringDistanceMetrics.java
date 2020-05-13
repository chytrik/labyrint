package org.mafagafogigante.labyrint.commands;

import org.apache.commons.lang3.StringUtils;

class StringDistanceMetrics {

  static int levenshteinDistance(final String a, final String b) {
    if (!CommandLimits.isWithinMaximumCommandLength(a)) {
      throw new IllegalArgumentException("vstup je příliš velký.");
    }
    if (!CommandLimits.isWithinMaximumCommandLength(b)) {
      throw new IllegalArgumentException("vstup je příliš velký");
    }
    return StringUtils.getLevenshteinDistance(a, b);
  }

}
