package com.example.todomvvm.screens.addedittask;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.todomvvm.R;
import com.example.todomvvm.data.notification.ReminderBroadcast;
import com.example.todomvvm.data.task.entity.TaskEntry;
import com.example.todomvvm.screens.addedittask.viewmodel.AddEditTaskViewModel;
import com.example.todomvvm.screens.addedittask.viewmodel.AddEditTaskViewModelFactory;
import com.example.todomvvm.screens.tasks.TaskListActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import static com.example.todomvvm.data.notification.ReminderBroadcast.ARGS_DESCRIPTION;
import static com.example.todomvvm.data.notification.ReminderBroadcast.ARGS_TASK_ID;
import static com.example.todomvvm.data.notification.ReminderBroadcast.CHANNEL_ID;

public class AddEditTaskActivity extends AppCompatActivity {

    // Extra for the task ID to be received in the intent
    public static final String EXTRA_TASK_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    // Constants for priority
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;
    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_TASK_ID = -1;
    // Constant for logging
    private static final String TAG = AddEditTaskActivity.class.getSimpleName();
    // Fields for views
    EditText mEditText;
    RadioGroup mRadioGroup;
    Button mButton;
    AddEditTaskViewModel viewModel;
    private int mTaskId = DEFAULT_TASK_ID;
    private CalendarView mCalendarView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);
        createNotificationChannel();
        initViews();


        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            mButton.setText(R.string.update_button);

            // populate the UI

            mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);
            AddEditTaskViewModelFactory factory = new AddEditTaskViewModelFactory(getApplication(), mTaskId);
            viewModel = ViewModelProviders.of(this, factory).get(AddEditTaskViewModel.class);
            Log.d("error", "error from above" + viewModel);

            viewModel.getTask().observe(this, new Observer<TaskEntry>() {
                @Override
                public void onChanged(TaskEntry taskEntry) {
                    viewModel.getTask().removeObserver(this);
                    populateUI(taskEntry);
                    viewModel.setDescription(taskEntry.getDescription());
                    viewModel.setExpiresAt(taskEntry.getExpiresAt());
                    viewModel.setPriority(taskEntry.getPriority());
                }
            });

        } else {
            AddEditTaskViewModelFactory factory = new AddEditTaskViewModelFactory(getApplication(), mTaskId);
            viewModel = ViewModelProviders.of(this, factory).get(AddEditTaskViewModel.class);
            Log.d("error", "error from above" + viewModel);
        }
        initializeListeners();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID, mTaskId);
        super.onSaveInstanceState(outState);
    }

    /**
     * initViews is called from onCreate to init the member variable views
     */
    private void initViews() {
        mEditText = findViewById(R.id.editTextTaskDescription);
        mRadioGroup = findViewById(R.id.radioGroup);
        mCalendarView = findViewById(R.id.calender);
        mButton = findViewById(R.id.saveButton);

    }

    private void initializeListeners() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String taskDescription = mEditText.getText().toString().trim();
                mButton.setEnabled(!taskDescription.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("error", "Error from below" + viewModel);
                viewModel.setDescription(editable.toString());

            }
        });
        getPriorityFromViews();
        mCalendarView.setMinDate(new Date().getTime());
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                viewModel.setExpiresAt(new Date(calendar.getTimeInMillis()));
            }
        });
    }

    /**
     * populateUI would be called to populate the UI when in update mode
     *
     * @param task the taskEntry to populate the UI
     */
    private void populateUI(TaskEntry task) {
        if (task == null) {
            return;
        }
        mEditText.setText(task.getDescription());
        setPriorityInViews(task.getPriority());
        mCalendarView.setDate(task.getExpiresAt().getTime());
    }

    /**
     * onSaveButtonClicked is called when the "save" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */
    public void onSaveButtonClicked() {
        // Not yet implemented
        boolean isCreate = mTaskId == DEFAULT_TASK_ID;
        TaskEntry taskEntry = viewModel.save(isCreate);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (alarmManager == null) {
            finish();
            return;
        }
        Intent alarmIntent = new Intent(AddEditTaskActivity.this, ReminderBroadcast.class);
        alarmIntent.putExtra(ARGS_TASK_ID, taskEntry.getId());
        alarmIntent.putExtra(ARGS_DESCRIPTION,taskEntry.getDescription());

        if (!isCreate) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(AddEditTaskActivity.this,
                    mTaskId, alarmIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            pendingIntent.cancel();
            alarmManager.cancel(pendingIntent);
        }
            PendingIntent pendingIntent = PendingIntent.getBroadcast(AddEditTaskActivity.this,
                    mTaskId, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, new Date().getTime() + 60 * 1000, pendingIntent);
            finish();

            //taskEntry.getExpiresAt().getTime() - 24 * 60 * 60 * 1000

    }

    public void getSpeechInput(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your device does not support Speech Input", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mEditText.setText(result.get(0));
                }
                break;

        }
    }

    /**
     * getPriority is called whenever the selected priority needs to be retrieved
     */
    public void getPriorityFromViews() {
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                int priority = 1;
                switch (checkedId) {
                    case R.id.radButton1:
                        priority = PRIORITY_HIGH;
                        break;
                    case R.id.radButton2:
                        priority = PRIORITY_MEDIUM;
                        break;
                    case R.id.radButton3:
                        priority = PRIORITY_LOW;
                }

                viewModel.setPriority(priority);
            }
        });
    }

    /**
     * setPriority is called when we receive a task from TaskListActivity
     *
     * @param priority the priority value
     */
    public void setPriorityInViews(int priority) {
        switch (priority) {
            case PRIORITY_HIGH:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton1);
                break;
            case PRIORITY_MEDIUM:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton2);
                break;
            case PRIORITY_LOW:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton3);
        }
    }
    public void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel";
            String description = "Notification for To-do Date reached.";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
