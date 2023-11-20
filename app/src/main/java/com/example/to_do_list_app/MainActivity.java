package com.example.to_do_list_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.to_do_list_app.Adapter.TaskAdapter;
import com.example.to_do_list_app.Interface.DialogCloseListener;
import com.example.to_do_list_app.Model.Task;
import com.example.to_do_list_app.Utils.DataBaseHandler;
import com.example.to_do_list_app.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    ActivityMainBinding binding;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new DataBaseHandler(this);
        db.OpenDB();

       // taskList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.tasksRecyclerView.setLayoutManager(layoutManager);
        taskAdapter = new TaskAdapter(MainActivity.this, db);
        binding.tasksRecyclerView.setAdapter(taskAdapter);

        ItemTouchHelper imtemtochhelper = new ItemTouchHelper(new RecyclerItemTouch(taskAdapter));
        imtemtochhelper.attachToRecyclerView(binding.tasksRecyclerView);

        taskList = db.getAllTask();
        Collections.reverse(taskList);
        taskAdapter.setData(taskList);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });


    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTask();
        Collections.reverse(taskList);
        taskAdapter.setData(taskList);
        taskAdapter.notifyDataSetChanged();
    }
}