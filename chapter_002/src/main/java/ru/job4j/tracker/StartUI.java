package ru.job4j.tracker;

public class StartUI {
    /**
     * Константа меню для добавления новой заявки.
     */
    private static final String ADD = "0";

    /**
     * Константа меню  - показать все заявки.
     */
    private static final String SHOW_ALL = "1";

    /**
     * Константа меню  для редактирования заявки.
     */
    private static final String EDIT = "2";

    /**
     * Константа меню для удаления заявки.
     */
    private static final String DELETE = "3";

    /**
     * Константа меню для поиска заявки по идентификатору.
     */
    private static final String FIND_BY_ID = "4";

    /**
     * Константа меню для поиска заявки по имени.
     */
    private static final String FIND_BY_NAME = "5";

    /**
     * Константа для выхода из цикла.
     */
    private static final String EXIT = "6";
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
        boolean exit = false;
        while (!exit) {
            this.showMenu();
            String answer = this.input.ask("Введите пункт меню : ");
            if (ADD.equals(answer)) {
                this.createItem();
            } else if (SHOW_ALL.equals(answer)) {
                this.showAllItems();
            } else if (EDIT.equals(answer)) {
                this.editItem();
            } else if (DELETE.equals(answer)) {
                this.deleteItem();
            } else if (FIND_BY_ID.equals(answer)) {
                this.findItemByID();
            } else if (FIND_BY_NAME.equals(answer)) {
                this.findItemByName();
            } else if (EXIT.equals(answer)) {
                System.out.println("Good bye, have a nice day!");
                exit = true;
            }
        }
    }

    /**
     * Метод реализует добавленяи новый заявки в хранилище.
     */
    private void createItem() {
        System.out.println("------------ Добавление новой заявки --------------");
        String name = this.input.ask("Введите имя заявки :");
        System.out.println("Введено имя: " + name);
        String desc = this.input.ask("Введите описание заявки :");
        System.out.println("Введено описание: " + desc);
        Item item = new Item(name, desc);
        this.tracker.add(item);
        System.out.println("------------ Добавлена новая заявка с Id : " + item.getId() + " -----------");
    }

    /**
     * Метод реализует вывод всех заявок в консоль.
     */
    private void showAllItems() {
        System.out.println("------------ Все существущие заявки --------------");
        Item[] result = this.tracker.findAll();
        if (result.length > 0) {
            for (int i = 0; i < result.length; i++) {
                System.out.println("Завка №" + (i + 1) + ":");
                System.out.println("- ай ди:" + result[i].getId());
                System.out.println("- имя:" + result[i].getName());
                System.out.println("- описание:" + result[i].getDesc());
            }
        } else {
            System.out.println("Трекер пуст, как осенний куст :)");
        }
    }

    /**
     * Метод реализует редактирование заявки.
     */
    private void editItem() {
        System.out.println("------------ Редактирование заявки --------------");
        String id = this.input.ask("Введите идентификатор заявки, которую нужно отредактировать:");
        System.out.println("Введен идентификатор: " + id);
        String name = this.input.ask("Введите новое имя заявки :");
        System.out.println("Введено имя: " + name);
        String desc = this.input.ask("Введите новое описание заявки :");
        System.out.println("Введено описание: " + desc);
        Item item = new Item(name, desc);
        item.setId(id);
        if (this.tracker.replace(id, item)) {
            System.out.println("------------ Заявка с Id : " + id + " отредактирована -----------");
        } else {
            System.out.println("------------ Заявка с Id : " + id + " не найдена -----------");
        }
    }

    /**
     * Метод реализует удаление заявки.
     */
    private void deleteItem() {
        System.out.println("------------ Удаление заявки --------------");
        String id = this.input.ask("Введите идентификатор заявки, которую нужно удалить:");
        System.out.println("Введен идентификатор: " + id);
        if (this.tracker.delete(id)) {
            System.out.println("------------ Заявка с Id : " + id + " удалена -----------");
        } else {
            System.out.println("------------ Заявка с Id : " + id + " не найдена -----------");
        }
    }

    /**
     * Метод реализует поиск заявки.
     */
    private void findItemByID() {
        System.out.println("------------ Поиск заявки по идентификатору --------------");
        String id = this.input.ask("Введите идентификатор заявки, которую нужно найти:");
        System.out.println("Введен идентификатор: " + id);
        Item result = this.tracker.findById(id);
        if (result != null) {
            System.out.println("------------ Заявка с Id : " + id + " найдена -----------");
            System.out.println("Имя:" + result.getName());
            System.out.println("Описание:" + result.getDesc());
        } else {
            System.out.println("------------ Заявка с Id : " + id + " не найдена -----------");
        }
    }

    /**
     * Метод реализует поиск заявки.
     */
    private void findItemByName() {
        System.out.println("------------ Поиск заявки по имени --------------");
        String name = this.input.ask("Введите имя заявки, которую нужно найти:");
        System.out.println("Введено имя: " + name);
        Item[] result = this.tracker.findByName(name);
        System.out.println("------------ Количество заявок с заданным именем: " + result.length + " ------------");
        if (result.length > 0) {
            for (Item item : result) {
                if (item != null) {
                    System.out.println("Идентификатор:" + item.getId());
                    System.out.println("Описание:" + item.getDesc());
                    System.out.println("------------");
                }
            }
        } else {
            System.out.println("------------ Заявка с именем : " + name + " не найдена -----------");
        }
    }

    private void showMenu() {
        System.out.println("Меню:");
        System.out.println("0. Добавить новую заявку");
        System.out.println("1. Показать все заявки");
        System.out.println("2. Отредактировать заявку");
        System.out.println("3. Удалить заявку");
        System.out.println("4. Найти заявку по идентификатору");
        System.out.println("5. Найти заявку по имени");
        System.out.println("6. Выход");
    }

    /**
     * Запускт программы.
     *
     * @param args не используется
     */
    public static void main(String[] args) {
        new StartUI(new ConsoleInput(), new Tracker()).init();
    }
}