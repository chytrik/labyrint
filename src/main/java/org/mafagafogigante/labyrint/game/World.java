package org.mafagafogigante.labyrint.game;

import org.mafagafogigante.labyrint.date.Date;
import org.mafagafogigante.labyrint.date.DungeonTimeUnit;
import org.mafagafogigante.labyrint.entity.EnchantmentFactory;
import org.mafagafogigante.labyrint.entity.creatures.CorpseItemPresetFactory;
import org.mafagafogigante.labyrint.entity.creatures.CreatureFactory;
import org.mafagafogigante.labyrint.entity.creatures.CreaturePresetFactory;
import org.mafagafogigante.labyrint.entity.creatures.JsonCreaturePresetFactory;
import org.mafagafogigante.labyrint.entity.creatures.Observer;
import org.mafagafogigante.labyrint.entity.items.ItemFactory;
import org.mafagafogigante.labyrint.entity.items.ItemPresetFactory;
import org.mafagafogigante.labyrint.entity.items.JsonItemPresetFactory;
import org.mafagafogigante.labyrint.io.DungeonResource;
import org.mafagafogigante.labyrint.io.ResourceNameResolver;
import org.mafagafogigante.labyrint.io.Version;
import org.mafagafogigante.labyrint.logging.DungeonLogger;
import org.mafagafogigante.labyrint.stats.WorldStatistics;
import org.mafagafogigante.labyrint.world.Sky;
import org.mafagafogigante.labyrint.world.SkyFactory;
import org.mafagafogigante.labyrint.world.Weather;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * A complete world, with a generator, entity factories, a map, a date, and statistics.
 */
public class World implements Serializable {

  private static final long serialVersionUID = Version.MAJOR;
  private final WorldGenerator generator = new WorldGenerator(this);

  // Each world should have its own factories because their limitations and characteristics are not meant to be shared.
  private final CreatureFactory creatureFactory;
  private final ItemFactory itemFactory;

  private final Map<Point, Location> locations = new HashMap<>();

  private final WorldStatistics worldStatistics;

  private final Date worldCreationDate = new Date(1, 1, 1);
  private final Sky sky = SkyFactory.makeDarrowmereSky();
  private Date worldDate = new Date(2055, 6, 2, 6, 10, 0);
  private final Weather weather = new Weather(worldDate);

  /**
   * Creates a new World.
   *
   * @param statistics a WorldStatistics object on which this World will record its status
   */
  public World(WorldStatistics statistics) {
    worldStatistics = statistics;

    String creaturesFilename = ResourceNameResolver.resolveName(DungeonResource.CREATURES);
    CreaturePresetFactory creaturePresetFactory = new JsonCreaturePresetFactory(creaturesFilename);
    creatureFactory = new CreatureFactory(creaturePresetFactory);

    String itemsFilename = ResourceNameResolver.resolveName(DungeonResource.ITEMS);
    String enchantmentsFilename = ResourceNameResolver.resolveName(DungeonResource.ENCHANTMENTS);
    ItemPresetFactory jsonItemPresetFactory = new JsonItemPresetFactory(itemsFilename);
    ItemPresetFactory corpseItemPresetFactory = new CorpseItemPresetFactory(creatureFactory);
    EnchantmentFactory enchantmentFactory = new EnchantmentFactory(enchantmentsFilename);
    itemFactory = new ItemFactory(enchantmentFactory, jsonItemPresetFactory, corpseItemPresetFactory);
  }

  /**
   * Returns a thorough description of what is currently visible in the world's sky.
   */
  public String describeTheSky(Observer observer) {
    return sky.describeYourself(observer);
  }

  public ItemFactory getItemFactory() {
    return itemFactory;
  }

  public CreatureFactory getCreatureFactory() {
    return creatureFactory;
  }

  Date getWorldCreationDate() {
    return worldCreationDate;
  }

  public Date getWorldDate() {
    return worldDate;
  }

  /**
   * Adds a Location to this World.
   */
  void addLocation(Location locationObject, Point coordinates) {
    if (locations.containsKey(coordinates)) {
      throw new IllegalStateException("tried to repeatedly add a location to " + coordinates + ".");
    }
    if (!locationObject.getWorld().equals(this)) {
      World world = locationObject.getWorld();
      throw new IllegalStateException("tried to add location with World field " + world + " to " + this + ".");
    }
    if (!locationObject.getPoint().equals(coordinates)) {
      Point point = locationObject.getPoint();
      throw new IllegalStateException("tried to add location with Point field " + point + " to " + coordinates + ".");
    }
    locations.put(coordinates, locationObject);
    worldStatistics.addLocation(locationObject.getName().getSingular());
  }

  /**
   * Gets the Location in the specified Point. If the Location in the Point has not yet been created, the world
   * generator will do it.
   *
   * @param point a Point object
   * @return a Location
   */
  @NotNull
  public Location getLocation(@NotNull Point point) {
    if (!locations.containsKey(point)) {
      generator.expand(point);
    }
    return locations.get(point);
  }

  /**
   * Returns the PartOfDay constant that represents the current part of the day.
   */
  public PartOfDay getPartOfDay() {
    return PartOfDay.getCorrespondingConstant(worldDate);
  }

  /**
   * Returns the current Weather of this World.
   */
  public Weather getWeather() {
    return weather;
  }

  /**
   * Rolls the world date a given amount of seconds forward.
   */
  void rollDate(long seconds) {
    if (seconds <= 0) {
      DungeonLogger.warning("Cannot roll the World's Date back.");
    } else {
      worldDate = worldDate.plus(seconds, DungeonTimeUnit.SECOND);
    }
  }

  /**
   * Checks if there is a location at the specified point. Invoking this method may trigger world expansion.
   */
  public boolean hasLocationAt(Point point) {
    if (alreadyHasLocationAt(point)) {
      return true;
    } else {
      if (point.getZ() == 0) {
        generator.expand(point);
      }
      return alreadyHasLocationAt(point);
    }
  }

  /**
   * Checks if there is already a location at the specified point. This method should only be called from World and
   * WorldGenerator.
   */
  public boolean alreadyHasLocationAt(Point point) {
    return locations.containsKey(point);
  }

}
