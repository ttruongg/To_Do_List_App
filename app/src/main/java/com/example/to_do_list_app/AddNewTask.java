package com.example.to_do_list_app;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.to_do_list_app.Interface.DialogCloseListener;
import com.example.to_do_list_app.Model.Task;
import com.example.to_do_list_app.Utils.DataBaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";
    private EditText newTask;
    private Button addTaskButton;
    private DataBaseHandler db;

    public static AddNewTask newTask(){
        return new AddNewTask();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_item, container, false);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newTask = getView().findViewById(R.id.newTask);
        addTaskButton = getView().findViewById(R.id.btnAddTask);

        db = new DataBaseHandler(getActivity());
        db.OpenDB();

        boolean isUpdate = false;
        final Bundle bundle = getArguments();
        if (bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            newTask.setText(task);
            if (task.length()>0){
                addTaskButton.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
            }
            newTask.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(charSequence.toString().equals("")){
                        addTaskButton.setEnabled(false);
                        addTaskButton.setTextColor(Color.GRAY);
                    }
                    else {
                        addTaskButton.setEnabled(true);
                        addTaskButton.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        boolean finalIsUpdate = isUpdate;
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = newTask.getText().toString();
                if(finalIsUpdate){
                    db.updateTask(bundle.getInt("id"), text);
                }
                else {
                    Task task = new Task();
                    task.setTask(text);
                    task.setStatus(0);
                }
                dismiss();
            }
        });

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener){
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
    }
}
