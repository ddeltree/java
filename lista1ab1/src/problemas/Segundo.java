package problemas;

import utils.InputHandler;

// Faça um programa que leia uma idade e verifique a classe eleitoral da pessoa (até 16 anos não pode votar, entre 16 e 18 e maior que 65 é facultativo), entre 18 e 65 eleitor obrigatório.
public class Segundo {
  public Segundo() {
    var scanner = new InputHandler();
    System.out.println("--- Identificador Galáctico de Identificação Eleitoral ---");
    int idade = scanner.askForInt("Revoluções planetárias desde o nascimento: ");
    String classificacao = idade < 16 ? "não votante"
        : idade >= 16 && idade < 18 || idade > 65 ? "facultativo" : "obrigatório";
    System.out.println("Você se encaixa como um eleitor " + classificacao);
  }
}
