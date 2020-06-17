package com.example.todomvvm.screens.tasks;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.todomvvm.R;
import com.example.todomvvm.data.notification.ReminderBroadcast;
import com.example.todomvvm.data.session.SessionRepository;
import com.example.todomvvm.data.task.entity.TaskEntry;
import com.example.todomvvm.screens.addedittask.AddEditTaskActivity;
import com.example.todomvvm.screens.login.viewmodel.LogoutViewModel;
import com.example.todomvvm.screens.splash.SplashActivity;
import com.example.todomvvm.screens.tasks.adapter.TaskAdapter;
import com.example.todomvvm.screens.tasks.viewmodel.DeleteAllTasksViewModel;
import com.example.todomvvm.screens.tasks.viewmodel.MainActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.net.URI;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class TaskListActivity extends AppCompatActivity implements TaskAdapter.ItemClickListener {

    // Constant for logging
    private static final String TAG = TaskListActivity.class.getSimpleName();
    MainActivityViewModel viewModel;
    // Member variables for the adapter and RecyclerView
    private RecyclerView mRecyclerView;
    private TaskAdapter mAdapter;
    LogoutViewModel logoutViewModel;
    DeleteAllTasksViewModel deleteAllTasksViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);


        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        logoutViewModel = ViewModelProviders.of(this).get(LogoutViewModel.class);
        deleteAllTasksViewModel=  ViewModelProviders.of(this).get(DeleteAllTasksViewModel.class);
        // Set the RecyclerView to  its corresponding view
        mRecyclerView = findViewById(R.id.recyclerViewTasks);

        // Set the layout for the RecyclerView to be a linear layout, which measures and
        // positions items within a RecyclerView into a linear list
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new TaskAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

        /*
         Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }


            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
                final int position = viewHolder.getAdapterPosition();
                final List<TaskEntry> todoList = mAdapter.getTasks();
                final TaskEntry deletedTask= todoList.get(position);
                viewModel.deleteTask(todoList.get(position));
                todoList.remove(position);
                mAdapter.setTasks(todoList);
                mAdapter.notifyDataSetChanged();
                Snackbar snackbar = Snackbar
                      .make(mRecyclerView, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("Undo", new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                            todoList.add(position,deletedTask);
                            mAdapter.setTasks(todoList);
                            mAdapter.notifyDataSetChanged();
                            viewModel.addTask(todoList.get(position));

                        }
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        }).attachToRecyclerView(mRecyclerView);


        /*
         Set the Floating Action Button (FAB) to its corresponding View.
         Attach an OnClickListener to it, so that when it's clicked, a new intent will be created
         to launch the AddTaskActivity.
         */
        FloatingActionButton fabButton = findViewById(R.id.fab);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new intent to start an AddTaskActivity
                Intent addTaskIntent = new Intent(TaskListActivity.this, AddEditTaskActivity.class);
                startActivity(addTaskIntent);
            }
        });

        viewModel.getTasks().observe(this, new Observer<List<TaskEntry>>() {
            @Override
            public void onChanged(List<TaskEntry> taskEntries) {
                mAdapter.setTasks(taskEntries);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.appmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                logoutViewModel.LogOutUser();
                Intent intent = new Intent(this, SplashActivity.class);
                startActivity(intent);
                return true;
            case R.id.deleteAllOption:
                handleOpenAlertDialogue();
                return true;

            case R.id.supportUS:
                Intent implicitIntent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/nirajbpant"));
                startActivity(implicitIntent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClickListener(int itemId) {
        // Launch AddTaskActivity adding the itemId as an extra in the intent
        Intent intent = new Intent(TaskListActivity.this, AddEditTaskActivity.class);
        intent.putExtra(AddEditTaskActivity.EXTRA_TASK_ID, itemId);
        startActivity(intent);
    }

    public void handleOpenAlertDialogue(){
        final AlertDialog alertDialog= new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Are you sure you want to delete all the tasks?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RecyclerView.ViewHolder viewHolder;
                        final List<TaskEntry> todoList = mAdapter.getTasks();
                        deleteAllTasksViewModel.deleteAllTasks();
                        todoList.clear();
                        mAdapter.setTasks(todoList);
                        mAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
