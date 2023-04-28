package com.example.diplombackend.model.figures.Line;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Point.Point;
import lombok.Data;

import java.util.List;

import static com.example.diplombackend.ListExtension.first;
import static com.example.diplombackend.ListExtension.last;
import static com.example.diplombackend.service.TextParser.splitByRegex;

@Data
public class CollinearVector extends Vector implements Figure {
    private Vector collinearTo;

    @Override
    public String toString() {
        return super.toString()
                .substring(0, super.toString().length() - 1)
                .replaceAll("Vector", "CollinearVector") +
                ", collinearTo=" + collinearTo.getName() +
                ')';
    }
}
