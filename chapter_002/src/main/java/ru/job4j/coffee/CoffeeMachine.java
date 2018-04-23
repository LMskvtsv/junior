package ru.job4j.coffee;

import java.util.Arrays;

public class CoffeeMachine {

    private int[] change;
    private int[][] coinsStorage;

    /**
     * This method fills the coffee machine with coins for giving a change.
     * Also fills distinctNominalValues array with distinct values of nominal values.
     *
     * @param coinsStorage - two-dimensional array, where each sub-array consist of two numbers.
     *                     The first number - nominal of the coin
     *                     and the second number - quantity of coins. For example {{10, 1}, {1, 1}, {5, 3}}
     *                     means that you want to put into the machine one '10' coin, one '1' coin and three '5' coins.
     *                     Nominal values can go in any order.
     */
    public void fillMachineWithCoins(int[][] coinsStorage) {
        this.coinsStorage = sortByNominalValues(coinsStorage);
    }

    /**
     * This method sorts a two two-dimensional array by nominal values of coins in ascending order.
     *
     * @param coinsStorage - two-dimensional array, where each sub-array consist of two numbers.
     *                     The first number - nominal value of the coin
     *                     and the second number - quantity of coins of a given nominal value. For example {{10, 1}, {1, 1}, {5, 3}}
     *                     means that you want to put into the machine one coin with nominal value = '10', one coin with nominal value = '1'
     *                     and three coins with nominal value =  '5'. Nominal values can go in any order.
     * @return - two two-dimensional array sorted by nominal values.
     */
    private int[][] sortByNominalValues(int[][] coinsStorage) {
        int[] temp;
        boolean isSorted = true;
        while (isSorted) {
            isSorted = false;
            for (int i = 0; i < coinsStorage.length - 1; i++) {
                if (coinsStorage[i][0] < coinsStorage[i + 1][0]) {
                    temp = coinsStorage[i];
                    coinsStorage[i] = coinsStorage[i + 1];
                    coinsStorage[i + 1] = temp;
                    isSorted = true;
                }
            }
        }
        return coinsStorage;
    }

    /**
     * For every distinct nominal value machine adds possible number of coins to 'change' array.
     * Always starts with the highest nominal value to give minimum number of coins.
     *
     * @param price - price of the coffee.
     * @param value - money paid for the coffee.
     * @return change as an array of coins.
     * @throws NotEnoughMoneyException - if price higher then the amount of money.
     * @throws NotEnoughCoinsException - if machine cannot give change because it doesn't have enough coins.
     */

    public int[] changes(int price, int value) throws NotEnoughMoneyException, NotEnoughCoinsException {
        change = new int[100];
        int changePositionCounter = 0;
        int sumLeft = value - price;
        if (sumLeft < 0) {
            throw new NotEnoughMoneyException("Not enough money. Please check price: " + price);
        }

        for (int i = 0; i < coinsStorage.length; i++) {
            int nominal = coinsStorage[i][0];
            if (sumLeft >= nominal) {
                int availableQty = Math.min(sumLeft / nominal, coinsStorage[i][1]);
                for (int j = 0; j < availableQty; j++) {
                    change[changePositionCounter++] = nominal;
                    sumLeft = sumLeft - nominal;
                    coinsStorage[i][1]--;
                }
            }
            if (sumLeft == 0) {
                break;
            }
        }

        if (sumLeft != 0) {
            throw new NotEnoughCoinsException("Cannot give all change! Not enough distinctNominalValues.");
        }
        change = Arrays.stream(change).filter(i -> i != 0).toArray();
        return change;
    }
}
