package problemas;

import utils.InputHandler;

// Faça um programa que leia o peso e a altura de uma pessoa e calcule o imc (imc = peso / altura^2 )
public class Primeiro {
  final float peso;
  final float altura;

  public Primeiro() {
    InputHandler scanner = new InputHandler();
    System.out.println("--- Calculadora de IMC ---");
    peso = scanner.askForFloat("Peso: ");
    altura = scanner.askForFloat("Altura: ");
    float imc = calcularIMC(peso, altura);
    System.out.println("IMC = " + imc);
  }

  float calcularIMC(float peso, float altura) {
    return peso / (altura * altura);
  }

}
