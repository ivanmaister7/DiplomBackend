package com.example.diplombackend.model.figures;

public interface Figure {
    String name = "";
    default String getName() { return name; }
}
