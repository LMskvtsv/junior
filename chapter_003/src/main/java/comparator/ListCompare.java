package comparator;

import java.util.Comparator;

public class ListCompare implements Comparator<String> {
    @Override
    public int compare(String left, String right) {
        char[] firsts = left.toCharArray();
        char[] second = right.toCharArray();

        int result = 0;
        int counter = firsts.length < second.length ? firsts.length : second.length;

        for (int i = 0; i < counter; i++) {
            result = Character.compare(firsts[i], second[i]);
            if (result != 0) {
                break;
            }
        }
        if (result == 0 && firsts.length != second.length) {
            result = Integer.compare(firsts.length, second.length);
        }

        return result;
    }
}
