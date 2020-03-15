package com.example.programmanagera2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class SeeTasksFragment extends Fragment {

    // declare private members adhering to UI components
    private EditText new_task_input;
    private Button insert_button;
    private ListView task_list_view;

    private TextView view_start;
    private TextView view_end;
    private TextView view_project;

    // declare private project field
    private Project current_project;
    private ArrayList<Task> task_list;

    // insert display aspects
    private String start_date;
    private String end_date;
    private String project_name;

    private Project this_project;
    private ArrayList<Task> tasks;
    private ArrayAdapter<Task> adapter;
    private int project_id;
    private String list_string;
    private String task_name;
    private ArrayList<String> task_list_array;
    private  ArrayAdapter<String> array_adapter;

    public interface tasksListener {

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_see_tasks, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view_start = (TextView) view.findViewById(R.id.start_date_view);
        view_end = (TextView) view.findViewById(R.id.end_date_view);
        view_project = (TextView) view.findViewById(R.id.project_name_display);
        new_task_input = (EditText) view.findViewById(R.id.task_input);

        task_list_view = view.findViewById(R.id.task_list);     // find the task list
        task_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);    // allow multiple

        // TODO : find out how to pass Project from main view to this view

        //initialize needed array and adapter for view list
        task_list_array =new ArrayList<>();
        array_adapter =new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1, task_list_array);

        insert_button = (Button) view.findViewById(R.id.insert_task_button);
        insert_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new_task_input.getText().toString().isEmpty())
                {
                    Toast.makeText(v.getContext(), "Task needs a name", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //display written task in list
                    list_string = new_task_input.getText().toString();
                    task_list_array.add(list_string);
                    task_list_view.setAdapter(array_adapter);
                    new_task_input.setText("");

                    String new_task = new_task_input.getText().toString();
                    // add the task to the database
                    ProjectDataAccess db = new ProjectDataAccess(getContext());     // call method in task object instead
                    db.insertTask(new_task, this_project.getProject_id());   // insert the task into the database

                    ArrayList<Task> tasks = db.getProjectTasks(this_project.getProject_id());
                    if(adapter == null)
                    {

                    }
                    new_task_input.setText("");
                }
            }
        });
    }

    /*
        Function: updateProject()
        Parameters: Project project
        Description: Sets all project aspects on page
        Returns: nothing
     */
    public void updateProject(Project project)
    {
        this_project = project;     // set this project to the one selected
        view_project.setText(this_project.getProject_name());
        view_start.setText(this_project.getStart_date().toString());    // show the start date
        view_end.setText(this_project.getEnd_date().toString());        // show the end date
    }
}
