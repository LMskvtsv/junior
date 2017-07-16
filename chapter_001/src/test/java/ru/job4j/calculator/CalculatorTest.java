package ru.job4j.calculator;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
* Тесты для проверки работы простого калькулятора.
*/
public class CalculatorTest {

/**
* Проверка сложения.
*/
  @Test
    public void whenAddOnePlusOneThenTwo() {
        Calculator calc = new Calculator();
        calc.add(1D, 1D);
        double result = calc.getResult();
        double expected = 2D;
        assertThat(result, is(expected));
    }

/**
* Проверка вычитания.
*/
  @Test
    public void whenSubstructTwoMinusOneThenOne() {
        Calculator calc = new Calculator();
        calc.substruct(2D, 1D);
        double result = calc.getResult();
        double expected = 1D;
        assertThat(result, is(expected));
    }

/**
* Проверка деления.
*/
  @Test
    public void whenDivFourByTwoThenTwo() {
        Calculator calc = new Calculator();
        calc.div(4D, 2D);
        double result = calc.getResult();
        double expected = 2D;
        assertThat(result, is(expected));
    }

/**
* Проверка умножения.
*/
  @Test
    public void whenFiveMultipleWithTwoThenTen() {
        Calculator calc = new Calculator();
        calc.multiple(5D, 2D);
        double result = calc.getResult();
        double expected = 10D;
        assertThat(result, is(expected));
    }
}
