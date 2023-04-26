package com.example.diplombackend.model.figures.Line;

import com.example.diplombackend.model.figures.Figure;
import lombok.Data;

@Data
public class Ray extends SingleLine implements Figure {
    public Ray(SingleLine ray) {
        setEquation(ray.getEquation());
        setName(ray.getName());
        setA(ray.getA());
        setB(ray.getB());
    }

    @Override
    public String toString() {
        return super.toString()
                .replaceAll("Line", "Ray")
                .replaceAll("A=", "begin=");
    }
}
