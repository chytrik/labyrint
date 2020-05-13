package org.mafagafogigante.labyrint.achievements;

import org.mafagafogigante.labyrint.date.Date;
import org.mafagafogigante.labyrint.entity.creatures.Creature;
import org.mafagafogigante.labyrint.entity.creatures.CreaturePreset;
import org.mafagafogigante.labyrint.game.Id;
import org.mafagafogigante.labyrint.game.PartOfDay;
import org.mafagafogigante.labyrint.game.Point;
import org.mafagafogigante.labyrint.stats.CauseOfDeath;
import org.mafagafogigante.labyrint.stats.Statistics;
import org.mafagafogigante.labyrint.util.CounterMap;
import org.mafagafogigante.labyrint.util.Percentage;

import org.junit.Assert;
import org.junit.Test;

public class AchievementTest {

  @Test
  public void testBattleAchievementShouldBeFulfilled() throws Exception {
    AchievementBuilder achievementBuilder = new AchievementBuilder();
    achievementBuilder.setId("TWO_COWS");
    achievementBuilder.setName("Two Cows");

    BattleStatisticsQuery cowQuery = new BattleStatisticsQuery();
    cowQuery.setId(new Id("COW"));

    achievementBuilder.addBattleStatisticsRequirement(new BattleStatisticsRequirement(cowQuery, 2));

    CreaturePreset cowPreset = new CreaturePreset();
    cowPreset.setId(new Id("COW"));
    cowPreset.setType("BEAST");
    cowPreset.setHealth(1);
    cowPreset.setVisibility(Percentage.fromString("80%"));

    Achievement achievement = achievementBuilder.createAchievement();
    CauseOfDeath unarmedCauseOfDeath = CauseOfDeath.getUnarmedCauseOfDeath();

    Statistics statistics = new Statistics();
    Assert.assertFalse(achievement.isFulfilled(statistics));
    statistics.getBattleStatistics().addBattle(new Creature(cowPreset), unarmedCauseOfDeath, PartOfDay.DAWN);
    Assert.assertFalse(achievement.isFulfilled(statistics));
    statistics.getBattleStatistics().addBattle(new Creature(cowPreset), unarmedCauseOfDeath, PartOfDay.DAWN);
    Assert.assertTrue(achievement.isFulfilled(statistics));
  }

  @Test
  public void testVisitedLocationsBasedAchievementsShouldWorkAsExpected() throws Exception {
    AchievementBuilder achievementBuilder = new AchievementBuilder();
    achievementBuilder.setId("VISIT_TWO_DIFFERENT_DESERTS");
    achievementBuilder.setName("Visit Two Different Deserts");
    CounterMap<Id> visitedLocations = new CounterMap<>();
    visitedLocations.incrementCounter(new Id("DESERT"), 2);
    achievementBuilder.setVisitedLocations(visitedLocations);
    Achievement achievement = achievementBuilder.createAchievement();

    Statistics statistics = new Statistics();
    Assert.assertFalse(achievement.isFulfilled(statistics));
    Date startOfDawn = new Date(PartOfDay.DAWN.getStartingHour());
    statistics.getExplorationStatistics().addVisit(new Point(0, 0, 0), new Id("DESERT"), startOfDawn);
    Assert.assertFalse(achievement.isFulfilled(statistics));
    statistics.getExplorationStatistics().addVisit(new Point(0, 0, 0), new Id("DESERT"), startOfDawn);
    Assert.assertFalse(achievement.isFulfilled(statistics));
    statistics.getExplorationStatistics().addVisit(new Point(1, 1, 1), new Id("DESERT"), startOfDawn);
    Assert.assertTrue(achievement.isFulfilled(statistics));
  }

  @Test
  public void testMaximumVisitsAchievementsShouldWorkAsExpected() throws Exception {
    AchievementBuilder achievementBuilder = new AchievementBuilder();
    achievementBuilder.setId("VISIT_ANY_DESERT_TWICE");
    achievementBuilder.setName("Visit Any Desert Twice");
    CounterMap<Id> maximumVisits = new CounterMap<>();
    maximumVisits.incrementCounter(new Id("DESERT"), 2);
    achievementBuilder.setMaximumNumberOfVisits(maximumVisits);
    Achievement achievement = achievementBuilder.createAchievement();

    Statistics statistics = new Statistics();
    Assert.assertFalse(achievement.isFulfilled(statistics));
    Date startOfDawn = new Date(PartOfDay.DAWN.getStartingHour());
    statistics.getExplorationStatistics().addVisit(new Point(0, 0, 0), new Id("DESERT"), startOfDawn);
    Assert.assertFalse(achievement.isFulfilled(statistics));
    statistics.getExplorationStatistics().addVisit(new Point(1, 1, 1), new Id("DESERT"), startOfDawn);
    Assert.assertFalse(achievement.isFulfilled(statistics));
    statistics.getExplorationStatistics().addVisit(new Point(0, 0, 0), new Id("DESERT"), startOfDawn);
    Assert.assertTrue(achievement.isFulfilled(statistics));
  }

}
