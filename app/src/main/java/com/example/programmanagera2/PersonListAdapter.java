package com.example.programmanagera2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PersonListAdapter extends ArrayAdapter<PersonInList> {
    private ArrayList<PersonInList> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView first_name;
        TextView last_name;
        TextView skill_level;
    }

    public PersonListAdapter(ArrayList<PersonInList> data, Context context) {
        super(context, R.layout.add_person_list_item, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        PersonInList personItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.add_person_list_item, parent, false);
            viewHolder.first_name = (TextView) convertView.findViewById(R.id.textView_first_name);
            viewHolder.last_name = (TextView) convertView.findViewById(R.id.textView_last_name);
            viewHolder.skill_level = (TextView) convertView.findViewById(R.id.textView_skill_level);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
//        lastPosition = position;

        viewHolder.first_name.setText(personItem.getFirst_name());
        viewHolder.last_name.setText(personItem.getLast_name());
        viewHolder.skill_level.setText(personItem.getSkill_level());

        // Return the completed view to render on screen
        return convertView;
    }

//    @Override
//    public void onClick(View v) {
//
//        int position=(Integer) v.getTag();
//        Object object= getItem(position);
//        PersonInList dataModel = (PersonInList) object;
//
//        switch (v.getId())
//        {
//            case R.id.:
//                Snackbar.make(v, "clicked" +dataModel.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
//    }

//    public PersonListAdapter(Context context, ArrayList<PersonInList> person) {
//        super(context, 0, person);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Get the data item for this position
//        PersonInList person = getItem(position);
//        // Check if an existing view is being reused, otherwise inflate the view
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.add_person_list_item, parent, false);
//        }
//        // Lookup view for data population
//        TextView textView_first_name = (TextView) convertView.findViewById(R.id.textView_first_name);
//        TextView textView_last_name = (TextView) convertView.findViewById(R.id.textView_last_name);
//        TextView textView_skill_level = (TextView) convertView.findViewById(R.id.textView_skill_level);
//        // Populate the data into the template view using the data object
//        textView_first_name.setText(person.getFirst_name());
//        textView_last_name.setText(person.getLast_name());
//        textView_skill_level.setText(person.getSkill_level());
//        // Return the completed view to render on screen
//        return convertView;
//    }
}
