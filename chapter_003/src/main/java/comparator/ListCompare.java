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
            if (Character.compare(firsts[i], second[i]) > 0) {
                result = 1;
                break;
            } else if (Character.compare(firsts[i], second[i]) < 0) {
                result = -1;
                break;
            } else if (Character.compare(firsts[i], second[i]) == 0) {
                if (firsts.length > second.length) {
                    result = 1;
                } else if (firsts.length < second.length) {
                    result = -1;
                } else if (firsts.length == second.length) {
                    result = 0;
                }
            }
        }
        return result;
    }
}
