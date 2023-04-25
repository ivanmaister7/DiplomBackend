package com.example.diplombackend.service;

import java.util.Arrays;
import java.util.List;

public class TextParser {
    public static List<String> split(String text) {
        return splitByRegex(text, " ");
    }
    public static List<String> splitByRegex(String text, String regex) {
        return Arrays.stream(text.split(regex)).toList();
    }
}
