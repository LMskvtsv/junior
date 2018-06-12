package exchange;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This class stores information about Orders grouped by price.
 */
public class OrderBook {

    /**
     * Collection of orders grouped by price and sorted by price in descending order and by order quantity in ascending order.
     */
    private TreeMap<Double, TreeSet<Order>> ordersByPrice = new TreeMap<>((o1, o2) -> -Double.compare(o1, o2));

    /**
     * Returns set of all prices available.
     * @return Set<Double>
     */
    public Set<Double> getKeySet() {
        return ordersByPrice.keySet();
    }

    /**
     * Returns all orders with the given price.
     * @param price
     * @return TreeSet<Order>
     */
    public TreeSet<Order> getOrdersByPrice(Double price) {
        return ordersByPrice.get(price);
    }

    /**
     * Add new order into storage.
     * @param order
     */
    public void addOrder(Order order) {
        if (ordersByPrice.containsKey(order.getPrice())) {
            ordersByPrice.get(order.getPrice()).add(order);
        } else {
            TreeSet<Order> set = new TreeSet<>(Comparator.comparing(Order::getQtty).thenComparing(Order::getId));
            set.add(order);
            ordersByPrice.put(order.getPrice(), set);
        }
    }

    /**
     * Deletes order from storage.
     * @param order
     * @return
     */
    public boolean deleteOrder(Order order) {
        boolean result = false;
        if (ordersByPrice.containsKey(order.getPrice())) {
            result = ordersByPrice.get(order.getPrice()).remove(order);
        }
        return result;
    }
}
