package ru.job4j.condition;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
* Тесты для проверки метода определяющего нахождение точки на прямой.
*/
public class PointTest {

/**
* Проверка когда точка находится на прямой.
*/
  @Test
    public void whenPointIsOnLine() {
        Point point = new Point(1, 3);
        boolean result = point.is(2, 1);
    	assertThat(result, is(true));
    }

/**
* Проверка когда точка не находится на прямой.
*/
  @Test
    public void whenPointIsNotOnLine() {
        Point point = new Point(1, 4);
        boolean result = point.is(2, 1);
        assertThat(result, is(false));
    }

}
