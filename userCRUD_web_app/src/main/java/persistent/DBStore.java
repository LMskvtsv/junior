package persistent;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
        SOURCE.setMinIdle(1);
        SOURCE.setMaxIdle(2);
        SOURCE.setMaxOpenPreparedStatements(2);
        if (!this.executeDBScripts("prepareDB.sql")) {
            LOGGER.error("Initial script was not successful");
        }

        add(new User("1",
                "root",
                "root",
                "root",
                "root@root.ru",
                new Role(Role.ADMIN_ID),
                new Timestamp(System.currentTimeMillis())));
    }

    public static DBStore getInstance() {
        return instance;
    }


    @Override
    public void add(User value) {
        try (Connection connection = SOURCE.getConnection()) {
            PreparedStatement st = connection.prepareStatement("INSERT OR REPLACE INTO users (id, name, login, password, email, role_id, created) VALUES (?, ?, ?, ?, ?, ?, ?)");
            st.setString(1, value.getId());
            st.setString(2, value.getName());
            st.setString(3, value.getLogin());
            st.setString(4, value.getPassword());
            st.setString(5, value.getEmail());
            st.setInt(6, value.getRole().getId());
            st.setTimestamp(7, value.getCreateDate());
            st.executeUpdate();
            LOGGER.info(st.toString());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            try {
                SOURCE.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return;
        }
    }

    @Override
    public void update(User value) {
        try (Connection connection = SOURCE.getConnection()) {
            PreparedStatement st = connection.prepareStatement("UPDATE users SET name = ?, login=?, email=?, role_id=? WHERE id = ?");
            st.setString(1, value.getName());
            st.setString(2, value.getLogin());
            st.setString(3, value.getEmail());
            st.setInt(4, value.getRole().getId());
            st.setString(5, value.getId());
            LOGGER.info(st.toString());
            st.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            try {
                SOURCE.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
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
            try {
                SOURCE.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return;
        }
    }

    @Override
    public Map<String, User> findAll() {
        Map<String, User> map = new ConcurrentHashMap<>();
        try (Connection connection = SOURCE.getConnection()) {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM users u JOIN roles r ON u.role_id = r.id");
            ResultSet rs = st.executeQuery();
            LOGGER.info(st.toString());
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String email = rs.getString("email");
                int roleId = rs.getInt("role_id");
                Timestamp created = rs.getTimestamp("created");
                map.put(id, new User(id, name, login, password, email, new Role(roleId), created));
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
            PreparedStatement st = connection.prepareStatement("SELECT * FROM users u JOIN roles r on u.role_id = r.id WHERE u.id = ?");
            st.setString(1, key);
            LOGGER.info(st.toString());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                user = new User(rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("email"),
                        new Role(rs.getInt("role_id")),
                        rs.getTimestamp("created"));
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
