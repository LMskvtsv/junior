package logic;

import org.apache.log4j.Logger;
import persistent.Countries;
import persistent.Country;
import persistent.DBStore;
import persistent.Role;
import persistent.Store;
import persistent.User;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Validation layer of the application.
 */
public class ValidateService {

    private final static Logger LOGGER = Logger.getLogger(ValidateService.class);

    private final static ValidateService VALIDATE_SERVICE =
            new ValidateService();

    private ValidateService() {
    }

    /**
     * Glodal access point to the object.
     *
     * @return
     */
    public static ValidateService getValidateService() {
        return VALIDATE_SERVICE;
    }

    private final Store store = DBStore.getDbStore();

    /**
     * Checks if user is null or if it is already exists. If so - user will no be added.
     *
     * @param user to add.
     * @return true if user was successfully added.
     */
    public boolean add(User user) {
        boolean result = false;
        if (user != null && store.findByID(user.getId()) == null) {
            if (validateFields(user)) {
                store.add(user);
                result = true;
            }
        } else {
            LOGGER.info(String.format("User is null or user with id '%s' already exists.", user.getId()));
        }
        return result;
    }

    private boolean validateFields(User user) {
        boolean result = true;
        if (user.getPassword() == null
                || user.getLogin() == null
                || user.getEmail() == null
                || user.getName() == null
                || user.getRole() == null
                || user.getCity() == null
                || user.getCountry() == null
                || user.getId() == null
                || user.getCreateDate() == null) {
            result = false;
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
    public boolean update(String key, String name, String login, String email, String roleID, String country, String city) {
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
            if (roleID != null) {
                userToUpdate.setRole(new Role(Integer.valueOf(roleID)));
            }
            if (country != null) {
                userToUpdate.setCountry(country);
            }
            if (city != null) {
                userToUpdate.setCity(city);
            }
            store.update(userToUpdate);
            result = true;
        } else {
            LOGGER.info("Id = null or user with id '%s' not found.");
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
            store.delete(userID);
            result = true;
        } else {
            LOGGER.info("Id = null or user with id '%s' not found.");
        }
        return result;
    }

    /**
     * Checks if there is any users.
     *
     * @return users.
     */
    public Map<String, User> findAll() {
        return store.findAll();
    }

    /**
     * Returns user found by id.
     *
     * @param key - user_id.
     * @return user or null in case user was not found.
     */
    public User findByID(String key) {
        return (User) store.findByID(key);
    }

    public User getUserByCredentials(String login, String password) {
        User user = null;
        if (login != null && password != null) {
            user = (User) store.findByCredentials(login, password);
        }
        return user;
    }

    public CopyOnWriteArrayList<Country> getCountries() {
        Countries countries = new Countries();
        countries.loadCountries();
        return countries.getCountries();
    }
}
