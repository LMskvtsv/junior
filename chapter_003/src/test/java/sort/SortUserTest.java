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
}
