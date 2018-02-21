package ru.job4j.tracker;

import java.util.*;

public class ConsoleInput implements Input {
    Scanner scanner = new Scanner(System.in);

    @Override
    public String ask(String question) {
        System.out.println(question);
        return scanner.nextLine();
    }
}
