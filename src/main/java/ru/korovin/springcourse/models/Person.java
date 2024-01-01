package ru.korovin.springcourse.models;

public class Person {
    private int person_id;
    private String FIO;

    public Person(){
    }

    public Person(int person_id, String FIO) {
        this.person_id = person_id;
        this.FIO = FIO;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    @Override
    public String toString() {
        return String.format("ID-%d, %s", person_id,FIO);
    }
}
