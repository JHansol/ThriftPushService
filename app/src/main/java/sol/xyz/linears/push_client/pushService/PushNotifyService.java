package sol.xyz.linears.push_client.pushService;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sol.xyz.linears.push_client.httpLib.HttpHelper;
import sol.xyz.linears.push_client.receiver.NetworkSensor;
import sol.xyz.linears.push_client.thrift.ThriftThreadPoolServer;
import sol.xyz.linears.push_client.thrift.androidStruct.MessageStruct;

import static sol.xyz.linears.push_client.utils.HanLog.han_Log;

/**
 * Created by han on 2017-10-30.
 */

public class PushNotifyService extends Service {

    private static Context main_context;
    public static boolean daemonAlive;

    private Intent initIntent;
    private String initUUID;

    private static Handler handler1 = new Handler() {
        public void handleMessage(android.os.Message msg) {
            han_Log("handler1()");
            PushNotification.getStaticInstance(main_context).sendTextNotification(msg);
        }
    };

    public static void msg_handler(MessageStruct msg1) {
        android.os.Message msg = handler1.obtainMessage();
        msg.obj = msg1;
        handler1.sendMessage(msg);
    }

    public static void network_Handler() {
        HttpHelper.getInstance().SetMyIp();
        HttpHelper.getInstance().PostMyIP();
    }

    @Override
    public void onCreate(){
        super.onCreate();
        FakeService.startForeground(this);
        Intent localIntent = new Intent(this, FakeService.class);
        startService(localIntent);
        // Proc #56: prcp  F/S/SF trm: 0 23298:sol.linears.xyz.push_client:PushService/u0a480 (fg-service)
        // 카톡, FCM, 비트윈 등 다 이방법을 안쓴다 ㅜ.ㅜ
        // 멘토님께 여쭈어보자

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        NetworkSensor receiver = new NetworkSensor();
        registerReceiver(receiver, filter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static final ExecutorService es = Executors.newSingleThreadExecutor();
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        han_Log("onStartCommand()");
        main_context = getApplicationContext();
        initIntent = intent;
        if (!checkDaemon()) {
            String uuid = UUID.randomUUID().toString();
            initUUID = uuid;
            Log.e("UUID", uuid);
            Prefer.getInstance(getApplicationContext()).setUUID(uuid);

            String ip = initIntent.getStringExtra("server_addr");
            int port = initIntent.getIntExtra("server_port", 10000);

            es.submit(new ThriftThreadPoolServer());

            //HttpHelper.getInstance().PostMyIP();

            setPid();
        }

        return START_REDELIVER_INTENT;
    }

    private void setPid(){
        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> pidsTask = activityManager.getRunningAppProcesses();

        Prefer.getInstance(getApplicationContext()).setDaemonPid(String.valueOf(pidsTask.get(pidsTask.size()-1).pid));
        han_Log(Prefer.getInstance(getApplicationContext()).getDaemonPid());

        //heroltektt:/ $ ps | grep 19768
        //u0_a480   19768 3295  1827028 77944 SyS_epoll_ 0000000000 S sol.linears.xyz.push_client:PushService
    }

    public boolean checkDaemon(){
        int pid = Prefer.getInstance(getApplicationContext()).getDaemonPid();
        Log.e("Saved pid:",""+pid);
        if (pid < 0){
            daemonAlive = false;
            return false;
        }
        try {
            char[] rawBuffer = new char[1024];
            FileReader fileReader = new FileReader("/proc/"+pid+"/cmdline");
            fileReader.read(rawBuffer);
            String buffer = String.copyValueOf(rawBuffer);
            buffer = buffer.substring(0,buffer.indexOf('\0'));
            if (buffer.endsWith(":PushService")) {
                han_Log("alive!!");
                daemonAlive = true;
                return true;
            }
        } catch (FileNotFoundException e) {
            han_Log("FileNotFoundException");
        } catch (EOFException e) {
            han_Log("EOFException");
        } catch (IOException e) {
            han_Log("IOException");
        }
        daemonAlive = false;
        return false;
    }

}
