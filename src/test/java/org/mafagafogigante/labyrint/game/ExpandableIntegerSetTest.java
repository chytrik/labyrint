package org.mafagafogigante.labyrint.game;

import org.junit.Test;

public class ExpandableIntegerSetTest {

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorThrowsIllegalArgumentExceptionIfMinimumDifferenceIsNegative() {
    new ExpandableIntegerSet(-1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorThrowsIllegalArgumentExceptionIfMinimumDifferenceIsZero() {
    new ExpandableIntegerSet(0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorThrowsIllegalArgumentExceptionOnMaximumDifferenceNotBiggerThanMinimumDifference() {
    new ExpandableIntegerSet(1, 1);
  }

}
