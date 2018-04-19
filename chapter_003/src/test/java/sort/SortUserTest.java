package sort;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SortUserTest {
    @Test
    public void addItem() {
        SortUser sortUser = new SortUser();
        ArrayList<User> list = new ArrayList<>();
        list.add(new User("1", 12));
        list.add(new User("2", 1));
        list.add(new User("3", 6));
        assertThat(sortUser.sort(list).first().getAge(), is(1));
        assertThat(sortUser.sort(list).last().getAge(), is(12));
    }

    @Test
    public void sortByAllFields() {
        SortUser sortUser = new SortUser();
        ArrayList<User> list = new ArrayList<>();

        User u1 = new User("Сергей", 25);
        User u2 = new User("Иван", 30);
        User u3 = new User("Сергей", 20);
        User u4 = new User("Иван", 25);

        list.add(u1);
        list.add(u2);
        list.add(u3);
        list.add(u4);

        ArrayList<User> expected = new ArrayList<>();
        expected.add(u3);
        expected.add(u1);
        expected.add(u4);
        expected.add(u2);

        assertThat(sortUser.sortByAllFields(list), is(expected));
    }

    @Test
    public void sortNameLength() {
        SortUser sortUser = new SortUser();
        ArrayList<User> list = new ArrayList<>();

        User u1 = new User("Сергей", 25);
        User u2 = new User("Иван", 30);
        User u3 = new User("Ян", 20);

        list.add(u1);
        list.add(u2);
        list.add(u3);

        ArrayList<User> expected = new ArrayList<>();
        expected.add(u3);
        expected.add(u2);
        expected.add(u1);

        assertThat(sortUser.sortNameLength(list), is(expected));
    }
}
