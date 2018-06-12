package exchange;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeSet;

/**
 * Class was created to store information about incoming orders and
 * calculate depth of market in accordance with existing orders.
 */
public class Exchange {

    /**
     * Field to store all buy orders grouped by Security Code.
     */
    private Map<String, OrderBook> ordersBuy = new HashMap();
    /**
     * Field to store all sell orders grouped by Security Code.
     */
    private Map<String, OrderBook> ordersSell = new HashMap();


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
        Map<String, OrderBook> orders = (newOrder.getDirection() == Direction.BUY) ? ordersBuy : ordersSell;
        if (checkOppositeDirection(newOrder)) {
            if (orders.containsKey(newOrder.getSecCode())) {
                orders.get(newOrder.getSecCode()).addOrder(newOrder);
            } else {
                OrderBook book = new OrderBook();
                book.addOrder(newOrder);
                orders.put(newOrder.getSecCode(), book);
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
                ordersToRemove.forEach(this::deleteOrder);
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
        TreeSet<Order> orders = new TreeSet<>();
        Map<String, OrderBook> ordersToCheck = (newOrder.getDirection() == Direction.BUY) ? ordersSell : ordersBuy;
        if (ordersToCheck.containsKey(newOrder.getSecCode())) {
            OrderBook book = ordersToCheck.get(newOrder.getSecCode());
            if (book != null) {
                if (book.getOrdersByPrice(newOrder.getPrice()) != null) {
                    orders = book.getOrdersByPrice(newOrder.getPrice());
                }
            }
        }
        return orders;
    }

    /**
     * Delete order with the same id.
     *
     * @param order
     * @return
     */
    private boolean deleteOrder(Order order) {
        boolean result;
        Map<String, OrderBook> orders;
        if (order.getDirection() == Direction.BUY) {
            orders = ordersBuy;
        } else {
            orders = ordersSell;
        }
        result = orders.get(order.getSecCode()).deleteOrder(order);
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
        String leftAlignFormat = "| %-10s | %-10s | %-10s |%n";
        System.out.format("Depth of Market: %s%s", secCode, System.lineSeparator());
        System.out.format("+--------------------------------------+%n");
        System.out.format("| Buy        | Price      | Sell       |%n");
        System.out.format("+--------------------------------------+%n");
        printOrders(leftAlignFormat, Direction.BUY, ordersBuy.get(secCode));
        printOrders(leftAlignFormat, Direction.SELL, ordersSell.get(secCode));
        System.out.format("+--------------------------------------+%n");
    }

    /**
     * Print collection with pre-defined format.
     *
     * @param format
     * @param orderBook
     * @param direction
     */
    private void printOrders(String format, int direction, OrderBook orderBook) {
        if (orderBook != null && !orderBook.getKeySet().isEmpty()) {
            for (Double d : orderBook.getKeySet()) {
                if (orderBook.getOrdersByPrice(d) != null) {
                    int qtty = 0;
                    for (Order o : orderBook.getOrdersByPrice(d)) {
                        qtty = qtty + o.getQtty();
                    }
                    if (qtty > 0) {
                        if (direction == Direction.SELL) {
                            System.out.format(format, "", String.valueOf(d), String.valueOf(qtty));
                        } else {
                            System.out.format(format, String.valueOf(qtty), String.valueOf(d), "");
                        }
                    }
                }
            }
        }
    }
}
