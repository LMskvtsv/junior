package words;

import java.io.InputStream;
import java.util.Scanner;
import java.util.Set;

public class WordIndex {

    private WordTrie wordsCollection;

    /**
     * Load file with words and for every word calculates index.
     *
     * @param filename - file with words in resources folder.
     */
    public void loadFile(String filename) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(filename);
        Scanner scanner = new Scanner(is);
        wordsCollection = new WordTrie();
        while (scanner.hasNext()) {
            String word = scanner.next();
            wordsCollection.insert(word);
        }

    }

    /**
     * Get all indexes by word.
     *
     * @param searchWord - the word to search.
     * @return set of indexes found.
     */

    Set<Integer> getIndexes4Word(String searchWord) {
        Set<Integer> indexes = null;
        return indexes;
    }
}
