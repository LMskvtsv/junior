package exchange;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class ExchangeTest {

    private PrintStream stdout = System.out;
    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void tearDown() {
        System.setOut(this.stdout);
    }


    @Test
    public void whenDiffBuyOrdersThenAllExist() {
        Exchange ex = new Exchange();
        String secCode = "LKOH";
        Order order1 = new Order(secCode, OrderType.NEW_ORDER, Direction.BUY, 11.0, 5);
        Order order2 = new Order(secCode, OrderType.NEW_ORDER, Direction.BUY, 11.5, 6);
        Order order3 = new Order(secCode, OrderType.NEW_ORDER, Direction.BUY, 11.7, 7);
        ex.processOrder(order1);
        ex.processOrder(order2);
        ex.processOrder(order3);
        ex.showDepthOfMarket(secCode);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(order1).append(" was added.").append(System.lineSeparator())
                                .append(order2).append(" was added.").append(System.lineSeparator())
                                .append(order3).append(" was added.").append(System.lineSeparator())
                                .append("Depth of Market: ").append(secCode).append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .append("| Buy        | Price      | Sell       |").append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .append("| 7          | 11.7       |            |").append(System.lineSeparator())
                                .append("| 6          | 11.5       |            |").append(System.lineSeparator())
                                .append("| 5          | 11.0       |            |").append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .toString()
                )
        );
    }

    @Test
    public void whenDiffSellOrdersThenAllExist() {
        Exchange ex = new Exchange();
        String secCode = "LKOH";
        Order order1 = new Order(secCode, OrderType.NEW_ORDER, Direction.SELL, 11.0, 8);
        Order order2 = new Order(secCode, OrderType.NEW_ORDER, Direction.SELL, 11.5, 9);
        Order order3 = new Order(secCode, OrderType.NEW_ORDER, Direction.SELL, 11.7, 10);
        ex.processOrder(order1);
        ex.processOrder(order2);
        ex.processOrder(order3);
        ex.showDepthOfMarket(secCode);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(order1).append(" was added.").append(System.lineSeparator())
                                .append(order2).append(" was added.").append(System.lineSeparator())
                                .append(order3).append(" was added.").append(System.lineSeparator())
                                .append("Depth of Market: ").append(secCode).append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .append("| Buy        | Price      | Sell       |").append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .append("|            | 11.7       | 10         |").append(System.lineSeparator())
                                .append("|            | 11.5       | 9          |").append(System.lineSeparator())
                                .append("|            | 11.0       | 8          |").append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .toString()
                )
        );
    }

    @Test
    public void whenDiffBuySellOrdersThenAllExist() {
        Exchange ex = new Exchange();
        String secCode = "LKOH";
        Order order1 = new Order(secCode, OrderType.NEW_ORDER, Direction.SELL, 11.0, 11);
        Order order2 = new Order(secCode, OrderType.NEW_ORDER, Direction.SELL, 11.5, 12);
        Order order3 = new Order(secCode, OrderType.NEW_ORDER, Direction.SELL, 11.7, 13);
        Order order4 = new Order(secCode, OrderType.NEW_ORDER, Direction.BUY, 11.1, 14);
        Order order5 = new Order(secCode, OrderType.NEW_ORDER, Direction.BUY, 11.6, 15);
        Order order6 = new Order(secCode, OrderType.NEW_ORDER, Direction.BUY, 11.8, 16);
        ex.processOrder(order1);
        ex.processOrder(order2);
        ex.processOrder(order3);
        ex.processOrder(order4);
        ex.processOrder(order5);
        ex.processOrder(order6);
        ex.showDepthOfMarket(secCode);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(order1).append(" was added.").append(System.lineSeparator())
                                .append(order2).append(" was added.").append(System.lineSeparator())
                                .append(order3).append(" was added.").append(System.lineSeparator())
                                .append(order4).append(" was added.").append(System.lineSeparator())
                                .append(order5).append(" was added.").append(System.lineSeparator())
                                .append(order6).append(" was added.").append(System.lineSeparator())
                                .append("Depth of Market: ").append(secCode).append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .append("| Buy        | Price      | Sell       |").append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .append("| 16         | 11.8       |            |").append(System.lineSeparator())
                                .append("| 15         | 11.6       |            |").append(System.lineSeparator())
                                .append("| 14         | 11.1       |            |").append(System.lineSeparator())
                                .append("|            | 11.7       | 13         |").append(System.lineSeparator())
                                .append("|            | 11.5       | 12         |").append(System.lineSeparator())
                                .append("|            | 11.0       | 11         |").append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .toString()
                )
        );
    }

    @Test
    public void whenTreeOrdersSamePriceSameDirectionThenOneRawPrints() {
        Exchange ex = new Exchange();
        String secCode = "LKOH";
        Order order1 = new Order(secCode, OrderType.NEW_ORDER, Direction.SELL, 11.0, 11);
        Order order2 = new Order(secCode, OrderType.NEW_ORDER, Direction.SELL, 11.0, 12);
        Order order3 = new Order(secCode, OrderType.NEW_ORDER, Direction.SELL, 11.0, 13);
        ex.processOrder(order1);
        ex.processOrder(order2);
        ex.processOrder(order3);
        ex.showDepthOfMarket(secCode);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(order1).append(" was added.").append(System.lineSeparator())
                                .append(order2).append(" was added.").append(System.lineSeparator())
                                .append(order3).append(" was added.").append(System.lineSeparator())
                                .append("Depth of Market: ").append(secCode).append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .append("| Buy        | Price      | Sell       |").append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .append("|            | 11.0       | 36         |").append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .toString()
                )
        );
    }

    @Test
    public void whenOneBuyAndTwoSellSamePriceThenOneRawPrints() {
        Exchange ex = new Exchange();
        String secCode = "LKOH";
        Order order1 = new Order(secCode, OrderType.NEW_ORDER, Direction.SELL, 11.0, 11);
        Order order2 = new Order(secCode, OrderType.NEW_ORDER, Direction.SELL, 11.0, 12);
        Order order3 = new Order(secCode, OrderType.NEW_ORDER, Direction.BUY, 11.0, 55);
        ex.processOrder(order1);
        ex.processOrder(order2);
        ex.processOrder(order3);
        ex.showDepthOfMarket(secCode);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(order1).append(" was added.").append(System.lineSeparator())
                                .append(order2).append(" was added.").append(System.lineSeparator())
                                .append(replaceOrderQtty(order3, 55)).append(" was crossed with ").append(order1).append(System.lineSeparator())
                                .append(replaceOrderQtty(order3, 44)).append(" was crossed with ").append(order2).append(System.lineSeparator())
                                .append(order1).append(" was deleted.").append(System.lineSeparator())
                                .append(order2).append(" was deleted.").append(System.lineSeparator())
                                .append(replaceOrderQtty(order3, 32)).append(" was added.").append(System.lineSeparator())
                                .append("Depth of Market: ").append(secCode).append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .append("| Buy        | Price      | Sell       |").append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .append("| 32         | 11.0       |            |").append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .toString()
                )
        );
    }

    @Test
    public void whenOneBuyAndTwoSellSamePriceThenZeroRawPrints() {
        Exchange ex = new Exchange();
        String secCode = "LKOH";
        Order order1 = new Order(secCode, OrderType.NEW_ORDER, Direction.SELL, 11.0, 11);
        Order order2 = new Order(secCode, OrderType.NEW_ORDER, Direction.SELL, 11.0, 12);
        Order order3 = new Order(secCode, OrderType.NEW_ORDER, Direction.BUY, 11.0, 23);
        ex.processOrder(order1);
        ex.processOrder(order2);
        ex.processOrder(order3);
        ex.showDepthOfMarket(secCode);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(order1).append(" was added.").append(System.lineSeparator())
                                .append(order2).append(" was added.").append(System.lineSeparator())
                                .append(replaceOrderQtty(order3, 23)).append(" was crossed with ").append(order1).append(System.lineSeparator())
                                .append(replaceOrderQtty(order3, 12)).append(" was crossed with ").append(order2).append(System.lineSeparator())
                                .append(order1).append(" was deleted.").append(System.lineSeparator())
                                .append(order2).append(" was deleted.").append(System.lineSeparator())
                                .append(replaceOrderQtty(order3, 0)).append(" was crossed immediately.").append(System.lineSeparator())
                                .append("Depth of Market: ").append(secCode).append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .append("| Buy        | Price      | Sell       |").append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .toString()
                )
        );
    }

    @Test
    public void whenMixedCrossedAndSameDirectionSamePrice() {
        Exchange ex = new Exchange();
        String secCode = "LKOH";
        Order order1 = new Order(secCode, OrderType.NEW_ORDER, Direction.SELL, 11.0, 5);
        Order order2 = new Order(secCode, OrderType.NEW_ORDER, Direction.BUY, 11.5, 10);
        Order order3 = new Order(secCode, OrderType.NEW_ORDER, Direction.BUY, 11.5, 10);
        Order order4 = new Order(secCode, OrderType.NEW_ORDER, Direction.SELL, 11.7, 5);
        Order order5 = new Order(secCode, OrderType.NEW_ORDER, Direction.BUY, 11.0, 4);
        Order order6 = new Order(secCode, OrderType.NEW_ORDER, Direction.SELL, 11.9, 3);
        ex.processOrder(order1);
        ex.processOrder(order2);
        ex.processOrder(order3);
        ex.processOrder(order4);
        ex.processOrder(order5);
        ex.processOrder(order6);
        ex.showDepthOfMarket(secCode);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(replaceOrderQtty(order1, 5)).append(" was added.").append(System.lineSeparator())
                                .append(order2).append(" was added.").append(System.lineSeparator())
                                .append(order3).append(" was added.").append(System.lineSeparator())
                                .append(order4).append(" was added.").append(System.lineSeparator())
                                .append(replaceOrderQtty(order5, 0)).append(" was crossed immediately.").append(System.lineSeparator())
                                .append(order6).append(" was added.").append(System.lineSeparator())
                                .append("Depth of Market: ").append(secCode).append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .append("| Buy        | Price      | Sell       |").append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .append("| 20         | 11.5       |            |").append(System.lineSeparator())
                                .append("|            | 11.9       | 3          |").append(System.lineSeparator())
                                .append("|            | 11.7       | 5          |").append(System.lineSeparator())
                                .append("|            | 11.0       | 1          |").append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .toString()
                )
        );
    }

    @Test
    public void whenDeleteOrderTypeThenDeleted() {
        Exchange ex = new Exchange();
        String secCode = "LKOH";
        Order order1 = new Order(secCode, OrderType.NEW_ORDER, Direction.BUY, 11.0, 5);
        ex.processOrder(order1);
        ex.showDepthOfMarket(secCode);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(order1).append(" was added.").append(System.lineSeparator())
                                .append("Depth of Market: ").append(secCode).append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .append("| Buy        | Price      | Sell       |").append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .append("| 5          | 11.0       |            |").append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .toString()
                )
        );
        out.reset();
        order1.setType(OrderType.DELETE_ORDER);
        ex.processOrder(order1);
        ex.showDepthOfMarket(secCode);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(order1).append(" was deleted.").append(System.lineSeparator())
                                .append("Depth of Market: ").append(secCode).append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .append("| Buy        | Price      | Sell       |").append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .toString()
                )
        );
    }

    @Test
    public void whenTwoSecCodesThenShowOnlyOne() {
        Exchange ex = new Exchange();
        String secCodeLkoh = "LKOH";
        String secCodeVtbr = "VTBR";
        Order order1 = new Order(secCodeLkoh, OrderType.NEW_ORDER, Direction.BUY, 11.0, 5);
        Order order2 = new Order(secCodeVtbr, OrderType.NEW_ORDER, Direction.SELL, 11.1, 3);
        ex.processOrder(order1);
        ex.processOrder(order2);
        ex.showDepthOfMarket(secCodeLkoh);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(order1).append(" was added.").append(System.lineSeparator())
                                .append(order2).append(" was added.").append(System.lineSeparator())
                                .append("Depth of Market: ").append(secCodeLkoh).append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .append("| Buy        | Price      | Sell       |").append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .append("| 5          | 11.0       |            |").append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .toString()
                )
        );
        out.reset();
        ex.showDepthOfMarket(secCodeVtbr);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append("Depth of Market: ").append(secCodeVtbr).append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .append("| Buy        | Price      | Sell       |").append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .append("|            | 11.1       | 3          |").append(System.lineSeparator())
                                .append("+--------------------------------------+").append(System.lineSeparator())
                                .toString()
                )
        );
    }

    private Order replaceOrderQtty(Order order, int qtty) {
        Order result = order;
        result.setQtty(qtty);
        return result;
    }
}