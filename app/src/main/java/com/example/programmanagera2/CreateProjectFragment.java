/*
File: HomeFragment
Programmers: John Stanly, Aaron Perry, Sasha Malesevic, Manthan Rami, Daniel Grew
Date Last Modified: 2020-03-12
Description: This class holds the fragment for creating a new project. It will take all input data
and save it in the database for access on the home fragment. There is a listView that simply displays
the names of all people added to the project.
 */

package com.example.programmanagera2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateProjectFragment extends Fragment {
    private EditText dateStart;
    private EditText dateEnd;
    private EditText projectName;
    private EditText personFirstName;
    private EditText personSecondName;
    private SeekBar personSkillLevel;
    private ListView personListview;
    private ArrayList<String> personList;
    private  ArrayAdapter<String> arrayAdapter;
    private String listString;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_create_project, container, false);
        Log.v("info", "Create project fragment created");
        return v;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get access to widgets
        projectName = (EditText) view.findViewById(R.id.editText_Project_Name);
        dateStart = (EditText) view.findViewById(R.id.editText_Start_Date);
        dateEnd = (EditText) view.findViewById(R.id.editText_End_Date);
        personFirstName =(EditText)view.findViewById(R.id.editText_person_first_name);
        personSecondName =(EditText)view.findViewById(R.id.editText_person_last_name);
        personSkillLevel=(SeekBar)view.findViewById(R.id.seekBar_skill_level);
        Button btnPerson = (Button) view.findViewById(R.id.button_add_person);
        personListview =view.findViewById(R.id.listView_people);

        //initialize needed array and adapter for view list
        personList =new ArrayList<>();
        arrayAdapter =new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1, personList);

        //get date text boxes
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month +1, day);
        btnPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listString="Skill Level: "+personSkillLevel.getProgress()+" Full Name: "+personFirstName.getText().toString()+" "+ personSecondName.getText().toString();
                personList.add(listString);
                personListview.setAdapter(arrayAdapter);
                personFirstName.setText("");
                personSecondName.setText("");
                personSkillLevel.setProgress(0);
            }
        });

        view.findViewById(R.id.button_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Person> personArrayList = new ArrayList<Person>();
                ArrayList<Task> taskArrayList = new ArrayList<Task>();

                //convert
                for (String s : personList) {
                    //split into [0] Skill, [1] First Name, [2] Last Name
                    String[] splitStr = s.split("\\s+");
                    int skillLevel = Integer.parseInt(splitStr[2]);
                    Person newPerson = new Person(0,splitStr[5], splitStr[6], skillLevel);
                    personArrayList.add(newPerson);
                }
                //create new project
                Project newProject = new Project(0, projectName.getText().toString(), personArrayList,
                        taskArrayList, dateStart.getText().toString(), dateEnd.getText().toString());
                //add new project to database
                Project.insertProject(view.getContext(), newProject);

                //log new project created
                Log.v("info", "User has created a new project: "+projectName);

                //go home
                NavHostFragment.findNavController(CreateProjectFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    /*
    Function: showDate()
    Parameters: insertProject(int year, int month, int day)
    Description: Called to input the current date into the date edittext box
    Returns: Nothing
     */
    private void showDate(int year, int month, int day) {
        dateStart.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        dateEnd.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
}