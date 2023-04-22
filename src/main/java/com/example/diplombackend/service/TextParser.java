package com.example.diplombackend.service;

import java.util.Arrays;
import java.util.List;

public class TextParser {
    static List<String> split(String text) {
        return splitByRegex(text, " ");
    }
    static List<String> splitByRegex(String text, String regex) {
        return Arrays.stream(text.split(regex)).toList();
    }
}
