package list;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class NodeCyclingTest {

    @Test
    public void endClosureDetection() {
        NodeCycling first = new NodeCycling(1);
        NodeCycling two = new NodeCycling(2);
        NodeCycling third = new NodeCycling(3);
        NodeCycling four = new NodeCycling(4);

        first.next = two;
        two.next = third;
        third.next = four;
        four.next = first;

        assertThat(NodeCycling.isCyclingExists(first), is(true));

    }

    @Test
    public void closureDetectionFalse() {
        NodeCycling first = new NodeCycling(1);
        NodeCycling two = new NodeCycling(2);
        NodeCycling third = new NodeCycling(3);
        NodeCycling four = new NodeCycling(4);

        first.next = two;
        two.next = third;
        third.next = four;
        four.next = null;

        assertThat(NodeCycling.isCyclingExists(first), is(false));

    }

    @Test
    public void middleClosureDetection() {
        NodeCycling first = new NodeCycling(1);
        NodeCycling two = new NodeCycling(2);
        NodeCycling third = new NodeCycling(3);

        first.next = two;
        two.next = third;
        third.next = two;

        assertThat(NodeCycling.isCyclingExists(first), is(true));

    }

}