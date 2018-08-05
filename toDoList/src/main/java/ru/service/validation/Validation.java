package ru.service.validation;

import ru.domain.Item;
import store.ItemsController;

import java.util.ArrayList;
import java.util.List;

public class Validation {
    private final ItemsController itemsController = new ItemsController();

    public Item validateAndCreate(Item item) {
        Item i = item;
        if (item != null && item.getDescription() != null && item.getCreated() != null) {
            i = itemsController.create(item);
        }
        return i;
    }

    public boolean validateAndUpdate(Item item) {
        boolean result = false;
        if (item != null && item.getId() != 0 && item.getDescription() != null && item.getCreated() != null) {
            itemsController.update(item);
            result = true;
        }
        return result;
    }

    public List<Item> getAll() {
        return new ArrayList<>(itemsController.getAll());
    }
}
