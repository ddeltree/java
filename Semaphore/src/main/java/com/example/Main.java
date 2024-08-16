package com.example;

public class Main {
    public static void main(String[] args) {
        semaphore();
    }

    static void semaphore() {
        Semaphore smf = new Semaphore();

        for (int i = 0; i < 10; i++) {
            System.out.println(smf.getColor());
            smf.next();
        }
    }
}