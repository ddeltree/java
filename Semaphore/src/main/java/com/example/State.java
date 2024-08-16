package com.example;

public enum State {
    GREEN(0),
    YELLOW(1),
    RED(2);

    // Field to store the integer code
    private final int code;

    // Constructor to set the integer code
    State(int code) {
        this.code = code;
    }

    // Getter method to retrieve the integer code
    public int getCode() {
        return code;
    }

    // Static method to get an enum instance by its integer code
    public static State fromCode(int code) {
        for (State status : State.values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
