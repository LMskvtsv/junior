package exchange;

import java.util.Objects;
import java.util.Random;

public class Order {

    /**
     * Уникальный ключ заявки.
     */
    private String id;

    /**
     * Идентификатор ценной бумаги, например GAZP.
     */
    private String secCode;

    /**
     * add/delete - выставить заявку на торги или снять.
     */
    private int type;

    /**
     * bid/ask - заявка имеет два направления. Заявка на покупка ценной бумаги или на продажу.
     */
    private int direction;

    /**
     * Цена, по которой мы ходим сделать действия покупки или продажи.
     */
    private double price;

    /**
     * Количество акций, которые мы хотим продать или купить.
     */
    private int qtty;

    public Order(String secCode, int type, int direction, double price, int qtty) {
        this.secCode = secCode;
        this.type = type;
        this.direction = direction;
        this.price = price;
        this.qtty = qtty;
        this.id = generateId();

    }

    private static final Random RND = new Random();

    /**
     * Метод генерирует уникальный ключ для заявки.
     *
     * @return Уникальный ключ.
     */
    private String generateId() {
        String id = System.currentTimeMillis() + String.valueOf(RND.nextInt(100));
        return id;
    }

    public String getId() {
        return id;
    }

    public String getSecCode() {
        return secCode;
    }

    public int getType() {
        return type;
    }

    public int getDirection() {
        return direction;
    }

    public double getPrice() {
        return price;
    }

    public int getQtty() {
        return qtty;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQtty(int qtty) {
        this.qtty = qtty;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        return builder.append("Order{id=").append(id)
                .append(", secCode=").append(secCode)
                .append(", type=").append(type)
                .append(", direction=").append(direction)
                .append(", price=").append(price)
                .append(", qtty=").append(qtty)
                .append("}").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
