package com.example.diplombackend.model.figures.Line;

import com.example.diplombackend.model.figures.Figure;
import lombok.Data;

@Data
public class ParallelLine extends Line implements Figure {
    private Figure base;

    @Override
    public String toString() {
        return super.toString()
                .substring(0, super.toString().length() - 1)
                .replaceAll("Line", "ParallelLine") +
                ", base=" + base +
                ')';
    }
}
