/*
File: ProjectDataAccess.java
Programmers: John Stanley, Aaron Perry, Sasha Malesevic, Manthan Rami, Daniel Grew
Date Last Modified: 2020-03-12
Description: This contains the ProjectDataAccess class, which acts as a data access layer, creating and
    accessing the Projects database "Projects.db" for reading and modification.
 */
package com.example.programmanagera2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.zip.InflaterOutputStream;

public class ProjectDataAccess {
    public static final String LOG_INFO = "INFO";

    // Define Constants to be used in creating the Database
    public static final String PROJECT_DB = "Projects.db";     // name of database
    public static final int DB_VERSION = 1;

    // Table Constants
    // Project Table
    // =======================================================
    public static final String PROJECT_TABLE = "projects";

    public static final String PROJECT_ID = "project_id";
    public static final int PROJECT_ID_COL = 0;

    public static final String PROJECT_NAME = "project_name";
    public static final int PROJECT_NAME_COL = 1;

    public static final String START_DATE = "start_date";
    public static final int START_DATE_COL = 2;

    public static final String END_DATE = "end_date";
    public static final int END_DATE_COL = 3;

    // Task Table
    // ========================================================
    public static final String TASK_TABLE = "tasks";

    public static final String TASK_ID = "task_id";
    public static final int TASK_ID_COL = 0;

    public static final String PROJECT_TASK_ID = "project_id";
    public static final int PROJECT_TASK_ID_COL = 1;

    public static final String TASK_DESCRIPTION = "task_description";
    public static final int TASK_DESCRIPTION_COL = 2;

    public static final String COMPLETE = "complete";
    public static final int COMPLETE_COL = 3;

    // Person Table
    // =======================================================
    public static final String PERSON_TABLE = "person";

    public static final String PERSON_ID = "person_id";
    public static final int PERSON_ID_COL = 0;

    public static final String FIRST_NAME = "first_name";
    public static final int FIRST_NAME_COL = 1;

    public static final String LAST_NAME = "last_name";
    public static final int LAST_NAME_COL = 2;

    public static final String SKILL = "skill";
    public static final int SKILL_COL = 3;

    // Team table
    // =======================================================
    public static final String TEAM_TABLE = "team";

    public static final String TEAM_PROJECT_ID = "project_id";
    public static final int TEAM_PROJECT_ID_COL = 0;

    public static final String TEAM_PERSON_ID = "person_id";
    public static int TEAM_PERSON_ID_COL = 1;

    // Create Table Statements
    public static final String CREATE_PROJECT_TABLE = "CREATE TABLE " + PROJECT_TABLE + " (" +
            PROJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
            ", " + PROJECT_NAME + " TEXT NOT NULL" +
            ", " + START_DATE + " TEXT" +
            ", " + END_DATE + " TEXT" +");";

    public static final String CREATE_TASK_TABLE = "CREATE TABLE " + TASK_TABLE + " (" +
            TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
            ", " + PROJECT_TASK_ID + " INTEGER NOT NULL" +
            ", " + TASK_DESCRIPTION + " TEXT" +
            ", " + COMPLETE + " INTEGER);";

    public static final String CREATE_PERSON_TABLE = "CREATE TABLE " + PERSON_TABLE + " (" +
            PERSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
            ", " + FIRST_NAME + " TEXT NOT NULL" +
            ", " + LAST_NAME + " TEXT NOT NULL" +
            ", " + SKILL + " INTEGER NOT NULL " +
            ");";

    public static final String CREATE_TEAM_TABLE = "CREATE TABLE " + TEAM_TABLE + " (" +
            TEAM_PROJECT_ID + " INTEGER NOT NULL" +
            ", " + TEAM_PERSON_ID + " INTEGER NOT NULL"
            + ");";

    // Create Drop Table Statements
    public static final String DROP_PROJECT_TABLE = "DROP TABLE IF EXISTS " + PROJECT_TABLE;
    public static final String DROP_TASK_TABLE = "DROP TABLE IF EXISTS " + TASK_TABLE;
    public static final String DROP_TEAM_TABLE = "DROP TABLE IF EXISTS " + TEAM_TABLE;
    public static final String DROP_PERSON_TABLE = "DROP TABLE IF EXISTS " + PERSON_TABLE;

