package il.ac.shenkar.ronesn;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TimeAlarm extends BroadcastReceiver {

    NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
       /* CharSequence from = "Nithin";
        CharSequence message = "Crazy About Android...";
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(), 0);
        Notification notif = new Notification(R.drawable.ic_stat_name,
                "Crazy About Android...", System.currentTimeMillis());
        notif.setLatestEventInfo(context, from, message, contentIntent);
        nm.notify(1, notif);*/



        Notification myNotification = new Notification(R.drawable.notif,
                "Shopping List",
                System.currentTimeMillis());
        Intent myIntent = new Intent(context, new_list.class);

        //Bundle b = intent.getExtras();
       // String str =b.getString("key");
        //String listName=((EditText) findViewById(R.id.list_name)).getText().toString();
       // b.putString("key", str);
        myIntent.putExtras(intent.getExtras());
        String notificationTitle =  "Shopping List says:";
        String notificationText = "זמן לקניות :)";
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
        myNotification.defaults |= Notification.DEFAULT_SOUND;
        myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        myNotification.setLatestEventInfo(context,
                notificationTitle,
                notificationText,
                pendingIntent);
         nm.notify(1, myNotification);
    }
}
