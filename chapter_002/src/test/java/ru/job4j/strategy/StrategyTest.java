package ru.job4j.strategy;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StrategyTest {
    @Test
    public void drawTriangle() {
        Triangle triangle = new Triangle();
        assertThat(triangle.draw(), is(new StringBuilder()
                .append("  +\n")
                .append(" +++\n")
                .append("+++++\n")
                .toString()));
    }

    @Test
    public void drawSquare() {
        Square square = new Square();
        assertThat(square.draw(), is(new StringBuilder()
                .append("++++\n")
                .append("+  +\n")
                .append("+  +\n")
                .append("++++\n")
                .toString()));
    }

    @Test
    public void paintSquare() {
        PrintStream stdout = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        new Paint().draw(new Square());
        assertThat(
                new String(out.toByteArray()),
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
        System.setOut(stdout);
    }

    @Test
    public void paintTriangle() {
        PrintStream stdout = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        new Paint().draw(new Triangle());
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append("  +\n")
                                .append(" +++\n")
                                .append("+++++\n")
                                .append(System.lineSeparator())
                                .toString()
                )
        );
        System.setOut(stdout);
    }
}
