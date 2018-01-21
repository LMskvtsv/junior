package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Leeda on 20/01/2018.
 */
public class ContainsCheckTest {

    /**
     * Checks if String a contains String b.
     */
    @Test
    public void startsWith() {
        ContainsCheck containsCheck = new ContainsCheck();
        String origin = "суммарный";
        String sub = "сумма";
        boolean actual = containsCheck.contains(origin, sub);
        boolean expected = true;
        assertThat(actual, is(expected));
    }

    /**
     * Checks if String origin contains String sub.
     */

    @Test
    public void inTheMiddle() {
        ContainsCheck containsCheck = new ContainsCheck();
        String origin = "проприетарный";
        String sub = "приет";
        boolean actual = containsCheck.contains(origin, sub);
        boolean expected = true;
        assertThat(actual, is(expected));
    }

    /**
     * Checks if String origin contains String sub.
     */

    @Test
    public void inTheEnd() {
        ContainsCheck containsCheck = new ContainsCheck();
        String origin = "бойкот";
        String sub = "кот";
        boolean actual = containsCheck.contains(origin, sub);
        boolean expected = true;
        assertThat(actual, is(expected));
    }

    /**
     * Checks if String origin contains String sub.
     */

    @Test
    public void doesntContains() {
        ContainsCheck containsCheck = new ContainsCheck();
        String origin = "бойкот";
        String sub = "мур";
        boolean actual = containsCheck.contains(origin, sub);
        boolean expected = false;
        assertThat(actual, is(expected));
    }

    /**
     * Checks if String origin contains String sub.
     */

    @Test
    public void repeatingGroup() {
        ContainsCheck containsCheck = new ContainsCheck();
        String origin = "бойкоткот";
        String sub = "кот";
        boolean actual = containsCheck.contains(origin, sub);
        boolean expected = true;
        assertThat(actual, is(expected));
    }

    /**
     * Checks if String origin contains String sub.
     */

    @Test
    public void oneLetterInARaw() {
        ContainsCheck containsCheck = new ContainsCheck();
        String origin = "бойкот";
        String sub = "кря";
        boolean actual = containsCheck.contains(origin, sub);
        boolean expected = false;
        assertThat(actual, is(expected));
    }

    /**
     * Checks if String origin contains String sub.
     */

    @Test
    public void severalLettersInARaw() {
        ContainsCheck containsCheck = new ContainsCheck();
        String origin = "бойкот";
        String sub = "кор";
        boolean actual = containsCheck.contains(origin, sub);
        boolean expected = false;
        assertThat(actual, is(expected));
    }

    /**
     * Checks if String origin contains String sub.
     */

    @Test
    public void subHasMoreSymbolsThanOrigin() {
        ContainsCheck containsCheck = new ContainsCheck();
        String origin = "бой";
        String sub = "бойкот";
        boolean actual = containsCheck.contains(origin, sub);
        boolean expected = false;
        assertThat(actual, is(expected));
    }

    /**
     * Checks if String origin contains String sub.
     */

    @Test
    public void subHasEqualsSymbolsThanOrigin() {
        ContainsCheck containsCheck = new ContainsCheck();
        String origin = "бойкот";
        String sub = "бойкот";
        boolean actual = containsCheck.contains(origin, sub);
        boolean expected = true;
        assertThat(actual, is(expected));
    }
}
