package org.mafagafogigante.labyrint.schema.rules;

import org.mafagafogigante.labyrint.schema.JsonRule;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import org.junit.Test;

public class PeriodJsonRuleTest {

  private static final JsonRule periodJsonRule = new PeriodJsonRule();

  @Test(expected = IllegalArgumentException.class)
  public void periodJsonRuleShouldFailOnInvalidPeriodFormat() {
    JsonValue jsonValue = Json.value("1 monthss");
    periodJsonRule.validate(jsonValue);
  }

  @Test
  public void percentageJsonRuleShouldPassOnValidDaysPeriod() {
    JsonValue oneDay = Json.value("1 den");
    JsonValue twoDays = Json.value("2 dny");
    periodJsonRule.validate(oneDay);
    periodJsonRule.validate(twoDays);
  }

  @Test
  public void percentageJsonRuleShouldPassOnValidMonthPeriod() {
    JsonValue oneMonth = Json.value("1 měsíc");
    JsonValue twoMonths = Json.value("2 měsíce");
    periodJsonRule.validate(oneMonth);
    periodJsonRule.validate(twoMonths);
  }

  @Test
  public void percentageJsonRuleShouldPassOnValidYearPeriod() {
    JsonValue oneYear = Json.value("1 rok");
    JsonValue twoYears = Json.value("2 roky");
    periodJsonRule.validate(oneYear);
    periodJsonRule.validate(twoYears);
  }

}
