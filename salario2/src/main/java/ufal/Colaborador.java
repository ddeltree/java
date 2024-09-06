package ufal;

public class Colaborador {
    private String nome;
    private String cpf;
    private String setor;
    private double salario;

    public Colaborador(String nome, String cpf, String setor) {
        super();
        this.nome = nome;
        this.cpf = cpf;
        this.setor = setor;
    }

    public double getSalario() {
        return salario;
    }

    public double calculaSalario() {
        return salario;
    }

    public void setSalario(double sal) {
        if (salario < 0) {
            return;
        }
        salario = sal;
    }
}
