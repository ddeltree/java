package ufal;

import java.util.Scanner;

public class Principal {

    public static void main(String[] args) throws Exception {
        Colaborador colaborador;

        Scanner scanner = new Scanner(System.in);

        System.out.print("Nome = ");
        String nome = scanner.nextLine().toLowerCase().strip();
        System.out.print("CPF = ");
        String cpf = scanner.nextLine().toLowerCase().strip();
        System.out.print("Setor = ");
        String setor = scanner.nextLine().toLowerCase().strip();
        System.out.print("Salário = ");
        double salario = Double.parseDouble(scanner.nextLine().toLowerCase().strip());

        System.out.print("Tipo de colaborador (vendedor / tecnico) = ");
        String tipo = scanner.nextLine().toLowerCase().strip();
        if (tipo.equals("vendedor")) {
            colaborador = new Vendedor(nome, cpf, setor);
            colaborador.setSalario(salario);

            System.out.print("Comissão = ");
            double comissao = Double.parseDouble(scanner.nextLine().toLowerCase().strip());
            ((Vendedor) colaborador).setComissao(comissao);
        } else {
            colaborador = new Tecnico(nome, cpf, setor);
            colaborador.setSalario(salario);

            System.out.print("Função = ");
            String funcao = scanner.nextLine().toLowerCase().strip();
            ((Tecnico) colaborador).setFuncao(funcao);
        }
        scanner.close();
        System.out.println("Salário final = R$" + colaborador.calculaSalario());
    }
}
