package com.shad.familymap;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.HashMap;
import java.util.TreeMap;

import Data.ModelData;
import Models.PersonModel;

public class expandableListCustom extends BaseExpandableListAdapter {
    private Context context;
    private HashMap<String,String> people;
    private TreeMap<Integer,String> events;
    private PersonModel currentPerson;

    public expandableListCustom(Context context,PersonModel person) {
        this.context = context;
        currentPerson=person;
        fillPersonAndEventData(currentPerson);
        events=ModelData.getPersonsEvents(currentPerson.getID());
    }

    private void fillPersonAndEventData(PersonModel person) {
        people=ModelData.getRelatives(person);
    }

    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(groupPosition==0){
            return events!=null?events.size():null;
        }
        else{
           return people.size();
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        if(groupPosition==0){
            return "Events";
        }
        else{
            return "Family";
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if(groupPosition==0){
            int i=0;
            for(TreeMap.Entry<Integer,String> j:events.entrySet()){
                if(i==childPosition){
                    return j.getValue();
                }
                else{
                    ++i;
                }
            }
            return null;
        }
        else{
            int i=0;
            for(HashMap.Entry<String,String> j:people.entrySet()){
                if(i==childPosition){
                    return j.getValue();
                }
                else{
                    ++i;
                }
            }

        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childID=(String) getChild(groupPosition,childPosition);
        final String firstTextFeild;
        final String secondTextFeild;
        final FontAwesomeIcons icon;
        final int color;
        if(childID==null){
            return null;
        }
        if(groupPosition==0){
            firstTextFeild=ModelData.getEventString(childID);
            secondTextFeild=ModelData.getPersonFullName(currentPerson.getID());
            icon=FontAwesomeIcons.fa_map_marker;
            color=Color.HSVToColor(new float[]{ModelData.getIconColor(ModelData.getEvent(childID).getEventType()),1.f,1.f});
    }
        else{
            firstTextFeild= ModelData.getPersonFullName(childID);

            secondTextFeild=getPersonKey(childID);
            if(ModelData.getPerson(childID).getGender().equalsIgnoreCase("m")){
                icon=FontAwesomeIcons.fa_male;
                color=R.color.male;
            }
            else{
                icon=FontAwesomeIcons.fa_female;
                color=R.color.female;
            }
        }
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView firstItemTextView = (TextView) convertView
                .findViewById(R.id.firstItem);
        firstItemTextView.setText(firstTextFeild);
        TextView secondItemTextView = (TextView) convertView
                .findViewById(R.id.secondItem);
        secondItemTextView.setText(secondTextFeild);
        Drawable iconDrawable = new IconDrawable(convertView.getContext(),icon).sizeDp(30).color(color);
        ImageView iconImage = convertView.findViewById(R.id.icon);
        iconImage.setImageDrawable(iconDrawable);
        return convertView;
    }

    private String getPersonKey(String childID) {
        for(HashMap.Entry<String,String> j:people.entrySet()){
            if(j.getValue().equals(childID)){
                return j.getKey();
            }
        }
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
