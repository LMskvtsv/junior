package wordIndex;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class WordIndex {

    private HashMap<String, HashSet<Integer>> wordsCollection = new HashMap<>();
    private int wordIndex = 0;

    /**
     * Load file with words and for every word calculates index.
     *
     * @param filename - file with words in resources folder.
     */
    public void loadFile(String filename) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(filename);
        Scanner scanner = new Scanner(is);
        while (scanner.hasNext()) {
            String word = scanner.next();
            if (!wordsCollection.containsKey(word)) {
                wordsCollection.put(word, new HashSet<>());
                wordsCollection.get(word).add(wordIndex++);
            } else {
                wordsCollection.get(word).add(wordIndex++);
            }
        }
    }

    /**
     * Get all indexes by word.
     *
     * @param searchWord - the word to search.
     * @return set of indexes found.
     */

    Set<Integer> getIndexes4Word(String searchWord) {
        Set<Integer> indexes = wordsCollection.get(searchWord);
        if(indexes.size() == 0){
            indexes = null;
        }
        return indexes;
    }
}
