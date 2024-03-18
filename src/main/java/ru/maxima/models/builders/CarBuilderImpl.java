package ru.maxima.models.builders;

public class CarBuilderImpl implements CarBuilder{

    private String body;
    private String wheels;
    private String interior;
    private String painting;
    @Override
    public CarBuilder makeBody(String body) {
        this.body = body;
        return this;
    }

    @Override
    public CarBuilder makeWheels(String wheels) {
        this.wheels = wheels;
        return this;
    }

    @Override
    public CarBuilder makeInterior(String interior) {
        this.interior = interior;
        return this;
    }

    @Override
    public CarBuilder makePainting(String painting) {
        this.painting = painting;
        return this;
    }

    @Override
    public Car build() {
        Car car = new Car(body, wheels, interior, painting);
        return car;
    }
}
