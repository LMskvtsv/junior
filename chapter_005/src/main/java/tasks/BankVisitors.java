package tasks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.TreeSet;

public class BankVisitors {

    private ArrayList<Person> visitors;
    private Map<Long, Integer> timeLine = new TreeMap<>();
    private Calendar calendar = Calendar.getInstance();
    private int minutesInPeriod = 12 * 60 + 11;
    private int millisecondsInMinute = 60 * 1_000;

    public BankVisitors(ArrayList<Person> visitors) {
        this.visitors = visitors;
    }

    /**
     * Initiates map with 1 minute periods within time interval 8:00 - 20:00.
     */
    public void init() {
        calendar.set(2018, Calendar.JUNE, 15);
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        timeLine.put(calendar.getTimeInMillis(), 0);
        for (int i = 0; i < minutesInPeriod; i++) {
            calendar.setTimeInMillis(calendar.getTimeInMillis() + millisecondsInMinute);
            timeLine.put(calendar.getTimeInMillis(), 0);
        }
    }

    /**
     * Calculates periods when there was max quantity of people at the same time.
     * @return string with periods.
     */
    public String returnMaxVisitPeriods() {
        for (Person p: visitors) {
            for (Long l: timeLine.keySet()) {
                if (l >= p.getEnter() && l <= p.getLeave()) {
                    timeLine.computeIfPresent(l, (k, v) -> (v = v + 1));
                }
            }
        }
        int max = Collections.max(timeLine.entrySet(), Comparator.comparing(Map.Entry::getValue)).getValue();
        TreeSet<Long> set = new TreeSet();
        for (Long l: timeLine.keySet()) {
            if (timeLine.get(l) == max) {
                set.add(l);
            }
        }
        TreeMap<Long, Long> periods = new TreeMap<>();
        long previous = set.first();
        long first = set.first();
        periods.put(first, 0L);
        for (Long l: set) {
            if (l - previous > millisecondsInMinute) {
                periods.put(first, previous);
                first = l;
                periods.put(l, 0L);
                previous = l;
            } else {
                previous = l;
                periods.put(first, previous);
            }
        }

        StringBuilder builder = new StringBuilder("Periods with max visitors:" + System.lineSeparator());
        for (Map.Entry<Long, Long> entry: periods.entrySet()) {
            calendar.setTime(new Date(entry.getKey()));
            builder.append(calendar.getTime());
            builder.append(" - ");
            calendar.setTime(new Date(entry.getValue()));
            builder.append(calendar.getTime());
            builder.append(System.lineSeparator());
        }
        System.out.println(calendar.getTimeZone());
        System.out.println(builder.toString());
        return builder.toString();
    }
}
