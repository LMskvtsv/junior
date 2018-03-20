package ru.job4j.tracker;

public class ValidateInput extends ConsoleInput {
    @Override
    public int ask(String question, int[] range) {
        System.out.println(question);
        try {
            return super.ask(question, range);
        } catch (IllegalArgumentException ex1) {
            System.out.println(ex1.getMessage());
        } catch (MenuOutException ex2) {
            System.out.println(ex2.getMessage());
        }
        return -1;
    }
}
