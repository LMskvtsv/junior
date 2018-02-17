package ru.job4j.inheritance;

public class Engineer extends Profession {

    private String sphere;

    public Engineer(String name, String diploma, String sphere) {
        super(name, diploma);
        this.sphere = sphere;
    }

    public String getSphere() {
        return sphere;
    }

    public Engineer(String name, String diploma) {
        super(name, diploma);
    }

    public Construction develope(String requirements) {
        System.out.println("Engineer " + getName() + " developing...");
        System.out.println("Using requirements: " + requirements);
        Construction construction = new Construction();
        construction.setConstriction("Construction made.");
        return construction;
    }

    public void optimize(Construction construction) {
        System.out.println("Optimizing construction: " + construction);
        construction.setConstriction("Optimized construction");
    }
}
