package com.example.diplombackend.model.figures.Polygon;

public enum PolygonType {
    TRIANGLE(3),
    SQUARE(4),
    PENTAGON(5),
    HEXAGON(6),
    HEPTAGON(7),
    OCTAGON(8),
    NONAGON(9),
    DECAGON(10);
    public final int sides;
    PolygonType(int sides) {
        this.sides = sides;
    }
    public static PolygonType valueOfSides(int sides){
        for (PolygonType e : values()) {
            if (e.sides == sides) {
                return e;
            }
        }
        return null;
    }
}
