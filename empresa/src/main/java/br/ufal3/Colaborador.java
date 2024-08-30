package br.ufal3;

public class Colaborador {
    protected String nome;
    protected String cpf;
    protected double salario;
    protected String setor;

    public double getSalario() {
        return salario;
    }

    public double calculaSalario() {
        return salario;
    }

    public void setSalario(double sal) {
        salario = Math.max(0, sal);
    }
}
