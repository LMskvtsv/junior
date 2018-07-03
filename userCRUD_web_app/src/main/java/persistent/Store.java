package persistent;

import java.util.Map;

public interface Store<K, V> {

     void add(V value);
     void update(V value);
     void delete(K key);
     Map<K, V> findAll();
     V findByID(K key);
}
