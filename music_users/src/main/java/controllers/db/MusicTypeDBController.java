package controllers.db;

import models.MusicType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MusicTypeDBController extends DBController<Integer, MusicType> {

    @Override
    public Map<Integer, MusicType> getAll() {
        ConcurrentHashMap<Integer, MusicType> map = new ConcurrentHashMap<>();
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM musicTypes")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MusicType musicType = new MusicType();
                musicType.setId(rs.getInt("id"));
                musicType.setTypeName(rs.getString("typeName"));
                map.put(musicType.getId(), musicType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public MusicType getById(Integer id) {
        MusicType musicType = new MusicType();
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM musicTypes WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                musicType.setId(rs.getInt("id"));
                musicType.setTypeName(rs.getString("typeName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return musicType;
    }

    @Override
    public void update(MusicType entity) {
        try (Connection connection = SOURCE.getConnection()) {
            PreparedStatement st = connection.prepareStatement("UPDATE musicTypes SET typeName = ? WHERE id = ?");
            st.setString(1, entity.getTypeName());
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
             PreparedStatement st = connection.prepareStatement("DELETE FROM musicTypes WHERE id = ?")) {
            st.setInt(1, id);
            LOGGER.info(st.toString());
            st.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public MusicType create(MusicType entity) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement st = connection.prepareStatement("INSERT OR IGNORE INTO musicTypes (typeName) VALUES (?)");
             PreparedStatement ps2 = connection.prepareStatement("SELECT last_insert_rowid();")) {
            st.setString(1, entity.getTypeName());
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
    public MusicType findByCredentials(String login, String password) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
