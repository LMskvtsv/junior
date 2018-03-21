package ru.job4j.tracker;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * //TODO add comments.
 *
 * @author Lidiya Moskovtseva
 * @version $Id$
 * @since 0.1
 */
public class ValidateInputTest {
    private final ByteArrayOutputStream mem = new ByteArrayOutputStream();
    private final PrintStream out = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(this.mem));
    }

    @After
    public void tearDown() {
        System.setOut(this.out);
    }

    @Test
    public void whenInvalidStringInput() {
        ValidateInput input = new ValidateInput(
                new StubInput(new String[]{"invalid", "1"})
        );
        String question = "Введите пункт меню:";
        input.ask(question, new int[]{1});
        assertThat(
                this.mem.toString(),
                is(
                        new StringBuilder("Пожалуйста введите значение в формате целого числа:" + System.lineSeparator()).toString()
                )
        );
    }

    @Test
    public void whenInvalidIntInput() {
        ValidateInput input = new ValidateInput(
                new StubInput(new String[]{"-1", "1"})
        );
        String question = "Введите пункт меню:";
        input.ask(question, new int[]{1});
        assertThat(
                this.mem.toString(),
                is(
                        new StringBuilder("Пожалуйста введите верный пункт меню еще раз:" + System.lineSeparator()).toString()
                )
        );
    }
}
