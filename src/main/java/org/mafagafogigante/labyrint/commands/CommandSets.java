package org.mafagafogigante.labyrint.commands;

import org.mafagafogigante.labyrint.achievements.Achievement;
import org.mafagafogigante.labyrint.achievements.AchievementStoreFactory;
import org.mafagafogigante.labyrint.achievements.AchievementTracker;
import org.mafagafogigante.labyrint.achievements.AchievementTrackerWriter;
import org.mafagafogigante.labyrint.date.Date;
import org.mafagafogigante.labyrint.entity.creatures.Creature;
import org.mafagafogigante.labyrint.entity.creatures.Hero;
import org.mafagafogigante.labyrint.entity.items.CreatureInventory.SimulationResult;
import org.mafagafogigante.labyrint.entity.items.Item;
import org.mafagafogigante.labyrint.game.DungeonString;
import org.mafagafogigante.labyrint.game.Engine;
import org.mafagafogigante.labyrint.game.Game;
import org.mafagafogigante.labyrint.game.GameState;
import org.mafagafogigante.labyrint.game.Id;
import org.mafagafogigante.labyrint.game.Location;
import org.mafagafogigante.labyrint.game.LocationPreset;
import org.mafagafogigante.labyrint.game.LocationPresetStore;
import org.mafagafogigante.labyrint.game.Point;
import org.mafagafogigante.labyrint.game.Random;
import org.mafagafogigante.labyrint.game.World;
import org.mafagafogigante.labyrint.gui.WritingSpecifications;
import org.mafagafogigante.labyrint.io.Loader;
import org.mafagafogigante.labyrint.io.PoemWriter;
import org.mafagafogigante.labyrint.io.SavesTableWriter;
import org.mafagafogigante.labyrint.io.Version;
import org.mafagafogigante.labyrint.io.Writer;
import org.mafagafogigante.labyrint.map.WorldMapWriter;
import org.mafagafogigante.labyrint.stats.CauseOfDeath;
import org.mafagafogigante.labyrint.stats.ExplorationStatistics;
import org.mafagafogigante.labyrint.util.ColumnAlignment;
import org.mafagafogigante.labyrint.util.CounterMap;
import org.mafagafogigante.labyrint.util.Messenger;
import org.mafagafogigante.labyrint.util.SystemInformation;
import org.mafagafogigante.labyrint.util.Table;
import org.mafagafogigante.labyrint.util.Tutorial;
import org.mafagafogigante.labyrint.util.library.Libraries;
import org.mafagafogigante.labyrint.wiki.WikiSearcher;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

final class CommandSets {

  private static final Map<String, CommandSet> commandSetMap = initializeCommandSetMap();

  private CommandSets() {
    throw new AssertionError();
  }

  @NotNull
  private static Map<String, CommandSet> initializeCommandSetMap() {
    Map<String, CommandSet> map = new HashMap<>();
    map.put("default", initializeDefaultCommandSet());
    map.put("extra", initializeExtraCommandSet());
    map.put("debug", initializeDebugCommandSet());
    return map;
  }

