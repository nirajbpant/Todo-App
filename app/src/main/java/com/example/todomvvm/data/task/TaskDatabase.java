package com.example.todomvvm.data.task;

import android.content.Context;
import android.util.Log;

import com.example.todomvvm.data.task.converter.DateConverter;
import com.example.todomvvm.data.task.entity.TaskEntry;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {TaskEntry.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class TaskDatabase extends RoomDatabase {

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(1);
    private static final String LOG_TAG = TaskDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static String DATABASE_NAME = "todolist";
    private static TaskDatabase sInstance;

    public static TaskDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating a new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        TaskDatabase.class, TaskDatabase.DATABASE_NAME)
                        //.allowMainThreadQueries()
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract TaskDao taskDao();


}
