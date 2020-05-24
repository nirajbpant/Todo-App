package com.example.todomvvm.screens.addedittask.viewmodel;

import android.app.Application;

import com.example.todomvvm.data.session.SessionRepository;
import com.example.todomvvm.data.task.TaskRepository;
import com.example.todomvvm.data.task.entity.TaskEntry;

import java.util.Date;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AddEditTaskViewModel extends AndroidViewModel {

    private final SessionRepository sessionRepository;
    private int taskId;
    private TaskRepository taskRepository;
    private LiveData<TaskEntry> task;
    private String description = "";
    private int priority = 1;
    private Date expiresAt = new Date();

    AddEditTaskViewModel(Application application, int taskId) {
        super(application);

        taskRepository = TaskRepository.getInstance(application);
        sessionRepository = SessionRepository.getInstance(application);
        this.taskId = taskId;
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


    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void save(boolean isCreate) {
        String email = sessionRepository.getEmail();
        if (isCreate) {
            insertTask(new TaskEntry(description, priority, email, expiresAt));
        } else {
            updateTask(new TaskEntry(taskId, description, priority, email, expiresAt));

        }
    }
}
