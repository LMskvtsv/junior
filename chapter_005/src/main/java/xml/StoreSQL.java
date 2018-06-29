package xml;


import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class StoreSQL {

    private String sqlUrl;
    private Properties properties = new Properties();
    private static final Logger LOGGER = Logger.getLogger(StoreSQL.class);

    public StoreSQL(String fileName) {
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        sqlUrl = properties.getProperty("Database.URL");
    }


    public void generate(long n) {
        PreparedStatement ps;
        Connection connection = null;
        try (Connection conn = DriverManager.getConnection(sqlUrl)) {
            if (conn != null) {
                connection = conn;
                conn.setAutoCommit(false);
                ps = conn.prepareStatement("create table if not exists entry (id integer)");
                ps.executeUpdate();
                ps = conn.prepareStatement("delete from entry");
                ps.executeUpdate();

                for (int i = 1; i <= n; i++) {
                    ps = conn.prepareStatement("insert into  entry (id) values (?)");
                    ps.setInt(1, i);
                    ps.executeUpdate();

                }
                conn.commit();
                ps.close();
            }

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    LOGGER.error(e.getMessage(), e1);
                }
            }
            LOGGER.error(e.getMessage(), e);
        }
    }

    public List<XmlUsage.Entry> getContentFromDB() {
        LinkedList<XmlUsage.Entry> list = new LinkedList<>();
        try (Connection conn = DriverManager.getConnection(sqlUrl)) {
            if (conn != null) {
                PreparedStatement query = conn.prepareStatement("select id from entry");
                ResultSet rs = query.executeQuery();
                while (rs.next()) {
                    list.add(new XmlUsage.Entry(rs.getInt("id")));
                }
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return list;
    }

}