    // ==============================================================================================
    // Create a Database helper class which extends SQLiteOpenHelper
    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        /*
        Function: onCreate
        Parameters: SQLiteDatabase
        Description: This method is an overridden onCreate method which is called on the class instantiation
        Returns: Nothing
         */
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            // Create the tables
            db.execSQL(CREATE_PROJECT_TABLE);
            db.execSQL(CREATE_TASK_TABLE);
            db.execSQL(CREATE_PERSON_TABLE);
            db.execSQL(CREATE_TEAM_TABLE);
        }

        /*
        Function: onUpgrade
        Parameters: SQLiteDatabase db, int oldVersion, int newVersion
        Description: This drops all elements of the database and instantiates a new database of the upgraded version
        Returns: nothing
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_PROJECT_TABLE);
            db.execSQL(DROP_TASK_TABLE);
            db.execSQL(DROP_PERSON_TABLE);
            db.execSQL(DROP_TEAM_TABLE);
            onCreate(db);
        }
    }

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    /*
    Function: ProjectDataAccess
    Parameters: Context context
    Description: This is a constructor for the ProjectDataAccess
    Returns: nothing
     */
    public ProjectDataAccess(Context context)
    {
        dbHelper = new DBHelper(context, PROJECT_DB, null, DB_VERSION);
    }

    // private methods
    /*
    Function: openReadOnlyDB()
    Parameters: none
    Description: opens a database to be read from
    Returns: nothing
    */
    private void openReadOnlyDB()
    {
        db = dbHelper.getReadableDatabase();
        Log.v(LOG_INFO, "Open database for reading");
    }
    /*
    Function: openWriteOnlyAccess()
    Parameters: none
    Description: opens a database to be written to / modified
    Returns:
    */
    private void openWriteOnlyDB()
    {
        db = dbHelper.getWritableDatabase();
        Log.v(LOG_INFO, "Opened database for writing");
    }
    /*
    Function: closeDB()
    Parameters: none
    Description: This function closes a database which is currently open
    Returns: nothing
     */
    private void closeDB()
    {
        if(db != null)
        {
            db.close();
            Log.v(LOG_INFO, "database closed");
        }
    }

    /*
    Function: closerCursor()
    Parameters: Cursor cursor
    Description: closes a cursor used to access database elements
    Returns: nothing
     */
    private void closeCursor(Cursor cursor)
    {
        if(cursor != null)
        {
            cursor.close();
            Log.v(LOG_INFO, "Cursor Closed");
        }
    }

    // Accessing and modifying the database
    /*
    Function: getProjects()
    Parameters: none
    Description: Retrieves all projects from the database
    Returns: ArrayList of Project objects
     */
    public ArrayList<Project> getProjects()
    {
        ArrayList<Project> projectList = new ArrayList<Project>();   // instantiate a new ArrayList of Project objects
        openReadOnlyDB();
        Cursor cursor = db.query(PROJECT_TABLE, null, null, null, null, null, null);
        while(cursor.moveToNext())
        {
            int project_id = cursor.getInt(PROJECT_ID_COL); // get the project ID
            ArrayList<Person> team = getProjectTeam(project_id); // get the team working on project
            ArrayList<Task> tasks = getProjectTasks(project_id); // get tasks for project

            Project project = new Project(project_id, cursor.getString(PROJECT_NAME_COL), team, tasks,
                    cursor.getString(START_DATE_COL), cursor.getString(END_DATE_COL));
            projectList.add(project);
            Log.v(LOG_INFO, "Retrieved project project_id: " + Integer.toString(project.getProject_id()) + " from project table");
        }
        this.closeDB();
        return projectList;
    }

    /*
    Function: getProjectTasks()
    Parameters: int project_id
    Description: gets all tasks for a given project
    Returns: a list of Task objects
     */
    public ArrayList<Task> getProjectTasks(int project_id)
    {
        ArrayList<Task> projectTasks = new ArrayList<>();
        String where = PROJECT_TASK_ID + "= ?";     // access the Tasks where project_id is equal to project_id
        String[] whereArgs = {Integer.toString(project_id)};        // array that holds args for above string

        openReadOnlyDB();       // open the database to read

        Cursor cursor = db.query(TASK_TABLE, null, where, whereArgs, null, null, null);

        while(cursor.moveToNext())
        {
            // create new task using database info
            Task task = new Task(
                    cursor.getInt(TASK_ID_COL), cursor.getString(TASK_DESCRIPTION_COL), cursor.getInt(COMPLETE_COL)
            );
            projectTasks.add(task);
            Log.v(LOG_INFO, "retrieved task task_id: " + Integer.toString(task.getTask_id()) + " from tasks table");
        }
        this.closeDB();
        return projectTasks;
    }

    /*
    Function: getProjectTeam()
    Parameters: project_id
    Description: Retrieves all team members that are a part of a project
    Returns: ArrayList of Person Objects
     */
    public ArrayList<Person> getProjectTeam(int project_id)
    {
        ArrayList<Person> projectTeam = new ArrayList<>();
        String where = TEAM_PROJECT_ID + "= ?";
        String[] whereArgs = {Integer.toString(project_id)};

        openReadOnlyDB();

        Cursor cursor = db.query(TEAM_TABLE, null, where, whereArgs, null, null, null);
//        cursor.moveToFirst();

        if(cursor == null)
        {
            Log.v("Database", "Cursor does not exist - accessing person");
        }

        while(cursor.moveToNext())
        {
            projectTeam.add(retrievePerson(cursor.getInt(TEAM_PERSON_ID_COL)));
            Log.v(LOG_INFO, "retrieved team from database for project_id: " + Integer.toString(project_id));
        }
        cursor.close();
        this.closeDB();
        return projectTeam;
    }

    /*
    Function: getPerson()
    Parameters: int person_id
    Description: Retrieves specific person from database Person table
    Returns: A Person object
     */
    public Person retrievePerson(int person_id)
    {
        // read person table of the database for specific person_id and create a person object
        String where = PERSON_ID + "= ?";
        String[] whereArgs = { Integer.toString(person_id) };

        openReadOnlyDB();

        Cursor cursor = db.query(PERSON_TABLE, null, where, whereArgs, null, null, null);
//        cursor.moveToFirst();

        if(cursor.getCount() == 0)
        {
            Log.v("Database", "Cursor does not exist - accessing person");
            return null;
        }

        Person person = new Person(cursor.getInt(PERSON_ID_COL), cursor.getString(FIRST_NAME_COL), cursor.getString(LAST_NAME_COL), cursor.getInt(SKILL_COL));
        Log.v(LOG_INFO, "Loaded Person person_id: " + Integer.toString(person.getPerson_id()) + " from person table");
        this.closeDB();
        cursor.close();
        return person;
    }

    /*
    Function: insertTask()
    Parameters: Task task, int project_id
    Description: insets a new task as a part of a specific project into the task table and ProjectTasks table
    Returns: nothing
     */
    public void insertTask(String task_description, int project_id)
    {
        ContentValues cv = new ContentValues();     // load values of task into content value object
        cv.put(TASK_DESCRIPTION, task_description);
        cv.put(COMPLETE, 0);

        openWriteOnlyDB();      // open the database for writing

        long row_id = db.insert(TASK_TABLE, null, cv);
        Log.v(LOG_INFO, "inserted task task_id: " + Long.toString(row_id) + " into tasks table");

        this.closeDB();
    }

    /*
    Function: insertProject()
    Parameters: Project project
    Description: inserts a new project and project details into the database
    Returns: nothing
     */
    public void insertProject(Project project)
    {
        // insert new project into projects table
        ContentValues cv = new ContentValues();     // create a contentValues object with project info
        cv.put(PROJECT_NAME, project.getProject_name());
        cv.put(START_DATE, project.getStart_date());
        cv.put(END_DATE, project.getEnd_date());

        this.openWriteOnlyDB();

        long row_id = db.insert(PROJECT_TABLE, null, cv);   // insert the project into database
        Log.v(LOG_INFO, "Inserted project project_id: " + Long.toString(row_id) + " into project table");

        this.closeDB();     // close the database for writing

        // insert list of people into people table
        for(int i = 0; i < project.getTeam().size(); i++)
        {
            insertPerson(project.getTeam().get(i));
        }

        long new_project_id = row_id;    // get product_id of most recent insert

        insertProjectTeam(project.getTeam(), new_project_id);   // insert team into databases
        // NOTE: TASKS ARE NOT CREATED WHEN A NEW PROJECT IS CREATED
    }

    /*
    Function: insertProjectTeam()
    Parameters: ArrayList<Person> team, long project_id
    Description: this method inserts a team into the database
    Returns: nothing
     */
    public void insertProjectTeam(ArrayList<Person> team, long project_id)
    {
        openWriteOnlyDB();
        // add each member of the team into the database using a loop
        for(int i = 0; i<team.size(); i++)
        {
            ContentValues cv = new ContentValues();
            cv.put(PERSON_ID, team.get(i).getPerson_id());
            cv.put(TEAM_PROJECT_ID, project_id);

            db.insert(TEAM_TABLE, null, cv);
            Log.v(LOG_INFO, "Inserted team for project_id: " + project_id + " into team table");
        }
        closeDB();
    }



    /*
    Function: updateCompleteness()
    Parameters: int task_id, int newStatus
    Description: changes the completeness status of a task
    Returns: nothing
     */
    public void updateTaskCompleteness(int task_id, int newStatus)
    {
        // update database entry where task_id == task_id to the new status
        ContentValues cv = new ContentValues();
        cv.put(COMPLETE, newStatus);

        String where = TASK_ID + "= ?";
        String[] whereArgs = { Integer.toString(task_id) };

        openWriteOnlyDB();      // open database for writing

        int rowCount = db.update(TASK_TABLE, cv, where, whereArgs);     // update the specified rows
        this.closeDB();     // close database

        Log.v(LOG_INFO, "Updated task task_id: " + task_id + " in task table");
    }

    /*
    Function: insertPerson()
    Parameters: Person person
    Description: inserts new person into the person table of the database
    Returns: nothing
     */
    public void insertPerson(Person person)
    {
        // check if person exists
        String checkExist = PERSON_ID + "= ? AND " + FIRST_NAME + "= ? AND " + LAST_NAME + "= ? AND " + SKILL + "= ?";
        String[] checkArgs = {Integer.toString(person.getPerson_id()), person.getFirst_name(), person.getLast_name(), Integer.toString(person.getSkill_level())};

        openReadOnlyDB();

        Cursor cursor = db.query(PERSON_TABLE, null, checkExist, checkArgs, null, null, null);

        if(cursor == null || cursor.getCount() == 0)        // if person does not already exist, add them to the database
        {
            this.closeDB(); // close database for reading
            openWriteOnlyDB();      // open database for writing

            ContentValues cv = new ContentValues();
            cv.put(FIRST_NAME, person.getFirst_name());
            cv.put(LAST_NAME, person.getLast_name());
            cv.put(SKILL, person.getSkill_level());

            long row_id = db.insert(PERSON_TABLE, null, cv);    // insert person into db
            Log.v(LOG_INFO, "Inserted Person " + person.getFirst_name() + " " + person.getLast_name() + " into the person table");

            this.closeDB(); // close db for writing
        }
    }

    /*
    Function: deleteTask()
    Parameters: int task_id
    Description: Deletes instance of task from database
    Returns: nothing
     */
    public void deleteTask(int task_id)
    {
        String where = TASK_ID + "= ?";
        String[] whereArgs = { Integer.toString(task_id) };

        this.openWriteOnlyDB();     // open database for writing

        int row_count = db.delete(TASK_TABLE, where, whereArgs);    // Delete instance of task

        db.close();     // close database
        Log.v(LOG_INFO, "deleted task with task ID " + task_id);
    }
}