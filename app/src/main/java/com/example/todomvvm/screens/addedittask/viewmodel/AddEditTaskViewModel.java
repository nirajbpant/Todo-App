package com.example.todomvvm.screens.addedittask.viewmodel;

import android.app.Application;

import com.example.todomvvm.data.task.TaskDatabase;
import com.example.todomvvm.data.task.TaskRepository;
import com.example.todomvvm.data.task.entity.TaskEntry;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AddEditTaskViewModel extends AndroidViewModel {

    TaskRepository taskRepository;
    LiveData<TaskEntry> task;

    AddEditTaskViewModel(Application application, int taskId) {
        super(application);
        TaskDatabase database = TaskDatabase.getInstance(application);
        taskRepository = new TaskRepository(database);
        if (taskId != -1)
            task = taskRepository.getTaskById(taskId);
    }


    public LiveData<TaskEntry> getTask() {
        return task;
    }

    public void insertTask(TaskEntry task) {
        taskRepository.insertTask(task);
    }

    public void updateTask(TaskEntry task) {
        taskRepository.insertTask(task);
    }


}