  private static CommandSet initializeDefaultCommandSet() {
    CommandSet commandSet = CommandSet.emptyCommandSet();
    // Respect the alphabetical ordering of the Command names.
    commandSet.addCommand(new Command("úspěchy", "Zobrazuje již odemčené úspěchy.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        AchievementTrackerWriter.parseCommand(arguments);
      }
    });
    commandSet.addCommand(new Command("věk", "Zobrazuje věk postavy.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().printAge();
      }
    });
    commandSet.addCommand(new Command("kouzlo", "Vrhá kouzlo.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().getSpellcaster().parseCast(arguments);
      }
    });
    commandSet.addCommand(new Command("znič", "Ničí předměty na zemi.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().destroyItems(arguments);
      }
    });
    commandSet.addCommand(new Command("odhoď", "Odhodí zadané věci.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().dropItems(arguments);
      }
    });
    commandSet.addCommand(new Command("pij", "Vypije zadanou věc") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().drinkItem(arguments);
      }
    });
    commandSet.addCommand(new Command("sněz", "Sní zadanou věc.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().eatItem(arguments);
      }
    });
    commandSet.addCommand(new Command("podmínky", "Vypíše aktuálně aktivní podmínky.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().writeConditions();
      }
    });
    commandSet.addCommand(new Command("nasaď", "Nasadí zadanou věc.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().parseEquip(arguments);
      }
    });
    commandSet.addCommand(new Command("prozkoumej", "Prozkoumá zadanou věc.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().examineItem(arguments);
      }
    });
    commandSet.addCommand(new Command("skonči", "Skončí hru.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.exit();
      }
    });
    commandSet.addCommand(new Command("rybař", "Zkusí lovit ryby") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().fish();
      }
    });
    commandSet.addCommand(new Command("jdi", "Umožní pohyb postavy v určeném směru.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().walk(arguments);
      }
    });
    commandSet.addCommand(new Command("věci", "Vypíše položky v inventáři postavy.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().writeInventory();
      }
    });
    commandSet.addCommand(new Command("zabij", "Zaútočí na cíl vybraný hráčem.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().attackTarget(arguments);
      }
    });
    commandSet.addCommand(new Command("nahraj", "Nahraje uloženou hru.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        GameState loadedGameState = Loader.parseLoadCommand(arguments);
        if (loadedGameState != null) {
          Game.unsetGameState();
          Game.setGameState(loadedGameState);
        }
      }
    });
    commandSet.addCommand(new Command("okolí", "Popisuje, co může postava vidět.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().look();
      }
    });
    commandSet.addCommand(new Command("mapa", "Zobrazuje mapu vašeho okolí.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        WorldMapWriter.writeMap();
      }
    });
    commandSet.addCommand(new Command("mléko", "Pokouší se o mléko stvůru.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().parseMilk(arguments);
      }
    });
    commandSet.addCommand(new Command("nová", "Zahájí novou hru.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.unsetGameState();
        Game.setGameState(Loader.newGame());
      }
    });
    commandSet.addCommand(new Command("seber", "Pokus o zvednutí věcí z aktuálního umístění.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().pickItems(arguments);
      }
    });
    commandSet.addCommand(new Command("čti", "Přečte zadanou věc.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().readItem(arguments);
      }
    });
    commandSet.addCommand(new Command("odpočiň", "Odpočiň až do uzdravení asi tři pětiny zdraví postavy.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().rest();
      }
    });
    commandSet.addCommand(new Command("ulož", "Uloží hru.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Loader.saveGame(Game.getGameState(), arguments);
      }
    });
    commandSet.addCommand(new Command("uložené", "Zobrazí tabulku se všemi uloženými soubory.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        SavesTableWriter.writeSavesFolderTable();
      }
    });
    commandSet.addCommand(new Command("spi", "Spí, dokud nevyjde slunce.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().sleep();
      }
    });
    commandSet.addCommand(new Command("kouzla", "Vypíše všechna kouzla známá postavou.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().writeSpellList();
      }
    });
    commandSet.addCommand(new Command("stav", "Zobrazuje stav postavy.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().printAllStatus();
      }
    });
    commandSet.addCommand(new Command("čas", "Zobrazuje, co postava ví o aktuálním čase.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().readTime();
      }
    });
    commandSet.addCommand(new Command("výuka", "Zobrazí výukový program.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Writer.write(new Tutorial(), new WritingSpecifications(false, 0));
      }
    });
    commandSet.addCommand(new Command("odlož", "Odhodí aktuálně nasazenou věc.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getHero().unequipWeapon();
      }
    });
    commandSet.addCommand(new Command("wiki", "Prohledá článek na wiki.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        WikiSearcher.search(arguments);
      }
    });
    return commandSet;
  }

  private static CommandSet initializeExtraCommandSet() {
    CommandSet commandSet = CommandSet.emptyCommandSet();
    commandSet.addCommand(new Command("text", "Na obrazovku vyvolá obrovské množství barevného textu.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        List<String> alphabet = Arrays.asList("abcdefghijklmnopqrstuvwxyz".split(""));
        DungeonString dungeonString = new DungeonString();
        for (int i = 0; i < 10000; i++) {
          dungeonString.setColor(new Color(Random.nextInteger(256), Random.nextInteger(256), Random.nextInteger(256)));
          dungeonString.append(Random.select(alphabet));
        }
        Writer.write(dungeonString);
      }
    });
    commandSet.addCommand(new Command("rada", "Zobrazuje náhodné rady pro hru.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Writer.write(Libraries.getHintLibrary().next());
      }
    });
    commandSet.addCommand(new Command("báseň", "Vytiskne báseň z knihovny básní.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        PoemWriter.parsePoemCommand(arguments);
      }
    });
    commandSet.addCommand(new Command("statistika", "Zobrazí všechny dostupné statistiky hry.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Game.getGameState().getStatistics().writeStatistics();
      }
    });
    commandSet.addCommand(new Command("systém", "Zobrazuje informace o základním systému.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Writer.write(new SystemInformation());
      }
    });
    commandSet.addCommand(new Command("verze", "Zobrazuje verzi hry.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Writer.write("Podzemí verze " + Version.getCurrentVersion() + ".");
      }
    });
    return commandSet;
  }

  private static CommandSet initializeDebugCommandSet() {
    CommandSet commandSet = CommandSet.emptyCommandSet();
    commandSet.addCommand(new Command("uspechy", "Píše úspěchy, které jste dosud neodemkli.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        AchievementTracker tracker = Game.getGameState().getHero().getAchievementTracker();
        List<Achievement> notYetUnlockedAchievementList = new ArrayList<>();
        for (Achievement achievement : AchievementStoreFactory.getDefaultStore().getAchievements()) {
          if (tracker.hasNotBeenUnlocked(achievement)) {
            notYetUnlockedAchievementList.add(achievement);
          }
        }
        if (notYetUnlockedAchievementList.isEmpty()) {
          Writer.write("Všechny úspěchy byly odemčeny.");
        } else {
          Collections.sort(notYetUnlockedAchievementList, new Comparator<Achievement>() {
            @Override
            public int compare(Achievement o1, Achievement o2) {
              return o1.getName().compareTo(o2.getName());
            }
          });
          for (Achievement achievement : notYetUnlockedAchievementList) {
            Writer.write(String.format("%s : %s", achievement.getName(), achievement.getInfo()));
          }
        }
      }
    });
    commandSet.addCommand(new Command("pruzkum", "Píše statistiky o vašem průzkumu.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        ExplorationStatistics explorationStatistics = Game.getGameState().getStatistics().getExplorationStatistics();
        List<ColumnAlignment> columnAlignments = new ArrayList<>();
        columnAlignments.add(ColumnAlignment.LEFT);
        columnAlignments.add(ColumnAlignment.RIGHT);
        columnAlignments.add(ColumnAlignment.RIGHT);
        columnAlignments.add(ColumnAlignment.RIGHT);
        Table table = new Table("Jméno", "Zabití", "Dosud navštívené", "Maximální počet návštěv");
        table.setColumnAlignments(columnAlignments);
        for (LocationPreset preset : LocationPresetStore.getDefaultLocationPresetStore().getAllPresets()) {
          String name = preset.getName().getSingular();
          String kills = String.valueOf(explorationStatistics.getKillCount(preset.getId()));
          String visitedSoFar = String.valueOf(explorationStatistics.getVisitedLocations(preset.getId()));
          String maximumNumberOfVisits = String.valueOf(explorationStatistics.getMaximumNumberOfVisits(preset.getId()));
          table.insertRow(name, kills, visitedSoFar, maximumNumberOfVisits);
        }
        Writer.write(table);
      }
    });
    commandSet.addCommand(new Command("zabiti", "Píše statistiky o vašich zabitích.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        CounterMap<CauseOfDeath> map =
            Game.getGameState().getStatistics().getBattleStatistics().getKillsByCauseOfDeath();
        if (map.isNotEmpty()) {
          Table table = new Table("Typ", "Počet");
          table.setColumnAlignments(Arrays.asList(ColumnAlignment.LEFT, ColumnAlignment.RIGHT));
          for (CauseOfDeath causeOfDeath : map.keySet()) {
            table.insertRow(causeOfDeath.toString(), String.valueOf(map.getCounter(causeOfDeath)));
          }
          Writer.write(table);
        } else {
          Writer.write("Ještě jsi nic nezabil. Jdi něco zabít!");
        }
      }
    });
    commandSet.addCommand(new Command("lokace", "Vypíše informace o aktuálním umístění.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        final int width = 40;  // The width of the row's "tag".
        Location heroLocation = Game.getGameState().getHero().getLocation();
        Point heroPosition = heroLocation.getPoint();
        DungeonString dungeonString = new DungeonString();
        dungeonString.append(StringUtils.rightPad("Body:", width));
        dungeonString.append(heroPosition.toString());
        dungeonString.append("\n");
        dungeonString.append(StringUtils.rightPad("Bytosti (" + heroLocation.getCreatureCount() + "):", width));
        dungeonString.append("\n");
        for (Creature creature : heroLocation.getCreatures()) {
          dungeonString.append("  " + creature.getName());
          dungeonString.append("\n");
        }
        if (!heroLocation.getItemList().isEmpty()) {
          dungeonString.append(StringUtils.rightPad("Věci (" + heroLocation.getItemList().size() + "):", width));
          dungeonString.append("\n");
          for (Item item : heroLocation.getItemList()) {
            dungeonString.append("  " + item.getQualifiedName());
            dungeonString.append("\n");
          }
        } else {
          dungeonString.append("Žádné věci.\n");
        }
        dungeonString.append(StringUtils.rightPad("Jasnozřivost:", width));
        dungeonString.append(heroLocation.getLuminosity().toPercentage().toString());
        dungeonString.append("\n");
          dungeonString.append(StringUtils.rightPad("Odolnost:", width));
        dungeonString.append(heroLocation.getLightPermittivity().toString());
        dungeonString.append("\n");
        dungeonString.append(StringUtils.rightPad("Blokované vstupy:", width));
        dungeonString.append(heroLocation.getBlockedEntrances().toString());
        dungeonString.append("\n");
        Writer.write(dungeonString);
      }
    });
    commandSet.addCommand(new Command("mapa", "Vytvoří mapu co nejúplnější.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        WorldMapWriter.writeDebugMap();
      }
    });
    commandSet.addCommand(new Command("dat", "Dává věci postavě.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        if (arguments.length != 0) {
          World world = Game.getGameState().getWorld();
          Date date = world.getWorldDate();
          try {
            Id id = new Id(arguments[0].toUpperCase(Locale.ENGLISH));
            if (world.getItemFactory().canMakeItem(id)) {
              Item item = world.getItemFactory().makeItem(id, date);
              Writer.write("Věc byla úspěšně vytvořena.");
              Hero hero = Game.getGameState().getHero();
              if (hero.getInventory().simulateItemAddition(item) == SimulationResult.SUCCESSFUL) {
                hero.addItem(item);
              } else {
                hero.getLocation().addItem(item);
                Writer.write("Věc nelze přidat do vašeho inventáře. Byl tedy přidán na aktuální umístění.");
              }
              Engine.refresh(); // Set the game state to unsaved after adding an item to the world.
            } else {
              Writer.write("Věc nelze z důvodu omezení vytvořit.");
            }
          } catch (IllegalArgumentException invalidPreset) {
            Writer.write("Věc nelze vytvořit.");
          }
        } else {
          Messenger.printMissingArgumentsMessage();
        }
      }
    });
    commandSet.addCommand(new Command("ulozeno", "Testuje, zda je hra uložena nebo ne.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        if (Game.getGameState().isSaved()) {
          Writer.write("Hra je uložena.");
        } else {
          Writer.write("Tento stav hry není uložen.");
        }
      }
    });
    commandSet.addCommand(new Command("tvorit", "Vytvoří stvůru.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        if (arguments.length != 0) {
          for (String argument : arguments) {
            Id givenId = new Id(argument.toUpperCase(Locale.ENGLISH));
            World world = Game.getGameState().getWorld();
            Creature creature = world.getCreatureFactory().makeCreature(givenId, world);
            if (creature != null) {
              Game.getGameState().getHero().getLocation().addCreature(creature);
              Writer.write("Vytvořeno " + creature.getName() + ".");
              Engine.refresh(); // Set the game state to unsaved after adding a creature to the world.
            } else {
              Writer.write(givenId + " neodpovídá žádnému známému tvoru.");
            }
          }
        } else {
          Messenger.printMissingArgumentsMessage();
        }
      }
    });
    commandSet.addCommand(new Command("cas", "Zapíše informace o aktuálním čase.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        Writer.write(Game.getGameState().getWorld().getWorldDate().toString());
      }
    });
    commandSet.addCommand(new Command("cekat", "Dás si na čas.") {
      @Override
      public void execute(@NotNull String[] arguments) {
        DebugWaitParser.parseDebugWait(arguments);
      }
    });
    return commandSet;
  }

  static boolean hasCommandSet(String identifier) {
    return commandSetMap.containsKey(identifier);
  }

  static CommandSet getCommandSet(String identifier) {
    return commandSetMap.get(identifier);
  }

}
