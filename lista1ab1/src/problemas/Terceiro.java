package problemas;

import java.util.Arrays;
import java.util.stream.Collectors;

import utils.InputHandler;

// Faça um programa que leia um número inteiro n e indique todos os múltiplos de 5 até n.
public class Terceiro {
  public Terceiro() {
    var scanner = new InputHandler();
    System.out.println("--- Multiplicador por 5 de Inteirabilidade Infinita ---");
    var n = scanner.askForInt("n = ");
    int maxFiveMultipleUnderN = n - (n % 5);
    int countMultiplesUnderN = maxFiveMultipleUnderN / 5 + 1;
    int[] multiplesOfFiveUnderN = new int[countMultiplesUnderN];
    for (int i = 0; i < countMultiplesUnderN; i++)
      multiplesOfFiveUnderN[i] = (i * 5);

    String result = Arrays.stream(multiplesOfFiveUnderN)
        .mapToObj(String::valueOf)
        .collect(Collectors.joining(", "));
    System.out.println(result);
  }
}
