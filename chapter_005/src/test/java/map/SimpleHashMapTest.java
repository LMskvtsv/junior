package map;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class SimpleHashMapTest {

    @Test
    public void whenAddedThanElementExists() {
        SimpleHashMap<String, Integer> map = new SimpleHashMap<>();
        assertThat(map.insert("first", 1), is(true));
        assertThat(map.insert("second", 2), is(true));
        assertThat(map.get("first"), is(1));
        assertThat(map.get("second"), is(2));
        assertThat(map.getSize(), is(2));
    }

    @Test
    public void whenAddedSameKeyThanValueUpdated() {
        SimpleHashMap<String, Integer> map = new SimpleHashMap<>();
        assertThat(map.insert("first", 1), is(true));
        assertThat(map.insert("first", 2), is(false));
        assertThat(map.get("first"), is(2));
        assertThat(map.getSize(), is(1));
    }

    @Test
    public void whenDeletedThanGetIsNullAndSizeLess() {
        SimpleHashMap<String, Integer> map = new SimpleHashMap<>();
        map.insert("first", 1);
        assertThat(map.get("first"), is(1));
        assertThat(map.getSize(), is(1));
        map.delete("first");
        assertNull(map.get("first"));
        assertThat(map.getSize(), is(0));
    }
}