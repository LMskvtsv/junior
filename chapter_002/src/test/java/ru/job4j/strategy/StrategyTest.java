package ru.job4j.strategy;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StrategyTest {
    private PrintStream stdout = System.out;
    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void tearDown() {
        System.setOut(this.stdout);
    }

    @Test
    public void drawTriangle() {
        assertThat(new Triangle().draw(), is(new StringBuilder()
                .append("  +\n")
                .append(" +++\n")
                .append("+++++\n")
                .toString()));
    }

    @Test
    public void drawSquare() {
        assertThat(new Square().draw(), is(new StringBuilder()
                .append("++++\n")
                .append("+  +\n")
                .append("+  +\n")
                .append("++++\n")
                .toString()));
    }

    @Test
    public void paintSquare() {
        new Paint().draw(new Square());
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append("++++\n")
                                .append("+  +\n")
                                .append("+  +\n")
                                .append("++++\n")
                                .append(System.lineSeparator())
                                .toString()
                )
        );
    }

    @Test
    public void paintTriangle() {
        new Paint().draw(new Triangle());
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append("  +\n")
                                .append(" +++\n")
                                .append("+++++\n")
                                .append(System.lineSeparator())
                                .toString()
                )
        );
    }
}
