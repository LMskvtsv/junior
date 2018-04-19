package map;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserConvertTest {
    @Test
    public void when2UsersListToMap() {
        UserConvert userConvert = new UserConvert();
        User user1 = new User(1, "Grandpa", "FarmCity");
        User user2 = new User(2, "Grandma", "FarmCity");
        List<User> usersList = Arrays.asList(user1, user2);
        HashMap<Integer, User> expect = new HashMap<>();
        expect.put(1, user1);
        expect.put(2, user2);
        assertThat(userConvert.process(usersList), is(expect));
    }
}
