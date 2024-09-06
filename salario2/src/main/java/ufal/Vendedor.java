package ufal;

public class Vendedor extends Colaborador {

  public Vendedor(String nome, String cpf, String setor) {
    super(nome, cpf, setor);
  }

  @Override
  public double calculaSalario() {
    return (89.0 / 100) * (getSalario() + getComissao());
  }

  private double comissao;

  public double getComissao() {
    return comissao;
  }

  public void setComissao(double com) {
    comissao = com;
  }

}
