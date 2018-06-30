package parser;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;

public class DataBaseObject {

    private Properties properties;

    private final static Logger LOG = Logger.getLogger(DataBaseObject.class);


    private final static String DB_PATH_KEY = "Database.URL";
    private final static String LOGIN_KEY = "Database.user";
    private final static String PASSWORD_KEY = "Database.password";

    public DataBaseObject(Properties properties) {
        this.properties = properties;
    }

    /**
     * Save all found java jobs to local database.
     *
     * @param list of found jobs.
     */
    public void saveDataToDatabase(LinkedList<JobOffer> list) {
        Connection connection = null;
        try (Connection conn = DriverManager.getConnection(String.valueOf(properties.get(DB_PATH_KEY)),
                String.valueOf(properties.get(LOGIN_KEY)),
                String.valueOf(properties.get(PASSWORD_KEY)))) {
            if (conn != null) {
                connection = conn;
                conn.setAutoCommit(false);
                PreparedStatement ps;
                for (JobOffer offer: list) {
                    ps = conn.prepareStatement("INSERT INTO job_offers (forum_id, title, href, created) VALUES (?, ?, ?, ?) ON CONFLICT (forum_id) DO NOTHING");
                    ps.setString(1, offer.getForumID());
                    ps.setString(2, offer.getTitle());
                    ps.setString(3, offer.getHref());
                    ps.setTimestamp(4, offer.getPostDate());
                    LOG.info(ps.toString());
                    ps.executeUpdate();
                }
                conn.commit();
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    LOG.error(e1.getMessage(), e1);
                }
            }
        }
    }

    /**
     * Executes sql scripts from resources folder.
     *
     * @param fileName
     * @return
     */
    public boolean executeDBScripts(String fileName) {
        try (Connection conn = DriverManager.getConnection(String.valueOf(properties.get(DB_PATH_KEY)),
                String.valueOf(properties.get(LOGIN_KEY)),
                String.valueOf(properties.get(PASSWORD_KEY)))) {
            BufferedReader in = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)));
            String str;
            StringBuffer sb = new StringBuffer();
            while ((str = in.readLine()) != null) {
                sb.append(str + System.lineSeparator());
            }
            in.close();
            LOG.info(sb.toString());
            conn.createStatement().executeUpdate(sb.toString());
        } catch (Exception e) {
            LOG.error(String.format("Failed to execute %s.", fileName));
            LOG.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

}
