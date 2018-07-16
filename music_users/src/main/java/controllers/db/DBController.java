package controllers.db;

import controllers.Controller;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.Properties;

public abstract class DBController<K, V> implements Controller<K, V> {
    protected final static Logger LOGGER = Logger.getLogger(Controller.class);

    protected static final BasicDataSource SOURCE = new BasicDataSource();
    private static final Properties PROPERTIES = new Properties();
    private static final String CONFIG_FILE_NAME = "db.properties";
    private static final String SQL_FILE_NAME = "music.sql";

    static {
        System.out.println("Static block");
        loadProperties();
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            LOGGER.info(e.getMessage(), e);
        }
        SOURCE.setUrl(PROPERTIES.getProperty("Database.URL"));
        SOURCE.setMinIdle(1);
        SOURCE.setMaxIdle(2);
        SOURCE.setMaxOpenPreparedStatements(2);
        executeDBScripts();
    }

    private static void loadProperties() {
        try {
            PROPERTIES.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE_NAME));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }


    /**
     * Executes sql scripts from resources folder.
     *
     * @return
     */
    private static boolean executeDBScripts() {
        try (Connection conn = SOURCE.getConnection()) {
            BufferedReader in = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(SQL_FILE_NAME)));
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
