package ru.job4j.condition;

/**
* Класс описывает треугольник и его свойства.
*@author Moskovtseva
*@since 09.10.2017
*/
public class Triangle {
   /**
   * Первая точка.
   */
   private Point a;
   /**
   * Вторая точка.
   */
   private Point b;
   /**
   * Третья точка.
   */
   private Point c;
   /**
   * Конструктор. Что бы создать треугольник нужны три точки.
   * @param a первая точка
   * @param b вторая точка
   * @param c третья точка
   */
   public Triangle(Point a, Point b, Point c) {
       this.a = a;
       this.b = b;
       this.c = c;
    }

/**
* Метод должен вычислять расстояние между точками left и right.
*
* @param left Точка слева
* @param right Точка с права.
* @return расстояние между точками left и right.
*/
public double distance(Point left, Point right) {
    double d = Math.sqrt((Math.pow((right.getX() - left.getX()), 2) + Math.pow((right.getY() - left.getY()), 2)));
    return d;
}

/**
* Метод вычисления периметра по длинам сторон.
*
* @param ab расстояние между точками a b
* @param ac расстояние между точками a c
* @param bc расстояние между точками b c
* @return Периметр.
*/
public double perimeter(double ab, double ac, double bc) {
    return (ab + ac + bc) / 2;
}

/**
* Метод должен вычислить прощадь треугольника.
*
* @return Вернуть прощадь, если треугольник существует или -1.
*/
public double area() {
    double rsl = -1;
    double ab = this.distance(this.a, this.b);
    double ac = this.distance(this.a, this.c);
    double bc = this.distance(this.b, this.c);
    double p = this.perimeter(ab, ac, bc);
    if (this.exist(ab, ac, bc)) {
        rsl = Math.sqrt(p * (p - ab) * (p - ac) * (p - bc));
    }
    return rsl;
}
/**
* Метод проверяет можно ли построить треугольник с такими длинами сторон.
*
* @param ab Длина от точки a b.
* @param ac Длина от точки a c.
* @param bc Длина от точки b c.
* @return true  - если треугольник существует.
*/
private boolean exist(double ab, double ac, double bc) {
	boolean isABMoreThanOthersSumm = (ab < (ac + bc)) ? true : false;
	boolean isACMoreThanOthersSumm = (ac < (ab + bc)) ? true : false;
	boolean isBCMoreThanOthersSumm = (bc < (ab + ac)) ? true : false;
	return isABMoreThanOthersSumm && isACMoreThanOthersSumm && isBCMoreThanOthersSumm;
   }
}
