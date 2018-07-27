package dbcontrol;

import java.util.List;

public interface Controller<E> {
    List<E> getAll();

    E create(E entity);

    void update(E entity);

}
