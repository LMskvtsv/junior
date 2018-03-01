package ru.job4j.tracker;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNull;

public class StartUITest {

    private PrintStream stdout = System.out;
    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void tearDown() {
        System.setOut(this.stdout);
    }


    @Test
    public void addItem() {
        Tracker tracker = new Tracker();
        Input stubInput = new StubInput(new String[]{"0", "name1", "desc1", "0", "name2", "desc2", "6"});
        new StartUI(stubInput, tracker).init();
        assertThat(tracker.findAll()[0].getName(), is("name1"));
        assertThat(tracker.findAll()[0].getDesc(), is("desc1"));
        assertThat(tracker.findAll()[1].getName(), is("name2"));
        assertThat(tracker.findAll()[1].getDesc(), is("desc2"));
    }

    @Test
    public void replaceItem() {
        Tracker tracker = new Tracker();
        Item origin = new Item("name3", "desc3");
        tracker.add(origin);
        Input stubInput = new StubInput(new String[]{"2", origin.getId(), "name4", "desc4", "6"});
        new StartUI(stubInput, tracker).init();
        assertThat(tracker.findById(origin.getId()).getName(), is("name4"));
        assertThat(tracker.findById(origin.getId()).getDesc(), is("desc4"));
    }

    @Test
    public void cannotFindToReplace() {
        Tracker tracker = new Tracker();
        Item origin = new Item("name3", "desc3");
        tracker.add(origin);
        Input stubInput = new StubInput(new String[]{"2", "fake_id", "name4", "desc4", "6"});
        new StartUI(stubInput, tracker).init();
        assertNull(tracker.findById("fake_id"));
    }

    @Test
    public void deleteItem() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("name5", "desc5");
        tracker.add(item1);
        Item item2 = new Item("name6", "desc6");
        tracker.add(item2);
        Input stubInput = new StubInput(new String[]{"3", item1.getId(), "1", "6"});
        new StartUI(stubInput, tracker).init();
        assertThat(tracker.findAll().length, is(1));
        assertThat(tracker.findAll()[0].getId(), is(item2.getId()));
    }

    @Test
    public void cannotFindToDelete() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("name5", "desc5");
        tracker.add(item1);
        Input stubInput = new StubInput(new String[]{"3", "fake_id", "1", "6"});
        new StartUI(stubInput, tracker).init();
        assertThat(tracker.findAll().length, is(1));
        assertThat(tracker.findAll()[0].getId(), is(item1.getId()));
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

        Item[] expected = new Item[3];
        expected[0] = item1;
        expected[1] = item2;
        expected[2] = item3;
        Input stubInput = new StubInput(new String[]{"1", "6"});
        new StartUI(stubInput, tracker).init();
        assertThat(tracker.findAll(), is(expected));
    }

    @Test
    public void findAllEmptyTracker() {
        Tracker tracker = new Tracker();
        Item[] expected = new Item[0];
        Input stubInput = new StubInput(new String[]{"1", "6"});
        new StartUI(stubInput, tracker).init();
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

        Input stubInput = new StubInput(new String[]{"5", item2.getName(), "6"});
        new StartUI(stubInput, tracker).init();
        Item[] actual = tracker.findByName("item2");
        Item[] expected = new Item[1];
        expected[0] = item2;
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

        Input stubInput = new StubInput(new String[]{"5", item2.getName(), "6"});
        new StartUI(stubInput, tracker).init();
        Item[] actual = tracker.findByName("item2");
        Item[] expected = new Item[2];
        expected[0] = item1;
        expected[1] = item2;
        assertThat(actual, is(expected));
    }

    @Test
    public void cannotFindByName() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("item1", "desc1");
        Item item2 = new Item("item2", "desc2");
        Item item3 = new Item("item3", "desc3");

        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);

        Input stubInput = new StubInput(new String[]{"5", "fake_name", "6"});
        new StartUI(stubInput, tracker).init();
        Item[] actual = tracker.findByName("fake_name");
        Item[] expected = new Item[0];
        assertThat(actual, is(expected));
    }

    @Test
    public void findItemByID() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("item1", "desc1");
        Item item2 = new Item("item2", "desc2");
        Item item3 = new Item("item3", "desc3");

        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);

        Input stubInput = new StubInput(new String[]{"4", item2.getId(), "6"});
        new StartUI(stubInput, tracker).init();
        Item actual = tracker.findById(item2.getId());
        assertThat(actual, is(item2));
    }

    @Test
    public void cannotFindByID() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("item1", "desc1");
        Item item2 = new Item("item2", "desc2");
        Item item3 = new Item("item3", "desc3");

        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);

        Input stubInput = new StubInput(new String[]{"4", "fake_id", "6"});
        new StartUI(stubInput, tracker).init();
        Item actual = tracker.findById("fake_id");
        assertNull(actual);
    }

    @Test
    public void consoleOutputShowAll() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("item1", "desc1");
        Item item2 = new Item("item2", "desc2");

        tracker.add(item1);
        tracker.add(item2);
        Input stubInput = new StubInput(new String[]{"1", "6"});
        new StartUI(stubInput, tracker).init();
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(getMenuString())
                                .append("Введите пункт меню : ").append(System.lineSeparator())
                                .append("------------ Все существущие заявки --------------").append(System.lineSeparator())
                                .append("Завка №1:").append(System.lineSeparator())
                                .append("- ай ди:").append(tracker.findAll()[0].getId()).append(System.lineSeparator())
                                .append("- имя:").append(tracker.findAll()[0].getName()).append(System.lineSeparator())
                                .append("- описание:").append(tracker.findAll()[0].getDesc()).append(System.lineSeparator())
                                .append("Завка №2:").append(System.lineSeparator())
                                .append("- ай ди:").append(tracker.findAll()[1].getId()).append(System.lineSeparator())
                                .append("- имя:").append(tracker.findAll()[1].getName()).append(System.lineSeparator())
                                .append("- описание:").append(tracker.findAll()[1].getDesc()).append(System.lineSeparator())
                                .append(getMenuString())
                                .append("Введите пункт меню : ").append(System.lineSeparator())
                                .append("Good bye, have a nice day!").append(System.lineSeparator())
                                .toString()
                )
        );
    }

    @Test
    public void consoleOutputShowAllEmptyTracker() {
        Tracker tracker = new Tracker();
        Input stubInput = new StubInput(new String[]{"1", "6"});
        new StartUI(stubInput, tracker).init();
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(getMenuString())
                                .append("Введите пункт меню : ").append(System.lineSeparator())
                                .append("------------ Все существущие заявки --------------").append(System.lineSeparator())
                                .append("Трекер пуст, как осенний куст :)").append(System.lineSeparator())
                                .append(getMenuString())
                                .append("Введите пункт меню : ").append(System.lineSeparator())
                                .append("Good bye, have a nice day!").append(System.lineSeparator())
                                .toString()
                )
        );
    }

    private String getMenuString() {
        StringBuilder menuString = new StringBuilder();
        menuString.append("Меню:").append(System.lineSeparator());
        menuString.append("0. Добавить новую заявку").append(System.lineSeparator());
        menuString.append("1. Показать все заявки").append(System.lineSeparator());
        menuString.append("2. Отредактировать заявку").append(System.lineSeparator());
        menuString.append("3. Удалить заявку").append(System.lineSeparator());
        menuString.append("4. Найти заявку по идентификатору").append(System.lineSeparator());
        menuString.append("5. Найти заявку по имени").append(System.lineSeparator());
        menuString.append("6. Выход").append(System.lineSeparator());
        return menuString.toString();
    }
}
