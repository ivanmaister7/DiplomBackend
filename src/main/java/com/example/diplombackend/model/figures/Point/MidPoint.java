package com.example.diplombackend.model.figures.Point;

import lombok.Data;

@Data
public class MidPoint extends Point {
    private Point A;
    private Point B;

    @Override
    public String toString() {
        return super.toString().replaceAll("\\)", ", ") +
                "A=" + A +
                ", B=" + B +
                ')';
    }
}
