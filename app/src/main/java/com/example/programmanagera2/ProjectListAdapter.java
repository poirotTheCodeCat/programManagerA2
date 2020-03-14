    //FILENAME      : InviteDataAdapter.java
//PROJECT       : PROG3150 - Assignment 1
//PROGRAMMER    : Aaron Perry, Daniel Grew, John Stanley, Manthan Rami, Sasha Malesevic
//FIRST VERSION : 2020-02-08

//Android Working with Recycler View
//By Ravi Tamada
//Retrieved from https://www.androidhive.info/2016/01/android-working-with-recycler-view/ (step 8)
//Adapted for the purposes required in this program -> InviteData RecyclerView

package com.example.programmanagera2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//CLASS   : ProjectListAdapter
//PURPOSE : Creates the adapter used in binding the InviteData array in the AddFriendsActivity to
//          the recyclerFriendView RecyclerView. The data is being displayed as present in
//          recycler_view_item.xml.
public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ProjectViewHolder> {
    private List<Project> projectInfoList;

    public class ProjectViewHolder extends RecyclerView.ViewHolder{
        public TextView project_name, team, tasks, end_date;

        public ProjectViewHolder(View view){
            super(view);
            project_name = view.findViewById(R.id.project_name);
            team = view.findViewById(R.id.project_num_of_members);
            tasks = view.findViewById(R.id.project_num_of_tasks);
            end_date = view.findViewById(R.id.project_end_date);
        }
    }

    public ProjectListAdapter(List<Project> projectInfoList){
        this.projectInfoList = projectInfoList;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_project_list, parent, false);

        return new ProjectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position){
        Project project = projectInfoList.get(position);
        holder.project_name.setText(project.getProject_name());
        holder.team.setText(project.getTeam().size()+" Members");
        //count number of completed tasks
        int taskCompleteCount = 0;
        for (Task task : project.getTasks()) {
            if(task.getTask_complete() == 1){
                ++taskCompleteCount;
            }
        }
        //display completed tasks
        holder.tasks.setText(taskCompleteCount+" of "+project.getTasks().size()+" tasks complete");
        holder.end_date.setText(project.getEnd_date().toString());
    }

    @Override
    public int getItemCount(){
        return projectInfoList.size();
    }
}
