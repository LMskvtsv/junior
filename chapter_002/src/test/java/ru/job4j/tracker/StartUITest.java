package ru.job4j.tracker;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNull;

public class StartUITest {

    private PrintStream stdout = System.out;
    private ByteArrayOutputStream out = new ByteArrayOutputStream();

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
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void tearDown() {
        System.setOut(this.stdout);
    }


    @Test
    public void addItem() {
        Input stubInput = new StubInput(new String[]{"0", "name1", "desc1", "n", "0", "name2", "desc2", "y"});
        new StartUI(stubInput, tracker).init();
        assertThat(tracker.findAll().get(0).getName(), is("name1"));
        assertThat(tracker.findAll().get(0).getDesc(), is("desc1"));
        assertThat(tracker.findAll().get(1).getName(), is("name2"));
        assertThat(tracker.findAll().get(1).getDesc(), is("desc2"));
    }

    @Test
    public void replaceItem() {
        Item origin = new Item("name3", "desc3");
        tracker.add(origin);
        Input stubInput = new StubInput(new String[]{"2", String.valueOf(origin.getId()), "name4", "desc4", "y"});
        new StartUI(stubInput, tracker).init();
        assertThat(tracker.findById(origin.getId()).getName(), is("name4"));
        assertThat(tracker.findById(origin.getId()).getDesc(), is("desc4"));
    }

    @Test
    public void cannotFindToReplace() {
        Item origin = new Item("name3", "desc3");
        tracker.add(origin);
        Input stubInput = new StubInput(new String[]{"2", "93094809", "name4", "desc4", "y"});
        new StartUI(stubInput, tracker).init();
        assertNull(tracker.findById(5686858));
    }

    @Test
    public void deleteItem() {
        Item item1 = new Item("name5", "desc5");
        tracker.add(item1);
        Item item2 = new Item("name6", "desc6");
        tracker.add(item2);
        Input stubInput = new StubInput(new String[]{"3", String.valueOf(item1.getId()), "n", "1", "y"});
        new StartUI(stubInput, tracker).init();
        assertThat(tracker.findAll().size(), is(1));
        assertThat(tracker.findAll().get(0).getId(), is(item2.getId()));
    }

    @Test
    public void cannotFindToDelete() {
        Item item1 = new Item("name5", "desc5");
        tracker.add(item1);
        Input stubInput = new StubInput(new String[]{"3", "00874837", "n", "1", "y"});
        new StartUI(stubInput, tracker).init();
        assertThat(tracker.findAll().size(), is(1));
        assertThat(tracker.findAll().get(0).getId(), is(item1.getId()));
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
        Input stubInput = new StubInput(new String[]{"1", "y"});
        new StartUI(stubInput, tracker).init();
        assertThat(tracker.findAll(), is(expected));
    }

    @Test
    public void findAllEmptyTracker() {
        ArrayList<Item> expected = new ArrayList<>();
        Input stubInput = new StubInput(new String[]{"1", "y"});
        new StartUI(stubInput, tracker).init();
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

        Input stubInput = new StubInput(new String[]{"5", item2.getName(), "y"});
        new StartUI(stubInput, tracker).init();
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

        Input stubInput = new StubInput(new String[]{"5", item2.getName(), "y"});
        new StartUI(stubInput, tracker).init();
        ArrayList<Item> actual = tracker.findByName("item2");
        ArrayList<Item> expected = new ArrayList<>();
        expected.add(item1);
        expected.add(item2);
        assertThat(actual, is(expected));
    }

    @Test
    public void cannotFindByName() {
        Item item1 = new Item("item1", "desc1");
        Item item2 = new Item("item2", "desc2");
        Item item3 = new Item("item3", "desc3");

        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);

        Input stubInput = new StubInput(new String[]{"5", "fake_name", "y"});
        new StartUI(stubInput, tracker).init();
        ArrayList<Item> actual = tracker.findByName("fake_name");
        ArrayList<Item> expected = new ArrayList<>();
        assertThat(actual, is(expected));
    }

    @Test
    public void findItemByID() {
        Item item1 = new Item("item1", "desc1");
        Item item2 = new Item("item2", "desc2");
        Item item3 = new Item("item3", "desc3");

        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);

        Input stubInput = new StubInput(new String[]{"4", String.valueOf(item2.getId()), "y"});
        new StartUI(stubInput, tracker).init();
        Item actual = tracker.findById(item2.getId());
        assertThat(actual, is(item2));
    }

    @Test
    public void cannotFindByID() {
        Item item1 = new Item("item1", "desc1");
        Item item2 = new Item("item2", "desc2");
        Item item3 = new Item("item3", "desc3");

        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);

        Input stubInput = new StubInput(new String[]{"4", "903947843", "y"});
        new StartUI(stubInput, tracker).init();
        Item actual = tracker.findById(878878);
        assertNull(actual);
    }

    @Test
    public void consoleOutputShowAll() {
        Item item1 = new Item("item1", "desc1");
        Item item2 = new Item("item2", "desc2");

        tracker.add(item1);
        tracker.add(item2);
        Input stubInput = new StubInput(new String[]{"1", "y"});
        new StartUI(stubInput, tracker).init();
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(getMenuString())
                                .append("Введите пункт меню:").append(System.lineSeparator())
                                .append("------------ Все существущие заявки --------------").append(System.lineSeparator())
                                .append("Завка №1:").append(System.lineSeparator())
                                .append("- ай ди:").append(tracker.findAll().get(0).getId()).append(System.lineSeparator())
                                .append("- имя:").append(tracker.findAll().get(0).getName()).append(System.lineSeparator())
                                .append("- описание:").append(tracker.findAll().get(0).getDesc()).append(System.lineSeparator())
                                .append("Завка №2:").append(System.lineSeparator())
                                .append("- ай ди:").append(tracker.findAll().get(1).getId()).append(System.lineSeparator())
                                .append("- имя:").append(tracker.findAll().get(1).getName()).append(System.lineSeparator())
                                .append("- описание:").append(tracker.findAll().get(1).getDesc()).append(System.lineSeparator())
                                .append("Exit? (y/n)").append(System.lineSeparator())
                                .toString()
                )
        );
    }

    @Test
    public void consoleOutputShowAllEmptyTracker() {
        Input stubInput = new StubInput(new String[]{"1", "y"});
        new StartUI(stubInput, tracker).init();
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(getMenuString())
                                .append("Введите пункт меню:").append(System.lineSeparator())
                                .append("------------ Все существущие заявки --------------").append(System.lineSeparator())
                                .append("Трекер пуст, как осенний куст :)").append(System.lineSeparator())
                                .append("Exit? (y/n)").append(System.lineSeparator())
                                .toString()
                )
        );
    }

    private String getMenuString() {
        StringBuilder menuString = new StringBuilder();
        menuString.append("0. Добавление новой заявки").append(System.lineSeparator());
        menuString.append("1. Показать все заявки").append(System.lineSeparator());
        menuString.append("2. Отредактировать заявку").append(System.lineSeparator());
        menuString.append("3. Удалить заявку").append(System.lineSeparator());
        menuString.append("4. Найти заявку по идентификатору").append(System.lineSeparator());
        menuString.append("5. Найти заявку по имени").append(System.lineSeparator());
        return menuString.toString();
    }
}
