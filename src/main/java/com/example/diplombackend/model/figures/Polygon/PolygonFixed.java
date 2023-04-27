package com.example.diplombackend.model.figures.Polygon;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Point.Point;
import lombok.Data;
import org.springframework.security.access.method.P;

import java.util.ArrayList;
import java.util.List;

@Data
public class PolygonFixed extends Polygon implements Figure {
    public PolygonFixed(Polygon polygon) {
        this.setPoints(polygon.getPoints());
    }

    @Override
    public String toString() {
        return super.toString()
                .substring(0, super.toString().length() - 1)
                .replaceAll("Polygon","PolygonFixed") +
                ')';
    }
}
