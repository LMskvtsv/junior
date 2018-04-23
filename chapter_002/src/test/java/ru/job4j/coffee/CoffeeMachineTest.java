package ru.job4j.coffee;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CoffeeMachineTest {
    CoffeeMachine coffeeMachine;

    @Before
    public void setUp() {
        coffeeMachine = new CoffeeMachine();
    }

    @Test
    public void coinsTenOnly() {
        int[][] coins = {{10, 6}};
        coffeeMachine.fillMachineWithCoins(coins);
        int[] actual = coffeeMachine.changes(20, 70);
        int[] expected = new int[]{10, 10, 10, 10, 10};
        assertThat(actual, is(expected));
    }

    @Test
    public void coinsTenAndFive() {
        int[][] coins = {{10, 1}, {5, 1}};
        coffeeMachine.fillMachineWithCoins(coins);
        int[] actual = coffeeMachine.changes(35, 50);
        int[] expected = new int[]{10, 5};
        assertThat(actual, is(expected));
    }

    @Test
    public void coinsTenFiveAndTwo() {
        int[][] coins = {{10, 1}, {5, 3}, {2, 3}};
        coffeeMachine.fillMachineWithCoins(coins);
        int[] actual = coffeeMachine.changes(3, 20);
        int[] expected = new int[]{10, 5, 2};
        assertThat(actual, is(expected));
    }

    @Test
    public void coinsTenFiveTwoAndOne() {
        int[][] coins = {{10, 10}, {5, 10}, {2, 10}, {1, 10}};
        coffeeMachine.fillMachineWithCoins(coins);
        int[] actual = coffeeMachine.changes(2, 20);
        int[] expected = new int[]{10, 5, 2, 1};
        assertThat(actual, is(expected));
    }

    @Test
    public void coinsTwoOnly1() {
        int[][] coins = {{2, 10}};
        coffeeMachine.fillMachineWithCoins(coins);
        int[] actual = coffeeMachine.changes(6, 10);
        int[] expected = new int[]{2, 2};
        assertThat(actual, is(expected));
    }

    @Test
    public void coinsTwoOnly2() {
        int[][] coins = {{2, 10}, {10, 10}, {5, 10}, {1, 1}};
        coffeeMachine.fillMachineWithCoins(coins);
        int[] actual = coffeeMachine.changes(6, 10);
        int[] expected = new int[]{2, 2};
        assertThat(actual, is(expected));
    }

    @Test
    public void coinsTwoAndOne() {
        int[][] coins = {{2, 10}, {10, 10}, {5, 10}, {1, 1}};
        coffeeMachine.fillMachineWithCoins(coins);
        int[] actual = coffeeMachine.changes(7, 10);
        int[] expected = new int[]{2, 1};
        assertThat(actual, is(expected));
    }

    @Test
    public void noChangeTest() {
        int[][] coins = {{2, 10}, {10, 10}, {5, 10}, {1, 1}};
        coffeeMachine.fillMachineWithCoins(coins);
        int[] actual = coffeeMachine.changes(5, 5);
        int[] expected = new int[]{};
        assertThat(actual, is(expected));
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void notEnoughMoney() {
        coffeeMachine.changes(6, 5);
    }

    @Test(expected = NotEnoughCoinsException.class)
    public void notEnoughCoins() {
        int[][] coins = {{10, 3}, {5, 1}, {1, 1}};
        coffeeMachine.fillMachineWithCoins(coins);
        coffeeMachine.changes(13, 50);
    }

    @Test
    public void substitutionOneTwoCoinWithTwoOneCoins() {
        int[][] coins = {{10, 3}, {5, 1}, {1, 2}};
        coffeeMachine.fillMachineWithCoins(coins);
        int[] actual = coffeeMachine.changes(13, 50);
        int[] expected = new int[]{10, 10, 10, 5, 1, 1};
        assertThat(actual, is(expected));
    }

    @Test
    public void severalBuysEnoughCoins() {
        int[][] coins = {{10, 1}, {2, 2}, {1, 1}};
        coffeeMachine.fillMachineWithCoins(coins);
        int[] actual = coffeeMachine.changes(6, 20);
        int[] expected = new int[]{10, 2, 2};
        assertThat(actual, is(expected));
        actual = coffeeMachine.changes(5, 6);
        expected = new int[]{1};
        assertThat(actual, is(expected));
    }

    @Test(expected = NotEnoughCoinsException.class)
    public void severalBuysNotEnoughCoins() {
        int[][] coins = {{10, 1}, {2, 2}};
        coffeeMachine.fillMachineWithCoins(coins);
        int[] actual = coffeeMachine.changes(6, 20);
        int[] expected = new int[]{10, 2, 2};
        assertThat(actual, is(expected));
        coffeeMachine.changes(5, 6);
    }

    @Test(expected = NotEnoughCoinsException.class)
    public void severalBuysNotEnoughCoins2() {
        int[][] coins = {{10, 1}, {2, 2}};
        coffeeMachine.fillMachineWithCoins(coins);
        int[] actual = coffeeMachine.changes(6, 20);
        int[] expected = new int[]{10, 2, 2};
        assertThat(actual, is(expected));
        coffeeMachine.changes(4, 6);
    }
}
