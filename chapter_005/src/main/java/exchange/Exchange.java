package exchange;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Class was created to store information about incoming orders and
 * calculate depth of market in accordance with existing orders.
 */
public class Exchange {

    /**
     * Field to store all buy orders grouped by Security Code.
     */
    private Map<String, HashSet<Order>> groupedBySecCodeBuy = new HashMap();
    /**
     * Field to store all sell orders grouped by Security Code.
     */
    private Map<String, HashSet<Order>> groupedBySecCodeSell = new HashMap();
    /**
     * Field to store all buy orders grouped by Price.
     */
    private Map<Double, HashSet<Order>> groupedByPriceBuy;
    /**
     * Field to store all sell orders grouped by Price.
     */
    private Map<Double, HashSet<Order>> groupedByPriceSell;


    /**
     * Process order in accordance to its Order Type.
     *
     * @param order - new incoming order.
     */
    public void processOrder(Order order) {
        if (order.getType() == OrderType.NEW_ORDER) {
            addOrder(order);
        } else {
            deleteOrder(order);
        }
    }

    /**
     * Adds order into collection in case it was not completely crossed with other orders with opposite direction.
     *
     * @param newOrder order to add.
     */
    private void addOrder(Order newOrder) {
        Map<String, HashSet<Order>> orders = (newOrder.getDirection() == Direction.BUY) ? groupedBySecCodeBuy : groupedBySecCodeSell;
        if (checkOppositeDirection(newOrder)) {
            if (orders.containsKey(newOrder.getSecCode())) {
                orders.get(newOrder.getSecCode()).add(newOrder);
            } else {
                HashSet<Order> list = new HashSet<>();
                list.add(newOrder);
                orders.put(newOrder.getSecCode(), list);
            }
            System.out.printf("%s was added.%s", newOrder, System.lineSeparator());
        } else {
            System.out.printf("%s was crossed immediately.%s", newOrder, System.lineSeparator());
        }
    }

    /**
     * Checks if order can be crossed with other orders which have opposite direction. If order can be crossed,
     * then calculates  qtty left and removes order which were fully filled.
     *
     * @param newOrder order to check.
     * @return True if order is needed to be deleted still. If order was completely crossed than there is no reason to add it.
     */
    private boolean checkOppositeDirection(Order newOrder) {
        boolean needToAdd = true;
        TreeSet<Order> samePriceOrders = getSamePriceOrders(newOrder);
        int qttyLeft = newOrder.getQtty();
        if (!samePriceOrders.isEmpty()) {
            LinkedList<Order> ordersToRemove = new LinkedList<>();
            for (Order o : samePriceOrders) {
                qttyLeft = qttyLeft - o.getQtty();
                if (qttyLeft > 0) {
                    ordersToRemove.add(o);
                    System.out.printf("%s was crossed with %s%s", newOrder, o, System.lineSeparator());
                    newOrder.setQtty(qttyLeft);
                } else if (qttyLeft == 0) {
                    ordersToRemove.add(o);
                    System.out.printf("%s was crossed with %s%s", newOrder, o, System.lineSeparator());
                    newOrder.setQtty(qttyLeft);
                    needToAdd = false;
                } else {
                    o.setQtty(Math.abs(qttyLeft));
                    newOrder.setQtty(0);
                    needToAdd = false;
                    break;
                }
            }
            if (!ordersToRemove.isEmpty()) {
                ordersToRemove.forEach((Order o) -> deleteOrder(o));
            }
        }
        return needToAdd;
    }

    /**
     * Finds orders with the same price from orders with opposite direction.
     *
     * @param newOrder - order to retrive price and direction to search.
     * @return list of orders sorted by qtty in ascending order.
     */
    private TreeSet<Order> getSamePriceOrders(Order newOrder) {
        TreeSet<Order> samePriceOrders = new TreeSet<>(Comparator.comparing(Order::getQtty).thenComparing(Order::getId));
        Map<String, HashSet<Order>> ordersToCheck;
        ordersToCheck = (newOrder.getDirection() == Direction.BUY) ? groupedBySecCodeSell : groupedBySecCodeBuy;
        String secCode = newOrder.getSecCode();
        if (!ordersToCheck.isEmpty() && ordersToCheck.get(secCode) != null) {
            for (Order o : ordersToCheck.get(secCode)) {
                if (o.getPrice() == newOrder.getPrice()) {
                    samePriceOrders.add(o);
                }
            }
        }
        return samePriceOrders;

    }

    /**
     * Delete order with the same id.
     *
     * @param order
     * @return
     */
    private boolean deleteOrder(Order order) {
        boolean result;
        Map<String, HashSet<Order>> orders;
        if (order.getDirection() == Direction.BUY) {
            orders = groupedBySecCodeBuy;
        } else {
            orders = groupedBySecCodeSell;
        }
        result = orders.get(order.getSecCode()).remove(order);
        String log;
        if (result) {
            log = String.format("%s was deleted.", order);
        } else {
            log = String.format("Did not find order id: %s.", order.getId());
        }
        System.out.println(log);
        return result;
    }

    /**
     * Print depth of market into console.
     *
     * @param secCode
     */
    public void showDepthOfMarket(String secCode) {
        groupedByPriceBuy = groupByPrice(secCode, groupedBySecCodeBuy);
        groupedByPriceSell = groupByPrice(secCode, groupedBySecCodeSell);
        String leftAlignFormat = "| %-10s | %-10s | %-10s |%n";
        System.out.format("Depth of Market: %s%s", secCode, System.lineSeparator());
        System.out.format("+--------------------------------------+%n");
        System.out.format("| Buy        | Price      | Sell       |%n");
        System.out.format("+--------------------------------------+%n");
        printOrders(leftAlignFormat, groupedByPriceBuy, Direction.BUY);
        printOrders(leftAlignFormat, groupedByPriceSell, Direction.SELL);
        System.out.format("+--------------------------------------+%n");
    }

    /**
     * Print collection with pre-defined format.
     *
     * @param format
     * @param groupedByPriceSell
     * @param direction
     */
    private void printOrders(String format, Map<Double, HashSet<Order>> groupedByPriceSell, int direction) {
        if (!groupedByPriceSell.isEmpty()) {
            for (Double d : groupedByPriceSell.keySet()) {
                int qtty = 0;
                for (Order o : groupedByPriceSell.get(d)) {
                    qtty = qtty + o.getQtty();
                }
                if (direction == Direction.SELL) {
                    System.out.format(format, "", String.valueOf(d), String.valueOf(qtty));
                } else {
                    System.out.format(format, String.valueOf(qtty), String.valueOf(d), "");
                }
            }
        }
    }

    /**
     * Gets order with pre-defined secCode and groups them by price.
     *
     * @param secCode - security code to select orders.
     * @param orders  - all orders grouped by security code.
     * @return - selected orders grouped by price.
     */
    private Map<Double, HashSet<Order>> groupByPrice(String secCode, Map<String, HashSet<Order>> orders) {
        HashSet<Order> groupedBySecCode = orders.get(secCode);
        Map<Double, HashSet<Order>> groupedByPrice = new TreeMap<>((o1, o2) -> -Double.compare(o1, o2));
        if (groupedBySecCode != null) {
            for (Order o : groupedBySecCode) {
                if (groupedByPrice.containsKey(o.getPrice())) {
                    groupedByPrice.get(o.getPrice()).add(o);
                } else {
                    HashSet<Order> list = new HashSet<>();
                    list.add(o);
                    groupedByPrice.put(o.getPrice(), list);
                }
            }
        }
        return groupedByPrice;
    }
}
