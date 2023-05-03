package com.example.diplombackend.model.figures.Vector;

import com.example.diplombackend.model.figures.Figure;
import lombok.Data;

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
