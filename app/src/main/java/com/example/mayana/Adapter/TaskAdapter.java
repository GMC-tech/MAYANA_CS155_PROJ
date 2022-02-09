package com.example.mayana.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mayana.Model.TaskModel;
import com.example.mayana.R;
import com.example.mayana.Task_AddNew;
import com.example.mayana.Tasks_Fragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private List<TaskModel> todoList;
    private Tasks_Fragment tFrag;
    private FirebaseFirestore firestore;

    public TaskAdapter(Tasks_Fragment taskFrag , List<TaskModel> todoList){
        this.todoList = todoList;
        tFrag = taskFrag;
    }

   @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(tFrag.getContext()).inflate(R.layout.task_inputlayout , parent , false);
        firestore = FirebaseFirestore.getInstance();

        return new MyViewHolder(view);
    }

    public void deleteTask(int position){
        TaskModel toDoModel = todoList.get(position);
        firestore.collection("task").document(toDoModel.TaskId).delete();
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public Context getContext(){
        return tFrag.getContext();
    }
    public void editTask(int position){
        TaskModel toDoModel = todoList.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("task" , toDoModel.getTask());
        bundle.putString("due" , toDoModel.getDue());
        bundle.putString("id" , toDoModel.TaskId);

        Task_AddNew addNewTask = new Task_AddNew();
        addNewTask.setArguments(bundle);
        addNewTask.show(tFrag.getParentFragmentManager() , addNewTask.getTag());
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TaskModel toDoModel = todoList.get(position);
        holder.mCheckBox.setText(toDoModel.getTask());

        holder.mDueDateTv.setText("Due On " + toDoModel.getDue());

        holder.mCheckBox.setChecked(toBoolean(toDoModel.getStatus()));

        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    firestore.collection("task").document(toDoModel.TaskId).update("status" , 1);
                }else{
                    firestore.collection("task").document(toDoModel.TaskId).update("status" , 0);
                }
            }
        });

    }

    private boolean toBoolean(int status){
        return status != 0;
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mDueDateTv;
        CheckBox mCheckBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mDueDateTv = itemView.findViewById(R.id.due_date);
            mCheckBox = itemView.findViewById(R.id.taskbox);

        }
    }
}
