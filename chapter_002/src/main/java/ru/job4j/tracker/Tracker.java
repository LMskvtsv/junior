package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

public class Tracker {
    /**
     * Массив для хранение заявок.
     */
    private final ArrayList<Item> items = new ArrayList<>();
    private static final Random RND = new Random();

    /**
     * Метод реализаущий добавление заявки в хранилище
     *
     * @param item новая заявка
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        this.items.add(item);
        item.setCreated(System.currentTimeMillis());
        return item;
    }

    /**
     * Метод генерирует уникальный ключ для заявки.
     * Так как у заявки нет уникальности полей, имени и описания. Для идентификации нам нужен уникальный ключ.
     *
     * @return Уникальный ключ.
     */
    private String generateId() {
        String id = System.currentTimeMillis() + String.valueOf(RND.nextInt(100));
        return id;
    }

    /**
     * Метод полностью заменяет заявку, найденную по id на новую.
     *
     * @param id   - ай ди заявки которую нужно заменить.
     * @param item - новый айтем на который произойдет замена.
     * @return true - если была найдена заявка по ай ди, false - если заявки с заданным ай ди не было найдено.
     */
    public boolean replace(String id, Item item) {
        boolean isExist = false;
        for (Item i : items) {
            if (id.equals(i.getId())) {
                isExist = true;
                items.add(items.indexOf(i), item);
                break;
            }
        }
        return isExist;
    }

    /**
     * Метод удаляет заявку, найденную по id.
     *
     * @param id - ай ди заявки которую нужно удалить.
     * @return true - если была найдена заявка по ай ди, false - если заявки с заданным ай ди не было найдено.
     */
    public boolean delete(String id) {
        boolean isExist = false;
        ArrayList<Item> result = this.findAll();
        for (Item i : result) {
            if (id.equals(i.getId())) {
                isExist = true;
                items.remove(i);
                break;
            }
        }
        return isExist;
    }

    /**
     * Метод отбирает все существующие заявки.
     *
     * @return - возвращает все заявки, которые были заполнены.
     */
    public ArrayList<Item> findAll() {
        return items;
    }

    /**
     * Метод выполняет поиск заявки по ее имени.
     * Так как поле имя - не уникальное, то может быть найдено несколько заявок.
     *
     * @param key - имя заявки которую нужно найти.
     * @return item[] - массив найденных заявок.
     */
    public ArrayList<Item> findByName(String key) {
        ArrayList<Item> all = findAll();
        ArrayList<Item> result = new ArrayList<>();
        for (Item item : all) {
            if (item != null && (key.equals(item.getName()))) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Метод выполняет поиск заявки по ее id.
     *
     * @param id - id заявки которую нужно найти.
     * @return найденную заявку.
     */
    public Item findById(String id) {
        Item result = null;
        for (Item item : this.items) {
            if (item != null && (id.equals(item.getId()))) {
                result = item;
                break;
            }
        }
        return result;
    }
}

