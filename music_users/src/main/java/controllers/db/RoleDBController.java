package controllers.db;

import models.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RoleDBController extends DBController<Integer, Role> {

    @Override
    public Map<Integer, Role> getAll() {
        ConcurrentHashMap<Integer, Role> map = new ConcurrentHashMap<>();
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM roles")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setRoleName(rs.getString("roleName"));
                map.put(role.getId(), role);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return map;
    }

    @Override
    public Role getById(Integer id) {
        Role musicType = new Role();
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM roles WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                musicType.setId(rs.getInt("id"));
                musicType.setRoleName(rs.getString("roleName"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return musicType;
    }

    @Override
    public void update(Role entity) {
        try (Connection connection = SOURCE.getConnection()) {
            PreparedStatement st = connection.prepareStatement("UPDATE roles SET roleName = ? WHERE id = ?");
            st.setString(1, entity.getRoleName());
            st.setInt(2, entity.getId());
            LOGGER.info(st.toString());
            st.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement st = connection.prepareStatement("DELETE FROM roles WHERE id = ?")) {
            st.setInt(1, id);
            LOGGER.info(st.toString());
            st.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public Role create(Role entity) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement st = connection.prepareStatement("INSERT INSERT OR IGNORE INTO roles (roleName) VALUES (?)");
             PreparedStatement ps2 = connection.prepareStatement("SELECT last_insert_rowid();")) {
            st.setString(1, entity.getRoleName());
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
    public Role findByCredentials(String login, String password) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
