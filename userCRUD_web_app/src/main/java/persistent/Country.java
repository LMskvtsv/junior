package persistent;

import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Country {
    String name;
    CopyOnWriteArrayList<City> cities;

    public Country(String name, CopyOnWriteArrayList<City> cities) {
        this.name = name;
        this.cities = cities;
    }

    public String getName() {
        return name;
    }

    public CopyOnWriteArrayList<City> getCities() {
        return cities;
    }
}
