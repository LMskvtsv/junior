package sort;

import java.util.List;
import java.util.TreeSet;

public class SortUser {
    public TreeSet<User> sort(List<User> userList) {
        TreeSet set = new TreeSet(userList);
        return set;
    }
}
