package sort;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class SortUser {
    public TreeSet<User> sort(List<User> userList) {
        TreeSet set = new TreeSet(userList);
        return set;
    }

    public List<User> sortNameLength(List<User> list) {
        Comparator<User> c = (User o1, User o2) -> (Integer.compare(o1.getName().length(), o2.getName().length()));
        list.sort(c);
        return list;
    }

    public List<User> sortByAllFields(List<User> list) {
        Comparator<User> c = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                int nameResult = o1.compareTo(o2);
                return nameResult == 0 ? Integer.compare(o1.getAge(), o2.getAge()) : nameResult;
            }
        };
        list.sort(c);
        return list;
    }
}
