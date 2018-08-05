package ru.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * All trades in incoming json object should be stored in array. This object represents the array of trades.
 */
public class Trades {

    private List<Trade> trades = new ArrayList<Trade>();

    public List<Trade> getTrades() {
        return trades;
    }

    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }
}
