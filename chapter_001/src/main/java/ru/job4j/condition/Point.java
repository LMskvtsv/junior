package ru.job4j.condition;

/**
*Класс описывает точку в системе координат.
*@author Moskovtseva
*@since 18.07.2017
*/
public class Point {
   /**
   * Координата по оси X.
   */
   private int x;

   /**
   * Координата по оси Y.
   */
   private int y;

   /**
   *Конструктор, инициализирующий координаты по оси Х и У.
   * @param x - координата Х.
   * @param y - координата У.
   */
   public  Point(int x, int y) {
      this.x = x;
      this.y = y;
  }

  /**
  * Геттер для координаты Х.
  * @return координату Х.
  */
   public int getX() {
      return this.x;
  }

  /**
  * Геттер для координаты У.
  * @return координату У.
  */
  public int getY() {
     return this.y;
  }

/**
* Метод проверяет нахождение точки на заданной передаваемыми параметрами прямой.
* @param a - угол.
* @param b - точка пересечения оси У.
* @return находится ли точка на прямой.
*/
  public boolean is(int a, int b) {
    return this.getY() == a * this.getX() + b;
 }
}
