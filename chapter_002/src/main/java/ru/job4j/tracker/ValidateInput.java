package ru.job4j.tracker;

public class ValidateInput implements Input {

    private Input input;

    public ValidateInput(Input input) {
        this.input = input;
    }

    @Override
    public String ask(String question) {
        return this.input.ask(question);
    }

    @Override
    public int ask(String question, int[] range) {
        boolean wrongFormat = true;
        int value = -1;
        do {
            try {
                value = this.input.ask(question, range);
                wrongFormat = false;
            } catch (MenuOutException ex1) {
                System.out.println("Пожалуйста введите верный пункт меню еще раз:");
            } catch (NumberFormatException ex2) {
                System.out.println("Пожалуйста введите значение в формате целого числа:");
            }
        } while (wrongFormat);
        return value;
    }
}
