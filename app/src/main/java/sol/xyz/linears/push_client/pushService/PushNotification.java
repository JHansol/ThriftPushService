package sol.xyz.linears.push_client.pushService;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import sol.xyz.linears.push_client.MainActivity;
import sol.xyz.linears.push_client.R;
import sol.xyz.linears.push_client.fragment.InfoFragment;
import sol.xyz.linears.push_client.fragment.dialog.model.Dialog;
import sol.xyz.linears.push_client.global.Global;
import sol.xyz.linears.push_client.thrift.androidStruct.MessageStruct;
import sol.xyz.linears.push_client.utils.HanLog;
import sol.xyz.linears.push_client.utils.JsonDialogHelper;

import static sol.xyz.linears.push_client.utils.HanLog.han_Log;

/**
 * Created by han on 2017-10-30.
 */

public class PushNotification {
    private Context appContext;
    private static PushNotification staticInstance;
    NotificationManager notificationManager;

    JsonDialogHelper helper;

    private PushNotification(Context context){
        appContext = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static PushNotification getStaticInstance(Context context){
        if (staticInstance == null){
            staticInstance = new PushNotification(context);
        }
        return staticInstance;
    }

    public void sendTextNotification(android.os.Message msg){
        MessageStruct data = (MessageStruct)msg.obj;
        helper = new JsonDialogHelper(data.getMessageBody());
        String bodyData = helper.JsonDataParsing();
        if(bodyData == null)
            return;

        han_Log("sendTextNotification()");
        Intent intent = new Intent(appContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(appContext, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(appContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(data.getTitle())
                .setContentText(bodyData)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        AddPushList(data);
        BadgeCountAdd();

        notificationManager.notify(0, notificationBuilder.build());
    }

    private void AddPushList(MessageStruct data){
        JsonDialogHelper helper = new JsonDialogHelper(data.getMessageBody());

        Dialog push = helper.JsonParsing();
        if(push == null)
            return;

        Global.pushAlarmList.add(push);
        InfoFragment.msg_handler();
    }

    private void BadgeCountAdd(){
        final String main = "sol.xyz.linears.push_client.MainActivity";
        Prefer.getInstance(appContext).BadgeCountAdd();

        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", Prefer.getInstance(appContext).getBadgeCount());
        intent.putExtra("badge_count_package_name", appContext.getPackageName());
        intent.putExtra("badge_count_class_name", main);
        appContext.sendBroadcast(intent);
    }

}
