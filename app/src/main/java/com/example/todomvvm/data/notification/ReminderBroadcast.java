package com.example.todomvvm.data.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.todomvvm.R;
import com.example.todomvvm.data.task.entity.TaskEntry;
import com.example.todomvvm.screens.addedittask.AddEditTaskActivity;
import com.example.todomvvm.screens.tasks.TaskListActivity;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    public final static String ARGS_TASK_ID= "Task Name";
    public final static String ARGS_DESCRIPTION= "Description";
    public final static String CHANNEL_ID= "100";

    @Override
    public void onReceive(Context context, Intent intent) {
        int id= intent.getIntExtra(ARGS_TASK_ID, -1);
        String description= intent.getStringExtra(ARGS_DESCRIPTION);
        Intent notifyIntent = new Intent(context, TaskListActivity.class);
        // Set the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Create the PendingIntent
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder= new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                .setContentTitle("Task Reminder")
                .setContentText("Alert! You have a task pending to be completed : "+description)
                .setContentIntent(notifyPendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(id, builder.build());
    }
}
