package ru.job4j.tracker;

import java.util.ArrayList;

class DeleteItem extends BaseAction {

    public DeleteItem(int key, String name) {
        super(key, name);
    }

    public void execute(Input input, Tracker tracker) {
        System.out.println("------------ Удаление заявки --------------");
        String id = input.ask("Введите идентификатор заявки, которую нужно удалить:");
        System.out.println("Введен идентификатор: " + id);
        if (tracker.delete(id)) {
            System.out.println("------------ Заявка с Id : " + id + " удалена -----------");
        } else {
            System.out.println("------------ Заявка с Id : " + id + " не найдена -----------");
        }
    }
}

public class MenuTracker {

    private ArrayList<BaseAction> actions = new ArrayList<>();
    private Input input;
    private Tracker tracker;

    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    public void fillActions() {
        actions.add(new AddItem(0, "Добавление новой заявки"));
        actions.add(new ShowAll(1, "Показать все заявки"));
        actions.add(new EditItem(2, "Отредактировать заявку"));
        actions.add(new DeleteItem(3, "Удалить заявку"));
        actions.add(new FindByID(4, "Найти заявку по идентификатору"));
        actions.add(new FindByName(5, "Найти заявку по имени"));
    }

    public void show() {
        for (BaseAction action : actions) {
            System.out.println(new StringBuilder().append(action.key()).append(". ").append(action.info()));
        }
    }

    public void select(int key) {
        actions.get(key).execute(input, tracker);
    }

    private class AddItem extends BaseAction {

        public AddItem(int key, String info) {
            super(key, info);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Добавление новой заявки --------------");
            String name = input.ask("Введите имя заявки :");
            System.out.println("Введено имя: " + name);
            String desc = input.ask("Введите описание заявки :");
            System.out.println("Введено описание: " + desc);
            Item item = new Item(name, desc);
            tracker.add(item);
            System.out.println("------------ Добавлена новая заявка с Id : " + item.getId() + " -----------");
        }
    }

    private static class ShowAll extends BaseAction {

        public ShowAll(int key, String info) {
            super(key, info);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Все существущие заявки --------------");
            ArrayList<Item> result = tracker.findAll();
            if (result.size() > 0) {
                for (Item i : result) {
                    System.out.println("Завка №" + (result.indexOf(i) + 1) + ":");
                    System.out.println("- ай ди:" + i.getId());
                    System.out.println("- имя:" + i.getName());
                    System.out.println("- описание:" + i.getDesc());
                }
            } else {
                System.out.println("Трекер пуст, как осенний куст :)");
            }
        }
    }

    private class EditItem extends BaseAction {

        public EditItem(int key, String info) {
            super(key, info);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Редактирование заявки --------------");
            String id = input.ask("Введите идентификатор заявки, которую нужно отредактировать:");
            System.out.println("Введен идентификатор: " + id);
            String name = input.ask("Введите новое имя заявки :");
            System.out.println("Введено имя: " + name);
            String desc = input.ask("Введите новое описание заявки :");
            System.out.println("Введено описание: " + desc);
            Item item = new Item(name, desc);
            item.setId(id);
            if (tracker.replace(id, item)) {
                System.out.println("------------ Заявка с Id : " + id + " отредактирована -----------");
            } else {
                System.out.println("------------ Заявка с Id : " + id + " не найдена -----------");
            }
        }
    }

    private class FindByID extends BaseAction {

        public FindByID(int key, String info) {
            super(key, info);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Поиск заявки по идентификатору --------------");
            String id = input.ask("Введите идентификатор заявки, которую нужно найти:");
            System.out.println("Введен идентификатор: " + id);
            Item result = tracker.findById(id);
            if (result != null) {
                System.out.println("------------ Заявка с Id : " + id + " найдена -----------");
                System.out.println("Имя:" + result.getName());
                System.out.println("Описание:" + result.getDesc());
            } else {
                System.out.println("------------ Заявка с Id : " + id + " не найдена -----------");
            }
        }
    }

    private class FindByName extends BaseAction {

        public FindByName(int key, String info) {
            super(key, info);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Поиск заявки по имени --------------");
            String name = input.ask("Введите имя заявки, которую нужно найти:");
            System.out.println("Введено имя: " + name);
            ArrayList<Item> result = tracker.findByName(name);
            System.out.println("------------ Количество заявок с заданным именем: " + result.size() + " ------------");
            if (result.size() > 0) {
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
    }
}
