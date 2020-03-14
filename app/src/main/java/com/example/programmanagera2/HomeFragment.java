package com.example.programmanagera2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList<Project> projectDataList = new ArrayList<Project>();
    private RecyclerView recyclerView;
    private ProjectListAdapter projectListAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = v.findViewById(R.id.recyclerFriendView);
        projectListAdapter = new ProjectListAdapter(projectDataList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(projectListAdapter);

        return v;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get view and initiate the floating + button
//        View v = inflater.inflate(R.layout.fragment_home, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fabAddProject);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//                Snackbar.make(view, "Project added", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
        //see if DB has an projects

        //if not then display empty list text notification

        //tasks.getAllTasks(content, projectID)
    }
}
