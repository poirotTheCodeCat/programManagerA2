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
import android.widget.ImageView;
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
        public TextView name, num_of_members, num_of_tasks, done;

        public ProjectViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.name);
            num_of_members = view.findViewById(R.id.num_of_members);
            num_of_tasks = view.findViewById(R.id.num_of_tasks);
            done = view.findViewById(R.id.done);
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

        //ProjectDataAccess pda = new ProjectDataAccess(null);

//        Project project = projectInfoList.get(position);
//        holder.name.setText(project.getName());
//        holder.num_of_members.setText(project.getEmail());
//        holder.num_of_tasks.setText(project.getEmail());
//        holder.done.setText(project.getEmail());

        

    }

    @Override
    public int getItemCount(){
        return projectInfoList.size();
    }
}
