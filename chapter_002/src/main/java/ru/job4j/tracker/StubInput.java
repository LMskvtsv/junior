package ru.job4j.tracker;

public class StubInput implements Input {

    public String[] userAnswers;
    private int index = 0;

    public StubInput(String[] userAnswers) {
        this.userAnswers = userAnswers;
    }

    @Override
    public String ask(String question) {
        System.out.println(question);
        return userAnswers[index++];
    }
}
