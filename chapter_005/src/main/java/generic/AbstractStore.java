package generic;

public abstract class AbstractStore<T extends Base> implements Store<T> {
    SimpleArray<T> array;

    public AbstractStore(int size) {
        this.array = new SimpleArray<>(size);
    }

    @Override
    public void add(T model) {
        array.add(model);
    }

    @Override
    public boolean replace(String id, T model) {
        boolean result = false;
        for (T element : array) {
            if (element.getId().equals(id)) {
                array.set(array.getIndex(element), model);
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean delete(String id) {
        boolean result = false;
        for (T element : array) {
            if (element != null && element.getId().equals(id)) {
                array.delete(array.getIndex(element));
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public T findById(String id) {
        T t = null;
        for (T element : array) {
            if (element != null && element.getId().equals(id)) {
                t = array.getValue(array.getIndex(element));
                break;
            }
        }
        return t;
    }
}
