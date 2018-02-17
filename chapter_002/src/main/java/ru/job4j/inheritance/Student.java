package ru.job4j.inheritance;

public class Student {
    private String name;
    private String faculty;
    private String homeWork;
    String knowledge;

    public Student(String name, String faculty) {
        this.name = name;
        this.faculty = faculty;
    }

    public String getName() {
        return name;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }

    public String doHomeWork() {
        if (knowledge != null) {
            homeWork = knowledge + " + HW is done.";
        }
        return homeWork;
    }
}
