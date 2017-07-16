package ru.job4j.calculator;

/**
  *Класс Calculate. Выполняет простейшие арифметические операции.
  *@author Moskovtseva
  *@since 16.07.2017
*/

public class Calculator {

/**
* Переменная хранит результат только последнего вычисления. По умолчанию = 0.0.
*/
    private double result;

/**
  * Сложение 2-х чисел с плавающей точкой.
  * @param first первое число
  * @param second второе число
*/
    public void add(double first, double second) {
        this.result = first + second;
    }

/**
  * Вычитание  чисел с плавающей точкой.
  * @param first первое число
  * @param second второе число
*/
     public void substruct(double first, double second) {
        this.result = first - second;
    }

/**
  * Деление 2-х чисел с плавающей точкой.
  * @param first первое число
  * @param second второе число
*/
     public void div(double first, double second) {
        this.result = first / second;
    }
/**
  * Умножение 2-х чисел с плавающей точкой.
  * @param first первое число
  * @param second второе число
*/

     public void multiple(double first, double second) {
        this.result = first * second;
    }

/**
  * Получение текущего значения поля result.
  *
  * @return result поле result
*/
    public double getResult() {
        return this.result;
    }
}
