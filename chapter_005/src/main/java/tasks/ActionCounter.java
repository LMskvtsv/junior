package tasks;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class ActionCounter {

    LinkedList<Long> timeStamps = new LinkedList<>();
    public final int ONE_DAY_MILLISECONDS = 1_000 * 60 * 60 * 24;

    /**
     * Adds new element's timestamp into collection. In case there is elements older than 24 hours - removes them.
     * @param milliseconds
     */
    public void add(long milliseconds) {
        this.timeStamps.add(milliseconds);
        Long first = timeStamps.getFirst();
        if(first != null && System.currentTimeMillis() - first <= ONE_DAY_MILLISECONDS) {
            timeStamps = timeStamps.stream()
                    .filter(t -> System.currentTimeMillis() - t <= ONE_DAY_MILLISECONDS).collect(Collectors.toCollection(LinkedList::new));
        }
    }

    /**
     * Get sub list with values that are in time fame since 'period' till now].
     * @param period how much milliseconds back we need to search.
     * @return minus 1 in case period was set more than 24 hours.
     */
    public int getCounter(long period) {
        int counter;
        LinkedList<Long> periodList ;
        if(period <= ONE_DAY_MILLISECONDS) {
            periodList = timeStamps.stream()
                    .filter(t -> (System.currentTimeMillis() - t) <= period).collect(Collectors.toCollection(LinkedList::new));
            counter = periodList.size();
        } else {
            counter = -1;
        }
        return counter;
    }
}
