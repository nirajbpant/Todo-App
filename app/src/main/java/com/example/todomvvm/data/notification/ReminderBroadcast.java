package com.example.todomvvm.data.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.todomvvm.R;
import com.example.todomvvm.data.task.entity.TaskEntry;
import com.example.todomvvm.screens.addedittask.AddEditTaskActivity;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    public final static String ARGS_TASK_ID= "Task Name";
    public final static String ARGS_DESCRIPTION= "Description";
    public final static String CHANNEL_ID= "100";
    AddEditTaskActivity resultPendingIntent;
    @Override
    public void onReceive(Context context, Intent intent) {
        int id= intent.getIntExtra(ARGS_TASK_ID, -1);
        String description= intent.getStringExtra(ARGS_DESCRIPTION);
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                .setContentTitle("Task Reminder")
                .setContentText("Alert! You have a task pending to be completed : "+description)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Log.d("test", "this is test");
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(id, builder.build());
    }
}
