package ru.job4j.max;

/**
 * Класс для задания на нахождения максимума из 2-х чисел.
 *
 *@author Moskovtseva
 *@since 16.07.2017
 */

public class Max {

/**
  * Нахождение максимума из 2-х чисел. Так как нужно использовать тернарный оператор в случе когда числа равны будет возвращаться второе число.
  * @param first первое число
  * @param second второе число
  * @return максимум из 2-х чисел.
  */
   public int max(int first, int second) {
      return first > second ? first : second;
   }
/**
  * Нахождение максимума из 3-х чисел.
  * @param first первое число
  * @param second второе число
  * @param third третье число
  * @return максимум из 3-х чисел.
  */
   public int max(int first, int second, int third) {
      return max(first, max(second, third));
   }
}
