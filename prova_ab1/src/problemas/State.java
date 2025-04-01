package problemas;

public enum State {
    VERDE(0),
    AMARELO(1),
    VERMELHO(2);

    private final int code;

    State(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static State fromCode(int code) {
        for (State status : State.values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
