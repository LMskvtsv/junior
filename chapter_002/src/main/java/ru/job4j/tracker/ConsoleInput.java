package ru.job4j.tracker;


import java.util.Scanner;

public class ConsoleInput implements Input {
    Scanner scanner = new Scanner(System.in);

    @Override
    public String ask(String question) {
        System.out.println(question);
        return scanner.nextLine();
    }

    @Override
    public int ask(String question, int[] range) throws MenuOutException {
        int answer = Integer.valueOf(scanner.nextLine());
        boolean exist = false;
        for (int i : range) {
            if (i == answer) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            throw new MenuOutException("Пожалуйста введите верный пункт меню еще раз:");
        } else {
            return answer;
        }
    }
}
