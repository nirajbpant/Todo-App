package com.example.todomvvm.screens.tasks.viewmodel;

import android.app.Application;

import com.example.todomvvm.data.session.SessionRepository;
import com.example.todomvvm.data.task.TaskRepository;
import com.example.todomvvm.data.task.entity.TaskEntry;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainActivityViewModel extends AndroidViewModel {

    TaskRepository taskRepository;

    private LiveData<List<TaskEntry>> tasks;


    public MainActivityViewModel(Application application) {
        super(application);
        taskRepository = TaskRepository.getInstance(application);
        SessionRepository sessionRepository = SessionRepository.getInstance(application);

        tasks = taskRepository.getTasks(sessionRepository.getEmail());
    }

    public LiveData<List<TaskEntry>> getTasks() {
        return tasks;
    }

    public void deleteTask(TaskEntry task) {
        taskRepository.deleteTask(task);
    }

}
