package com.example.programmanagera2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateProjectFragment extends Fragment {
    private EditText dateStart;
    private EditText dateEnd;
    ListView listView;
    ArrayList<PersonInList> arrayOfPeople = new ArrayList<PersonInList>();
    private static PersonListAdapter adapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_project, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //make an array adapter to link the list to the data array
        adapter = new PersonListAdapter(arrayOfPeople, getActivity());
        //attach the adapter to the list
        listView = (ListView) view.findViewById(R.id.listView_people);
        listView.setAdapter(adapter);

        //get date text boxes
        dateStart = (EditText) view.findViewById(R.id.editText_Start_Date);
        dateEnd = (EditText) view.findViewById(R.id.editText_End_Date);

        //get date values
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month +1, day);

        final String[] fullName = {"", ""};

        //set up listener for add person button
        view.findViewById(R.id.button_add_person).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText tvFirstName = getView().findViewById(R.id.editText_person_first_name);
                EditText tvLastName = getView().findViewById(R.id.editText_person_last_name);
                SeekBar sbSkillLevel = getView().findViewById(R.id.seekBar_skill_level);
                fullName[0] = tvFirstName.getText().toString();
                fullName[1] = tvLastName.getText().toString();
                int skillLevel = sbSkillLevel.getProgress();
                boolean good = true;

                //check is first name is empty
                if(fullName[0].trim().isEmpty()){
                    Toast.makeText(getView().getContext(),"Provide a first name", Toast.LENGTH_SHORT).show();
                    good = false;
                }
                //check if last name is empty
                if(fullName[1].trim().isEmpty()){
                    Toast.makeText(getView().getContext(),"Provide a last name", Toast.LENGTH_SHORT).show();
                    good = false;
                }
                //if all conditions met add to list
                if(good){
                    PersonInList newPerson = new PersonInList(fullName[0], fullName[1], skillLevel);
                    adapter.add(newPerson);
                } else {
                    good = true;
                    tvFirstName.setText("");
                    tvLastName.setText("");
                    sbSkillLevel.setProgress(0);
                }
            }
        });

        //set up listener for done button
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
