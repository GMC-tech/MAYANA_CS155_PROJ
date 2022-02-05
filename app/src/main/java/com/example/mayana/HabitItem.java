package com.example.mayana;

public class HabitItem {
    private int HabitImageResource;
    private String TaskName;
    private String TaskProgress;

    public HabitItem(int imageResource, String Name, String Progress){

        HabitImageResource = imageResource;
        TaskName = Name;
        TaskProgress = Progress;
    }

    public int getHabitImageResource(){
        return HabitImageResource;
    }

    public String getTaskName(){
        return TaskName;
    }

    public String getTaskProgress() {
        return TaskProgress;
    }
}
