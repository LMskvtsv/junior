package ru.job4j.tracker;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TrackerTest {

    @Test
    public void addItem() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("item1", "desc1");
        Item item2 = new Item("item2", "desc2");
        Item item3 = new Item("item3", "desc3");

        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);

        assertThat(tracker.findAll().get(0), is(item1));
        assertThat(tracker.findAll().get(1), is(item2));
        assertThat(tracker.findAll().get(2), is(item3));
    }

    @Test
    public void replaceItem() {
        Tracker tracker = new Tracker();
        Item previous = new Item("item1", "desc1");
        Item modified = new Item("item2", "desc2");

        tracker.add(previous);
        modified.setId(previous.getId());
        tracker.replace(previous.getId(), modified);

        assertThat(tracker.findById(previous.getId()).getName(), is("item2"));
        assertThat(tracker.findById(previous.getId()).getDesc(), is("desc2"));
    }

    @Test
    public void deleteMiddleItem() {
        Tracker tracker = new Tracker();
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
        Tracker tracker = new Tracker();
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
        Tracker tracker = new Tracker();
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
        Tracker tracker = new Tracker();
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
        Tracker tracker = new Tracker();
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
        Tracker tracker = new Tracker();
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
        Tracker tracker = new Tracker();
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
