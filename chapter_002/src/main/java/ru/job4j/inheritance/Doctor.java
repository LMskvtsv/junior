package ru.job4j.inheritance;

/**
 * Doctor class. Doctor is a child from Profession class.
 */
public class Doctor extends Profession {
    /**
     * Consists information about doctor's specialization.
     */
    private String specialization;
    /**
     * Consists information about doctor's experience (whole years).
     */
    private int xp;
    /**
     * All the knowleges that doctor obtained throughout his learning and experience.
     */
    String knowledge;


    /**
     * Constructor.
     *
     * @param name    - doctors name.
     * @param diploma - doctors diploma.
     */
    public Doctor(String name, String diploma) {
        super(name, diploma);
        setKnowledge("Starting knowledge from diploma: " + diploma);
    }

    /**
     * Setter for knowledge.
     *
     * @param knowledge - knowledge, wich should be set do doctor.
     */
    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    /**
     * Adding new knowledge to the existing knowlege.
     *
     * @param consultation - knowledge, wich should be added.
     */
    public void addNewKnowledge(String consultation) {
        this.knowledge += " Added new knowledge: " + consultation;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public String getSpecialization() {
        return specialization;
    }

    public int getXp() {
        return xp;
    }

    /**
     * Giving prescription in accordance with patient data.
     *
     * @param patient - patient.
     * @return String - prescription after analysing patient's data.
     */
    public String givePrescription(Patient patient) {
        String complains = patient.getComplains();
        String prescription = null;
        if (complains != null) {
            System.out.println("Doctor " + getName() + " does medical inspection of patient " + patient.getName() + ", age " + patient.getAge() + ".");
            prescription = "Some prescription";
        }
        return prescription;
    }

    /**
     * Consulting another Doctor. Transferring knowledge.
     *
     * @param doctor - another doctor.
     */
    public void consulting(Doctor doctor) {
        doctor.addNewKnowledge(this.knowledge);
        System.out.println("Doctor " + getName() + "is consulting doctor " + doctor.getName());
    }
}
