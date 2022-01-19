package com.example.mayana;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> {

    private ArrayList<HabitItem> HabitList;
    public static class HabitViewHolder extends  RecyclerView.ViewHolder{

    public ImageView ImageHabit;
    public TextView HabitName;
    public TextView HabitProgress;

        public HabitViewHolder(View itemView) {
            super(itemView);
            ImageHabit = itemView.findViewById(R.id.imageView);
            HabitName = itemView.findViewById(R.id.textView_Task_Name);
            HabitProgress = itemView.findViewById(R.id.textView_Progress);

        }
    }

    public HabitAdapter(ArrayList<HabitItem> habitList){
        HabitList =habitList;
    }

    @Override
    public HabitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_item, parent, false);
        HabitViewHolder hvh = new HabitViewHolder(v);
        return hvh;
    }

    @Override
    public void onBindViewHolder(HabitViewHolder holder, int position) {
        HabitItem currentHabit = HabitList.get(position);

        holder.ImageHabit.setImageResource(currentHabit.getHabitImageResource());
        holder.HabitName.setText(currentHabit.getTaskName());
        holder.HabitProgress.setText(currentHabit.getTaskProgress());
    }

    @Override
    public int getItemCount() {
        return HabitList.size();
    }
}
