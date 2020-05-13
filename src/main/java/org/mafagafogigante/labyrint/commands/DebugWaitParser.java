package org.mafagafogigante.labyrint.commands;

import org.mafagafogigante.labyrint.date.DungeonTimeParser;
import org.mafagafogigante.labyrint.date.Duration;
import org.mafagafogigante.labyrint.game.DungeonString;
import org.mafagafogigante.labyrint.game.Engine;
import org.mafagafogigante.labyrint.game.Game;
import org.mafagafogigante.labyrint.game.PartOfDay;
import org.mafagafogigante.labyrint.io.Writer;
import org.mafagafogigante.labyrint.util.Matches;
import org.mafagafogigante.labyrint.util.Messenger;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.util.Arrays;

/**
 * The parser of the debugging Wait command.
 */
class DebugWaitParser {

  private DebugWaitParser() {
    throw new AssertionError();
  }

  /**
   * Evaluates and returns a constant representing which syntax was used.
   */
  private static Syntax evaluateSyntax(String[] arguments) {
    if (isForSyntax(arguments)) {
      return Syntax.FOR;
    } else if (isUntilNextSyntax(arguments)) {
      return Syntax.UNTIL;
    } else {
      return Syntax.INVALID;
    }
  }

  private static boolean isForSyntax(String[] arguments) {
    return arguments.length > 1 && "for".equalsIgnoreCase(arguments[0]);
  }

  private static boolean isUntilNextSyntax(String[] arguments) {
    return arguments.length > 2 && "until".equalsIgnoreCase(arguments[0]) && "next".equalsIgnoreCase(arguments[1]);
  }

  private static void writeDebugWaitSyntax() {
    DungeonString string = new DungeonString();
    string.append("Usage: wait ");
    final Color highlightColor = Color.ORANGE;
    string.setColor(highlightColor);
    string.append("for");
    string.resetColor();
    string.append(" [amount of time] or wait ");
    string.setColor(highlightColor);
    string.append("until next");
    string.resetColor();
    string.append(" [part of the day].");
    Writer.write(string);
  }

  static void parseDebugWait(@NotNull String[] arguments) {
    Syntax syntax = evaluateSyntax(arguments);
    if (syntax == Syntax.INVALID) {
      writeDebugWaitSyntax();
    } else {
      if (syntax == Syntax.FOR) {
        String timeString = StringUtils.join(arguments, " ", 1, arguments.length);
        try {
          Duration duration = DungeonTimeParser.parseDuration(timeString);
          rollDate(duration.getSeconds());
        } catch (IllegalArgumentException badArgument) {
          Writer.write("Poskytněte malé kladné násobení a jednotky jako: „2 minuty a 10 sekund“");
        }
      } else if (syntax == Syntax.UNTIL) {
        Matches<PartOfDay> matches = Matches.findBestCompleteMatches(Arrays.asList(PartOfDay.values()), arguments[2]);
        if (matches.size() == 0) {
          Writer.write("To neodpovídalo žádné části dne.");
        } else if (matches.size() == 1) {
          rollDate(PartOfDay.getSecondsToNext(Game.getGameState().getWorld().getWorldDate(), matches.getMatch(0)));
        } else {
          Messenger.printAmbiguousSelectionMessage();
        }
      }
    }
  }

  private static void rollDate(long seconds) {
    Engine.rollDateAndRefresh(seconds);
    Writer.write("Počkejte " + seconds + " vteřin.");
  }

  private enum Syntax {FOR, UNTIL, INVALID}

}
