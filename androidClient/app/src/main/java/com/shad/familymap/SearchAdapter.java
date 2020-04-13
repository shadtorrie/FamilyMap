package com.shad.familymap;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;

import Data.ModelData;
import Models.EventModel;
import Models.Model;
import Models.PersonModel;

class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> implements SearchView.OnQueryTextListener{
    private ArrayList<Model> foundItems;
    private ItemClickListener clickListener;
    public SearchAdapter() {
        foundItems=new ArrayList<>();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        foundItems=ModelData.search(query);
        notifyDataSetChanged();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
    public interface ItemClickListener {
        void onClick(View view, int position);
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public LinearLayout mLinearLayout;

        public MyViewHolder(LinearLayout v) {
            super(v);
            mLinearLayout = v;
            mLinearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getPosition());
        }
    }
    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {
        String firstTextField="";
        String secondTextField="";
        int color=0;
        FontAwesomeIcons icon=null;
        if(foundItems.get(position) instanceof EventModel){
            EventModel currentEvent=(EventModel)foundItems.get(position);
            firstTextField= ModelData.getEventString(currentEvent.getID());
            secondTextField=ModelData.getPersonFullName(currentEvent.getPersonID());
            icon= FontAwesomeIcons.fa_map_marker;
            color= Color.HSVToColor(new float[]{ModelData.getIconColor(ModelData.getEvent(currentEvent.getID()).getEventType()),1.f,1.f});
        }
        else if(foundItems.get(position)instanceof PersonModel){
            PersonModel currentPerson=(PersonModel) foundItems.get(position);
            firstTextField= ModelData.getPersonFullName(currentPerson.getID());
            secondTextField="";
            if(currentPerson.getGender().equalsIgnoreCase("m")){
                icon=FontAwesomeIcons.fa_male;
                color=R.color.male;
            }
            else{
                icon=FontAwesomeIcons.fa_female;
                color=R.color.female;
            }
        }
        ImageView image =holder.mLinearLayout.findViewById(R.id.icon);
        TextView firstItemTextView = (TextView) holder.mLinearLayout
                .findViewById(R.id.firstItem);
        TextView secondItemTextView = (TextView) holder.mLinearLayout
                .findViewById(R.id.secondItem);
        firstItemTextView.setText(firstTextField);
        secondItemTextView.setText(secondTextField);
        Drawable iconDrawable = new IconDrawable(holder.mLinearLayout.getContext(),icon).sizeDp(30).color(color);
        image.setImageDrawable(iconDrawable);
    }

    @Override
    public int getItemCount() {
        return foundItems.size();
    }
    public Model getItem(int position){
        return foundItems.get(position);
    }
}
