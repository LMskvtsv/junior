package controllers.db;

import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserDBController extends DBController<Integer, User> {

    @Override
    public Map<Integer, User> getAll() {
        ConcurrentHashMap<Integer, User> map = new ConcurrentHashMap<>();
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setAddressID(rs.getInt("addressId"));
                user.setMusicTypeID(rs.getInt("musicTypeId"));
                user.setRoleID(rs.getInt("roleId"));
                map.put(user.getId(), user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public User getById(Integer id) {
        User user = new User();
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setAddressID(rs.getInt("addressId"));
                user.setMusicTypeID(rs.getInt("musicTypeId"));
                user.setRoleID(rs.getInt("roleId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void update(User entity) {
        try (Connection connection = SOURCE.getConnection()) {
            PreparedStatement st = connection.prepareStatement("UPDATE users SET login = ?, password = ?, addressId = ?, musicTypeId = ?, roleId = ? WHERE id = ?");
            st.setString(1, entity.getLogin());
            st.setString(2, entity.getPassword());
            st.setInt(3, entity.getAddressID());
            st.setInt(4, entity.getMusicTypeID());
            st.setInt(5, entity.getRoleID());
            st.setInt(6, entity.getId());
            LOGGER.info(st.toString());
            st.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement st = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            st.setInt(1, id);
            LOGGER.info(st.toString());
            st.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public User create(User entity) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement st = connection.prepareStatement("INSERT OR IGNORE INTO users (login, password, addressId, musicTypeId, roleId) VALUES (?, ?, ?, ?, ?)");
             PreparedStatement ps2 = connection.prepareStatement("SELECT last_insert_rowid();")) {
            st.setString(1, entity.getLogin());
            st.setString(2, entity.getPassword());
            st.setInt(3, entity.getAddressID());
            st.setInt(4, entity.getMusicTypeID());
            st.setInt(5, entity.getRoleID());
            st.executeUpdate();
            LOGGER.info(st.toString());
            ResultSet rs = ps2.executeQuery();
            while (rs.next()) {
                entity.setId(rs.getInt(1));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return entity;
    }

    @Override
    public User findByCredentials(String login, String password) {
        User user = new User();
        try (Connection connection = SOURCE.getConnection()) {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM users u JOIN roles r on u.roleId = r.id WHERE u.login = ? AND u.password = ?");
            st.setString(1, login);
            st.setString(2, password);
            LOGGER.info(st.toString());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setAddressID(rs.getInt("addressId"));
                user.setRoleID(rs.getInt("roleId"));
                user.setMusicTypeID(rs.getInt("musicTypeId"));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return user;
    }
}
