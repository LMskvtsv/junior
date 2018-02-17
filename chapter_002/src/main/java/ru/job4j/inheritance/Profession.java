package ru.job4j.inheritance;

/**
 * This is common class for abstract profession.
 */
public class Profession {
    /**
     * Name of the person.
     */
    private String name;

    /**
     * Diploma of the person.
     */
    private String diploma;

    /**
     * Constructor.
     *
     * @param name    - name of the person.
     * @param diploma - diploma of the person.
     */
    public Profession(String name, String diploma) {
        this.name = name;
        this.diploma = diploma;
    }

    /**
     * Getter. Returns name.
     *
     * @return String.
     */

    public String getName() {
        return name;
    }

    /**
     * Getter. Returns diploma.
     *
     * @return String.
     */

    public String getDiploma() {
        return diploma;
    }
}
