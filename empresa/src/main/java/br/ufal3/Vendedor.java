package br.ufal3;

public class Vendedor extends Colaborador {
  private double comissao;

  public double getComissao() {
    return comissao;
  }

  public void setComissao(double com) {
    comissao = com;
  }

  @Override
  public double calculaSalario() {
    return (89 / 100) * (salario + comissao);
  }
}
