package com.example.to_do_list_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.to_do_list_app.Adapter.TaskAdapter;
import com.example.to_do_list_app.Model.Task;
import com.example.to_do_list_app.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        taskList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.tasksRecyclerView.setLayoutManager(layoutManager);
        taskAdapter = new TaskAdapter(this);
        binding.tasksRecyclerView.setAdapter(taskAdapter);



        taskList.add(new Task(1,0,"This is a task, for example"));
        taskList.add(new Task(1,0,"This is a task, for example"));
        taskList.add(new Task(1,0,"This is a task, for example"));


        taskAdapter.setData(taskList);







    }
}