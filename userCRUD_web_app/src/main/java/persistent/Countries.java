package persistent;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class Countries {

    private CopyOnWriteArrayList<Country> countries = new CopyOnWriteArrayList<>();

    private final static String RUSSIA = "Russia";
    private final static String MOSCOW = "Moscow";
    private final static String SPB = "SPB";

    private final static String CYPRUS = "Cyprus";
    private final static String LIMASSOL = "Limassol";
    private final static String NICOSIA = "Nicosia";

    private final static String USA = "United States";
    private final static String NEW_YORK = "New York";
    private final static String ORLANDO = "Orlando";

    public void loadCountries() {
        countries.add(new Country(RUSSIA, new CopyOnWriteArrayList(
                Arrays.asList(new City[]{new City(MOSCOW), new City(SPB)}))));
        countries.add(new Country(CYPRUS, new CopyOnWriteArrayList(
                Arrays.asList(new City[]{new City(LIMASSOL), new City(NICOSIA)}))));
        countries.add(new Country(USA, new CopyOnWriteArrayList(
                Arrays.asList(new City[]{new City(NEW_YORK), new City(ORLANDO)}))));
    }

    public CopyOnWriteArrayList<Country> getCountries() {
        return countries;
    }
}
