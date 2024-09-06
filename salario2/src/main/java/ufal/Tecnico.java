package ufal;

public class Tecnico extends Colaborador {

  public Tecnico(String nome, String cpf, String setor) {
    super(nome, cpf, setor);
  }

  @Override
  public double calculaSalario() {
    return 89.0 / 100 * getSalario();
  }

  private String funcao;

  public String getFuncao() {
    return funcao;
  }

  public void setFuncao(String funcao) {
    this.funcao = funcao;
  }

}
