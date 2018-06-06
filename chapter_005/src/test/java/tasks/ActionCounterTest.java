package tasks;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;

public class ActionCounterTest {

    @Test
    public void when10Seconds() {
        ActionCounter ac = new ActionCounter();
        ac.add(System.currentTimeMillis());
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ac.add(System.currentTimeMillis());
        ac.add(System.currentTimeMillis());
        ac.add(System.currentTimeMillis());
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ac.add(System.currentTimeMillis());

        assertThat(ac.getCounter(ac.ONE_DAY_MILLISECONDS), is(5));
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(ac.getCounter(ac.ONE_DAY_MILLISECONDS), is(5));
        assertThat(ac.getCounter(2_100), is(4));
        assertThat(ac.getCounter(ac.ONE_DAY_MILLISECONDS), is(5));
    }
}