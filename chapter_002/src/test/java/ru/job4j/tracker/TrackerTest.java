package ru.job4j.tracker;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TrackerTest {

    private String configFileName = "DataBase.cfg";
    private String sqlUrl = "jdbc:sqlite:tracker.db";
    Tracker tracker;

    @Before
    public void setUp() {
        tracker = new Tracker(configFileName);
        PreparedStatement ps;
        try (Connection conn = DriverManager.getConnection(sqlUrl)) {
            ps = conn.prepareStatement("delete from items");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addItem() {
        Item item1 = new Item("item1", "desc1");
        Item item2 = new Item("item2", "desc2");
        Item item3 = new Item("item3", "desc3");

        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);

        ArrayList<Item> expected = new ArrayList<>();
        expected.addAll(Arrays.asList(item1, item2, item3));
        ArrayList<Item> actual = tracker.findAll();
        assertThat(actual, is(expected));

    }

    @Test
    public void replaceItem() {
        Item previous = new Item("item1", "desc1");
        Item modified = new Item("item2", "desc2");
        modified.setCreated(new Timestamp(System.currentTimeMillis()));
        tracker.add(previous);
        modified.setId(previous.getId());
        tracker.replace(previous.getId(), modified);

        assertThat(tracker.findById(previous.getId()).getName(), is("item2"));
        assertThat(tracker.findById(previous.getId()).getDesc(), is("desc2"));
    }

    @Test
    public void deleteMiddleItem() {
        Item item1 = new Item("item1", "desc1");
        Item item2 = new Item("item2", "desc2");
        Item item3 = new Item("item3", "desc3");

        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);

        tracker.delete(item2.getId());

        ArrayList<Item> expected = new ArrayList<>();
        expected.add(item1);
        expected.add(item3);

        assertThat(tracker.findAll(), is(expected));
    }

    @Test
    public void deleteFirstItem() {
        Item item1 = new Item("item1", "desc1");
        Item item2 = new Item("item2", "desc2");
        Item item3 = new Item("item3", "desc3");

        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);

        tracker.delete(item1.getId());

        ArrayList<Item> expected = new ArrayList<>();
        expected.add(item2);
        expected.add(item3);

        assertThat(tracker.findAll(), is(expected));
    }

    @Test
    public void deleteLastItem() {
        Item item1 = new Item("item1", "desc1");
        Item item2 = new Item("item2", "desc2");
        Item item3 = new Item("item3", "desc3");

        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);

        tracker.delete(item3.getId());

        ArrayList<Item> expected = new ArrayList<>();
        expected.add(item1);
        expected.add(item2);
        assertThat(tracker.findAll(), is(expected));
    }

    @Test
    public void findAllItems() {
        Item item1 = new Item("item1", "desc1");
        Item item2 = new Item("item2", "desc2");
        Item item3 = new Item("item3", "desc3");

        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);

        ArrayList<Item> expected = new ArrayList<>();
        expected.add(item1);
        expected.add(item2);
        expected.add(item3);
        assertThat(tracker.findAll(), is(expected));
    }

    @Test
    public void findItemByNameSingle() {
        Item item1 = new Item("item1", "desc1");
        Item item2 = new Item("item2", "desc2");
        Item item3 = new Item("item3", "desc3");

        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);

        ArrayList<Item> actual = tracker.findByName("item2");
        ArrayList<Item> expected = new ArrayList<>();
        expected.add(item2);
        assertThat(actual, is(expected));
    }

    @Test
    public void findItemByNameMultiple() {
        Item item1 = new Item("item2", "desc1");
        Item item2 = new Item("item2", "desc2");
        Item item3 = new Item("item3", "desc3");

        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);

        ArrayList<Item> actual = tracker.findByName("item2");
        ArrayList<Item> expected = new ArrayList<>();
        expected.add(item1);
        expected.add(item2);
        assertThat(actual, is(expected));
    }

    @Test
    public void findItemById() {
        Item item1 = new Item("item2", "desc1");
        Item item2 = new Item("item2", "desc2");
        Item item3 = new Item("item3", "desc3");

        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);

        Item actual = tracker.findById(item2.getId());
        assertThat(actual, is(item2));
    }
}
