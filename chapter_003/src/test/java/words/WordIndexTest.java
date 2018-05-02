package wordIndex;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class WordIndexTest {
    @Test
    public void whenSeveralWords() {
        WordIndex wordIndex = new WordIndex();
        wordIndex.loadFile("Words.txt");
        Set<Integer> expected = new HashSet<>();
        expected.add(8);
        expected.add(14);
        assertThat(wordIndex.getIndexes4Word("и"), is(expected));
    }

    @Test
    public void whenOneWord() {
        WordIndex wordIndex = new WordIndex();
        wordIndex.loadFile("Words.txt");
        Set<Integer> expected = new HashSet<>();
        expected.add(6);
        assertThat(wordIndex.getIndexes4Word("солнышко"), is(expected));
    }
}
