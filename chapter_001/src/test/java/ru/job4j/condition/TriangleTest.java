package ru.job4j.condition;

import org.junit.Test;

import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

/**
* Тесты для проверки методов класса Треугольник.
*/
public class TriangleTest {

/**
* Проверка расчета площади  когда треугольник существует.
*/
  @Test
    public void areaTestTriangleExists() {
        Point first  = new Point(0, 0);
	Point second = new Point(0, 2);
	Point third = new Point(2, 0);
	Triangle triangle = new Triangle(first, second, third);
        double result = triangle.area();
	double expected = 2D;
    	assertThat(result, closeTo(expected, 0.1));
    }

/**
* Проверка расчета площади когда треугольник не существует.
*/
  @Test
    public void areaTestTriangleIsNotExist() {
        Point first  = new Point(0, 0);
        Point second = new Point(0, 2);
        Point third = new Point(0, 1);
        Triangle triangle = new Triangle(first, second, third);
        double result = triangle.area();
        double expected = (double) -1;
        assertThat(result, closeTo(expected, 0.1));
    }

/**
* Проверка расчета периметра  треугольника.
*/
  @Test
    public void perimeterTest() {
        Point first  = new Point(0, 0);
        Point second = new Point(0, 2);
        Point third = new Point(0, 1);
        Triangle triangle = new Triangle(first, second, third);
        double ab = triangle.distance(first, second);
        double ac = triangle.distance(first, third);
        double bc = triangle.distance(second, third);
        double p = triangle.perimeter(ab, ac, bc);
        double expected = (double) 2;
        assertThat(p, closeTo(expected, 0.1));
    }
/**
* Проверка расчета сторон треугольника.
*/
  @Test
    public void distanceTest() {
        Point first  = new Point(0, 0);
        Point second = new Point(0, 2);
        Point third = new Point(0, 1);
        Triangle triangle = new Triangle(first, second, third);
        double ab = triangle.distance(first, second);
        double ac = triangle.distance(first, third);
        double bc = triangle.distance(second, third);
        double expectedAB = (double) 2;
	double expectedAC = (double) 1;
	double expectedBC = (double) 1;
        assertThat(ab, closeTo(expectedAB, 0.1));
	assertThat(ac, closeTo(expectedAC, 0.1));
	assertThat(bc, closeTo(expectedBC, 0.1));
    }
}
