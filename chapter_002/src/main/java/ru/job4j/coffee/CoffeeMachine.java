package ru.job4j.coffee;

import java.util.Arrays;

public class CoffeeMachine {

    private int[] distinctNominalValues = new int[100];
    int nominalValuesCounter = 0;

    private int[] change;
    private int changePositionCounter;

    private int[][] coinsStorage;

    /**
     * This method fills the coffee machine with coins for giving a change.
     * Also fills distinctNominalValues array with distinct values of nominal values.
     * @param coinsStorage - two-dimensional array, where each sub-array consist of two numbers.
     *                    The first number - nominal of the coin
     *                    and the second number - quantity of coins. For example {{10, 1}, {1, 1}, {5, 3}}
     *                    means that you want to put into the machine one '10' coin, one '1' coin and three '5' coins.
     *                    Nominal values can go in any order.
     */
    public void fillMachineWithCoins(int[][] coinsStorage) {
        this.coinsStorage = sortByNominalValues(coinsStorage);
        for (int i = 0; i < coinsStorage.length; i++) {
            int nominal = coinsStorage[i][0];
            distinctNominalValues[nominalValuesCounter++] = nominal;
        }
        distinctNominalValues = Arrays.stream(distinctNominalValues).filter(z -> z != 0).toArray();
    }

    /**
     * This method sorts a two two-dimensional array by nominal values of coins in ascending order.
     * @param coinsStorage - two-dimensional array, where each sub-array consist of two numbers.
     *                    The first number - nominal value of the coin
     *                    and the second number - quantity of coins of a given nominal value. For example {{10, 1}, {1, 1}, {5, 3}}
     *                    means that you want to put into the machine one coin with nominal value = '10', one coin with nominal value = '1'
     *                    and three coins with nominal value =  '5'. Nominal values can go in any order.
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
     * @param price - price of the coffee.
     * @param value - money paid for the coffee.
     * @return change as an array of coins.
     * @throws NotEnoughMoneyException - if price higher then the amount of money.
     * @throws NotEnoughCoinsException - if machine cannot give change because it doesn't have enough coins.
     */

    public int[] changes(int price, int value) throws NotEnoughMoneyException, NotEnoughCoinsException {
        change = new int[100];
        changePositionCounter = 0;
        int sumLeft = value - price;
        if (sumLeft < 0) {
            throw new NotEnoughMoneyException("Not enough money. Please check price: " + price);
        }
        boolean enoughCoins = true;

        for (int i = 0; i < distinctNominalValues.length; i++) {
            sumLeft = addCoinsToChange(sumLeft, distinctNominalValues[i]);
            if (i == distinctNominalValues.length - 1 && sumLeft != 0) {
                enoughCoins = false;
            }
        }

        if (!enoughCoins) {
            throw new NotEnoughCoinsException("Cannot give all change! Not enough distinctNominalValues.");
        }
        change = Arrays.stream(change).filter(i -> i != 0).toArray();
        return change;
    }

    /**
     * This method adds all possible coins into change's array with the given nominal value for the given amount of money.
     * @param sumLeft - given amount of money we need to accumulate for the change.
     * @param nominal - nominal value of coins we need to calculate.
     * @return - amount of money left after we are done with calculations.
     * At first it calculates number of coins available, then number of coins is needed. If there is no coins with given
     * nominal value available - method returns the initial amount of money. Otherwise, it checks if there is enough coins. If
     * coins not enough - it adds all possible coins to change's array and returns the amount left.
     */
    private int addCoinsToChange(int sumLeft, int nominal) {
        int left = 0;
        int availableCoins = getNumberOfCoins(nominal);
        int toGiveCoins;
        int diff;
        int numberOfCoins;
        if (availableCoins == 0) {
            left = sumLeft;
        } else {
            if (sumLeft % nominal == 0) {
                toGiveCoins = sumLeft / nominal;
                diff = availableCoins - toGiveCoins;
                numberOfCoins = sumLeft / nominal;
            } else {
                left = sumLeft % nominal;
                toGiveCoins = (sumLeft - left) / nominal;
                diff = availableCoins - toGiveCoins;
                numberOfCoins = (sumLeft - left) / nominal;
            }
            if (diff >= 0) {
                for (int i = 0; i < numberOfCoins; i++) {
                    change[changePositionCounter++] = nominal;
                    removeOneCoin(nominal);
                }
            } else {
                for (int i = 0; i < availableCoins; i++) {
                    change[changePositionCounter++] = nominal;
                    removeOneCoin(nominal);
                }
                left = sumLeft - (availableCoins * nominal);
            }
        }
        return left;
    }

    /**
     * Method searches number of coins through the coins storage.
     * @param nominal - nominal value of coin.
     * @return - the number of coins of a given nominal value
     */
    private int getNumberOfCoins(int nominal) {
        int numberOfCoins = 0;
        for (int i = 0; i < coinsStorage.length; i++) {
            if (nominal == coinsStorage[i][0]) {
                numberOfCoins = coinsStorage[i][1];
                break;
            }
        }
        return numberOfCoins;
    }

    /**
     * Decreases number of coins of a given nominal value by 1 in a coins storage.
     * @param nominal - nominal value of coin.
     */
    private void removeOneCoin(int nominal) {
        for (int i = 0; i < coinsStorage.length; i++) {
            if (nominal == coinsStorage[i][0] && coinsStorage[i][1] > 0) {
                coinsStorage[i][1]--;
                break;
            }
        }
    }
}
