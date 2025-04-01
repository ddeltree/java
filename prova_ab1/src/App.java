import problemas.*;
import utils.InputHandler;

public class App {
    public static void main(String[] args) throws Exception {
        var scanner = new InputHandler();
        int opt = 0;
        do {
            opt = scanner.askForInt("Problema (1-4): ");
        } while (opt < 1 || opt > 4);

        switch (opt) {
            case 1:
                new Primeiro();
                break;
            case 2:
                new Segundo();
                break;
            case 3:
                new Terceiro();
                break;
            case 4:
                new Quarto();
                break;
            default:
                break;
        }
    }
}
