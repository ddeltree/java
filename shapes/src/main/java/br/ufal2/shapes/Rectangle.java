package br.ufal2.shapes;

public class Rectangle extends Shape {
    protected float base;
    protected float height;

    public Rectangle(float base, float height) {
        super();
        this.base = base;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return base * height;
    }
}
