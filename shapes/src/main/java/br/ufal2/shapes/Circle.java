package br.ufal2.shapes;

public class Circle extends Shape {
    protected float radius;

    public Circle(float radius) {
        super();
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * Math.pow(radius, 2);
    }
}
