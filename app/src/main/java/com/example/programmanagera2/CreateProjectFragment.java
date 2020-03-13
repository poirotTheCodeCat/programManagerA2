package com.example.programmanagera2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Calendar;

public class CreateProjectFragment extends Fragment {
    private int year, month, day;
    private Calendar calendar;
    private EditText dateStart;
    private EditText dateEnd;

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
        //get date text boxes
        dateStart = (EditText) view.findViewById(R.id.editText_Start_Date);
        dateEnd = (EditText) view.findViewById(R.id.editText_End_Date);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

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
