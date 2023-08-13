package com.example.trainDelayMonitor.objects;

public enum AvailableTrains {
  ONE("1"),
  TWO("2"),
  THREE("3"),
  FOUR("4"),
  FIVE("5"),
  SIX("6"),
  SEVEN("7"),
  A("A"),
  B("B"),
  C("C"),
  D("D"),
  E("E"),
  F("F"),
  G("G"),
  J("J"),
  L("L"),
  M("M"),
  N("N"),
  Q("Q"),
  R("R"),
  S("S"),
  W("W"),
  Z("Z"),
  FS("FS"),
  RS("RS"),
  SI("SI");

  public String line;

  AvailableTrains(String line) {
    this.line = line;
  }

  public static boolean checkLineExists(String line) {
    for(AvailableTrains train: AvailableTrains.values()) {
      if(train.line.equals(line)) return true;
    }
    return false;
  }
}
