package com;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        List<String> strings = new ArrayList<>();

        strings.add("ok1");
        strings.add("ok2");
        strings.add("ok3");
        strings.add("ok4");
        strings.add("ok5");
        strings.add("ok6");
        strings.add("ok7");

        strings.forEach((string) -> System.out.println(string));
    }
}
