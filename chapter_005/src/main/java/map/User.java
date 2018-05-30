package map;

import java.util.Calendar;
import java.util.Objects;

public class User {

    String name;
    Calendar birthday;
    int children;

    public User(String name, Calendar birthday, int children) {
        this.name = name;
        this.birthday = birthday;
        this.children = children;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday, children);
    }
}
