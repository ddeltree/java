package problemas;

import java.util.ArrayList;
import java.util.List;

import utils.InputHandler;

// Faça uma função que leia um número inteiro e indique todos os seus divisores.
public class Terceiro {
  public Terceiro() {
    solve();
  }

  void solve() {
    var scanner = new InputHandler();
    var number = scanner.askForInt("Digite um número: ");
    List<Integer> divisores = new ArrayList<Integer>();

    for (int i = 1; i <= number; i++) {
      if (number % i == 0) {
        divisores.add(i);
      }
    }
    System.out.println(divisores);
  }
}
