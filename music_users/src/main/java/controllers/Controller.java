package controllers;

import java.util.Map;

public interface Controller<K, E> {
    Map<K, E> getAll();

    E getById(K id);

    void update(E entity);

    void delete(K id);

    E create(E entity);

    E findByCredentials(String login, String password);
}
