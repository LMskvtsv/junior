package map;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class WordComparatorTest {
    @Test
    public void when2WordsContainsSameSymbols() {
        WordComparator wordComparator = new WordComparator();
        assertThat(wordComparator.containsSameSymbols("мама", "амам"), is(true));
    }

    @Test
    public void whenSameWords() {
        WordComparator wordComparator = new WordComparator();
        assertThat(wordComparator.containsSameSymbols("мама", "мама"), is(true));
    }

    @Test
    public void when2WordsContainsDiffNumberOfSymbols() {
        WordComparator wordComparator = new WordComparator();
        assertThat(wordComparator.containsSameSymbols("мама", "амама"), is(false));
    }

    @Test
    public void when2WordsContainsDiffSymbols() {
        WordComparator wordComparator = new WordComparator();
        assertThat(wordComparator.containsSameSymbols("мама", "амамф"), is(false));
    }
}