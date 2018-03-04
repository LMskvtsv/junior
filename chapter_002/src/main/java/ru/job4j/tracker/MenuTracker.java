package ru.job4j.tracker;

class DeleteItem implements UserAction {

    @Override
    public int key() {
        return 3;
    }

    @Override
    public String info() {
        return "Удалить заявку";
    }

    @Override
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

    private UserAction[] actions = new UserAction[6];
    private Input input;
    private Tracker tracker;

    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    public void fillActions() {
        actions[0] = new AddItem();
        actions[1] = new ShowAll();
        actions[2] = new EditItem();
        actions[3] = new DeleteItem();
        actions[4] = new FindByID();
        actions[5] = new FindByName();
    }

    public void show() {
        for (UserAction action : actions) {
            System.out.println(new StringBuilder().append(action.key()).append(". ").append(action.info()));
        }
    }

    public void select(int key) {
        actions[key].execute(input, tracker);
    }

    private class AddItem implements UserAction {

        @Override
        public int key() {
            return 0;
        }

        @Override
        public String info() {
            return "Добавление новой заявки";
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

    private static class ShowAll implements UserAction {

        @Override
        public int key() {
            return 1;
        }

        @Override
        public String info() {
            return "Показать все заявки";
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Все существущие заявки --------------");
            Item[] result = tracker.findAll();
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
    }

    private class EditItem implements UserAction {

        @Override
        public int key() {
            return 2;
        }

        @Override
        public String info() {
            return "Отредактировать заявку";
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

    private class FindByID implements UserAction {

        @Override
        public int key() {
            return 4;
        }

        @Override
        public String info() {
            return "Найти заявку по идентификатору";
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

    private class FindByName implements UserAction {

        @Override
        public int key() {
            return 5;
        }

        @Override
        public String info() {
            return "Найти заявку по имени";
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Поиск заявки по имени --------------");
            String name = input.ask("Введите имя заявки, которую нужно найти:");
            System.out.println("Введено имя: " + name);
            Item[] result = tracker.findByName(name);
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
    }
}
