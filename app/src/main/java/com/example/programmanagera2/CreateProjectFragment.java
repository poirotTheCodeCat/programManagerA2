package com.example.programmanagera2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateProjectFragment extends Fragment {
    private int year, month, day;
    private Calendar calendar;
    private EditText dateStart;
    private EditText dateEnd;
    private Button btnUser;
    private EditText userName;
    private EditText userEmail;
    private ListView userListview;
    private ArrayList<String> userList;
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
        userName=(EditText)view.findViewById(R.id.editText_Person_Name);
        userEmail=(EditText)view.findViewById(R.id.editText_Person_Email);
        btnUser=(Button)view.findViewById(R.id.button_Add_User);
        userListview=view.findViewById(R.id.listView_Users);
        userList=new ArrayList<>();
        arrayAdapter =new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,userList);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listString=userName.getText().toString()+" : "+userEmail.getText().toString();
                userList.add(listString);
                userListview.setAdapter(arrayAdapter);
                userName.setText("");
                userEmail.setText("");
            }
        });

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
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
