package com.example.diplombackend.model.figures.Vector;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Point.Point;
import lombok.Data;

import java.util.List;

import static com.example.diplombackend.ListExtension.*;
import static com.example.diplombackend.service.TextParser.splitByRegex;

@Data
public class Vector implements Figure {
    private String name;
    private String equation;
    private Point A;
    private Point B;

    public double getVectorLength() {
        List<String> catets = splitByRegex(equation, "; ");
        double catetA = Double.parseDouble(first(catets).replaceAll("\\(", ""));
        double catetB = Double.parseDouble(last(catets).replaceAll("\\)", ""));
        return Math.sqrt(catetA * catetA + catetB * catetB);
    }
}
