package sol.xyz.linears.pushClient.pushService;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import sol.xyz.linears.pushClient.R;

/**
 * Created by han on 2017-10-30.
 */

public class FakeService extends Service { // 불멸의 프로세스ㅋㅋ
    @Override
    public void onCreate(){
        super.onCreate();
        startForeground(this);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void startForeground(Service service) {
        if (service != null) {
            try {
                Notification notification = getNotification(service);
                if (notification != null) {
                    service.startForeground(-1111, notification);
                }
            } catch (Exception e) {
            }
        }
    }

    public static Notification getNotification(Context paramContext){
        int smallIcon = R.mipmap.ic_launcher;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            smallIcon = R.mipmap.ic_launcher;
        }
        Notification notification =  new NotificationCompat.Builder(paramContext)
                .setSmallIcon(smallIcon)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setAutoCancel(true)
                .setWhen(0)
                .setTicker("").build();
        notification.flags = 16;
        return  notification;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_REDELIVER_INTENT;
    }
}
