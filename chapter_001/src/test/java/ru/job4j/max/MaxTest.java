package ru.job4j.max;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
* Тесты для проверки работы метода max().
*/
public class MaxTest {

/**
* Проверка когда первое значение больше второго.
*/
  @Test
    public void whenSecondLessFirst() {
        Max maximum = new Max();
        int result = maximum.max(2, 1);
    	assertThat(result, is(2));
    }

/**
* Проверка когда второе значение больше первого.
*/
  @Test
    public void whenFirstLessSecond() {
        Max maximum = new Max();
        int result = maximum.max(3, 4);
        assertThat(result, is(4));
    }

/**
* Проверка когда оба значения равны.
*/
  @Test
    public void whenFirstEqualsSecond() {
        Max maximum = new Max();
        int result = maximum.max(5, 5);
        assertThat(result, is(5));
    }
}
