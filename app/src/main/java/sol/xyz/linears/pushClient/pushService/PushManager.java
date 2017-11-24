package sol.xyz.linears.pushClient.pushService;

import android.content.Context;
import android.content.Intent;

/**
 * Created by han on 2017-10-30.
 */

public class PushManager {
    private Context context;
    private static PushManager staticInstance;
    private String serverAddress;
    private int serverPort;
    private String token;

    private PushManager(Context context, String ip, int port){
        this.context = context;
        this.serverAddress = ip;
        this.serverPort = port;

        pullUpService();
    }

    public static void initialize(Context context, String ip, int port){
        if (staticInstance == null){
            staticInstance = new PushManager(context, ip, port);
        }
    }

    public static void initialize(Context context){
        if (staticInstance == null){
            staticInstance = new PushManager(context, "1", 2);
        }
    }


    private void pullUpService(){ // intent에 정보 담아 전달 후 서비스 시작
        PushNotification.getStaticInstance(context);
        Intent intent = new Intent(context, PushNotifyService.class); //"one.studio.sol.linears.xyz.PushService"
        intent.putExtra("server_addr", this.serverAddress);
        intent.putExtra("server_port",this.serverPort);
        intent.putExtra("package_name",context.getPackageName());
        try {
            context.startService(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
