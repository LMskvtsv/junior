package tasks;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BankVisitorsTest {

    @Test
    public void whenTwoMaxPeriodsThanTwoAreShown() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Nicosia"));
        calendar.set(2018, Calendar.JUNE, 15);
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        ArrayList<Person> list = new ArrayList<>();
        calendar.set(Calendar.MINUTE, 5);
        Person p = new Person();
        p.setEnter(calendar.getTimeInMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 10);
        p.setLeave(calendar.getTimeInMillis());
        list.add(p);
        Person p2 = new Person();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 10);
        p2.setEnter(calendar.getTimeInMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        p2.setLeave(calendar.getTimeInMillis());
        list.add(p2);

        Person p3 = new Person();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 5);
        p3.setEnter(calendar.getTimeInMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 0);
        p3.setLeave(calendar.getTimeInMillis());
        list.add(p3);

        Person p4 = new Person();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 5);
        p4.setEnter(calendar.getTimeInMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 0);
        p4.setLeave(calendar.getTimeInMillis());
        list.add(p4);

        Person p5 = new Person();
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 15);
        p5.setEnter(calendar.getTimeInMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.set(Calendar.MINUTE, 45);
        p5.setLeave(calendar.getTimeInMillis());
        list.add(p5);

        Person p6 = new Person();
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.set(Calendar.MINUTE, 0);
        p6.setEnter(calendar.getTimeInMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 0);
        p6.setLeave(calendar.getTimeInMillis());
        list.add(p6);

        BankVisitors bv = new BankVisitors(list);
        bv.init();
        StringBuilder builder = new StringBuilder("Periods with max visitors:" + System.lineSeparator());
        assertThat(bv.returnMaxVisitPeriods(),
                is(builder
                        .append("Fri Jun 15 08:10:00 EEST 2018 - Fri Jun 15 09:00:00 EEST 2018")
                        .append(System.lineSeparator())
                        .append("Fri Jun 15 14:00:00 EEST 2018 - Fri Jun 15 14:45:00 EEST 2018")
                        .append(System.lineSeparator()).toString()));
    }
}