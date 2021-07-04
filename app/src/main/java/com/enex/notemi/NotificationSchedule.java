package com.enex.notemi;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationSchedule extends BroadcastReceiver {
    private String notificationText;
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent todoIntent = new Intent(context,Todo.class);
        todoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,todoIntent,0);


                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                context,"todo"
        ).setContentTitle("Todo Alert")
                .setSmallIcon(R.drawable.ic_baseline_volume_up_24).setContentText("You have a task scheduled for now.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setContentIntent(pendingIntent)
                        ;

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(111,notificationBuilder.build());
    }
}
