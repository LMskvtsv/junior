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

    @Override
    public int ask(String question, int[] range) throws MenuOutException {
        int answer = Integer.valueOf(userAnswers[index++]);
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
