package map;


import org.junit.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UserTest {
    @Test
    public void userMapDemonstrationDifferentReferences() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(1990, Calendar.SEPTEMBER, 25);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(1990, Calendar.SEPTEMBER, 25);
        User first = new User("Ivan", calendar1, 0);
        User second = new User("Ivan", calendar2, 0);
        Map<User, Object> map = new HashMap<>();
        map.put(first, "first");
        map.put(second, "second");
        System.out.println(map);
        System.out.println("first hash code: " + first.hashCode());
        System.out.println("second hash code: " + second.hashCode());
    }

    @Test
    public void userMapDemonstrationSameReference() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(1990, Calendar.SEPTEMBER, 25);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(1990, Calendar.SEPTEMBER, 25);
        User first = new User("Ivan", calendar1, 0);
        Map<User, Object> map = new HashMap<>();
        map.put(first, "first");
        map.put(first, "second");
        System.out.println(map);
        System.out.println(first.hashCode());
    }
}