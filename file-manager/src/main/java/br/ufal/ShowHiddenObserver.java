package br.ufal;

import java.util.*;

abstract class ShowHiddenObserver {
  private static boolean showHidden = true;
  private static List<Listener> listeners = new ArrayList<Listener>();

  public static void addListener(Listener toAdd) {
    listeners.add(toAdd);
  }

  public static void toggleShowHidden() {
    showHidden = !showHidden;
    System.out.println("Hide: " + showHidden);
    for (Listener hl : listeners)
      hl.onToggleHidden(showHidden);
  }

  public static boolean shouldHide() {
    return showHidden;
  }
}

interface Listener {
  void onToggleHidden(boolean showHidden);
}