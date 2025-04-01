package utils;

import java.util.Scanner;

public class InputHandler {
  private static final Scanner scanner = new Scanner(System.in);

  public InputHandler() {
  }

  public String askForString(String prompt) {
    System.out.print(prompt);
    return scanner.nextLine();
  }

  public int askForInt(String prompt) {
    System.out.print(prompt);
    int value = scanner.nextInt();
    scanner.nextLine();
    return value;
  }

  public float askForFloat(String prompt) {
    System.out.print(prompt);
    float value = scanner.nextFloat();
    scanner.nextLine();
    return value;
  }
}
