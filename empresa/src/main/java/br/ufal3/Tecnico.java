package br.ufal3;

public class Tecnico extends Colaborador {

  private String funcao;

  public String getFuncao() {
    return funcao;
  }

  public void setFuncao(String funcao) {
    this.funcao = funcao;
  }

  @Override
  public double calculaSalario() {
    return 89.0 / 100 * getSalario();
  }
}
