package ru.scapegoats.dayplanner.activity.main.reciever;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ru.scapegoats.dayplanner.R;
import ru.scapegoats.dayplanner.activity.main.MainActivity;

public class TimeNotificationReciever extends BroadcastReceiver {
    public final static String NOTIFICATION_CONTENT="notification_content";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("ABC","recieved");
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder=new Notification.Builder(context);
//Интент для активити, которую мы хотим запускать при нажатии на уведомление
        Intent intentTL = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context
                ,0
                ,intentTL
                ,PendingIntent.FLAG_CANCEL_CURRENT);

        //get notification content
        String content=intent.getExtras().getString(NOTIFICATION_CONTENT);
        String title=context.getResources().getString(R.string.notificationTitle);

        builder.setContentTitle(title).
                setContentText(content).
                setContentIntent(pendingIntent).
                setSmallIcon(R.drawable.ic_notifications_active);
        Notification notification=builder.getNotification();

        notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;
        nm.notify(1, notification);

        // Установим следующее напоминание.
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(context, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
