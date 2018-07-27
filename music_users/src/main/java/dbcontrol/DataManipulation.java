package dbcontrol;

import controllers.Controller;
import controllers.db.AddressDBController;
import controllers.db.MusicTypeDBController;
import controllers.db.RoleDBController;
import controllers.db.UserDBController;
import models.Address;
import models.MusicType;
import models.Role;
import models.User;

import java.util.ArrayList;
import java.util.Map;

public class DataManipulation {
    private final Controller userController = new UserDBController();
    private final Controller addressController = new AddressDBController();
    private final Controller musicTypeController = new MusicTypeDBController();
    private final Controller roleController = new RoleDBController();

    private final static DataManipulation USER_MANIPULATION =
            new DataManipulation();

    private DataManipulation() {
    }

    /**
     * Glodal access point to the object.
     *
     * @return
     */
    public static DataManipulation getDataManipulation() {
        return USER_MANIPULATION;
    }


    public Address getUserAddress(User user) {
        return (Address) addressController.getById(user.getRoleID());
    }

    public Role getUserRole(User user) {
        return (Role) roleController.getById(user.getRoleID());
    }

    public MusicType getUserMusicType(User user) {
        return (MusicType) musicTypeController.getById(user.getMusicTypeID());
    }

    public void addUser(User user) {
        userController.create(user);
    }

    public void addAddress(Address address) {
        addressController.create(address);
    }

    public User findUserByAddress(Address address) {
        User user = new User();
        for (Object object : userController.getAll().values()) {
            User u = (User) object;
            if (u.getAddressID() == address.getId()) {
                user = u;
            }
        }
        return user;
    }

    public User findUserByCretentials(String login, String password) {
        return (User) userController.findByCredentials(login, password);
    }

    public ArrayList<User> findUserByMusicType(MusicType musicType) {
        ArrayList<User> users = new ArrayList<>();
        for (Object object : userController.getAll().values()) {
            User u = (User) object;
            if (u.getMusicTypeID() == musicType.getId()) {
                users.add(u);
            }
        }
        return users;
    }

    public ArrayList<User> findUserByRole(Role role) {
        ArrayList<User> users = new ArrayList<>();
        for (Object object : userController.getAll().values()) {
            User u = (User) object;
            if (u.getRoleID() == role.getId()) {
                users.add(u);
            }
        }
        return users;
    }

    public Map getAllRoles() {
        return roleController.getAll();
    }

    public Map getAllMusicTypes() {
        return musicTypeController.getAll();
    }

    public Map getAllAddresses() {
        return addressController.getAll();
    }

    public Map getAllUsers() {
        return userController.getAll();
    }

    public void removeUser(int id) {
        userController.delete(id);
    }

}
