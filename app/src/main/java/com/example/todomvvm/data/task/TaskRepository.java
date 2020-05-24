package com.example.todomvvm.data.task;

import android.content.Context;

import com.example.todomvvm.data.task.entity.TaskEntry;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TaskRepository {

    private static TaskRepository instance;
    TaskDao dao;

    private TaskRepository(Context context) {
        TaskDatabase database = TaskDatabase.getInstance(context);
        dao = database.taskDao();
    }

    public static TaskRepository getInstance(Context context) {
        if (instance == null) {
            instance = new TaskRepository(context);
        }
        return instance;
    }

    public LiveData<List<TaskEntry>> getTasks(String email) {
        return dao.loadAllTasks(email);
    }

    public LiveData<TaskEntry> getTaskById(int taskId) {
        return dao.loadTAskById(taskId);
    }

    public void updateTask(final TaskEntry task) {
        TaskDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.update(task);
            }
        });
    }

    public void deleteTask(final TaskEntry task) {
        TaskDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteTask(task);
            }
        });
    }

    public void insertTask(final TaskEntry task) {
        TaskDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertTask(task);
            }
        });
    }
}
