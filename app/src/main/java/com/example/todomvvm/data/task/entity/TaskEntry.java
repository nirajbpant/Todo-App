package com.example.todomvvm.data.task.entity;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "task")
public class TaskEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String description;
    private int priority;
    @ColumnInfo(name = "updated_at")
    private Date updatedAt;
    @ColumnInfo(name = "expires_at")
    private Date expiresAt;
    @ColumnInfo(name = "user_email")
    private String userEmail;

    @Ignore
    public TaskEntry(String description, int priority, String userEmail, Date expiresAt) {
        this.description = description;
        this.priority = priority;
        this.userEmail = userEmail;
        this.expiresAt = expiresAt;
        this.updatedAt = new Date();
    }

    public TaskEntry(int id, String description, int priority, String userEmail, Date expiresAt) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.userEmail = userEmail;
        this.expiresAt = expiresAt;
        this.updatedAt = new Date();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
