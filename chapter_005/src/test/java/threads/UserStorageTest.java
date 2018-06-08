package threads;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class UserStorageTest {

    @Test
    public void transferTest() {
        UserStorage storage = new UserStorage();
        User user1 = new User(1, 100.0);
        User user2 = new User(2, 200.0);
        storage.add(user1);
        storage.add(user2);
        storage.transfer(1, 2, 50.0);
        assertThat(user1.getAmount(), is(50.0));
        assertThat(user2.getAmount(), is(250.0));
    }

    @Test
    public void updateTest() {
        UserStorage storage = new UserStorage();
        User user1 = new User(1, 100.0);
        User user2 = new User(2, 200.0);
        storage.add(user1);
        storage.add(user2);
        storage.update(new User(1, 500));
        assertThat(user1.getAmount(), is(500.0));
    }
}