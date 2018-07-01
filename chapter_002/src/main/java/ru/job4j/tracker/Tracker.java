package ru.job4j.tracker;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

public class Tracker implements AutoCloseable {

    private Properties properties = new Properties();
    private static final Logger LOGGER = Logger.getLogger(Tracker.class);
    private Connection connection;

    private static String sqlUrl;

    public Tracker(String fileName) {
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        sqlUrl = properties.getProperty("Database.URL");
        executeDBScripts("init.sql");
    }

    /**
     * Метод реализаущий добавление заявки в хранилище
     *
     * @param item новая заявка
     */
    public Item add(Item item) {
        try {
            this.connection = DriverManager.getConnection(sqlUrl);
            PreparedStatement ps = connection.prepareStatement("INSERT INTO items (name, description, created) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, item.getName());
            ps.setString(2, item.getDesc());
            ps.setTimestamp(3, item.getCreated());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                item.setId(rs.getInt("last_insert_rowid()"));
            }
    } catch (SQLException e) {
        e.printStackTrace();
    }
        return item;
    }


    /**
     * Метод полностью заменяет заявку, найденную по id на новую.
     *
     * @param id   - ай ди заявки которую нужно заменить.
     * @param item - новый айтем на который произойдет замена.
     * @return true - если была найдена заявка по ай ди, false - если заявки с заданным ай ди не было найдено.
     */
    public boolean replace(int id, Item item) {
        boolean result = false;
        try {
            connection = DriverManager.getConnection(sqlUrl);
            PreparedStatement ps = connection.prepareStatement("UPDATE  items SET name = ?, description = ?, created = ? WHERE id = ?");
            ps.setString(1, item.getName());
            ps.setString(2, item.getDesc());
            ps.setTimestamp(3, item.getCreated());
            ps.setInt(4, id);
            ps.executeUpdate();
            result = true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return result;
    }

    /**
     * Метод удаляет заявку, найденную по id.
     *
     * @param id - ай ди заявки которую нужно удалить.
     * @return true - если была найдена заявка по ай ди, false - если заявки с заданным ай ди не было найдено.
     */
    public boolean delete(int id) {
        boolean result = false;
        try {
            connection = DriverManager.getConnection(sqlUrl);
            PreparedStatement ps = connection.prepareStatement("DELETE FROM items WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            result = true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Метод отбирает все существующие заявки.
     *
     * @return - возвращает все заявки, которые были заполнены.
     */
    public ArrayList<Item> findAll() {
        ArrayList<Item> items = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(sqlUrl);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM items");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item(rs.getString("name"), rs.getString("description"));
                item.setId(rs.getInt("id"));
                String date = rs.getString("created");
                item.setCreated(new Timestamp(Long.valueOf(date)));
                items.add(item);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return items;
    }

    /**
     * Метод выполняет поиск заявки по ее имени.
     * Так как поле имя - не уникальное, то может быть найдено несколько заявок.
     *
     * @param key - имя заявки которую нужно найти.
     * @return item[] - массив найденных заявок.
     */
    public ArrayList<Item> findByName(String key) {
        ArrayList<Item> items = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(sqlUrl);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM items WHERE name = ?");
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item(rs.getString("name"), rs.getString("description"));
                item.setId(rs.getInt("id"));
                item.setCreated(rs.getTimestamp("created"));
                items.add(item);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return items;
    }

    /**
     * Метод выполняет поиск заявки по ее id.
     *
     * @param id - id заявки которую нужно найти.
     * @return найденную заявку.
     */
    public Item findById(int id) {
        Item item = null;
        try {
            connection = DriverManager.getConnection(sqlUrl);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM items WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                item = new Item(rs.getString("name"), rs.getString("description"));
                item.setId(rs.getInt("id"));
                item.setCreated(rs.getTimestamp("created"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return item;
    }

    private boolean executeDBScripts(String fileName) {
        boolean isScriptExecuted = false;
        try {
            connection = DriverManager.getConnection(sqlUrl);
            BufferedReader in = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)));
            String str;
            StringBuffer sb = new StringBuffer();
            while ((str = in.readLine()) != null) {
                sb.append(str + System.lineSeparator());
            }
            in.close();
            connection.createStatement().executeUpdate(sb.toString());
            isScriptExecuted = true;
        } catch (Exception e) {
            LOGGER.info("Failed to Execute" + fileName + ". The error is" + e.getMessage());
            LOGGER.info(String.format("Failed to Execute %s", fileName));
            LOGGER.error(e.getMessage(), e);
        }
        return isScriptExecuted;
    }

    @Override
    public void close() throws Exception {
       this.connection.close();
    }
}

