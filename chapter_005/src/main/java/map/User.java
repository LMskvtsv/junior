package map;

import java.util.Calendar;

public class User {

    String name;
    Calendar birthday;
    int children;

    public User(String name, Calendar birthday, int children) {
        this.name = name;
        this.birthday = birthday;
        this.children = children;
    }
}
