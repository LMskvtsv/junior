package persistent;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class DBStore implements Store<String, User> {

    private final static Logger LOGGER = Logger.getLogger(DBStore.class);

    private static final BasicDataSource SOURCE = new BasicDataSource();
    private static DBStore instance = new DBStore();
    Properties properties = new Properties();
    private final String configFileName = "db.properties";

    private DBStore() {
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(configFileName));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            LOGGER.info(e.getMessage(), e);
        }
        SOURCE.setUrl(properties.getProperty("Database.URL"));
        SOURCE.setMinIdle(5);
        SOURCE.setMaxIdle(10);
        SOURCE.setMaxOpenPreparedStatements(100);
        if (!this.executeDBScripts("prepareDB.sql")) {
            LOGGER.error("Initial script was not successful");
        }
    }

    public static DBStore getInstance() {
        return instance;
    }

    @Override
    public void add(User value) {
        try (Connection connection = SOURCE.getConnection()) {
            PreparedStatement st = connection.prepareStatement("INSERT INTO users (id, name, login, email, created) VALUES (?, ?, ?, ?, ?)");
            st.setString(1, value.getId());
            st.setString(2, value.getName());
            st.setString(3, value.getLogin());
            st.setString(4, value.getEmail());
            st.setTimestamp(5, value.getCreateDate());
            st.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return;
        }
    }

    @Override
    public void update(User value) {
        try (Connection connection = SOURCE.getConnection()) {
            PreparedStatement st = connection.prepareStatement("UPDATE users SET name = ?, login=?, email=? WHERE id = ?");
            st.setString(1, value.getName());
            st.setString(2, value.getLogin());
            st.setString(3, value.getEmail());
            st.setString(4, value.getId());
            LOGGER.info(st.toString());
            st.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return;
        }
    }

    @Override
    public void delete(String key) {
        try (Connection connection = SOURCE.getConnection()) {
            PreparedStatement st = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            st.setString(1, key);
            LOGGER.info(st.toString());
            st.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return;
        }
    }

    @Override
    public Map<String, User> findAll() {
        Map<String, User> map = new ConcurrentHashMap<>();
        try (Connection connection = SOURCE.getConnection()) {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM users");
            ResultSet rs = st.executeQuery();
            LOGGER.info(st.toString());
            while (rs.next()) {
                map.put(rs.getString("id"), new User(rs.getString("id"), rs.getString("name"), rs.getString("login"), rs.getString("email"), rs.getTimestamp("created")));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return map;
    }

    @Override
    public User findByID(String key) {
        User user = null;
        try (Connection connection = SOURCE.getConnection()) {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            st.setString(1, key);
            LOGGER.info(st.toString());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                user = new User(rs.getString("id"), rs.getString("name"), rs.getString("login"), rs.getString("email"), rs.getTimestamp("created"));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return user;
    }

    /**
     * Executes sql scripts from resources folder.
     *
     * @param fileName
     * @return
     */
    public boolean executeDBScripts(String fileName) {
        try (Connection conn = SOURCE.getConnection()) {
            BufferedReader in = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)));
            String str;
            StringBuffer sb = new StringBuffer();
            while ((str = in.readLine()) != null) {
                sb.append(str + System.lineSeparator());
            }
            in.close();
            LOGGER.info(sb.toString());
            conn.createStatement().executeUpdate(sb.toString());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
        return true;
    }
}
