package problemas;

import utils.InputHandler;

// Faça um programa que realize a conversão de celsius para farenheight (F = C * 1.8 + 32)
public class Primeiro {
  public Primeiro() {
    solve();
  }

  void solve() {
    var scanner = new InputHandler();
    var celsius = scanner.askForFloat("Digite a temperatura em Celsius: ");
    System.out.println("Temperatura em Fahrenheit: " + solve(celsius));
  }

  double solve(double celsius) {
    return celsius * 1.8 + 32;
  }
}