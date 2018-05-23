package iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Converter {
    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        List<Integer> list = new ArrayList<>();
        it.forEachRemaining(i -> i.forEachRemaining(list::add));
        return list.iterator();
    }
}
