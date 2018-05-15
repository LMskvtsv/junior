package map;

import java.util.HashMap;
import java.util.Map;

public class WordComparator {

    /**
     * Compares set of characters between two strings.
     *
     * @param first  string
     * @param second string
     * @return true if words consist of same symbols.
     */
    public boolean containsSameSymbols(String first, String second) {
        return getMapOfChars(first).equals(getMapOfChars(second));
    }

    /**
     * Counts number of each symbol in string.
     *
     * @param string
     * @return map with char as key and number of this char in a given string as value.
     */
    private Map<Character, Integer> getMapOfChars(String string) {
        Map<Character, Integer> firstCharCounter = new HashMap<>();
        for (Character ch : string.toCharArray()) {
            if (firstCharCounter.containsKey(ch)) {
                int counter = firstCharCounter.get(ch);
                firstCharCounter.put(ch, ++counter);
            } else {
                firstCharCounter.put(ch, 1);
            }
        }
        return firstCharCounter;
    }
}
