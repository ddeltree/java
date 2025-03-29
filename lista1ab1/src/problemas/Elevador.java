package problemas;

import utils.InputHandler;

// Crie uma classe denominada Elevador para armazenar as informações de um elevador dentro de um prédio. A classe deve armazenar o andar atual (térreo = 0), total de andares no prédio, excluindo o térreo, capacidade do elevador, e quantas pessoas estão presentes nele. 
// Deve existir um construtor padrao ue inicializa os atributos com valores padrao e um construtor que recebe como parâmetro os dados de inicialização.Alem disso,devem existir os métodos:Entra:para acrescentar uma pessoa no elevador(só deve acrescentar se ainda houver espaço);Sai:para remover uma pessoa do elevador(só deve remover se houver alguém dentro dele);Sobe:para subir um andar(não deve subir se já estiver no último andar);Desce:para descer um andar(não deve descer se já estiver no térreo);Finaliza:terminar o programa.Encapsular todos os atributos da classe.Criar um Loop com um menu solicitando qual opção acima deve ser executada e,a cada repetição,informar os dados de andar e quantidade de pessoas.

public class Elevador {
  int andarAtual = 0;
  int pessoasPresentes = 0;
  public boolean isWorking = true;
  final int andaresTotais;
  final int capacidade;

  public Elevador(int capacidade, int andaresTotais) {
    this.capacidade = Math.max(1, capacidade);
    this.andaresTotais = Math.max(1, andaresTotais);
  }

  public void entra() {
    if (pessoasPresentes < capacidade) {
      pessoasPresentes++;
    }
  }

  public void sai() {
    if (pessoasPresentes > 0)
      pessoasPresentes--;
  }

  public void sobe() {
    if (andarAtual < andaresTotais)
      andarAtual++;
  }

  public void desce() {
    if (andarAtual > 0)
      andarAtual--;
  }

  public void finaliza() {
    isWorking = false;
  }

  public void work() {
    var scanner = new InputHandler();
    String msg = String.format("""
        +----- ELEVADOR -----+-------------------
        |  1. Entra          |
        |  2. Sai            |   Andar %d/%d
        |  3. Sobe           |   Pessoas %d/%d
        |  4. Desce          |
        +--------------------+-------------------
        """, andarAtual, andaresTotais, pessoasPresentes, capacidade);
    System.out.println(msg);
    int opt = scanner.askForInt("\t>>> ");
    switch (opt) {
      case 1:
        entra();
        break;
      case 2:
        sai();
        break;
      case 3:
        sobe();
        break;
      case 4:
        desce();
        break;
      default:
        isWorking = false;
        break;
    }
  }
}
