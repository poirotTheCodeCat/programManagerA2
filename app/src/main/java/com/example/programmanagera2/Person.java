/*
File:
Programmers: John Stanly, Aaron Perry, Sasha Malesevic, Manthan Rami, Daniel Grew
Date Last Modified: 2020-03-12
Description:
 */
package com.example.programmanagera2;

public class Person {
    private int person_id;
    private String first_name;
    private String last_name;
    private int skill_level;

    public Person(int id, String first, String last, int skill)
    {
        person_id = id;
        first_name = first;
        last_name = last;
        skill_level = skill;
    }

    // accessors and mutators
    public int getPerson_id() {
        return person_id;
    }

    public int getSkill_level() {
        return skill_level;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public void setSkill_level(int skill_level) {
        this.skill_level = skill_level;
    }
}
