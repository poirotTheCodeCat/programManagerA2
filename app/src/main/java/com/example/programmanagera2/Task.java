/*
File: Task.java
Programmers: John Stanly, Aaron Perry, Sasha Malesevic, Manthan Rami, Daniel Grew
Date Last Modified: 2020-03-12
Description: This file contains the Task class which contains all of the logic pertaining to handling task information
 */
package com.example.programmanagera2;

import android.content.Context;

import java.util.ArrayList;

public class Task {
    private int task_id;
    private String task_description;
    private int task_complete;

    public static final int IS_COMPLETED = 1;
    public static final int IN_PROGRESS = 0;

    public Task(int id, String description, int completed)
    {
        task_id = id;
        task_description = description;
        task_complete = completed;
    }

    // mutators and accessors

    public int getTask_complete() {
        return task_complete;
    }

    public int getTask_id() {
        return task_id;
    }

    public String getTask_description() {
        return task_description;
    }

    public void setTask_complete(int task_complete) {
        this.task_complete = task_complete;
    }

    public void setTask_description(String task_description) {
        this.task_description = task_description;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }
    public static ArrayList<Task> getAllTasks(Context context, int project_id)
    {
        ProjectDataAccess db = new ProjectDataAccess(context);
        ArrayList<Task> tasks = db.getProjectTasks(project_id);
        return tasks;
    }
}
