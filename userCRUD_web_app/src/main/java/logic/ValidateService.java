package logic;

import org.apache.log4j.Logger;
import persistent.MemoryStore;
import persistent.User;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Validation layer of the application.
 */
public class ValidateService {

    private final static Logger LOGGER = Logger.getLogger(ValidateService.class);

    private static ValidateService singletonInstance =
            new ValidateService();

    private ValidateService() {
    }

    /**
     * Glodal access point to the object.
     *
     * @return
     */
    public static ValidateService getSingletonInstance() {
        return singletonInstance;
    }

    private final MemoryStore memoryStore = MemoryStore.getSingletonInstance();

    /**
     * Checks if user is null or if it is already exists. If so - user will no be added.
     *
     * @param user to add.
     * @return true if user was successfully added.
     */
    public boolean add(User user) {
        boolean result = false;
        if (user != null && memoryStore.findByID(user.getId()) == null) {
            memoryStore.add(user);
            result = true;
        }
        return result;
    }

    /**
     * Before updating the existing user checks if new fields are not null. If so -
     * they will not be updated.
     *
     * @param key   - user_id
     * @param name  - user's new name
     * @param login - user's new login
     * @param email - user's new email
     * @return true if user was successfully edited.
     */
    public boolean update(String key, String name, String login, String email) {
        boolean result = false;
        User userToUpdate = findByID(key);
        if (key != null && userToUpdate != null) {
            if (name != null) {
                userToUpdate.setName(name);
            }
            if (login != null) {
                userToUpdate.setLogin(login);
            }
            if (email != null) {
                userToUpdate.setEmail(email);
            }
            result = true;
        }
        return result;
    }

    /**
     * Check if user exists before deleting.
     *
     * @param userID
     * @return true if user was successfully deleted.
     */
    public boolean delete(String userID) {
        boolean result = false;
        if (userID != null && findByID(userID) != null) {
            memoryStore.delete(userID);
            result = true;
        }
        return result;
    }

    /**
     * Checks if there is any users.
     *
     * @return users or 'none users exist' message.
     */
    public String findAll() {
        ConcurrentHashMap<String, User> map = memoryStore.findAll();
        String result;
        if (map.size() > 0) {
            result = map.toString();
        } else {
            result = "Users  were not found. You can add one.";
        }
        return result;
    }

    /**
     * Returns user found by id.
     *
     * @param key - user_id.
     * @return user or null in case user was not found.
     */
    public User findByID(String key) {
        return memoryStore.findByID(key);
    }
}
