package ru.job4j.inheritance;

public class Patient {
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    private String name;
    private int age;
    private String complains;

    public Patient(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getComplains() {
        return complains;
    }

    public void setComplains(String complains) {
        this.complains = complains;
    }

    public void cure(String prescription) {
        if (prescription != null) {
            System.out.println("Patient is following prescription.");
        }
    }


}
