package persistent;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Persistent layer (in memory).
 */
public class MemoryStore implements Store<String, User> {

    private final static Logger LOGGER = Logger.getLogger(MemoryStore.class);
    private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    private final static MemoryStore MEMORY_STORE =
            new MemoryStore();

    private MemoryStore() {
    }

    public static MemoryStore getMemoryStore() {
        return MEMORY_STORE;
    }

    /**
     * Adds user into map collection.
     *
     * @param user - user to add.
     */
    @Override
    public void add(User user) {
        User u = users.put(user.getId(), user);
        LOGGER.info(String.format("User %s was added.", user.toString()));
    }

    /**
     * Updates user in map collection.
     *
     * @param user - user to update.
     */
    @Override
    public void update(User user) {
        User u = users.replace(user.getId(), user);
        LOGGER.info(String.format("User %s was updated.", u.toString()));
    }

    /**
     * Deletes user from map collection.
     *
     * @param userID - user id to delete.
     */
    @Override
    public void delete(String userID) {
        User u = users.remove(userID);
        LOGGER.info(String.format("User %s was deleted.", u.toString()));
    }

    /**
     * Returns all users.
     *
     * @return collection with users.
     */
    @Override
    public ConcurrentHashMap<String, User> findAll() {
        return new ConcurrentHashMap<>(users);
    }

    /**
     * Find user by user_id.
     *
     * @param key - user_id.
     * @return user or null.
     */
    @Override
    public User findByID(String key) {
        return users.get(key);
    }

    @Override
    public User findByCredentials(String login, String password) {
        User user = null;
        for (Map.Entry<String, User> entry : users.entrySet()) {
            if (entry.getValue().getLogin().equals(login) && entry.getValue().getPassword().equals(password)) {
                user = entry.getValue();
                break;
            }
        }
        return user;
    }
}
