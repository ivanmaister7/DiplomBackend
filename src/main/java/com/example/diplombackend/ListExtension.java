package com.example.diplombackend;

import java.util.List;

public class ListExtension {
    public static <T> T last(List<T> list) {
        if (list.isEmpty())
            throw new IllegalArgumentException();
        return list.get(list.size() - 1);
    }
    public static <T> T prelast(List<T> list) {
        if (list.size() < 2)
            throw new IllegalArgumentException();
        return list.get(list.size() - 2);
    }
    public static <T> T first(List<T> list) {
        if (list.isEmpty())
            throw new IllegalArgumentException();
        return list.get(0);
    }
    public static <T> int lastIndex(List<T> list) {
        if (list.size() < 1)
            throw new IllegalArgumentException();
        return list.size() - 1;
    }
}
