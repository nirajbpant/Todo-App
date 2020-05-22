package com.example.todomvvm.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TaskDao {

    @Query("select * from task order by priority")
    LiveData<List<TaskEntry>> loadAllTasks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(TaskEntry task);

    @Update
    void update(TaskEntry task);

    @Delete
    void deleteTask(TaskEntry task);

    @Query("Select * from task where id =:taskId")
    LiveData<TaskEntry> loadTAskById(int taskId);

}
