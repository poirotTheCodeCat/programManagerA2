/*
File: Project.java
Programmers: John Stanly, Aaron Perry, Sasha Malesevic, Manthan Rami, Daniel Grew
Date Last Modified: 2020-03-12
Description: This file contains all of the logic for the Project class including business logic associated with the class
 */
package com.example.programmanagera2;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Project {
    // private data members
    private int project_id;
    private String project_name;
    private ArrayList<Person> team;
    private ArrayList<Task> tasks;
    private String start_date;
    private String end_date;

    public Project(int id, String name, ArrayList<Person> projectTeam, ArrayList<Task> projectTasks,  String start, String end)   // constructor for the Project Class
    {
        project_id = id;
        project_name = name;
        team = projectTeam;
        tasks = projectTasks;
        start_date = start;
        end_date = end;
    }

    // mutators
    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public void setTeam(ArrayList<Person> team) {
        this.team = team;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    // accessors
    public int getProject_id() {
        return project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public ArrayList<Person> getTeam() {
        return team;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public ArrayList<Task> getTasks() { return tasks; }

    /*
    Function: insertProject()
    Parameters: Context context, Project project
    Description: Calls on a database method to insert a project into the database
    Returns: Nothing
     */
    public static void insertProject(Context context, Project project)
    {
        ProjectDataAccess db = new ProjectDataAccess(context);
        db.insertProject(project);
    }

    /*
    Function: getAllProjects()
    Parameters: Context context
    Description: Calls on a database method to get all projects from database
    Returns: Nothing
     */
    public static ArrayList<Project> getAllProjects(Context context)
    {
        ProjectDataAccess db = new ProjectDataAccess(context);
        ArrayList<Project> projects = db.getProjects();
        return projects;
    }
}
