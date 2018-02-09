package ru.job4j.array;

/**
 * Class for checking if word contains another word.
 */
public class ContainsCheck {

    /**
     * Checking if word contains another word.
     *
     * @param origin - string
     * @param sub    - string
     * @return boolean - if origin contains sub.
     */
    boolean contains(String origin, String sub) {
        boolean result = false;
        if (sub.length() <= origin.length()) {
            char[] originArray = origin.toCharArray();
            char[] subArray = sub.toCharArray();
            for (int i = 0; i < originArray.length; i++) {
                if (result) {
                    break;
                }
                for (int j = 0; j < subArray.length; j++) {
                    if (originArray[i] == subArray[j]) {
                        i++;
                        result = true;
                    } else {
                        result = false;
                        break;
                    }
                }
            }
        }
        return result;
    }
}
