package com.example.todomvvm.data.task;

import com.example.todomvvm.data.task.entity.TaskEntry;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TaskRepository {

    TaskDao dao;

    public TaskRepository(TaskDatabase taskDatabase) {
        dao = taskDatabase.taskDao();
    }

    public LiveData<List<TaskEntry>> getTasks() {
        return dao.loadAllTasks();
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
