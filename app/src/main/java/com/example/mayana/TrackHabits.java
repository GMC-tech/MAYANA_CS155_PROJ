package com.example.mayana;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class TrackHabits extends AppCompatActivity {

    private RecyclerView HabitRecyclerView;
    private RecyclerView.Adapter HabitAdapter;
    private RecyclerView.LayoutManager HabitLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_habits);

        //sample items since there is no database yet
        ArrayList<HabitItem> habitList = new ArrayList<>();
        habitList.add(new HabitItem(R.drawable.ic_run_habit, "Running", "40%"));
        habitList.add(new HabitItem(R.drawable.ic_run_habit, "Running", "60%"));
        habitList.add(new HabitItem(R.drawable.ic_run_habit, "Running", "60%"));
        habitList.add(new HabitItem(R.drawable.ic_run_habit, "Running", "60%"));
        HabitRecyclerView = findViewById(R.id.recyclerView);
        HabitRecyclerView.setHasFixedSize(true);
        //HabitLayoutManager = new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        HabitAdapter = new HabitAdapter(habitList);

        //HabitRecyclerView.setLayoutManager(HabitLayoutManager);
        HabitRecyclerView.setLayoutManager(gridLayoutManager);
        HabitRecyclerView.setAdapter(HabitAdapter);
    }
}