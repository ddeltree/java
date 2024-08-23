package br.ufal2;

import br.ufal2.shapes.*;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        Shape[] shapes = new Shape[] { new Circle(5), new Rectangle(5, 4) };
        for (Shape shape : shapes) {
            System.out.println(shape.calculateArea());
        }
    }
}
