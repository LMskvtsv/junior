package controllers.db;

import models.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AddressDBController extends DBController<Integer, Address> {

    @Override
    public Map<Integer, Address> getAll() {
        ConcurrentHashMap<Integer, Address> map = new ConcurrentHashMap<>();
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM adresses")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Address address = new Address();
                address.setId(rs.getInt("id"));
                address.setCountry(rs.getString("country"));
                address.setCity(rs.getString("city"));
                address.setStreet(rs.getString("street"));
                address.setBuilding(rs.getString("building"));
                address.setFlat(rs.getString("flat"));
                map.put(address.getId(), address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Address getById(Integer id) {
        Address address = new Address();
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM adresses WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                address.setId(rs.getInt("id"));
                address.setCountry(rs.getString("country"));
                address.setCity(rs.getString("city"));
                address.setStreet(rs.getString("street"));
                address.setBuilding(rs.getString("building"));
                address.setFlat(rs.getString("flat"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return address;
    }

    @Override
    public void update(Address entity) {
        try (Connection connection = SOURCE.getConnection()) {
            PreparedStatement st = connection.prepareStatement("UPDATE users SET country = ?, city = ?, street = ?, building = ?, flat = ? WHERE id = ?");
            st.setString(1, entity.getCountry());
            st.setString(2, entity.getCity());
            st.setString(3, entity.getStreet());
            st.setString(4, entity.getBuilding());
            st.setString(5, entity.getFlat());
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
             PreparedStatement st = connection.prepareStatement("DELETE FROM adresses WHERE id = ?")) {
            st.setInt(1, id);
            LOGGER.info(st.toString());
            st.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public Address create(Address entity) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement st = connection.prepareStatement("INSERT INSERT OR IGNORE INTO users (country, city, street, building, flat) VALUES (?, ?, ?, ?, ?)");
             PreparedStatement ps2 = connection.prepareStatement("SELECT last_insert_rowid();")) {
            st.setString(1, entity.getCountry());
            st.setString(2, entity.getCity());
            st.setString(3, entity.getStreet());
            st.setString(4, entity.getBuilding());
            st.setString(5, entity.getFlat());
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
    public Address findByCredentials(String login, String password) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
