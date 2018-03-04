package ru.job4j.tracker;

public class StartUI {

    /**
     * Получение данных от пользователя.
     */
    private final Input input;

    /**
     * Хранилище заявок.
     */
    private final Tracker tracker;

    /**
     * Конструтор инициализирующий поля.
     *
     * @param input   ввод данных.
     * @param tracker хранилище заявок.
     */
    StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Основой цикл программы.
     */
    void init() {
        MenuTracker menu = new MenuTracker(input, tracker);
        menu.fillActions();
        do {
            menu.show();
            menu.select(Integer.valueOf(input.ask("Введите пункт меню:")));
        }
        while (!"y".equals(this.input.ask("Exit? (y/n)")));

    }
}