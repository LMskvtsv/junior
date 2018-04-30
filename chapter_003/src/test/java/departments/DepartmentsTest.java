package departments;

import org.junit.Test;

import java.util.Comparator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DepartmentsTest {
    @Test
    public void whenAddThanNaturalOrder() {
        Departments departments = new Departments();
        String[] values = new String[]{"K1\\SK1", "K1\\SK2", "K1\\SK1\\SSK1", "K1\\SK1\\SSK2", "K2", "K2\\SK1\\SSK1", "K2\\SK1\\SSK2"};
        departments.addInNaturalOrder(values);
        String[] expected = new String[]{"K1", "K1\\SK1", "K1\\SK1\\SSK1", "K1\\SK1\\SSK2", "K1\\SK2", "K2", "K2\\SK1", "K2\\SK1\\SSK1", "K2\\SK1\\SSK2"};
        assertThat(departments.getDepartments().toArray(), is(expected));
    }

    @Test
    public void sortBackwards() {
        Departments departments = new Departments();
        String[] values = new String[]{"K1\\SK1", "K1\\SK2", "K1\\SK1\\SSK1", "K1\\SK1\\SSK2", "K2", "K2\\SK1\\SSK1", "K2\\SK1\\SSK2"};
        departments.addInNaturalOrder(values);
        String[] expected = new String[]{"K2\\SK1\\SSK2", "K2\\SK1\\SSK1", "K2\\SK1", "K2", "K1\\SK2", "K1\\SK1\\SSK2", "K1\\SK1\\SSK1", "K1\\SK1", "K1"};
        assertThat(departments.sort(departments.getDepartments(), Comparator.reverseOrder()).toArray(), is(expected));
    }

    @Test
    public void sortNaturalOrder() {
        Departments departments = new Departments();
        String[] values = new String[]{"K1\\SK1", "K1\\SK2", "K1\\SK1\\SSK1", "K1\\SK1\\SSK2", "K2", "K2\\SK1\\SSK1", "K2\\SK1\\SSK2"};
        departments.addInNaturalOrder(values);
        String[] expected = new String[]{"K1", "K1\\SK1", "K1\\SK1\\SSK1", "K1\\SK1\\SSK2", "K1\\SK2", "K2", "K2\\SK1", "K2\\SK1\\SSK1", "K2\\SK1\\SSK2"};
        departments.sort(departments.getDepartments(), Comparator.reverseOrder());
        assertThat(departments.sort(departments.getDepartments(), Comparator.naturalOrder()).toArray(), is(expected));
    }
}
