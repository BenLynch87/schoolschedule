
package com.lynch.schoolschedule.helper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;

import com.lynch.schoolschedule.R;
import com.lynch.schoolschedule.UI.MainActivity;

public class NotificationHelper {
    public static void createNotification(Context context, long id, String title, String text) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel channel = new NotificationChannel("default", "Reminders", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Assessment reminders");
        channel.enableLights(true);
        channel.setLightColor(Color.BLUE);
        manager.createNotificationChannel(channel);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        manager.notify((int) id, builder.build());
    }
}
