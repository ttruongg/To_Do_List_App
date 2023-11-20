package com.example.to_do_list_app.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.to_do_list_app.Model.Task;

import java.net.IDN;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ToDoListDataBase";
    private static final String TABLE_TASK = "ToDoList";
    private static final String COLUMN_NAME_ID = "id";
    private static final String COLUMN_NAME_TASK = "task";
    private static final String COLUMN_NAME_STATUS = "status";
    private static final String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASK + "(" + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME_TASK + " TEXT, "
            + COLUMN_NAME_STATUS + " INTEGER)";

    private SQLiteDatabase db;

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TASK_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);

        onCreate(db);

    }

    public void OpenDB(){
        db = this.getWritableDatabase();
    }

    public void insertTask(Task task){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_TASK, task.getTask());
        cv.put(COLUMN_NAME_STATUS, 0);
        db.insert(TABLE_TASK, null, cv);

    }

    public List<Task> getAllTask(){
        List<Task> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(TABLE_TASK, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        Task task = new Task();
                        task.setId(cur.getInt(cur.getColumnIndexOrThrow(COLUMN_NAME_ID)));
                        task.setTask(cur.getString(cur.getColumnIndexOrThrow(COLUMN_NAME_TASK)));
                        task.setStatus(cur.getInt(cur.getColumnIndexOrThrow(COLUMN_NAME_STATUS)));
                        taskList.add(task);
                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return taskList;
    }

    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_STATUS, status);
        db.update(TABLE_TASK, cv, COLUMN_NAME_ID + "=?", new String[]{String.valueOf(id)});

    }

    public void updateTask(int id, String task){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_TASK, task);
        db.update(TABLE_TASK, cv, COLUMN_NAME_ID + "=?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(TABLE_TASK, COLUMN_NAME_ID + "=?", new String[] {String.valueOf(id)});
    }
}
