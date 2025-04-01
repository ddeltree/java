package problemas;

public class Semaphore {
  private State status = State.VERDE;

  public Semaphore() {
  }

  public Semaphore(State state) {
    super();
    status = state;
  }

  public void next() {
    int newCode = (status.getCode() + 1) % 3;
    status = State.fromCode(newCode);
  }

  public State getColor() {
    return status;
  }
}
