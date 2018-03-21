package ru.job4j.tracker;

public abstract class BaseAction {

    private int key;
    private String info;

    public BaseAction(int key, String info) {
        this.info = info;
        this.key = key;
    }

    public int key() {
        return key;
    }

    public String info() {
        return info;
    }

    public abstract void execute(Input input, Tracker tracker);
}
