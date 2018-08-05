package ru.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "transmissions")
public class Transmission {
    private int id;
    private String name;

    public Transmission(int id) {
        this.id = id;
    }

    public Transmission() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Transmission{id=" + id + ", name='" + name + '\'' + '}';
    }
}
