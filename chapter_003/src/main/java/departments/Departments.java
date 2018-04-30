package departments;

import java.util.Comparator;
import java.util.TreeSet;

public class Departments {

    private TreeSet<String> departments = new TreeSet<>();

    /**
     * Adds distinct department into departments collection in Natural Order.
     *
     * @param values - array of department paths. Department path can be represented like: "K1\SK1\SSK1", split with '\'
     */
    public void addInNaturalOrder(String[] values) {
        for (String s : values) {
            departments.add(s);
            while (s.lastIndexOf("\\") > -1) {
                int i = s.lastIndexOf("\\");
                s = s.substring(0, i);
                departments.add(s);
            }
        }
    }

    public TreeSet<String> getDepartments() {
        return departments;
    }

    /**
     * Creates new object with Comparator required.
     *
     * @param departments which need to be sorted.
     * @return - sorted departments.
     */
    public TreeSet<String> sort(TreeSet<String> departments, Comparator<String> comparator) {
        TreeSet<String> newest = new TreeSet<>(comparator);
        newest.addAll(departments);
        return newest;
    }
}
