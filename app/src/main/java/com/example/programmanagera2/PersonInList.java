package com.example.programmanagera2;

public class PersonInList {
    private String first_name;
    private String last_name;
    private int skill_level;

    public PersonInList(String first_name, String last_name, int skill_level)
    {
        this.first_name = first_name;
        this.last_name = last_name;
        this.skill_level = skill_level;
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
}
