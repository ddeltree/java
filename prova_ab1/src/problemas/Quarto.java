package problemas;

/*
 * Crie uma classe Semáforo que possua os atributos cor (String) e ligado (boolean) e também deve existir um
construtor que inicialize o semáforo em uma das cores validas. Além disso, deve existir uma operação estado"que
imprime uma String indicando a cor atual do semáforo e uma operação transição", obedecendo encapsulamento
e as seguintes restrições:
A) o estado do semáforo deve ser representado apenas por cores válidas (vermelho, verde ou amarelo)
B) apenas transições válidas devem ocorrer (de verde para amarelo, de amarelo para vermelho ou de vermelho
para verde)
Obs.: Você pode denir um intervalo de tempo para que as transições ocorram ou denir um menu onde o usuário
solicite uma transição.
 */

public class Quarto {
  Semaforo semaforo = new Semaforo();

  public Quarto() {
    solve();
  }

  void solve() {
    while (semaforo.ligado) {
      System.out.println(semaforo.getColor().name());
      semaforo.next();
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
