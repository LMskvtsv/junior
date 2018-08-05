package logic;

import dbcontrol.UserControl;
import ru.domain.User;
import org.apache.log4j.Logger;

import java.util.List;

public class ValidateServiceUsers {
    private final static Logger LOGGER = Logger.getLogger(ValidateServiceUsers.class);

    private final UserControl userControl = new UserControl();

    public boolean create(User user) {
        boolean result = validate(user);
        if (result) {
            userControl.create(user);
        }
        return result;
    }

    public boolean update(User user) {
        boolean result = validate(user);
        if (result) {
            userControl.update(user);
        }
        return result;
    }

    public User getUserByCredentials(String login, String password){
        User user = null;
        if(login != null && password != null){
            List<User> list = userControl.getByCredentials(login, password);
            if(list.size() == 1){
                user = list.get(0);
            } else {
               LOGGER.error("User not found or more than one user exists.");
            }
        }
        return user;
    }

    private boolean validate(User user) {
        boolean result = false;
        if (user != null
                && user.getLogin() != null
                && user.getPassword() != null
                && user.getName() != null) {
            result = true;
        }
        return result;
    }
}
