package org.mafagafogigante.labyrint.entity.creatures;

/**
 * HealthState enum that defines the six stages of healthiness.
 */
public enum HealthState {

  UNINJURED("Bez zranění"),
  BARELY_INJURED("Trochu zraněný"),
  INJURED("Zraněný"),
  BADLY_INJURED("Těžce zraněn"),
  NEAR_DEATH("Blízko smrti"),
  DEAD("Mrtvý");

  private final String stringRepresentation;

  HealthState(String stringRepresentation) {
    this.stringRepresentation = stringRepresentation;
  }

  @Override
  public String toString() {
    return stringRepresentation;
  }

}
