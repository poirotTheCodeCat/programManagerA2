package com.example.programmanagera2;

import android.os.Bundle;
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
        return v;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get date text boxes
        dateStart = (EditText) view.findViewById(R.id.editText_Start_Date);
        dateEnd = (EditText) view.findViewById(R.id.editText_End_Date);
        personFirstName =(EditText)view.findViewById(R.id.editText_person_first_name);
        personSecondName =(EditText)view.findViewById(R.id.editText_person_last_name);
        personSkillLevel=(SeekBar)view.findViewById(R.id.seekBar_skill_level);
        Button btnPerson = (Button) view.findViewById(R.id.button_add_person);
        personListview =view.findViewById(R.id.listView_people);
        personList =new ArrayList<>();
        arrayAdapter =new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1, personList);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month +1, day);
        btnPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listString= personSkillLevel.getProgress()+" "+personFirstName.getText().toString()+" "+ personSecondName.getText().toString();
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
                NavHostFragment.findNavController(CreateProjectFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    //fill date boxes with current date
    private void showDate(int year, int month, int day) {
        dateStart.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        dateEnd.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
}

//call insertProject()
//just pass 2 variables