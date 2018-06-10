package threads;

import java.util.concurrent.ConcurrentHashMap;

public class NonBlockingCache {
    private ConcurrentHashMap<Integer, Base> cache = new ConcurrentHashMap<>();

    public void add(Base model) {
        cache.put(model.getId(), model);
    }

    public Base update(Base model) {
        return cache.computeIfPresent(model.getId(), (k, v) -> {
            int version = v.getVersion();
            if (version == v.getVersion()) {
                v.setInfo(model.getInfo());
                v.setVersion(++version);
            } else {
                throw new OptimisticException();
            }
            return v;
        });
    }

    public Base delete(Base model) {
        return cache.remove(model.getId());
    }

    @Override
    public String toString() {
        return cache.toString();
    }
}
