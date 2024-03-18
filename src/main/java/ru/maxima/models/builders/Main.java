package ru.maxima.models.builders;

public class Main {
    public static void main(String[] args) {
        CarBuilder carBuilder = new CarBuilderImpl();

        Car traktor = carBuilder
                .makeBody("Kuzov traktora")
                .makeWheels("Wheels 1 meter")
                .makePainting("Blue color")
                .build();

        System.out.println(traktor);

    }
}
