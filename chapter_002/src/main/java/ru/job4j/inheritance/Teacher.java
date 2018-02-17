package ru.job4j.inheritance;

public class Teacher extends Profession {
    String subject;
    String institution;
    String lection;

    public Teacher(String name, String diploma, String subject, String institution) {
        super(name, diploma);
        this.subject = subject;
        this.institution = institution;
    }

    public void teach(Student student) {
        student.setKnowledge(lection);
        System.out.println("Teacher " + getName() + " is teaching student " + student.getName() + "from faculty " + student.getFaculty());
    }

    public void prepareLection() {
        if (getDiploma() != null) {
            this.lection = "This is new lection.";
        }

    }

    public String checkHomeWork(String homework) {
        String mark = "F";
        if (homework != null) {
            mark = "A";
        }
        return mark;
    }
}
